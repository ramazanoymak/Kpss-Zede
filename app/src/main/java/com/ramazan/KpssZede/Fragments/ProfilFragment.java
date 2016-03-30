package com.ramazan.KpssZede.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ramazan.KpssZede.Activity.MainActivity;
import com.ramazan.KpssZede.Adapter.FavoriAdapter;
import com.ramazan.KpssZede.AppConfig;
import com.ramazan.KpssZede.Data;
import com.ramazan.KpssZede.ImageLoader;
import com.ramazan.KpssZede.Model.FavoriModel;
import com.ramazan.KpssZede.R;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by ramazan on 11.03.2016.
 */
public class ProfilFragment extends Fragment  implements AdapterView.OnItemSelectedListener {
    Button btnFavori;
    Button btnShares;
    Button btnPaylas;
    TextView ad;
    TextView soyad;
    ImageView resim;
    String[] dizi;
    final int loader = R.drawable.test;
    ImageLoader imgLoader;
    Data servis;
    private Spinner spinner_konu;
    ListView listViewProfile;
    EditText edtSoru;

    public ProfilFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView =inflater.inflate(R.layout.profil, container, false);
        btnFavori = (Button) rootView.findViewById(R.id.btn_favori);
        btnShares = (Button) rootView.findViewById(R.id.btn_paylasim);
        ad = (TextView) rootView.findViewById(R.id.profil_ad);
        soyad = (TextView) rootView.findViewById(R.id.profil__soyad);
        resim = (ImageView) rootView.findViewById(R.id.img_profil);
        edtSoru = (EditText) rootView.findViewById(R.id.edt_profil_soru);
        listViewProfile = (ListView) rootView.findViewById(R.id.listView_profil);

        servis = new Data(getActivity().getApplicationContext());
        dizi = Data.dosyadoku();

        imgLoader = new ImageLoader(getActivity().getApplicationContext(),1);
        if (dizi != null) {
            ad.setText(dizi[1]);
            soyad.setText(dizi[2]);
            imgLoader.DisplayImage(dizi[5], loader, resim);
        }

        spinner_konu = (Spinner) rootView.findViewById(R.id.spinner_ders);
        btnPaylas = (Button) rootView.findViewById(R.id.btn_paylas);

        btnPaylas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((MainActivity)getActivity()).isConnected()){
                            String k_id = dizi[0];

                            String tur_id=String.valueOf(spinner_konu.getAdapter().getItemId(spinner_konu.getId()));

                            String s_icerik=edtSoru.getText().toString().trim();
                            if (!s_icerik.equals("")){
                                String[] sorupaket = new String[3];//nesne oluşturuken hata veriyo.
                                sorupaket[0]=k_id;
                                sorupaket[1]=tur_id;
                                sorupaket[2]=s_icerik;
                                new SendTask().execute(sorupaket);
                            }
                            else {
                                Toast.makeText(getActivity().getApplicationContext(),
                                        "Bilgilerinizi eksik girmeyiniz !!",
                                        Toast.LENGTH_SHORT).show();}
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(),
                            "Lütfen İnternet bağlantınızı kontrol ediniz!",
                            Toast.LENGTH_SHORT).show();
                }
                Toast.makeText(getActivity().getApplicationContext(),
                        "OnClickListener : " +
                                "\nSpinner 1 : " + String.valueOf(spinner_konu.getId()),
                        Toast.LENGTH_SHORT).show();
            }
        });
        btnFavori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<FavoriModel> FavoriData=FavoriModel.getAll();
                if (FavoriData!=null) {
                    if (FavoriData.size() >= 0) {
                        listViewProfile.setAdapter(new FavoriAdapter(getActivity().getApplicationContext(), FavoriData));
                    }
                    else {
                        Toast.makeText(getActivity().getApplicationContext(),"Henüz Favori Sorunuz bulunmamaktadır!",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getActivity().getApplicationContext(),"Favori listeniz Boş !!",
                            Toast.LENGTH_SHORT).show();
                }

            }
        });
        return rootView;
    }
    public  class SendTask extends AsyncTask<String,String,String> {
        int durum;
        String servercevap="";
        @Override
        protected String doInBackground(String... params) {
            try{
                String strReq =servis.HttpPostSendSoru(AppConfig.URL_SendSoru,params[0],  params[1], params[2]);
                String[] Scevap=servis.checkServer(strReq);
                durum=Integer.parseInt(Scevap[0]);
                servercevap=Scevap[1];
            }
            catch (Exception e){}

            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (durum==0){
                //progressDialog.dismiss();
                Toast.makeText(getActivity().getApplicationContext(),servercevap,Toast.LENGTH_LONG).show();
                //new GetGuncelSoruTask().execute();
            }
            else{
                // progressDialog.dismiss();
                Toast.makeText(getActivity().getApplicationContext(),servercevap,Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
