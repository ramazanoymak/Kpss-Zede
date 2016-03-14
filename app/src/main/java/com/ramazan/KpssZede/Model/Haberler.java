package com.ramazan.KpssZede.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by ramazan on 14.12.2015.
 */
@Table(name = "Haberler")
public class Haberler extends Model {


    @Column(name = "zaman")
    public String zaman;

    @Column(name = "baslik")
    public String baslik;

    @Column(name = "ozet")
    public String ozet;

    @Column(name = "icerik")
    public String icerik;

    @Column(name = "resim")
    public String resim;


    public Haberler(){}

    public Haberler(String zaman, String baslik, String ozet, String icerik, String resim) {
        this.zaman = zaman;
        this.baslik = baslik;
        this.ozet = ozet;
        this.icerik = icerik;
        this.resim = resim;
    }

    public List<Haberler> getAll(){
        return new Select().from(Haberler.class).execute();
    }

    public Boolean isExist(String zaman){
        return new Select().from(Haberler.class).where("zaman=?",zaman).exists();


    }

    public List<Haberler> getDatafromId(int id){

        return new Select().from(Haberler.class).where("id=?",id).execute();
    }

}
