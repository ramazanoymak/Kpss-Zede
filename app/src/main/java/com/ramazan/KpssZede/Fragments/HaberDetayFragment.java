package com.ramazan.KpssZede.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramazan.KpssZede.Activity.MainActivity;
import com.ramazan.KpssZede.ImageLoader;
import com.ramazan.KpssZede.R;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * Created by ramazan on 06.03.2016.
 */
public class HaberDetayFragment extends Fragment {
    View rootView;
    TextView baslik;
    TextView icerik;
    TextView tarih;
    ImageView resim;

    String _baslik="",_icerik="",_resim="",_tarih="";
    int loader = R.drawable.test;
    ImageLoader imgLoader;
    String s="";

  public HaberDetayFragment(String title,String description,String image,String time){
       _baslik=title;
        _icerik=description;
        _resim=image;
        _tarih=time;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.haber_detay, container, false);

         baslik = (TextView) rootView.findViewById(R.id.hbr_d_baslik);
         icerik = (TextView) rootView.findViewById(R.id.hbr_d_icerik);
         tarih = (TextView) rootView.findViewById(R.id.hdzaman);
         resim = (ImageView) rootView.findViewById(R.id.hbr_d_imageView);
        imgLoader = new ImageLoader(getActivity().getApplicationContext(),0);

        try {
            s = URLDecoder.decode(_icerik, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        baslik.setText(_baslik);
        icerik.setText(s);
        tarih.setText(_tarih);


        imgLoader = new ImageLoader(getActivity().getApplicationContext(),0);
        imgLoader.DisplayImage(_resim, loader, resim);

        return rootView;
    }
}
