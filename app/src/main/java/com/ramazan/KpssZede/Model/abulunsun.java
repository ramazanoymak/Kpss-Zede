package com.ramazan.KpssZede.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import java.util.List;

/**
 * Created by ramazan on 15.12.2015.
 */
@Table(name = "abulunsun")
public class abulunsun extends Model{

    @Column(name="icerik")
    public String icerik;

    @Column(name="remoteID")
    public int remoteID; //dışardan gelen id ile burdaki id yi karşılaştır


    public abulunsun(){}

    public abulunsun(int remoteID, String icerik) {
        this.remoteID = remoteID;
        this.icerik = icerik;
    }

    public List<abulunsun> getAll(){
        return new Select().from(abulunsun.class).execute();
    }

    public Boolean isExist(int remoteID){
        return
                new Select().from(abulunsun.class).where("remoteID=?",remoteID).exists();
    }

    public List<abulunsun> getDatafromId(int id){
        return new Select().from(abulunsun.class).where("id=?",id).execute();//executesingle ile modeli alabilirsin
    }

}
