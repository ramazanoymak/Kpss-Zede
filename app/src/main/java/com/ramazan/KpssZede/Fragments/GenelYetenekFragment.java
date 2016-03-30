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
import com.ramazan.KpssZede.Model.GeometriModel;
import com.ramazan.KpssZede.Model.MatematikModel;
import com.ramazan.KpssZede.Model.Mesages;
import com.ramazan.KpssZede.Model.SoruModel;
import com.ramazan.KpssZede.Model.TurkceModel;
import com.ramazan.KpssZede.R;
import com.ramazan.KpssZede.SessionManager;

import java.util.List;

/**
 * Created by ramazan on 15.03.2016.
 */
public class GenelYetenekFragment extends Fragment {
    Context _c;
    private SessionManager session;
    Data servis;
    Button btnTurkce;
    Button btnMatematik;
    Button btnGeometri;
    ListView listViewSoru;
    public static int loginFlag=0;
    int tur_id=0;
    int dbSayac=0;
    String[] dizi;
    ProgressDialog pDialog;
   public GenelYetenekFragment(Context c){
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
        final View rootView = inflater.inflate(R.layout.genel_yetenek, container, false);
        listViewSoru = (ListView) rootView.findViewById(R.id.listViewchat);
        btnTurkce = (Button) rootView.findViewById(R.id.btn_Turkce);
        btnMatematik = (Button) rootView.findViewById(R.id.btn_Matematik);
        btnGeometri = (Button) rootView.findViewById(R.id.btn_geometri);
        Button btnLoadMore = new Button(getActivity());
        btnLoadMore.setText("Daha Fazla");
        listViewSoru.addFooterView(btnLoadMore);

        if (loginFlag==1){
            ((MainActivity)getActivity()).drawerYenile();
        }

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
                tur_id=1;
                new GetGuncelSoruTask().execute(k_id);
            }
            else {
                session.setLogin(false);
                ((MainActivity)getActivity()).fragment(6);        }
        }
        catch(Exception e) {e.printStackTrace();}


        btnTurkce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_Turkce();
                Data.isSavedb=1;
                String k_id=dizi[0];
                tur_id=1;
                new GetGuncelSoruTask().execute(k_id);

            }
        });
        btnMatematik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_matematik();
                Data.isSavedb=1;
                String k_id=dizi[0];
                tur_id=2;
                new GetGuncelSoruTask().execute(k_id);
            }
        });
        btnGeometri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            btn_geometri();
                Data.isSavedb=1;
                String k_id=dizi[0];
                tur_id=3;
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
                Toast.makeText(_c, "Veri alınamadı..", Toast.LENGTH_LONG).show();}
            pDialog.dismiss();

        }
    }
    private List<SoruModel> getdbSoru(int tur){
    List<TurkceModel> TurkceSoru=null;
    List<MatematikModel> matematikSoru=null;
    List<GeometriModel> geometrisoru=null;
    List<SoruModel> sorular=null;
    switch (tur){
        case 1:
            TurkceSoru=new TurkceModel().getAll();
            for (int i=0; i<TurkceSoru.size(); i++){
                SoruModel model=new SoruModel();
                model.setSid(TurkceSoru.get(i).sid);
                model.setT_id(TurkceSoru.get(i).t_id);
                model.setK_id(TurkceSoru.get(i).k_id);
                model.setS_icerik(TurkceSoru.get(i).s_icerik);
                model.setK_resim(TurkceSoru.get(i).k_resim);
                model.setK_adi(TurkceSoru.get(i).k_adi);
                model.setTime(TurkceSoru.get(i).time);
                sorular.add(model);
            }break;
        case 2:  matematikSoru=new MatematikModel().getAll();
            for (int i=0; i<matematikSoru.size(); i++){
                SoruModel model=new SoruModel();
                model.setSid(matematikSoru.get(i).sid);
                model.setT_id(matematikSoru.get(i).t_id);
                model.setK_id(matematikSoru.get(i).k_id);
                model.setS_icerik(matematikSoru.get(i).s_icerik);
                model.setK_resim(matematikSoru.get(i).k_resim);
                model.setK_adi(matematikSoru.get(i).k_adi);
                model.setTime(matematikSoru.get(i).time);
                sorular.add(model);
            }break;
        case 3:  geometrisoru=new GeometriModel().getAll();
            for (int i=0; i<geometrisoru.size(); i++){
                SoruModel model=new SoruModel();
                model.setSid(geometrisoru.get(i).sid);
                model.setT_id(geometrisoru.get(i).t_id);
                model.setK_id(geometrisoru.get(i).k_id);
                model.setS_icerik(geometrisoru.get(i).s_icerik);
                model.setK_resim(geometrisoru.get(i).k_resim);
                model.setK_adi(geometrisoru.get(i).k_adi);
                model.setTime(geometrisoru.get(i).time);
                sorular.add(model);
            }break;
    }
    return sorular;
}
    private void btn_Turkce(){
        btnTurkce.setTextColor(Color.parseColor("#fbf2fc"));
        btnGeometri.setTextColor(Color.parseColor("#000000"));
        btnMatematik.setTextColor(Color.parseColor("#000000"));
    }
    private void btn_matematik(){
        btnTurkce.setTextColor(Color.parseColor("#000000"));
        btnGeometri.setTextColor(Color.parseColor("#000000"));
        btnMatematik.setTextColor(Color.parseColor("#fbf2fc"));
    }
    private void btn_geometri(){
        btnTurkce.setTextColor(Color.parseColor("#000000"));
        btnGeometri.setTextColor(Color.parseColor("#fbf2fc"));
        btnMatematik.setTextColor(Color.parseColor("#000000"));
    }
}
