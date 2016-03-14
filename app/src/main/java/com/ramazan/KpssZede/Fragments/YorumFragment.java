package com.ramazan.KpssZede.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ramazan.KpssZede.Adapter.yorumAdapter;
import com.ramazan.KpssZede.AppConfig;
import com.ramazan.KpssZede.Data;
import com.ramazan.KpssZede.ImageLoader;
import com.ramazan.KpssZede.Model.Mesages;
import com.ramazan.KpssZede.Model.Yorum;
import com.ramazan.KpssZede.R;

import java.util.List;

/**
 * Created by ramazan on 26.12.2015.
 */
public class YorumFragment extends Fragment {
    TextView ad;
    TextView zaman;
    TextView soru;
    EditText yorum;
    Button y_gonder;
    ImageView resim;
    Data servis;
    ListView listViewy;
    int mid;
    Context _c;
    ProgressDialog progressDialog;

    public YorumFragment(Context c,int position) {
        this._c=c;
        mid=position;
        servis = new Data(_c);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView =inflater.inflate(R.layout.yorum, container, false);

        ad=(TextView)rootView.findViewById(R.id.textad);
        zaman=(TextView)rootView.findViewById(R.id.textzaman);
        soru=(TextView)rootView.findViewById(R.id.textSoru);
        resim=(ImageView)rootView.findViewById(R.id.imageyorm);
        yorum=(EditText)rootView.findViewById(R.id.TextYrm);
        y_gonder=(Button)rootView.findViewById(R.id.buttony);
        y_gonder.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                String yrm = yorum.getText().toString().trim();

                if (!yrm.isEmpty()) {

                    String[] dizi = servis.dosyadoku();

                    if (dizi != null) {
                        dizi[2]=yrm;

                        new SendTask().execute(dizi);

                        yorum.setText("");
                    }
                } else {
                    Toast.makeText(_c, "Lütfen boş yorum girmeyiniz.!", Toast.LENGTH_LONG).show();
                }
            }
        });
        listViewy=(ListView)rootView.findViewById(R.id.listViewYorum);

        int loader = R.drawable.test;
        ImageLoader imgLoader = new ImageLoader(_c,1);

        List<Mesages> data=new Mesages().soru(mid);

        ad.setText(data.get(0).ad);
        zaman.setText(data.get(0).created_at);
        soru.setText(data.get(0).mesaj);

        imgLoader.DisplayImage(data.get(0).resim, loader, resim);

        new YorumTask().execute();

        return rootView;

    }
    public  class YorumTask extends AsyncTask<String,String,String> {
        List<Yorum> yorumList;

      /*  @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.show(_c,"","Lütfen Bekleyin..");
        }*/

        @Override
        protected String doInBackground(String... params) {

            String id=String.valueOf(mid);
            try {
                if (isConnected()) {
                    String strReq =servis.HttpPostGetYorum(AppConfig.URL_yorum,id);
                    yorumList = servis.yorumlar(strReq);
                }
                else{
                    yorumList=new Yorum().getAll(mid);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (yorumList!=null){
               // progressDialog.dismiss();
                listViewy.setAdapter(new yorumAdapter(yorumList, _c));
            }
        }
    }

    public  class SendTask extends  AsyncTask<String,String,String> {
        int durum;
        @Override
        protected String doInBackground(String... params) {
           // progressDialog.show(_c,"","Yorum Gönderiliyor..");
            try{
                if (isConnected()) {
                    String id = String.valueOf(mid);
                    String strReq = servis.HttpPostSendYorum(AppConfig.URL_sendyorum, id, params[1], params[2]);

                    String[] Scevap = servis.checkServer(strReq);

                    durum = Integer.parseInt(Scevap[0]);
                }
            }catch (Exception e){}
            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (durum==0){
                //progressDialog.dismiss();
                Toast.makeText(_c, "Yorum gönderildi..", Toast.LENGTH_LONG).show();
            }
            else{
                Toast.makeText(_c, "islemde bir hata oluştu", Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
