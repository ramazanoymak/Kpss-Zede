package com.ramazan.KpssZede.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;

import java.util.List;

/**
 * Created by ramazan on 17.03.2016.
 */
@Table(name = "FavoriModel")
public class FavoriModel extends Model {

    @Column(name="sid")
    public int sid;

    @Column(name="s_icerik")
    public String s_icerik;

    @Column(name="s_resim")
    public String s_resim;

    @Column(name="k_id")
    public int k_id;

    @Column(name="k_adi")
    public String k_adi;

    @Column(name="k_resim")
    public String k_resim;

    @Column(name="time")
    public String time;

    public FavoriModel(){}

    public FavoriModel(int sid, String time, String k_resim, String k_adi, int k_id, String s_resim, String s_icerik) {
        this.sid = sid;
        this.time = time;
        this.k_resim = k_resim;
        this.k_adi = k_adi;
        this.k_id = k_id;
        this.s_resim = s_resim;
        this.s_icerik = s_icerik;
    }

    public static List<FavoriModel> getAll(){
        return new Select().from(FavoriModel.class).execute();
    }

    public Boolean isExist(int sid){
        return new Select().from(FavoriModel.class).where("sid=?", sid).exists();
    }

    public static List<FavoriModel> delete(int sid){
        return new Delete().from(FavoriModel.class).where("sid=?", sid).execute();
    }

}