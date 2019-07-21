package com.example.app4g.users.login.presenter;

public interface ILoginPresenter {

    void clear();
    void doLogin(String nik, String passwd);
    void setProgressBarVisiblity(int visiblity);
}
