package com.example.app4g.petani.anak.presenter;

import android.graphics.Bitmap;

public interface IAnakPresenter {
    void clear();
    void doAnak(String nama, String tglLahir, String jenisKelamin, String idUser);
    void setProgressBarVisiblity(int visiblity);
}
