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
@Table(name = "YorumModel")
public class YorumModel extends Model {

    @Column(name="yid")
    public int yid;

    @Column(name="sid")
    public int sid;

    @Column(name="k_id")
    public int k_id;

    @Column(name="y_icerik")
    public String y_icerik;

    @Column(name="k_adi")
    public String k_adi;

    @Column(name="time")
    public String time;

    public YorumModel(){}

    public YorumModel(int yid, int sid, int k_id, String y_icerik, String k_adi, String time) {
        this.yid = yid;
        this.sid = sid;
        this.k_id = k_id;
        this.y_icerik = y_icerik;
        this.k_adi = k_adi;
        this.time = time;
    }

    public Boolean isExist(int yid){
        return new Select().from(YorumModel.class).where("yid=?", yid).exists();
    }

    public List<YorumModel> delete(int yidid){
        return new Delete().from(YorumModel.class).where("yid=?", yid).execute();
    }

    public List<YorumModel> Yorumlarım (int k_id){
        return new Select().from(YorumModel.class).where("k_id=?", k_id).execute();
    }

    public void Guncelle(int yid, String y_icerik){
        new Update(YorumModel.class).set("y_icerik ="+y_icerik)
                .where("yid = ?", yid).execute();
    }
}
