package com.ramazan.KpssZede.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.support.design.widget.NavigationView;


//import android.support.v7.app.ActionBarDrawerToggle;

import android.support.v4.app.ActionBarDrawerToggle;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;


import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.ramazan.KpssZede.AppService;
import com.ramazan.KpssZede.Data;
import com.ramazan.KpssZede.DiskCache;
import com.ramazan.KpssZede.Fragments.AbulunsunFragment;
import com.ramazan.KpssZede.Fragments.AyarlarFragment;
import com.ramazan.KpssZede.Fragments.GenelKulturFragment;
import com.ramazan.KpssZede.Fragments.GenelYetenekFragment;
import com.ramazan.KpssZede.Fragments.HaberFragment;
import com.ramazan.KpssZede.Fragments.LoginFragment;
import com.ramazan.KpssZede.Fragments.ProfilFragment;
import com.ramazan.KpssZede.Fragments.RegisterFragment;
import com.ramazan.KpssZede.Fragments.SifremiUnuttumFragment;
import com.ramazan.KpssZede.ImageLoader;
import com.ramazan.KpssZede.R;
import com.ramazan.KpssZede.SessionManager;

public class MainActivity extends  AppCompatActivity {

    private CharSequence mTitle;
    private SessionManager session;
    ActionBarDrawerToggle actionBarDrawerToggle;
    //Defining Variables
    private Toolbar toolbar;
    private NavigationView navigationView;
    public DrawerLayout drawerLayout;
    // AdView adView;

    public static TextView ad;
    public static ImageView resim;
    String user[]=null;
    int loader;
    ImageLoader imgLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if( getIntent().getBooleanExtra("Exit me", false)){
            finish();
            return; // add this to prevent from doing unnecessary stuffs
        }
        fragment(0);
            // Intent intentStartService = new Intent(MainActivity.this,AppService.class);
               // startService(intentStartService);

       /* AdView adView = (AdView) this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);  Admob reklemı */

        session = new SessionManager(getApplicationContext());

        //Initializing NavigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        // Initializing Toolbar and setting it as the actionbar
        toolbar = (Toolbar) findViewById(R.id.tool_bar);

        if (toolbar != null) {
            toolbar.setTitle(R.string.app_name);
            setSupportActionBar(toolbar);
            // You were missing this setHomeAsUpIndicator
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer1);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mTitle=menuItem.getTitle();
                //Checking if the item is in checked state or not, if not make it in checked state
                if(menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();
                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()){
                    case R.id.genelYetenek:
                        setTitle(mTitle);
                        fragment(0);
                        return true;
                    case R.id.genelkultur:
                        setTitle(mTitle);
                        fragment(1);
                        return true;
                    case R.id.haberler:
                        setTitle(mTitle);
                        fragment(2);
                        return true;
                    case R.id.abulunsun:
                        setTitle(mTitle);
                        fragment(3);
                        return true;
                    case R.id.profile:
                        setTitle(mTitle);
                        fragment(4);
                        return true;
                    case R.id.ayarlar:
                        setTitle(mTitle);
                        fragment(5);
                        return true;
                    case R.id.oturum:
                        Menu menu = navigationView.getMenu();
                        MenuItem toggleItem = menu.findItem(R.id.oturum);
                        if (session.isLoggedIn()){
                            session.setLogin(false);
                            toggleItem.setTitle("Oturum Aç");
                            setTitle("Oturumu Aç");
                            ad.setText("KpssZede");
                            resim.setImageResource(loader);
                            fragment(6);
                        }
                        else{
                            setTitle("Oturumu Kapat");
                            toggleItem.setTitle("Oturumu Kapat");
                            fragment(6);
                        }
                        return true;

                    default:
                        Toast.makeText(getApplicationContext(),"Somethings Wrong",Toast.LENGTH_SHORT).show();
                        return true;

                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        ad=(TextView)findViewById(R.id.username);
        resim=(ImageView)findViewById(R.id.profile_image);
        loader = R.drawable.test;
        imgLoader = new ImageLoader(getApplicationContext(),1);

        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.drawable.ic_drawer1,
                R.string.openDrawer,
                R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getSupportActionBar().setTitle(mTitle);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);

                //burda loginse cıkış yap yazdır deilse oturum aç yazsın
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //Drawer ayarı resim ad menü ayarlarını burda yaptım
        if (navigationView != null) {
           drawerYenile();
        }
        //calling sync state is necessay or else your hamburger icon wont show up
    }

    public void drawerYenile(){
    Menu menu = navigationView.getMenu();
    MenuItem toggleItem = menu.findItem(R.id.oturum);
    if (session.isLoggedIn()) {
        user = Data.dosyadoku();
        if (user != null) {
            ad.setText(user[1]);
            imgLoader.DisplayImage(user[5], loader, resim);
            toggleItem.setTitle("Oturumu Kapat");
        }
    } else {
        ad.setText("KpssZede");
        resim.setImageResource(loader);
        toggleItem.setTitle("Oturum Aç");
    }
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("Exit me", true);
            startActivity(intent);
            finish();
            return true;
        }
        if (id == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
            return true;
        }
       /* case android.R.id.home: burda çözümü yap
        DrawerLayout.openDrawer(GravityCompat.START);  // OPEN DRAWER
        return true;*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    public void fragment(int position){

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();

        switch(position) {
            case 0 :
                GenelYetenekFragment genelYetenekFragment = new GenelYetenekFragment(getApplicationContext());
                fragmentManager.beginTransaction().replace(R.id.content_frame, genelYetenekFragment).commit();
                break;
            case 1 :
                GenelKulturFragment genelKulturFragment = new GenelKulturFragment(getApplicationContext());
                fragmentManager.beginTransaction().replace(R.id.content_frame, genelKulturFragment).commit();
                break;
            case 2 :
                HaberFragment haberFragment = new HaberFragment(getApplicationContext(),position);
                Bundle args = new Bundle();
                args.putInt(HaberFragment.ARG_PLANET_NUMBER,position);
                haberFragment.setArguments(args);
                fragmentManager.beginTransaction().replace(R.id.content_frame, haberFragment).commit();
                break;
            case 3 :
                AbulunsunFragment abulunsunFragment = new AbulunsunFragment();
                fragmentManager.beginTransaction().replace(R.id.content_frame, abulunsunFragment).commit();
                break;
            case 4 :
                ProfilFragment profilFragment = new ProfilFragment();
                fragmentManager.beginTransaction().replace(R.id.content_frame, profilFragment).commit();
                break;
            case 5 :
                AyarlarFragment ayarlarFragment = new AyarlarFragment(getApplicationContext());
                fragmentManager.beginTransaction().replace(R.id.content_frame, ayarlarFragment).commit();
                break;
            case 6 :
                LoginFragment loginFragment = new LoginFragment(getApplicationContext());
                fragmentManager.beginTransaction().replace(R.id.content_frame, loginFragment).commit();
                break;
            case 7 :
                RegisterFragment registerFragment = new RegisterFragment(getApplicationContext());
                fragmentManager.beginTransaction().replace(R.id.content_frame, registerFragment).commit();
                break;
            case 8 :
                SifremiUnuttumFragment sifremiUnuttumFragment = new SifremiUnuttumFragment(getApplicationContext());
                fragmentManager.beginTransaction().replace(R.id.content_frame, sifremiUnuttumFragment).commit();
                break;

        }
    }

    public  boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

}