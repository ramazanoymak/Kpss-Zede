package com.ramazan.KpssZede.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramazan.KpssZede.Data;
import com.ramazan.KpssZede.ImageLoader;
import com.ramazan.KpssZede.Model.Mesages;
import com.ramazan.KpssZede.Model.Yorum;
import com.ramazan.KpssZede.R;

import java.util.List;

/**
 * Created by ramazan on 25.12.2015.
 */
public class yorumAdapter extends BaseAdapter {

    Data servis;
    Context _c;
    List<Yorum> _data;

    public yorumAdapter(List<Yorum> data,Context c) {

        this._c=c;
        this._data=data;
        servis=new Data(_c);
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
            v=v1.inflate(R.layout.yorum_item,null);
        }

        TextView ad=(TextView)v.findViewById(R.id.textyad);
        TextView zaman=(TextView)v.findViewById(R.id.textyzaman);
        TextView yorum=(TextView)v.findViewById(R.id.textyicerik);

        Yorum model=_data.get(position);

        ad.setText(model.ad);
        zaman.setText(model.zaman);
        yorum.setText(model.yorum);
        return v;
    }
}
