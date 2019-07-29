package com.example.app4g.data;

public class DataAnak {
    public String nik,jenisKelami, nama, tglLahir, idAnak;


    public DataAnak(){}

    public DataAnak(String nik,String jenisKelami, String nama, String tglLahir, String idAnak){
        this.nik = nik;
        this.nama = nama;
        this.tglLahir= tglLahir;
        this.jenisKelami = jenisKelami;
        this.idAnak = idAnak;
    }

    public String getIdAnak() {
        return idAnak;
    }

    public void setIdAnak(String idAnak) {
        this.idAnak = idAnak;
    }

    public String getJenisKelami() {
        return jenisKelami;
    }

    public void setJenisKelami(String jenisKelami) {
        this.jenisKelami = jenisKelami;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTglLahir() {
        return tglLahir;
    }

    public void setTglLahir(String tglLahir) {
        this.tglLahir = tglLahir;
    }
}
