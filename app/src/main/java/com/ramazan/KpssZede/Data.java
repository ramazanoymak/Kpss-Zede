package com.ramazan.KpssZede;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.ramazan.KpssZede.Fragments.RegisterFragment;
import com.ramazan.KpssZede.Model.CografyaModel;
import com.ramazan.KpssZede.Model.GeometriModel;
import com.ramazan.KpssZede.Model.Haberler;
import com.ramazan.KpssZede.Model.MatematikModel;
import com.ramazan.KpssZede.Model.Mesages;
import com.ramazan.KpssZede.Model.SoruModel;
import com.ramazan.KpssZede.Model.TarihModel;
import com.ramazan.KpssZede.Model.TurkceModel;
import com.ramazan.KpssZede.Model.VatandaslıkModel;
import com.ramazan.KpssZede.Model.Yorum;
import com.ramazan.KpssZede.Model.abulunsun;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.NTUserPrincipal;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramazan on 18.10.2015.
 */

  public class Data extends KpsszedeApp{

     FileOutputStream fOut;
     OutputStreamWriter myOutWriter;
     BufferedWriter fbw;
    static Context _c;
    public  static int isSavedb=0;
    private static boolean serviceRunning;

    public Data(Context c) {
        _c=c;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }

    public String HttpPostForgetPassword(String url,String email) {

        InputStream inputStream = null;
        String result = "";
        try {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost post=new HttpPost(url);

            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("email", email));

            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(nameValuePair, HTTP.UTF_8);
           //ent.setContentType("application/json");
            post.setEntity(ent);

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(post);

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "veri alma başarılı deil";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    public String HttpPostLogin(String url,String email,String password) {

        InputStream inputStream = null;
        String result = "";
        try {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost post=new HttpPost(url);

            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("email", email));
            nameValuePair.add(new BasicNameValuePair("sifre", password));

            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(nameValuePair, HTTP.UTF_8);
            //ent.setContentType("application/json");
            post.setEntity(ent);


            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(post);

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "veri alma başarılı deil";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    public String HttpPostRegister(String url,String ad,String soyad,String email,String password,String resim,
                                   String reg_id) {

        InputStream inputStream = null;
        String result = "";
        try {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost post=new HttpPost(url);

            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(4);
            nameValuePair.add(new BasicNameValuePair("ad", ad));
            nameValuePair.add(new BasicNameValuePair("soyad", soyad));
            nameValuePair.add(new BasicNameValuePair("email", email));
            nameValuePair.add(new BasicNameValuePair("sifre", password));
            nameValuePair.add(new BasicNameValuePair("resim", resim));
            nameValuePair.add(new BasicNameValuePair("reg_id", reg_id));

            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(nameValuePair, HTTP.UTF_8);
            //ent.setContentType("application/json");
            post.setEntity(ent);


            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(post);

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "veri alma başarılı deil";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    public String HttpPostUpdateSifre(String url,String email,String password) {

        InputStream inputStream = null;
        String result = "";
        try {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost post=new HttpPost(url);

            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(4);
            nameValuePair.add(new BasicNameValuePair("email", email));
            nameValuePair.add(new BasicNameValuePair("password", password));
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(nameValuePair, HTTP.UTF_8);
            //ent.setContentType("application/json");
            post.setEntity(ent);


            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(post);

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "veri alma başarılı deil";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    public String HttpPostUpdateResim(String url,String email,String resim) {

        InputStream inputStream = null;
        String result = "";
        try {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost post=new HttpPost(url);

            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(4);
            nameValuePair.add(new BasicNameValuePair("email", email));
            nameValuePair.add(new BasicNameValuePair("resim", resim));
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(nameValuePair, HTTP.UTF_8);
            //ent.setContentType("application/json");
            post.setEntity(ent);


            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(post);

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "veri alma başarılı deil";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }


    public String HttpPostSendSoru(String url,String k_id,String tur_id,String s_icerik) {

        InputStream inputStream = null;
        String result = "";
        try {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost post=new HttpPost(url);

            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("k_id", k_id));
            nameValuePair.add(new BasicNameValuePair("tur_id", tur_id));
            nameValuePair.add(new BasicNameValuePair("s_icerik", s_icerik));
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(nameValuePair, HTTP.UTF_8);
            //ent.setContentType("application/json");
            post.setEntity(ent);

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(post);

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "veri alma başarılı deil";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    public String HttpPostGetGuncelSoru(String url,String tur_id,String k_id,String sayac) {
        InputStream inputStream = null;
        String result = "";
        try {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost post=new HttpPost(url);

            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("tur_id", tur_id));
            nameValuePair.add(new BasicNameValuePair("k_id", k_id));
            nameValuePair.add(new BasicNameValuePair("sayac", sayac));

            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(nameValuePair, HTTP.UTF_8);
            //ent.setContentType("application/json");
            post.setEntity(ent);


            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(post);

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "veri alma başarılı deil";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    public String HttpPostSendYorum(String url,String mid,String ad,String yorum) {

        InputStream inputStream = null;
        String result = "";
        try {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost post=new HttpPost(url);

            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("mid", mid));
            nameValuePair.add(new BasicNameValuePair("ad", ad));
            nameValuePair.add(new BasicNameValuePair("yorum", yorum));

            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(nameValuePair, HTTP.UTF_8);
            //ent.setContentType("application/json");
            post.setEntity(ent);


            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(post);

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "veri alma başarılı deil";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    public String HttpPostGetYorum(String url,String mid) {

        InputStream inputStream = null;
        String result = "";
        try {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost post=new HttpPost(url);

            List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
            nameValuePair.add(new BasicNameValuePair("mid", mid));
            UrlEncodedFormEntity ent = new UrlEncodedFormEntity(nameValuePair, HTTP.UTF_8);
            //ent.setContentType("application/json");
            post.setEntity(ent);
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(post);

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "veri alma başarılı deil";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    public static  String GET(String url) {
        InputStream inputStream;
        String result = "";
        try {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "veri alma başarılı deil";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
        return result;
    }

    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;
    }

    public List<Haberler> haberler(String data){

        String zaman="";
        String baslik="";
        String ozet="";
        String resim="";
        String icerik="";
        try {

            JSONObject post = (new JSONObject(data));
            JSONArray List = post.getJSONArray("posts");

            for (int i = 0; i < List.length(); i++) {

                JSONObject o = List.getJSONObject(i);

                zaman=o.getJSONObject("Tarih").getString("0");
                baslik = o.getJSONObject("baslik").getString("0");
                ozet = o.getJSONObject("ozet").getString("0");
                resim = o.getJSONObject("resim").getString("0");
                icerik = o.getString("icerik");

        if (!new Haberler().isExist(zaman)){
                 Haberler h=new Haberler(zaman,baslik,ozet,icerik,resim);
                 h.save();
                }
            }
        }
        catch (JSONException e){
            e.printStackTrace();}

        return new Haberler().getAll();
    }

    public List<Mesages> messages(String data){

        int id;
        String ad="";
        String resim="";
        String mesaj="";
        String zaman="";
            try {
                JSONObject jObj = new JSONObject(data);
                int error= jObj.getInt("error");
                if (error==0) {
                    JSONArray List = jObj.getJSONArray("mesaj");
                    for (int i = 0; i < List.length(); i++) {

                        JSONObject o = List.getJSONObject(i);

                        id=o.getInt("id");
                        ad=o.getString("ad").toString();
                        resim=o.getString("resim").toString();
                        mesaj=o.getString("mesaj").toString();
                        zaman=o.getString("time").toString();

                        if (!new Mesages().isExist(id)) {
                            Mesages m = new Mesages(ad, resim, mesaj,id,zaman);
                            m.save();//varsa zaten kyıt ettim.hem server hem burdan silcem
                        }
                       /* else { //varsa silsin
                            new Delete().from(Mesages.class).where("remoteID=?", id).execute();
                        }*/
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        return new  Mesages().getAll();
    }

    public List<SoruModel> SoruListe(String data){
        List<SoruModel> ListSoru=null;
        int sid;
        int kid;
        int tid;
        String kad="";
        String kresim="";
        String soru="";
        String zaman="";
        try {
            JSONObject jObj = new JSONObject(data);
            int error= jObj.getInt("error");
            if (error==0) {
                JSONArray List = jObj.getJSONArray("soru");
                for (int i = 0; i < List.length(); i++) {

                    JSONObject o = List.getJSONObject(i);
                    sid=o.getInt("sid");
                    kid=o.getInt("kid");
                    tid=o.getInt("tid");
                    kad=o.getString("kad").toString();
                    kresim=o.getString("kresim").toString();
                    soru=o.getString("sicerik").toString();
                    zaman=o.getString("time").toString();

                    if (isSavedb==1){
                        SavedbSoru(tid,kid,sid,kad,kresim,soru,zaman);
                    }
                    SoruModel soruModel=new SoruModel();
                    soruModel.setK_adi(kad);
                    soruModel.setK_id(kid);
                    soruModel.setK_resim(kresim);
                    soruModel.setS_icerik(soru);
                    soruModel.setTime(zaman);
                    soruModel.setT_id(tid);
                    soruModel.setSid(sid);
                    ListSoru.add(soruModel);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return ListSoru;
    }
    private void SavedbSoru(int tid,int kid,int sid,String kad,String kresim,String soru,String zaman){
    int lenght=0;
    switch (tid){
        case 1:
            lenght =new TurkceModel().getAll().size();
            if (lenght<25){
                if (!new TurkceModel().isExist(sid)) {
                    TurkceModel m = new TurkceModel(sid,tid,zaman,kresim,kad,kid,"",soru);
                    m.save();
                }
            }
            else {
                try{
                new TurkceModel().deleteLastItem();
                if (!new TurkceModel().isExist(sid)) {
                    TurkceModel m = new TurkceModel(sid,tid,zaman,kresim,kad,kid,"",soru);
                    m.save();
                }
            }catch (Exception ex){}
            }
            break;
        case 2:
            lenght =new MatematikModel().getAll().size();
            if (lenght<25){
                if (!new MatematikModel().isExist(sid)) {
                    MatematikModel m = new MatematikModel(sid,tid,zaman,kresim,kad,kid,"",soru);
                    m.save();
                }
            }
            else
            {try{
                new MatematikModel().deleteLastItem();
                if (!new MatematikModel().isExist(sid)) {
                    MatematikModel m = new MatematikModel(sid,tid,zaman,kresim,kad,kid,"",soru);
                    m.save();
                }
            }catch (Exception ex){}
            }break;
        case 3:
            lenght =new GeometriModel().getAll().size();
            if (lenght<25){
                if (!new GeometriModel().isExist(sid)) {
                    GeometriModel m = new GeometriModel(sid,tid,zaman,kresim,kad,kid,"",soru);
                    m.save();
                }
            }
            else
            {try{
                new GeometriModel().deleteLastItem();
                if (!new GeometriModel().isExist(sid)) {
                    GeometriModel m = new GeometriModel(sid,tid,zaman,kresim,kad,kid,"",soru);
                    m.save();
                }
            }catch (Exception ex){}
            }
            break;
        case 4:
            lenght =new TarihModel().getAll().size();
            if (lenght<25){
                if (!new TarihModel().isExist(sid)) {
                    TarihModel m = new TarihModel(sid,tid,zaman,kresim,kad,kid,"",soru);
                    m.save();
                }
            }
            else {
                try{
                    new TarihModel().deleteLastItem();
                    if (!new TarihModel().isExist(sid)) {
                        TarihModel m = new TarihModel(sid,tid,zaman,kresim,kad,kid,"",soru);
                        m.save();
                    }
                }catch (Exception ex){}
            }break;
        case 5:
            lenght =new CografyaModel().getAll().size();
            if (lenght<25){
                if (!new CografyaModel().isExist(sid)) {
                    CografyaModel m = new CografyaModel(sid,tid,zaman,kresim,kad,kid,"",soru);
                    m.save();
                }
            }
            else {
                try{
                    new CografyaModel().deleteLastItem();
                    if (!new CografyaModel().isExist(sid)) {
                        CografyaModel m = new CografyaModel(sid,tid,zaman,kresim,kad,kid,"",soru);
                        m.save();
                    }
                }catch (Exception ex){}
            }break;
        case 6:
            lenght =new VatandaslıkModel().getAll().size();
            if (lenght<25){
                if (!new VatandaslıkModel().isExist(sid)) {
                    VatandaslıkModel m = new VatandaslıkModel(sid,tid,zaman,kresim,kad,kid,"",soru);
                    m.save();
                }
            }
            else {
                try{
                    new VatandaslıkModel().deleteLastItem();
                    if (!new VatandaslıkModel().isExist(sid)) {
                        VatandaslıkModel m = new VatandaslıkModel(sid,tid,zaman,kresim,kad,kid,"",soru);
                        m.save();
                    }
                }catch (Exception ex){}
            }
            break;
        default:break;
    }
}

    public List<abulunsun> Abulunsun(String data) {

        String icerik="";
        int id;
        try {

            JSONObject post = (new JSONObject(data));
            JSONArray List = post.getJSONArray("veri");
            for (int i = 0; i < List.length(); i++) {

                JSONObject o = List.getJSONObject(i);
                id=o.getInt("id");
                icerik = o.getString("icerik");

                if (!new abulunsun().isExist(id)){
                    abulunsun a=new abulunsun(id,icerik);
                    a.save();
                }
            }
        }
        catch (JSONException e){
            e.printStackTrace();}
        return new abulunsun().getAll();
    }

    public List<Yorum> yorumlar(String data){
        int mid=0;
        int yid;
        String ad="";
        String yorum="";
        String zaman="";
        try {
            JSONObject jObj = new JSONObject(data);
            int error= jObj.getInt("error");
            if (error==0) {
                JSONArray List = jObj.getJSONArray("yorum");
                for (int i = 0; i < List.length(); i++) {

                    JSONObject o = List.getJSONObject(i);

                    mid=o.getInt("mid");
                    yid=o.getInt("id");
                    ad=o.getString("ad").toString();
                    yorum=o.getString("yorum").toString();
                    zaman=o.getString("time").toString();

                    if (!new Yorum().isExist(yid)) {
                        Yorum y = new Yorum(yid,mid,ad,yorum,zaman);
                        y.save();//varsa zaten kyıt ettim.hem server hem burdan silcem
                    }
                       /* else { //varsa silsin
                            new Delete().from(Yorum.class).where("remoteID=?", id).execute();
                        }*/
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return new  Yorum().getAll(mid);
    }

    public String[] checkServer(String strReq) {

        String[] dizi=new String[2];
        try {
            JSONObject jObj = new JSONObject(strReq);
            int error = jObj.getInt("error");
            String chechk=jObj.getString("error");
            dizi[0]=chechk;
            // Check for error node in json
            if (error==0) {
                dizi[1]=jObj.getString("message");

            } else {
                dizi[1]=jObj.getString("message");
            }
        } catch (JSONException e) {
            // JSON error
            e.printStackTrace();
            Toast.makeText(_c, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return dizi;

    }

    public String[] checkServerİmgUpdate(String strReq) {

        String[] dizi=new String[2];
        try {
            JSONObject jObj = new JSONObject(strReq);
            int error = jObj.getInt("error");
            String chechk=jObj.getString("error");
            dizi[0]=chechk;
            // Check for error node in json
            if (error==0) {
                dizi[1]=jObj.getString("resim");

            } else {
                dizi[1]=jObj.getString("message");
            }
        } catch (JSONException e) {
            // JSON error
            e.printStackTrace();
            Toast.makeText(_c, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return dizi;

    }

    public String[] checkUser(String strReq) {

        String[] dizi=new String[2];
        try {

            JSONObject jObj = new JSONObject(strReq);
            int error = jObj.getInt("error");
            String msg=jObj.getString("message");
            String chechk=jObj.getString("error");

            // Check for error node in json
            if (error==0) {
                // user successfully logged in
                JSONObject user = jObj.getJSONObject("user");

                int id=user.getInt("id");
                String ad = user.getString("ad");
                String soyad = user.getString("soyad");
                String mail = user.getString("email");
                String sifre=user.getString("password");
                String resim=user.getString("resim");

                String data=id+","+ad+","+soyad+","+mail+","+sifre+","+resim;
                datayaz(data);

                dizi[0]=chechk;
                dizi[1]=msg;

            } else {

                dizi[0]=chechk;
                dizi[1]=msg;
            }
        } catch (JSONException e) {
            // JSON error
            e.printStackTrace();
            Toast.makeText(_c, "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return dizi;

    }

    public  void datayaz(String data){
        try {
            fOut =_c.getApplicationContext().openFileOutput("user.txt", _c.MODE_PRIVATE);
            myOutWriter = new OutputStreamWriter(fOut);
            fbw = new BufferedWriter(myOutWriter);
            fbw.write(data);
            fbw.newLine();
            fbw.close();
            myOutWriter.close();
            fOut.close();
            fOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String[] dosyadoku() {

        String[] datas=null;
        InputStreamReader in = null;

        try {
            in=new InputStreamReader((_c.openFileInput("user.txt")));
            BufferedReader bufferedReader = new BufferedReader(in);
            String satir="";
            while ((satir=bufferedReader.readLine())!= null) {
                datas=satir.split(",");
            }
            in.close();
        } catch (FileNotFoundException e) {
            Log.e("favori activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        return datas;
    }

    public List<Mesages> getDbMessages(){
        return new  Mesages().getAll();
    }

    public List<abulunsun> getDbabulunsun(){
        return new abulunsun().getAll();
    }

    public static boolean isServiceRunning() {
        return serviceRunning;
    }

    public void setServiceRunning(boolean serviceRunning) {
        this.serviceRunning = serviceRunning;
    }


}
