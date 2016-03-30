package com.ramazan.KpssZede.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ramazan.KpssZede.Activity.MainActivity;
import com.ramazan.KpssZede.Adapter.SoruAdapter;
import com.ramazan.KpssZede.AppConfig;
import com.ramazan.KpssZede.Data;
import com.ramazan.KpssZede.Model.CografyaModel;
import com.ramazan.KpssZede.Model.GeometriModel;
import com.ramazan.KpssZede.Model.MatematikModel;
import com.ramazan.KpssZede.Model.Mesages;
import com.ramazan.KpssZede.Model.SoruModel;
import com.ramazan.KpssZede.Model.TarihModel;
import com.ramazan.KpssZede.Model.TurkceModel;
import com.ramazan.KpssZede.Model.VatandaslıkModel;
import com.ramazan.KpssZede.R;
import com.ramazan.KpssZede.SessionManager;

import java.util.List;

/**
 * Created by ramazan on 20.02.2016.
 */
public class GenelKulturFragment extends Fragment {

    Context _c;
    private SessionManager session;
    Data servis;
    ListView listViewSoru;
    public static int loginFlag=0;
    Button btnTarih;
    Button btnCografya;
    Button btnVatandaslik;
    int tur_id=0;
    int dbSayac=0;
    String[] dizi;
    ProgressDialog pDialog;

