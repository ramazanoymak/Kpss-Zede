package com.ramazan.KpssZede.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ramazan.KpssZede.AppConfig;
import com.ramazan.KpssZede.Data;
import com.ramazan.KpssZede.ImageLoader;
import com.ramazan.KpssZede.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by ramazan on 11.03.2016.
 */
public class AyarlarFragment extends Fragment {
    Button btnKaydet;
    Button btnSifreDegistir;
    Button btnprofilResimSec;

    TextView adSoyad;
    TextView mail;
    String[] dizi;
    EditText EdtSifre;
    EditText EdtYeniSifre;
    String eskiSifre="";
    String yeniSifre="";
    Data servis;

    ImageView imageView;
    Uri selectedImage;
    Bitmap bitmap;
    String resim="";;

    final int loader = R.drawable.test;
    ImageLoader imgLoader;
    Context _c;
    UpdateThreadSifre updateThreadSifre;
    UpdateThreadResim updateThreadResim;

    AyarlarFragment(Context c){
        this._c=c;
        servis = new Data(_c);
        updateThreadSifre=new UpdateThreadSifre();
        updateThreadResim=new UpdateThreadResim();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView =inflater.inflate(R.layout.ayarlar, container, false);
        btnKaydet = (Button) rootView.findViewById(R.id.btn_ayar_kaydet);
        btnSifreDegistir = (Button) rootView.findViewById(R.id.btn_ayarSifredeistir);
        btnprofilResimSec = (Button) rootView.findViewById(R.id.ayar_imgUpload);
        imageView=(ImageView)rootView.findViewById(R.id.img_ayar_profil);
        adSoyad = (TextView) rootView.findViewById(R.id.ayar_ad_saoyad);
        mail = (TextView) rootView.findViewById(R.id.ayar_mail);
        EdtSifre = (EditText) rootView.findViewById(R.id.edt_ayar_esifre);
        EdtYeniSifre = (EditText) rootView.findViewById(R.id.edt_ayar_ysifre);

        dizi = Data.dosyadoku();
        imgLoader = new ImageLoader(getActivity().getApplicationContext(),1);
        if (dizi != null) {
            adSoyad.setText(dizi[1]+"  "+dizi[2]);
            mail.setText(dizi[3]);
            imgLoader.DisplayImage(dizi[5], loader, imageView);
        }

        btnprofilResimSec.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //resmi seç ekranda göster kayit ola tıklandığında yüksin herşeyi
                loadImagefromGallery(view);

            }
        });
        btnSifreDegistir.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (isConnected()){
                eskiSifre=EdtSifre.getText().toString().trim();
                yeniSifre=EdtSifre.getText().toString().trim();
                if (eskiSifre.equals(dizi[4])){
                    if (yeniSifre!=null && !yeniSifre.isEmpty() ){
                        updateThreadSifre.start();
                    }
                }
                else{
                    Toast.makeText(getActivity().getApplicationContext(), "Lütfen eski Şifrenizi doğru giriniz.!",
                            Toast.LENGTH_LONG).show();
                }
                }
                else{
                    Toast.makeText(_c, "Lütfen bağlantınızı kontrol ediniz!", Toast.LENGTH_LONG).show();
                }


            }
        });
        btnKaydet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (isConnected()){
                    //ayarlar.php ye gidip yeni fotoadresiyle guncelle güncelle dosyaya yaz
                    Bitmap bt = getResizedBitmap(bitmap, 100, 80);//yeniden boyutlandır
                    //String[] dizi = {name, sname, email, password};
                    //buraya string olarak resmi çek
                    resim = getStringImage(bt);//decode et

                    updateThreadResim.start();
                }
                else{
                    Toast.makeText(_c, "Lütfen bağlantınızı kontrol ediniz!", Toast.LENGTH_LONG).show();
                }

            }
        });

        return rootView;
    }
    public class UpdateThreadSifre extends Thread{
        @Override
        public void run() {
            super.run();

            String strReq = servis.HttpPostUpdateSifre(AppConfig.URL_upadeteSifre,  dizi[3], yeniSifre);
            String[] Scevap=servis.checkServer(strReq);

            int durum=Integer.parseInt(Scevap[0]);
            if (durum==0){
                String data=dizi[0]+","+dizi[1]+","+dizi[2]+","+dizi[3]+","+yeniSifre+","+dizi[5];
                servis.datayaz(data);
                Toast.makeText(_c,"işleminiz başarılı bir şekilde gerçekleştirilmiştir.", Toast.LENGTH_LONG).show();
            }
            else {
                String msg=Scevap[1].toString();
                Toast.makeText(_c, msg, Toast.LENGTH_LONG).show();
            }
        }
    }

    public class UpdateThreadResim extends Thread{
        @Override
        public void run() {
            super.run();

            String strReq = servis.HttpPostUpdateResim(AppConfig.URL_upadeteResim,  dizi[3],resim);
            String[] Scevap=servis.checkServerİmgUpdate(strReq);
            String resim=Scevap[1];
            int durum=Integer.parseInt(Scevap[0]);
            if (durum==0){
                String data=dizi[0]+","+dizi[1]+","+dizi[2]+","+dizi[3]+","+dizi[4]+","+resim;
                servis.datayaz(data);
                Toast.makeText(_c,"işleminiz başarılı bir şekilde gerçekleştirilmiştir.", Toast.LENGTH_LONG).show();
            }
            else {
                String msg=Scevap[1].toString();
                Toast.makeText(_c, msg, Toast.LENGTH_LONG).show();
            }
        }
    }



    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, AppConfig.RESULT_LOAD_IMG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == AppConfig.RESULT_LOAD_IMG && resultCode == getActivity().RESULT_OK
                    && null != data && data.getData() != null) {
                // Get the Image from data

                selectedImage = data.getData();
                Bitmap resizeBitmap=null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    resizeBitmap=getResizedBitmap(bitmap,110,110);
                    imageView.setImageBitmap(resizeBitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Resim seçmediniz",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), "Bir hata oluştu.", Toast.LENGTH_LONG)
                    .show();
        }

    }

    public Bitmap getResizedBitmap(Bitmap bm, int newWidth, int newHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                bm, 0, 0, width, height, matrix, false);
        bm.recycle();
        return resizedBitmap;
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public  boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
