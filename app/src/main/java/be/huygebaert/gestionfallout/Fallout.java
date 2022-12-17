package be.huygebaert.gestionfallout;

import android.app.Application;
import android.content.Context;

public class Fallout extends Application {

    private static Context context;

    public void onCreate() {
        super.onCreate();
        Fallout.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return Fallout.context;
    }

}