package com.ramazan.KpssZede;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.ramazan.KpssZede.Model.Haberler;
import com.ramazan.KpssZede.Model.abulunsun;

import java.util.List;

/**
 * Created by ramazan on 26.12.2015.
 */
public class AppService extends Service {

    Data servis;
    UpdaterHaber updaterHaber;

    private boolean runFlag = false;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        servis=new Data(getApplicationContext());
        this.updaterHaber = new UpdaterHaber();

    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        this.updaterHaber.start();
        runFlag=true;
        this.servis.setServiceRunning(true);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.updaterHaber.interrupt();
        this.servis.setServiceRunning(false);

    }

    private class UpdaterHaber extends  Thread{

        List<Haberler> haberlers;
        List<abulunsun> _abulunsun=null;

        @Override
        public void run() {
            super.run();
            AppService _service=AppService.this;
                try {
                    if (isConnected()) {
                        String data = servis.GET(AppConfig.URL_haberler);
                        haberlers = servis.haberler(data);

                        String veri=servis.GET(AppConfig.URL_Abulunsun);
                        _abulunsun=servis.Abulunsun(veri);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            haberlers=null;
            _abulunsun=null;
            _service.onDestroy();
        }
    }

    private class UpdaterMessage extends  Thread{

        List<Haberler> haberlers;
        List<abulunsun> _abulunsun=null;

        @Override
        public void run() {
            super.run();
            AppService _service=AppService.this;
            try {
                if (isConnected()) {
                    String data = servis.GET(AppConfig.URL_haberler);
                    haberlers = servis.haberler(data);

                    String veri=servis.GET(AppConfig.URL_Abulunsun);
                    _abulunsun=servis.Abulunsun(veri);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            haberlers=null;
            _abulunsun=null;
            _service.onDestroy();
        }
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
