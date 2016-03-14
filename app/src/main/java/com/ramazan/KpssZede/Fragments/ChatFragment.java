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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ramazan.KpssZede.Activity.MainActivity;
import com.ramazan.KpssZede.Adapter.MessageAdapter;
import com.ramazan.KpssZede.AppConfig;
import com.ramazan.KpssZede.Data;
import com.ramazan.KpssZede.Model.Haberler;
import com.ramazan.KpssZede.Model.Mesages;
import com.ramazan.KpssZede.Model.abulunsun;
import com.ramazan.KpssZede.R;
import com.ramazan.KpssZede.SessionManager;

import java.util.List;

/**
 * Created by ramazan on 20.02.2016.
 */
public class ChatFragment extends Fragment {

    Context _c;
    private SessionManager session;
    Data servis;
    private EditText EdtTextMsg;
    private Button btnGndr;
    ListView listViewchat;
    public static int loginFlag=0;
    ProgressDialog progressDialog;

    public ChatFragment(Context c) {
        this._c = c;
        servis = new Data(_c);
        session = new SessionManager(_c);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.chat_list, container, false);
        listViewchat = (ListView) rootView.findViewById(R.id.listViewchat);
if (loginFlag==1){
    ((MainActivity)getActivity()).drawerYenile();

}
        listViewchat.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);

                Mesages model = (Mesages) parent.getItemAtPosition(position);
                YorumFragment yorumFragment = new YorumFragment(_c, model.remoteID);
                android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, yorumFragment).commit();

            }
        });
        EdtTextMsg = (EditText) rootView.findViewById(R.id.eTextmsg);

        EdtTextMsg.setFocusable(false);
        EdtTextMsg.setFocusableInTouchMode(true);

        btnGndr = (Button) rootView.findViewById(R.id.btngndr);

        btnGndr.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                String msg = EdtTextMsg.getText().toString().trim();

                if (!msg.isEmpty()) {

                    String[] dizi = servis.dosyadoku();

                    if (dizi != null) {
                        dizi[4] = msg;

                        new SendTask().execute(dizi);
                        EdtTextMsg.setText("");
                    }
                } else {
                    Toast.makeText(_c, "Lütfen boş mesaj girmeyiniz.!", Toast.LENGTH_LONG).show();
                }
            }
        });


    try {
        String[] dizi = servis.dosyadoku();
        if (dizi != null) {
            String email = dizi[3];
            new HaberTask().execute(email);
        }
        else {
            loginfromUser();
        }
    }
    catch(Exception e) {e.printStackTrace();}

    return rootView;
}

    public  class HaberTask extends AsyncTask<String,String,String> {
        List<Mesages> messages=null;
        /*@Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.show(getActivity().getApplicationContext(),"", "Lütfen bekleyin..");
        }*/
        @Override
    protected String doInBackground(String... params) {
                try {
                    if (!servis.isServiceRunning()){
                        messages=servis.getDbMessages();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
        return null;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

       if (messages!=null){
           //progressDialog.dismiss();
            listViewchat.setAdapter(new MessageAdapter(messages, _c));
            listViewchat.setTranscriptMode(ListView.TRANSCRIPT_MODE_NORMAL);
            listViewchat.setStackFromBottom(true);
        }
        else {
          // progressDialog.dismiss();
            Toast.makeText(_c, "Veri alınamadı..", Toast.LENGTH_LONG).show();}

    }
}

    public  class SendTask extends  AsyncTask<String,String,String> {
    int durum;
       /* @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.show(_c, "", "Mesaj Gönderiliyor...");

        }
*/
        @Override
    protected String doInBackground(String... params) {
        String strReq =servis.HttpPostSendMessage(params[0],AppConfig.URL_chat,params[1],params[2],params[3],params[4],params[5]);
        String[] Scevap=servis.checkServer(strReq);
        durum=Integer.parseInt(Scevap[0]);
        return null;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (durum==0){
            //progressDialog.dismiss();
            Toast.makeText(_c,"Mesaj Gönderildi.",Toast.LENGTH_LONG).show();
            //new HaberTask().execute();
        }
        else{
           // progressDialog.dismiss();
            Toast.makeText(_c,"islem basarisiz",Toast.LENGTH_LONG).show();
        }
    }
}
    private void loginfromUser() {
        session.setLogin(false);
        LoginFragment fragment = new LoginFragment(_c);
        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

    }
}
