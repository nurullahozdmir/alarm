package com.example.alarm.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.alarm.VeriModeli;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;


    private static final String DATABASE_NAME = "telefon_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void delete(String position) {

        SQLiteDatabase db = this.getWritableDatabase();
        String table = Aranacaklar.TABLE_NAME_ARA;
        String [] whereArgs = new String[] {String.valueOf(position)};
        db.delete (table, "TEL_NO = ?", whereArgs);
        db.close();

    }



    @Override
    public void onCreate(SQLiteDatabase db) {

        // tablonun create edşilmesi

        db.execSQL(Aranacaklar.CREATE_TABLE);
    }

    // Database upgrade edilmesi
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Aranacaklar.TABLE_NAME_ARA);


        // Tablo yeniden oluşturulur
        onCreate(db);
    }

    /*   MarkaKodları  */

    public long insertTelefonListesi(String adsoyad, String telno, String tarih, String saat) {

        System.out.println("geldi");
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Aranacaklar.AD_SOYAD,adsoyad);
        values.put(Aranacaklar.TEL_NO, telno);
        values.put(Aranacaklar.TARIH,tarih);
        values.put(Aranacaklar.SAAT,saat);

        long id = db.insert(Aranacaklar.TABLE_NAME_ARA, null, values);

        db.close();

        return id;
    }


    public List<VeriModeli> getTelefonListesi() {
        List<VeriModeli> tellis = new ArrayList<>();
      //      markalis.add("Marka Değeri Giriniz.");
    //    markalis.add("");// burası kaldırılacak

        tellis.add(new VeriModeli("999" ,"yok şimdilik5","888","54","43"));

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Aranacaklar.TABLE_NAME_ARA + " ORDER BY " +
                Aranacaklar.TARIH + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Aranacaklar ara = new Aranacaklar();

                ara.setId(cursor.getInt(cursor.getColumnIndex(Aranacaklar.ID)));
                ara.setAdsoyad(cursor.getString(cursor.getColumnIndex(Aranacaklar.AD_SOYAD)));
                ara.setTel_no(cursor.getString(cursor.getColumnIndex(Aranacaklar.TEL_NO)));
                ara.setTarih(cursor.getString(cursor.getColumnIndex(Aranacaklar.TARIH)));
                ara.setSaat(cursor.getString(cursor.getColumnIndex(Aranacaklar.SAAT)));

                tellis.add(new VeriModeli(ara.getTel_no(),ara.getAdsoyad(),ara.getTarih(),ara.getSaat(),"aktif"));

            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return marka list
        return tellis;
    }


}
