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
import android.widget.Toast;

import com.ramazan.KpssZede.AppConfig;
import com.ramazan.KpssZede.Data;
import com.ramazan.KpssZede.R;
import com.ramazan.KpssZede.SessionManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by ramazan on 07.12.2015.
 */
public class RegisterFragment extends Fragment {

    Context _c;
    private SessionManager session;
    Data servis;

    private ImageView imageView;
    Uri selectedImage;
    private Bitmap bitmap;

    private Button btnRegister;
    private Button btnLinkToLogin;
    private Button btnimgUpload;
    private EditText inputFullName;
    private EditText inputsName;
    private EditText inputEmail;
    private EditText inputPassword;
    String name;
    String sname;
    String email;
    String password;
    String resim="";;
    RegisterThread registerUser;


    public RegisterFragment(Context c){
        this._c=c;
        servis = new Data(_c);
        session = new SessionManager(_c);
        registerUser=new RegisterThread();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.register, container, false);

        inputFullName = (EditText) rootView.findViewById(R.id.name);
        inputsName = (EditText) rootView.findViewById(R.id.sname);
        inputEmail = (EditText) rootView.findViewById(R.id.email);
        inputPassword = (EditText) rootView.findViewById(R.id.password);
        btnRegister = (Button) rootView.findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) rootView.findViewById(R.id.btnLinkToLoginScreen);
        btnimgUpload = (Button) rootView.findViewById(R.id.imgUpload);
        imageView=(ImageView)rootView.findViewById(R.id.imgView);

        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if (isConnected()) {

                    name = inputFullName.getText().toString().trim();
                    sname = inputsName.getText().toString().trim();
                    email = inputEmail.getText().toString().trim();
                    password = inputPassword.getText().toString().trim();
                    if (!name.isEmpty() && !sname.isEmpty() && !email.isEmpty() && !password.isEmpty()) {

                        Bitmap bt = getResizedBitmap(bitmap, 100, 80);//yeniden boyutlandır
                        //String[] dizi = {name, sname, email, password};
                        //buraya string olarak resmi çek
                        resim = getStringImage(bt);//decode et
                        registerUser.start();

                    } else {
                        Toast.makeText(_c, "Lütfen eksik bilgi girmeyiniz!", Toast.LENGTH_LONG).show();
                    }

                }else{
                    Toast.makeText(_c, "Lütfen bağlantınızı kontrol ediniz!", Toast.LENGTH_LONG).show();
                }

            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                loginfromUser();
            }
        });

        // Link to Login Screen
        btnimgUpload.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                //resmi seç ekranda göster kayit ola tıklandığında yüksin herşeyi
                loadImagefromGallery(view);

            }
        });


        return rootView;
    }
    private void loginUser() {
        session.setLogin(true);
        ChatFragment.loginFlag=1;
        HaberFragment fragment = new HaberFragment(_c,0);
        Bundle args = new Bundle();
        args.putInt(HaberFragment.ARG_PLANET_NUMBER, 0);
        fragment.setArguments(args);
        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

    }

    private void loginfromUser() {
        LoginFragment fragment = new LoginFragment(_c);
        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

    }

    public void loadImagefromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, AppConfig.RESULT_LOAD_IMG);
    }

    // When Image is selected from Gallery
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
                Toast.makeText(_c, "Resim seçmediniz",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(_c, "Bir hata oluştu.", Toast.LENGTH_LONG)
                    .show();
        }

    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
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

    public class RegisterThread extends Thread{
        @Override
        public void run() {
            super.run();
            String strReq = servis.HttpPostRegister(AppConfig.URL_REGISTER, name, sname, email, password,resim);
            String[] Scevap=servis.checkUser(strReq);

            int durum=Integer.parseInt(Scevap[0]);
            if (durum==0){
                loginUser();//chat fragmente git
            }
            else {
                String msg=Scevap[1].toString();
                Toast.makeText(_c, msg, Toast.LENGTH_LONG).show();
            }
        }
    }

    public  boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
