package com.f221103807.edittextandbutton;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DBNAME = "project.db";
    public DBHelper(Context context) {super(context, "project.db", null, 1);}
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table mahasiswa(nim text primary key, nama text, kelas text, semester text, jurusan text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists mahasiswa");
    }
    public Boolean checknim(String nim){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from mahasiswa where nim = ?", new String[]{nim});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
    public Boolean insertDataMhs(String nim, String nama, String kelas, String semester, String jurusan){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nim", nim);
        values.put("nama", nama);
        values.put("kelas", kelas);
        values.put("semester", semester);
        values.put("jurusan", jurusan);
        long result = db.insert("mahasiswa", null, values);
        if (result == 1)
            return false;
        else
            return true;
    }

    public Cursor tampilDataMhs(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery( "select * from mahasiswa", null );
        return cursor;
    }
}
