package com.ramazan.KpssZede.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramazan.KpssZede.Data;
import com.ramazan.KpssZede.ImageLoader;
import com.ramazan.KpssZede.R;

/**
 * Created by ramazan on 11.03.2016.
 */
public class ProfilFragment extends Fragment {
    Button btnFavori;
    Button btnShares;
    TextView ad;
    TextView soyad;
    ImageView resim;
    String[] dizi;
    final int loader = R.drawable.test;
    ImageLoader imgLoader;

    ProfilFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView =inflater.inflate(R.layout.profil, container, false);
        btnFavori = (Button) rootView.findViewById(R.id.btn_favori);
        btnShares = (Button) rootView.findViewById(R.id.btn_paylasim);
        ad = (TextView) rootView.findViewById(R.id.profil_ad);
        soyad = (TextView) rootView.findViewById(R.id.profil__soyad);
        resim = (ImageView) rootView.findViewById(R.id.img_profil);
        dizi = Data.dosyadoku();
        imgLoader = new ImageLoader(getActivity().getApplicationContext(),1);
        if (dizi != null) {
            ad.setText(dizi[1]);
            soyad.setText(dizi[2]);
            imgLoader.DisplayImage(dizi[5], loader, resim);
        }
        return rootView;
    }
}
