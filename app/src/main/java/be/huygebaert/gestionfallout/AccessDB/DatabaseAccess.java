package be.huygebaert.gestionfallout.AccessDB;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseAccess {
    private DatabaseOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;
    //Contient les valeurs de la db.
    private Cursor c = null;

    private DatabaseAccess(Context context){
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DatabaseAccess getInstance(Context context){
        if(instance ==null){
            instance = new DatabaseAccess(context);
        }
        return instance;
    }

    public void openDatabase(){
        this.db = openHelper.getWritableDatabase();
    }

    public void closeDatabase(){
        if(db!=null){
            this.db.close();
        }
    }



}
