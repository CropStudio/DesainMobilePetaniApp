package com.example.app4g;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.app4g.users.login.Login;
import com.example.app4g.users.registrasi.Regist;

public class InputDataAnak extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_data_anak);
        getSupportActionBar().hide();
     }
    @Override
    public void onBackPressed() {
        Intent a = new Intent(InputDataAnak.this, ListDataAnak.class);
        startActivity(a);
        finish();
    }
}
