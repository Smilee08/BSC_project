package com.example.final_pro;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class SqliteManager extends SQLiteOpenHelper {

    private static final String dbname="dbEcontact";

    public SqliteManager(@Nullable Context context) {
        super(context, dbname, null, 1);
    }
//create table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="create Table tbl_Emergency_cont(name TEXT primary key ,contact TEXT )";
        sqLiteDatabase.execSQL(query);

    }
//if exist then dont create
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String query="Drop TABLE IF EXISTS tbl_Emergency_cont";
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }
    //insert
   /* public String addContact(String name, String contact){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put("name",name);
        cv.put("contact",contact);

       float res= db.insert("tbl_Emergency_cont",null,cv);
        if(res==-1) {
            return "Failed";
        }else{
            return "Successfully inserted";}
    }
    //featch
    public Cursor readContact(){
        SQLiteDatabase db=this.getReadableDatabase();
        String query="select * from tbl_Emergency_cont order by name desc";
        Cursor cursor=db.rawQuery(query,null);
        return cursor;
    }*/
}
