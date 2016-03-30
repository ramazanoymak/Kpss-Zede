package com.ramazan.KpssZede.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramazan.KpssZede.ImageLoader;
import com.ramazan.KpssZede.Model.Haberler;
import com.ramazan.KpssZede.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramazan on 18.10.2015.
 */

public class HaberAdapter extends BaseAdapter {
    Context _c;
    List<Haberler> _data;

    public HaberAdapter(List<Haberler> data, Context c) {
        _data=data;
        _c=c;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        // Loader image - will be shown before loading image
        int loader = R.drawable.test;
        // ImageLoader class instance
        ImageLoader imgLoader = new ImageLoader(_c,0);

        if (v == null) {
            LayoutInflater v1=(LayoutInflater)_c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=v1.inflate(R.layout.haber_list,null);
        }
        ImageView resim=(ImageView)v.findViewById(R.id.imgHaber);
        TextView baslik=(TextView)v.findViewById(R.id.txtBaslik);
        TextView ozet=(TextView)v.findViewById(R.id.txtOzet);
        TextView zaman=(TextView)v.findViewById(R.id.textTarih);

        Haberler model=_data.get(position);
        baslik.setText(model.baslik.trim());
        ozet.setText(model.ozet.trim());
        zaman.setText(model.zaman.trim());

        imgLoader.DisplayImage(model.resim, loader, resim);


        return v;
    }


}
