package com.ramazan.KpssZede.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.ramazan.KpssZede.Adapter.AbulunsunAdapter;
import com.ramazan.KpssZede.Adapter.HaberAdapter;
import com.ramazan.KpssZede.Adapter.HdetayAdapter;
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
 * Created by ramazan on 10.12.2015.
 */

public class HaberFragment extends Fragment{

    public static final String ARG_PLANET_NUMBER = "planet_number";
    Context _c;
    int _position;
    private SessionManager session;

    Data servis;
    ListView _listView;
    View rootView;

    final int[] sayac = {0};

    public HaberFragment(Context c, int position){
        this._c=c;
        this._position=position;
        servis=new Data(_c);
        session = new SessionManager(_c);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        switch (_position){
            case 0:

                if (!session.isLoggedIn())//login deilse logout et
                {
                    loginfromUser();
                }
                else {
                    ChatFragment fragment = new ChatFragment(_c);
                    android.support.v4.app.FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                }
                break;
            case 1:


                sayac[0]=0;
                rootView = inflater.inflate(R.layout.fragment_haber, container, false);
                _listView=(ListView) rootView.findViewById(R.id.listView);

                _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    Haberler model =new Haberler ();
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        view.setSelected(true);
                        sayac[0] =1;

                        model=(Haberler) parent.getItemAtPosition(position);

                        if (model != null) {
                            HaberDetayFragment fragment = new HaberDetayFragment(model.baslik,model.icerik,
                                    model.resim,model.zaman);
                            android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).addToBackStack("geriFragment").commit();
                        }

                    }
                });
                if (sayac[0] ==0){
                    new HaberTask().execute();
                }
                break;
                case 2:
                    AbulunsunFragment fragment = new AbulunsunFragment();
                    android.support.v4.app.FragmentManager fragmentManager =getActivity().getSupportFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
                    break;

                case 3:
                   ProfilFragment profilFragment = new ProfilFragment();
                    android.support.v4.app.FragmentManager fragmentManager1 =getActivity().getSupportFragmentManager();
                    fragmentManager1.beginTransaction().replace(R.id.content_frame, profilFragment).commit();
            break;
            case 4:
                AyarlarFragment ayarlarFragment = new AyarlarFragment(_c);
                android.support.v4.app.FragmentManager fragmentManager2 =getActivity().getSupportFragmentManager();
                fragmentManager2.beginTransaction().replace(R.id.content_frame, ayarlarFragment).commit();
                break;
    }

            return rootView;
    }

    public  class HaberTask extends  AsyncTask<String,String,String> {
        List<Haberler> haberlerList=null;

        @Override
        protected String doInBackground(String... params) {
        try{
    if (!servis.isServiceRunning()){
        haberlerList=new Haberler().getAll();
    }
            }catch (Exception e){}

            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(haberlerList!=null){
                _listView.setAdapter(new HaberAdapter(haberlerList,_c));
            }
            else {
                Toast.makeText(_c, "Veri alınamadı..", Toast.LENGTH_LONG).show();}

        }
    }

    private void loginfromUser() {
        LoginFragment fragment = new LoginFragment(_c);
        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

    }

}
