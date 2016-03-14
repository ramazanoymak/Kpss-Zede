package com.ramazan.KpssZede.Fragments;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ramazan.KpssZede.AppConfig;
import com.ramazan.KpssZede.Data;
import com.ramazan.KpssZede.ImageLoader;
import com.ramazan.KpssZede.R;
import com.ramazan.KpssZede.SessionManager;

/**
 * Created by ramazan on 29.02.2016.
 */
public class SifremiUnuttumFragment extends Fragment {

    Context _c;
    private Button btnLogin;
    private Button btnLinkToRegister;
    private EditText inputEmail;
    Data servis;
    String password;
    String email;
    String[] Scevap;
    // AdView adView;
    String user[]=null;
    int loader;
    public SifremiUnuttumFragment(Context c){
        this._c=c;
        servis = new Data(_c);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        final View rootView =inflater.inflate(R.layout.sifremiunuttum, container, false);

        inputEmail = (EditText) rootView.findViewById(R.id.email);
        btnLogin = (Button) rootView.findViewById(R.id.btnForgetPassword);
        btnLinkToRegister = (Button) rootView.findViewById(R.id.btnLinkToRegisterScreen);

        loader = R.drawable.test;

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if (isConnected()) {

                    email = inputEmail.getText().toString().trim();
                    // Check for empty data in the form
                    if (!email.isEmpty()) {
                        new RequestForgetPassword().execute();
                    } else {
                        // Prompt user to enter credentials
                        Toast.makeText(_c, "Lütfen bilgileri eksik girmeyin!", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(_c, "Lütfen İnternet bağlantınızı kontrol ediniz!", Toast.LENGTH_LONG).show();
                }

            }

        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                RegisterUser();
            }
        });

        return rootView;
    }

    private void loginUser() {
        HaberFragment fragment = new HaberFragment(_c,0);
        Bundle args = new Bundle();
        args.putInt(HaberFragment.ARG_PLANET_NUMBER, 0);
        fragment.setArguments(args);
        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    private void RegisterUser() {
        RegisterFragment fragment = new RegisterFragment(_c);
        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    }

    public  class RequestForgetPassword extends AsyncTask<String,String,String> {
        @Override
        protected String doInBackground(String... params) {
            try{
                String strReq = servis.HttpPostForgetPassword(AppConfig.URL_ForgetPassword, email);//login.php ye gitti
                if (strReq!=null && !strReq.isEmpty()) {
                    Scevap = servis.checkServer(strReq);
                    int durum = Integer.parseInt(Scevap[0]);
                    if (durum == 0) {
                        Toast.makeText(_c, "Şifreniz E-Posta adresinize gönderilmiştir.", Toast.LENGTH_LONG).show();
                        loginUser(); //chatfragmente gönderdim
                    } else {
                        String msg = Scevap[1].toString();
                        Toast.makeText(_c, msg, Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(_c,"Olağan bir hata oluştu lütfen tekrar deneyin!",Toast.LENGTH_SHORT).show();
                }

            }
            catch (Exception e){}

            return null;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }

    }
    public  boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
