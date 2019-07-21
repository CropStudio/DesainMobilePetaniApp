package com.example.app4g.users.model;

import android.graphics.Bitmap;

public class RegisterModel implements IUserRegister {

    String nik, nama, noHp, role, password;
    Bitmap ktp, kk;

    public RegisterModel(String nik, String nama, String noHp, String role, String password, Bitmap ktp, Bitmap kk){
        this.nik = nik;
        this.nama= nama;
        this.noHp=noHp;
        this.role=role;
        this.password=password;
        this.ktp = ktp;
        this.kk=kk;
    }

    public void setKtp(Bitmap ktp) {
        this.ktp = ktp;
    }

    public void setKk(Bitmap kk) {
        this.kk = kk;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public String getNik() {
        return nik;
    }

    @Override
    public String getNama() {
        return nama;
    }

    @Override
    public String getNoHp() {
        return noHp;
    }

    @Override
    public String getRole() {
        return role;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Bitmap getKtp() {
        return ktp;
    }

    @Override
    public Bitmap getKk() {
        return kk;
    }

}
