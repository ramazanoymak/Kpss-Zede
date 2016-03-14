package com.ramazan.KpssZede.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ramazan.KpssZede.Activity.MainActivity;
import com.ramazan.KpssZede.Model.abulunsun;
import com.ramazan.KpssZede.R;

import java.util.List;

/**
 * Created by ramazan on 26.02.2016.
 */
public class AbulunsunFragment extends Fragment {
    Button ileri;
    Button geri;
    TextView eleman;
    TextView elemanSayisi;
    TextView icerik;
    List<abulunsun> abulunsunList;
    int sayac=0;

    AbulunsunFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.abulunsun, container, false);
        ileri = (Button) rootView.findViewById(R.id.ileri);
        geri = (Button) rootView.findViewById(R.id.geri);
         icerik = (TextView) rootView.findViewById(R.id.ab_icerik);
        eleman = (TextView) rootView.findViewById(R.id.eleman);
        elemanSayisi = (TextView) rootView.findViewById(R.id.elemanSayisi);

        abulunsunList=new abulunsun().getAll();

        ileri.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sayac++;
                if (sayac == abulunsunList.size()) {
                    sayac = 0;
                    if (sayac < abulunsunList.size()) {
                        eleman.setText(String.valueOf(sayac));
                        icerik.setText(abulunsunList.get(sayac).icerik);
                    }
                }
                if (sayac < abulunsunList.size()) {
                    eleman.setText(String.valueOf(sayac));
                    icerik.setText(abulunsunList.get(sayac).icerik);
                }

            }
        });

        geri.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (sayac > 0) {
                    sayac--;
                    if (sayac < abulunsunList.size()) {
                        eleman.setText(String.valueOf(sayac));
                        icerik.setText(abulunsunList.get(sayac).icerik);
                    }
                }
                if (sayac == 0) {
                    sayac = abulunsunList.size();
                    sayac--;
                    if (sayac < abulunsunList.size()) {
                        eleman.setText(String.valueOf(sayac));
                        icerik.setText(abulunsunList.get(sayac).icerik);
                    }
                }
            }
        });

        icerik.setText(abulunsunList.get(0).icerik);
        elemanSayisi.setText(String.valueOf(abulunsunList.size()));
        eleman.setText(String.valueOf(sayac));
        return rootView;
    }
}
