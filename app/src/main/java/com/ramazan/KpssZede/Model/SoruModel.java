package com.ramazan.KpssZede.Model;


/**
 * Created by ramazan on 29.03.2016.
 */
public class SoruModel {
    public int sid;
    public int t_id;
    public String s_icerik;
    public String s_resim;
    public int k_id;
    public String k_adi;
    public String k_resim;
    public String time;

    public SoruModel(int sid, int t_id, String s_icerik, String s_resim, int k_id, String k_adi, String k_resim, String time) {
        this.sid = sid;
        this.t_id = t_id;
        this.s_icerik = s_icerik;
        this.s_resim = s_resim;
        this.k_id = k_id;
        this.k_adi = k_adi;
        this.k_resim = k_resim;
        this.time = time;
    }

    public SoruModel(){}

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getT_id() {
        return t_id;
    }

    public void setT_id(int t_id) {
        this.t_id = t_id;
    }

    public String getS_icerik() {
        return s_icerik;
    }

    public void setS_icerik(String s_icerik) {
        this.s_icerik = s_icerik;
    }

    public String getS_resim() {
        return s_resim;
    }

    public void setS_resim(String s_resim) {
        this.s_resim = s_resim;
    }

    public int getK_id() {
        return k_id;
    }

    public void setK_id(int k_id) {
        this.k_id = k_id;
    }

    public String getK_adi() {
        return k_adi;
    }

    public void setK_adi(String k_adi) {
        this.k_adi = k_adi;
    }

    public String getK_resim() {
        return k_resim;
    }

    public void setK_resim(String k_resim) {
        this.k_resim = k_resim;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
