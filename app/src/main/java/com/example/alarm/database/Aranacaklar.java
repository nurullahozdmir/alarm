package com.example.alarm.database;

public class Aranacaklar {

    public static final String TABLE_NAME_ARA = "aranacaklar";

    public static final String ID = "id";
    public static final String AD_SOYAD = "adsoyad";
    public static final String TEL_NO = "tel_no";
    public static final String TARIH = "tarih";
    public static final String SAAT = "saat";


    private int id;
    private String adsoyad;
    private String tel_no;
    private String tarih;
    private String saat;



    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME_ARA + "("
                    + ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + AD_SOYAD + " TEXT,"
                    + TEL_NO + " INTEGER,"
                    + TARIH + " TEXT,"
                    + SAAT + " TEXT"
                    + ")";

    public Aranacaklar() {

    }

    public Aranacaklar(int id, String adsoyad, String tel_no, String tarih, String saat) {
        this.id = id;
        this.adsoyad = adsoyad;
        this.tel_no = tel_no;
        this.tarih = tarih;
        this.saat = saat;
    }

    public String getSaat() {
        return saat;
    }

    public void setSaat(String saat) {
        this.saat = saat;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAdsoyad() {
        return adsoyad;
    }

    public void setAdsoyad(String adsoyad) {
        this.adsoyad = adsoyad;
    }

    public String getTel_no() {
        return tel_no;
    }

    public void setTel_no(String tel_no) {
        this.tel_no = tel_no;
    }
}
