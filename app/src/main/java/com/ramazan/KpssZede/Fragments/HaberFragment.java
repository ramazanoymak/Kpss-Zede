package com.ramazan.KpssZede.Fragments;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.ramazan.KpssZede.Adapter.HaberAdapter;
import com.ramazan.KpssZede.Data;
import com.ramazan.KpssZede.Model.Haberler;
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
    Data servis;
    ListView _listView;
    View rootView;

    final int[] sayac = {0};

    public HaberFragment(Context c, int position){
        this._c=c;
        this._position=position;
        servis=new Data(_c);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

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
            return rootView;
    }

    public  class HaberTask extends  AsyncTask<String,String,String> {

        List<Haberler> haberlerList=null;
        @Override
        protected String doInBackground(String... params) {
        try{
    if (!servis.isServiceRunning()){
        haberlerList=new Haberler().getAll();
    }}catch (Exception e){}
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


}
