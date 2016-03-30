package com.ramazan.KpssZede.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by ramazan on 14.12.2015.
 */
@Table(name = "Messages")
public class Messages extends Model {

    @Column(name="ad")
    public String ad;

    @Column(name="soyad")
    public String soyad;

    @Column(name="resim")
    public String resim;

    @Column(name="mesaj")
    public String mesaj;

    @Column(name="remoteID")
    public int remoteID;

    @Column(name="create_at")
    public String created_at;


    public Messages(){}

    public Messages(String ad, String resim, String mesaj, int remoteID, String created_at) {
        this.ad = ad;
        this.resim = resim;
        this.mesaj = mesaj;
        this.remoteID = remoteID;
        this.created_at = created_at;
    }

    public List<Messages> getAll(){
        return new Select().from(Messages.class).execute();
    }

    public Boolean isExist(int remoteID){
        return new Select().from(Messages.class).where("remoteID=?", remoteID).exists();
    }
    public List<Messages> delete(int remoteID){
        return new Delete().from(Messages.class).where("remoteID=?", remoteID).execute();
    }

}
