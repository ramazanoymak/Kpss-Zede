package com.ramazan.KpssZede.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;

import java.util.List;

/**
 * Created by ramazan on 29.03.2016.
 */
@Table(name = "GeometriModel")
public class GeometriModel extends Model{

        @Column(name="sid")
        public int sid;

        @Column(name="t_id")
        public int t_id;

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

        public GeometriModel(){}

        public GeometriModel(int sid, int t_id,String time, String k_resim, String k_adi, int k_id, String s_resim, String s_icerik) {
            this.sid = sid;
            this.t_id = t_id;
            this.time = time;
            this.k_resim = k_resim;
            this.k_adi = k_adi;
            this.k_id = k_id;
            this.s_resim = s_resim;
            this.s_icerik = s_icerik;
        }

        public List<GeometriModel> getAll(){
            return new Select().from(GeometriModel.class).execute();
        }

        public Boolean isExist(int sid){
            return new Select().from(GeometriModel.class).where("sid=?", sid).exists();
        }

        public List<GeometriModel> delete(int sid){
            return new Delete().from(GeometriModel.class).where("sid=?", sid).execute();
        }

        public List<GeometriModel> sorularım (int k_id){
            return new Select().from(GeometriModel.class).where("k_id=?", k_id).execute();
        }

        public List<GeometriModel> GetItemFromUserId (int k_id){
            return new Select().from(GeometriModel.class).where("k_id=?", k_id).execute();
        }

        public void Guncelle(int sid, String s_icerik, String s_resim){
            new Update(GeometriModel.class).set("s_icerik =" + s_icerik + "s_resim =" + s_resim)
                    .where("sid = ?", sid).execute();
        }

        public void deleteLastItem(){
            GeometriModel.delete(GeometriModel.class, 24);
        }
}