    public GenelKulturFragment(Context c) {
        this._c = c;
        servis = new Data(_c);
        session = new SessionManager(_c);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (!session.isLoggedIn())//login deilse logout et
        {
            ((MainActivity)getActivity()).fragment(6);

        }
        final View rootView = inflater.inflate(R.layout.genel_kultur, container, false);
        listViewSoru = (ListView) rootView.findViewById(R.id.listViewchat);
        btnTarih = (Button) rootView.findViewById(R.id.btn_Tarih);
        btnCografya = (Button) rootView.findViewById(R.id.btn_cografya);
        btnVatandaslik = (Button) rootView.findViewById(R.id.btn_vatandaslik);

            if (loginFlag==1){
                ((MainActivity)getActivity()).drawerYenile();
            }
        Button btnLoadMore = new Button(getActivity());
        btnLoadMore.setText("Daha Fazla");
        listViewSoru.addFooterView(btnLoadMore);

        listViewSoru.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                Mesages model = (Mesages) parent.getItemAtPosition(position);
                CevapFragment cevapFragment = new CevapFragment(_c, model.remoteID);
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, cevapFragment).commit();
            }
        });
    try {
        dizi = servis.dosyadoku();
        if (dizi != null) {
            String k_id = dizi[0];
            tur_id=4;
            new GetGuncelSoruTask().execute(k_id);
        }
        else {
            session.setLogin(false);
            ((MainActivity)getActivity()).fragment(6);        }
    }
    catch(Exception e) {e.printStackTrace();}

        btnTarih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_Tarih();
                tur_id=4;
                Data.isSavedb=1;
                String k_id=dizi[0];
                new GetGuncelSoruTask().execute(k_id);
            }
        });
        btnVatandaslik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_Vatandaslik();
                tur_id=6;
                Data.isSavedb=1;
                String k_id=dizi[0];
                new GetGuncelSoruTask().execute(k_id);

            }
        });
        btnCografya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_cografya();
                tur_id=5;
                Data.isSavedb=1;
                String k_id=dizi[0];
                new GetGuncelSoruTask().execute(k_id);

            }
        });
        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Data.isSavedb=0;
                String k_id=dizi[0];
                dbSayac=dbSayac+25;
                new GetGuncelSoruTask().execute(k_id);
            }
        });

    return rootView;
}

    public  class GetGuncelSoruTask extends AsyncTask<String,String,String> {
        List<SoruModel> soruModelList=null;
        @Override
        protected void onPreExecute() {
            // Showing progress dialog before sending http request
            pDialog = new ProgressDialog(_c);
            pDialog.setMessage("Lütfen Bekleyiniz..");
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... params) {
            try {
                if (((MainActivity)getActivity()).isConnected()){
                    String strReq =servis.HttpPostGetGuncelSoru(AppConfig.URL_getGuncelSoru, String.valueOf(tur_id), params[0],
                            String.valueOf(dbSayac));
                    soruModelList=servis.SoruListe(strReq);
                }
                else{
                    soruModelList=getdbSoru(tur_id);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (soruModelList!=null){
                int currentPosition = listViewSoru.getFirstVisiblePosition();
                listViewSoru.setAdapter(new SoruAdapter(soruModelList, _c));
                listViewSoru.setSelectionFromTop(currentPosition + 1, 0);
                listViewSoru.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
                listViewSoru.setStackFromBottom(true);
            }
            else {
                Toast.makeText(_c, "Veri alınamadı..", Toast.LENGTH_LONG).show();
            }
            pDialog.dismiss();

        }
    }

    private List<SoruModel> getdbSoru(int tur){
        List<TarihModel> TarihSoru=null;
        List<VatandaslıkModel> VatandaslikSoru=null;
        List<CografyaModel> Cografyasoru=null;
        List<SoruModel> sorular=null;
        switch (tur){
            case 4:
                TarihSoru=new TarihModel().getAll();
                for (int i=0; i<TarihSoru.size(); i++){
                    SoruModel model=new SoruModel();
                    model.setSid(TarihSoru.get(i).sid);
                    model.setT_id(TarihSoru.get(i).t_id);
                    model.setK_id(TarihSoru.get(i).k_id);
                    model.setS_icerik(TarihSoru.get(i).s_icerik);
                    model.setK_resim(TarihSoru.get(i).k_resim);
                    model.setK_adi(TarihSoru.get(i).k_adi);
                    model.setTime(TarihSoru.get(i).time);
                    sorular.add(model);
                }break;
            case 5:  Cografyasoru=new CografyaModel().getAll();
                for (int i=0; i<Cografyasoru.size(); i++){
                    SoruModel model=new SoruModel();
                    model.setSid(Cografyasoru.get(i).sid);
                    model.setT_id(Cografyasoru.get(i).t_id);
                    model.setK_id(Cografyasoru.get(i).k_id);
                    model.setS_icerik(Cografyasoru.get(i).s_icerik);
                    model.setK_resim(Cografyasoru.get(i).k_resim);
                    model.setK_adi(Cografyasoru.get(i).k_adi);
                    model.setTime(Cografyasoru.get(i).time);
                    sorular.add(model);
                }break;
            case 6: VatandaslikSoru=new VatandaslıkModel().getAll();
                for (int i=0; i<VatandaslikSoru.size(); i++){
                    SoruModel model=new SoruModel();
                    model.setSid(VatandaslikSoru.get(i).sid);
                    model.setT_id(VatandaslikSoru.get(i).t_id);
                    model.setK_id(VatandaslikSoru.get(i).k_id);
                    model.setS_icerik(VatandaslikSoru.get(i).s_icerik);
                    model.setK_resim(VatandaslikSoru.get(i).k_resim);
                    model.setK_adi(VatandaslikSoru.get(i).k_adi);
                    model.setTime(VatandaslikSoru.get(i).time);
                    sorular.add(model);
                }break;
        }
        return sorular;
    }

    private void btn_Tarih(){
        btnTarih.setTextColor(Color.parseColor("#fbf2fc"));
        btnCografya.setTextColor(Color.parseColor("#000000"));
        btnVatandaslik.setTextColor(Color.parseColor("#000000"));
    }
    private void btn_Vatandaslik(){
        btnTarih.setTextColor(Color.parseColor("#000000"));
        btnCografya.setTextColor(Color.parseColor("#000000"));
        btnVatandaslik.setTextColor(Color.parseColor("#fbf2fc"));
    }
    private void btn_cografya(){
        btnTarih.setTextColor(Color.parseColor("#000000"));
        btnCografya.setTextColor(Color.parseColor("#fbf2fc"));
        btnVatandaslik.setTextColor(Color.parseColor("#000000"));
    }


}
