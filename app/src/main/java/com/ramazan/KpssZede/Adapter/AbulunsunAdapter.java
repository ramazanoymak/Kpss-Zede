package com.ramazan.KpssZede.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.ramazan.KpssZede.Fragments.HaberFragment;
import com.ramazan.KpssZede.Model.abulunsun;
import com.ramazan.KpssZede.R;

/**
 * Created by ramazan on 29.11.2015.
 */
public class AbulunsunAdapter extends BaseAdapter {

    Context _c;
    abulunsun _data;
    FragmentActivity _activity;
    ListView _listview;
   int sayac=0;


    public AbulunsunAdapter(Context c, abulunsun data, FragmentActivity activity) {
       _data=data;
        _c=c;
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

        if (v == null) {
            LayoutInflater v1 = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = v1.inflate(R.layout.abulunsun, null);
           // lViev=v1.inflate(R.layout.fragment_planet,null);
        }

        Button ileri = (Button) v.findViewById(R.id.ileri);
        Button geri = (Button) v.findViewById(R.id.geri);
/*
        ImageButton back = (ImageButton) v.findViewById(R.id.back);
*/
        TextView icerik = (TextView) v.findViewById(R.id.ab_icerik);

       // _listview=(ListView)lViev.findViewById(R.id.listView);

        icerik.setText(_data.icerik);

        icerik.setTextColor(Color.BLACK);

       /* back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                HaberFragment fragment = new HaberFragment(_c, 0);
                Bundle args = new Bundle();
                args.putInt(HaberFragment.ARG_PLANET_NUMBER, 0);
                fragment.setArguments(args);
                android.support.v4.app.FragmentManager fragmentManager = _activity.getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            }
        });*/
        ileri.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
               int id=_data.remoteID;
                id++;

                if (new abulunsun().isExist(id)) {

                    abulunsun model=new abulunsun().getDatafromId(id).get(0);

                    _listview.setAdapter(new AbulunsunAdapter(_c,model,_activity));
                }

            }
        });

        geri.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                int id=_data.remoteID;
                id--;

                if (new abulunsun().isExist(id)) {

                    abulunsun model=new abulunsun().getDatafromId(id).get(0);

                    _listview.setAdapter(new AbulunsunAdapter(_c,model,_activity));
                }
            }
        });

        return v;
        //abulunsun layout u düzenle.
    }
}
