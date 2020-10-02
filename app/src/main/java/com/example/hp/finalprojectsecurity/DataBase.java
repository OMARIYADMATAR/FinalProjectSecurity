package com.example.hp.finalprojectsecurity;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class DataBase extends SQLiteOpenHelper {
    SQLiteDatabase db = this.getWritableDatabase();

    public static final int DATABASE_VERSION = 7;
    public static final String DATABASE_NAME = "User";
    public static final String TABLE_NAME = "users";
    public static final String COLUMN_ID = "_UserID";
    public static final String COLUMN_phoone = "UserPhoone";
    public static final String COLUMN_NAME = "UserName";
    public static final String COLUMN_PASSWORD = "UserPassword";

    public DataBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String CREATE_TABLE = "CREATE table users(" + COLUMN_ID + " INTEGER primary key AUTOINCREMENT," + COLUMN_NAME + " TEXT NOT NULL,"+COLUMN_phoone+" TEXT,"+COLUMN_PASSWORD+" TEXT);";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public  boolean Insert_data(String name,String phoone,String password){

        ContentValues values = new ContentValues();

        String ENCpassword=encryption(password);
        values.put(DataBase.COLUMN_PASSWORD,ENCpassword);
        values.put(DataBase.COLUMN_NAME,name);
        values.put(DataBase.COLUMN_phoone,phoone);


        Long result= db.insert(DataBase.TABLE_NAME, null, values);
        if(result==-1){
            return false;

        }else{
            db.close();
            return true;
        }


    }


    public Cursor Return_data(String name, String password){

        Cursor cursor=db.rawQuery("select * from "+TABLE_NAME+" where "+COLUMN_NAME+"='"+name+"' and "+COLUMN_PASSWORD+"= '"+encryption(password)+"'",null);
        return cursor;
    }

    public Cursor Return_all_data(){

        Cursor cursor=db.rawQuery("select * from "+TABLE_NAME,null);
        return cursor;
    }

    public String encryption(String strNormalText){
        String seedValue = "YourSecKey";
        String normalTextEnc="";
        try {
            normalTextEnc = AESHelper.encrypt(seedValue, strNormalText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return normalTextEnc;
    }


}