package com.example.app4g.petani.anak.model;

public class AnakModel implements IAnak{
    String nama, tglLahir, jenisKelain, idUser;

    public AnakModel(String nama, String tglLahir, String jenisKelain, String idUser){
        this.nama = nama;
        this.tglLahir = tglLahir;
        this.jenisKelain= jenisKelain;
        this.idUser = idUser;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setTglLahir(String tglLahir) {
        this.tglLahir = tglLahir;
    }

    public void setJenisKelain(String jenisKelain) {
        this.jenisKelain = jenisKelain;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    @Override
    public String getNama() {
        return nama;
    }

    @Override
    public String getTglLajir() {
        return tglLahir;
    }

    @Override
    public String getJenisKelamin() {
        return jenisKelain;
    }

    @Override
    public String getIdUser() {
        return idUser;
    }
}
