package com.example.alarm;

public class VeriModeli {
    private String telefon;
    private String adSoyad;
    private String tarih;
    private String saat;
    private String durum;


    public VeriModeli(String telefon, String  adSoyad, String tarih, String saat, String durum) {
        this.telefon = telefon;
        this.adSoyad = adSoyad;
        this.tarih = tarih;
        this.saat = saat;
        this.durum = durum;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getAdSoyad() {
        return adSoyad;
    }

    public void setAdSoyad(String adSoyad) {
        this.adSoyad = adSoyad;
    }

    public String getTarih() {
        return tarih;
    }

    public void setTarih(String tarih) {
        this.tarih = tarih;
    }

    public String getSaat() {
        return saat;
    }

    public void setSaat(String saat) {
        this.saat = saat;
    }

    public String getDurum() {
        return durum;
    }

    public void setDurum(String durum) {
        this.durum = durum;
    }
}
