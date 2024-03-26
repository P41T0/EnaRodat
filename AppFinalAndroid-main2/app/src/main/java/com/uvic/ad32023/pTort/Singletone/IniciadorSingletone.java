package com.uvic.ad32023.pTort.Singletone;

import android.app.Application;
import android.util.Log;

public class IniciadorSingletone extends Application {
    @Override
    public void onCreate() {
        Log.i("ProjF","Singleton iniciat!");
        super.onCreate();
        singletone_monuments.getInstance().setContext(this);
    }
}
