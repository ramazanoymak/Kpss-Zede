package com.ramazan.KpssZede.Adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ramazan.KpssZede.Activity.MainActivity;
import com.ramazan.KpssZede.Data;
import com.ramazan.KpssZede.Fragments.AbulunsunFragment;
import com.ramazan.KpssZede.Fragments.AyarlarFragment;
import com.ramazan.KpssZede.Fragments.GenelKulturFragment;
import com.ramazan.KpssZede.Fragments.GenelYetenekFragment;
import com.ramazan.KpssZede.Fragments.HaberFragment;
import com.ramazan.KpssZede.Fragments.LoginFragment;
import com.ramazan.KpssZede.Fragments.ProfilFragment;
import com.ramazan.KpssZede.Fragments.RegisterFragment;
import com.ramazan.KpssZede.Fragments.SifremiUnuttumFragment;
import com.ramazan.KpssZede.ImageLoader;
import com.ramazan.KpssZede.Model.FavoriModel;
import com.ramazan.KpssZede.Model.Mesages;
import com.ramazan.KpssZede.Model.SoruModel;
import com.ramazan.KpssZede.R;

import java.util.List;

/**
 * Created by ramazan on 10.12.2015.
 */
public class SoruAdapter extends BaseAdapter {

    Data servis;
    Context _c;
    List<SoruModel> _data;
    ImageView resim;
    final int loader = R.drawable.test;
    ImageLoader imgLoader;
    Button btnFavoriekle;
    Button btnCevapla;
    public SoruAdapter(List<SoruModel> data, Context c) {

        this._c=c;
        this._data=data;
        servis=new Data(_c);
        imgLoader = new ImageLoader(_c,1);
    }

    @Override
    public int getCount() {
        return _data.size();
    }

    @Override
    public Object getItem(int position) {
        return _data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater v1=(LayoutInflater)_c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=v1.inflate(R.layout.soru,null);
        }
        btnFavoriekle = (Button) v.findViewById(R.id.btn_Favoriekle);
        btnCevapla = (Button) v.findViewById(R.id.btn_cevapla);

         resim=(ImageView)v.findViewById(R.id.imgUser);
        TextView ad=(TextView)v.findViewById(R.id.ad);
        TextView zaman=(TextView)v.findViewById(R.id.time);
        TextView soru=(TextView)v.findViewById(R.id.icerik);

            ad.setText(_data.get(position).getK_adi());
            zaman.setText(_data.get(position).getTime());
            soru.setText(_data.get(position).getS_icerik());
                if (_data.get(position).getK_resim()!=null){
                    imgLoader.DisplayImage(_data.get(position).getK_resim(), loader, resim);
                }
        /*if (new FavoriModel().isExist(model.remoteID)) {
            btnFavoriekle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.favoriaktif, 0, 0, 0);
        }*/

        btnFavoriekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Mesages favoriModel=(Mesages)getItem(position);

                if (favoriModel!=null) {
                    if (!new FavoriModel().isExist(favoriModel.remoteID)) {
                        FavoriModel m = new FavoriModel(favoriModel.remoteID,favoriModel.created_at,favoriModel.resim,
                                favoriModel.ad,0,"",favoriModel.mesaj );
                        m.save();
                        btnFavoriekle.setCompoundDrawablesWithIntrinsicBounds(R.drawable.favoriaktif, 0, 0, 0);
                    }
                }

            }
        });
        btnCevapla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return v;
    }
}
