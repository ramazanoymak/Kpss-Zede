package com.ramazan.KpssZede.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
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

import com.ramazan.KpssZede.Activity.MainActivity;
import com.ramazan.KpssZede.AppConfig;
import com.ramazan.KpssZede.Data;
import com.ramazan.KpssZede.R;
import com.ramazan.KpssZede.SessionManager;
import static com.ramazan.KpssZede.AppConfig.SENDER_ID;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import com.google.android.gcm.GCMRegistrar;


/**
 * Created by ramazan on 07.12.2015.
 */
public class RegisterFragment extends Fragment {

    Context _c;
    private SessionManager session;
    Data servis;

    private ImageView imageView;
    Uri selectedImage;
    private Bitmap bitmapUser;

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
    String regId;

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
           // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(_c);
        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(_c);
        // Get GCM registration id
       regId = GCMRegistrar.getRegistrationId(_c);


        // Register Button Click event
        btnRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if (((MainActivity) getActivity()).isConnected()) {

                    name = inputFullName.getText().toString().trim();
                    sname = inputsName.getText().toString().trim();
                    email = inputEmail.getText().toString().trim();
                    password = inputPassword.getText().toString().trim();
                    if (!name.isEmpty() && !sname.isEmpty() && !email.isEmpty() && !password.isEmpty()) {

                        if (bitmapUser!=null){
                            Bitmap İmageUser = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
                            //buraya string olarak resmi çek
                            resim = getStringImage(İmageUser,Bitmap.CompressFormat.JPEG, 100);//decode et
                            if (regId.equals("")) {
                                // Registration is not present, register now with GCM
                                GCMRegistrar.register(_c, SENDER_ID);
                                registerUser.start();
                            }
                            else {
                                // Device is already registered on GCM
                                if (GCMRegistrar.isRegisteredOnServer(_c)) {
                                    // Skips registration.
                                    Toast.makeText(_c, "cihaz kayitli zaten", Toast.LENGTH_LONG).show();
                                } else {
                                    registerUser.start();
                                }
                            }
                        }
                        else{
                            Toast.makeText(_c, "Lütfen profil resmi seçiniz", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(_c, "Lütfen eksik bilgi girmeyiniz!", Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(_c, "Lütfen bağlantınızı kontrol ediniz!", Toast.LENGTH_LONG).show();
                }

            }
        });

        // Link to Login Screen
        btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                ((MainActivity) getActivity()).fragment(6);
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
        GenelKulturFragment.loginFlag=1;
        GenelYetenekFragment genelYetenekFragment = new GenelYetenekFragment(getActivity().getApplicationContext());
        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, genelYetenekFragment).commit();

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
                    bitmapUser = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    resizeBitmap=getResizedBitmap(bitmapUser,110,110);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ExifInterface exif = new ExifInterface(data.getData().getPath());
                int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
                Matrix matrix = new Matrix();
                if (orientation == 6) {
                    matrix.postRotate(90);
                }
                else if (orientation == 3) {
                    matrix.postRotate(180);
                }
                else if (orientation == 8) {
                    matrix.postRotate(270);
                }
                resizeBitmap = Bitmap.createBitmap(resizeBitmap, 0, 0, resizeBitmap.getWidth(), resizeBitmap.getHeight(), matrix, true); // rotating bitmap
                imageView.setImageBitmap(resizeBitmap);

            } else {
                Toast.makeText(_c, "Resim seçmediniz",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(_c, "Bir hata oluştu.", Toast.LENGTH_LONG)
                    .show();
        }

    }

    public String getStringImage(Bitmap bmp,Bitmap.CompressFormat compressFormat, int quality){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(compressFormat, quality, baos);
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
            String strReq = servis.HttpPostRegister(AppConfig.URL_REGISTER, name, sname, email, password,resim,regId);
            String[] Scevap=servis.checkUser(strReq);
            int durum=Integer.parseInt(Scevap[0]);
            if (durum==0){
                loginUser();//soru fragmente git
            }
            else {
                String msg=Scevap[1].toString();
                Toast.makeText(_c, msg, Toast.LENGTH_LONG).show();
            }
        }
    }
}
