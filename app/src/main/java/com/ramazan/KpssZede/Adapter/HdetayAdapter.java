package com.ramazan.KpssZede.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramazan.KpssZede.Fragments.HaberFragment;
import com.ramazan.KpssZede.ImageLoader;
import com.ramazan.KpssZede.Model.Haberler;
import com.ramazan.KpssZede.R;


/**
 * Created by ramazan on 25.11.2015.
 */
public class HdetayAdapter extends BaseAdapter{

   Haberler _data;
    Context _c;
    FragmentActivity _activity;

    public HdetayAdapter(Context c, Haberler data, FragmentActivity activity) {
        _c=c;
        _data=data;
        _activity=activity;

    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        // Loader image - will be shown before loading image
        int loader = R.drawable.test;
        // ImageLoader class instance
        ImageLoader imgLoader = new ImageLoader(_c,0);

        if (v == null) {
            LayoutInflater v1 = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = v1.inflate(R.layout.haber_detay, null);
        }

        TextView baslik = (TextView) v.findViewById(R.id.hbr_d_baslik);
        TextView icerik = (TextView) v.findViewById(R.id.hbr_d_icerik);
        ImageView resim = (ImageView) v.findViewById(R.id.hbr_d_imageView);

        baslik.setText(_data.baslik);
        icerik.setText(_data.icerik);

        baslik.setTextColor(Color.BLACK);
        icerik.setTextColor(Color.BLACK);

        imgLoader.DisplayImage(_data.resim, loader, resim);

        /*ImageButton button=(ImageButton)v.findViewById(R.id.imageButton);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                HaberFragment fragment = new HaberFragment(_c, 0);

                Bundle args = new Bundle();
                args.putInt(HaberFragment.ARG_PLANET_NUMBER, 0);
                fragment.setArguments(args);

                android.support.v4.app.FragmentManager fragmentManager = _activity. getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }
        });*/
        return v;
    }
}
