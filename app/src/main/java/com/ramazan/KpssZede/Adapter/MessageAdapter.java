package com.ramazan.KpssZede.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramazan.KpssZede.Data;
import com.ramazan.KpssZede.ImageLoader;
import com.ramazan.KpssZede.Model.Mesages;
import com.ramazan.KpssZede.Model.Messages;
import com.ramazan.KpssZede.R;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramazan on 10.12.2015.
 */
public class MessageAdapter extends BaseAdapter {

    Data servis;
    Context _c;
    List<Mesages> _data;
    ImageView resim;
    final int loader = R.drawable.test;
    ImageLoader imgLoader;
    public MessageAdapter(List<Mesages> data,Context c) {

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
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater v1=(LayoutInflater)_c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v=v1.inflate(R.layout.chat,null);
        }
         resim=(ImageView)v.findViewById(R.id.imgUser);

        TextView ad=(TextView)v.findViewById(R.id.ad);
        TextView zaman=(TextView)v.findViewById(R.id.time);
        TextView mesaj=(TextView)v.findViewById(R.id.icerik);

        final Mesages model=_data.get(position);

            ad.setText(model.ad);
            zaman.setText(model.created_at);
            mesaj.setText(model.mesaj);

                if (model.resim!=null){
                    imgLoader.DisplayImage(model.resim, loader, resim);//resim adapte edildi
                }

        return v;
    }


}
