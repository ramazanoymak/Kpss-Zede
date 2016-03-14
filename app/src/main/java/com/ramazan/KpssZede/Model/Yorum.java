package com.ramazan.KpssZede.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by ramazan on 25.12.2015.
 */

@Table(name = "Yorum")
public class Yorum extends Model {

    @Column(name="yid")
    public int yid;

    @Column(name="mid")
    public int mid;

    @Column(name="ad")
    public String ad;

    @Column(name="yorum")
    public String yorum;

    @Column(name="zaman")
    public String zaman;

    public Yorum(){}

    public Yorum(int yid, int mid, String ad, String yorum, String zaman) {
        this.yid = yid;
        this.mid = mid;
        this.ad = ad;
        this.yorum = yorum;
        this.zaman = zaman;
    }

    public List<Yorum> getAll(int mid){
        return new Select().from(Yorum.class).where("mid=?",mid).execute();
    }

    public Boolean isExist(int yid){
        return new Select().from(Yorum.class).where("yid=?", yid).exists();
    }

    public List<Yorum> delete(int yid){
        return new Delete().from(Yorum.class).where("yid=?", yid).execute();
    }

}
