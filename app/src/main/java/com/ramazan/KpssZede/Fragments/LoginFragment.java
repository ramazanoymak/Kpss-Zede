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

import com.ramazan.KpssZede.Activity.MainActivity;
import com.ramazan.KpssZede.AppConfig;
import com.ramazan.KpssZede.Data;
import com.ramazan.KpssZede.R;
import com.ramazan.KpssZede.SessionManager;

/**
 * Created by ramazan on 06.12.2015.
 */
public class LoginFragment extends Fragment {

    Context _c;
    private Button btnLogin;
    private Button btnLinkToRegister;
    private Button btnLinkToForgetPassword;
    private EditText inputEmail;
    private EditText inputPassword;
    Data servis;
    String password;
    String email;
    String[] Scevap;
    private SessionManager session;
    // AdView adView;

    public LoginFragment(Context c){

        this._c=c;
        servis = new Data(_c);
        session = new SessionManager(_c);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        final View rootView =inflater.inflate(R.layout.login, container, false);

        inputEmail = (EditText) rootView.findViewById(R.id.email);
        inputPassword = (EditText) rootView.findViewById(R.id.password);
        btnLogin = (Button) rootView.findViewById(R.id.btnLogin);
        btnLinkToRegister = (Button) rootView.findViewById(R.id.btnLinkToRegisterScreen);
        btnLinkToForgetPassword = (Button) rootView.findViewById(R.id.btnLinkToForgetPassword);

        // Login button Click Event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                if (((MainActivity) getActivity()).isConnected()) {
                    password = inputPassword.getText().toString().trim();
                    email = inputEmail.getText().toString().trim();
                    // Check for empty data in the form
                    if (!email.isEmpty() && !password.isEmpty()) {
                        new RequestLogin().execute();
                    } else {
                        // Prompt user to enter credentials
                        Toast.makeText(_c, "Lütfen eksik bilgileri doldurun!", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(_c, "Lütfen İnternet bağlantınızı kontrol ediniz!", Toast.LENGTH_LONG).show();
                }

            }

        });

        // Link to Register Screen
        btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                ((MainActivity) getActivity()).fragment(7);
            }
        });
        btnLinkToForgetPassword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                ((MainActivity) getActivity()).fragment(8);
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

    public  class RequestLogin extends AsyncTask<String,String,String> {
           @Override
           protected String doInBackground(String... params) {
             try{
        String strReq = servis.HttpPostLogin(AppConfig.URL_LOGIN, email, password);//login.php ye gitti
        if (strReq!=null && !strReq.isEmpty()) {
        Scevap = servis.checkUser(strReq);
        int durum = Integer.parseInt(Scevap[0]);

        if (durum == 0) {
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
               return null;}
           @Override
           protected void onPostExecute(String s) {
               super.onPostExecute(s);

           }

       }

    }

