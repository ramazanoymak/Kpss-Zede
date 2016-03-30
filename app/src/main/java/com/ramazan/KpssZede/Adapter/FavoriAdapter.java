package com.ramazan.KpssZede.Adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ramazan.KpssZede.ImageLoader;
import com.ramazan.KpssZede.Model.FavoriModel;
import com.ramazan.KpssZede.R;

import java.util.List;

/**
 * Created by ramazan on 29.11.2015.
 */
public class FavoriAdapter extends BaseAdapter {

    Context _c;
    List<FavoriModel> _data;
    ImageView resim;
    final int loader = R.drawable.test;
    ImageLoader imgLoader;
    Button btnFavoriSil;
    Button btnCevapla;

    public FavoriAdapter(Context c, List<FavoriModel> data) {
       _data=data;
        _c=c;
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
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater v1 = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = v1.inflate(R.layout.favori, null);
        }
        btnFavoriSil = (Button) v.findViewById(R.id.btn_Favorisil);

        resim = (ImageView) v.findViewById(R.id.favori_imgUser);
        TextView ad = (TextView) v.findViewById(R.id.favori_ad);
        TextView zaman = (TextView) v.findViewById(R.id.favori_time);
        TextView icerik = (TextView) v.findViewById(R.id.favori_icerik);

        icerik.setText(_data.get(position).s_icerik);
        ad.setText(_data.get(position).k_adi);
        zaman.setText(_data.get(position).time);

        if (_data.get(position).k_resim != null) {
            imgLoader.DisplayImage(_data.get(position).k_resim, loader, resim);
        }
        btnFavoriSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavoriModel favoriModel=(FavoriModel)getItem(position);
                if (favoriModel!=null) {
                    FavoriModel.delete(favoriModel.sid);
                    _data.remove(position);
                    notifyDataSetChanged();
                }
            }
        });
        return v;
    }
}
