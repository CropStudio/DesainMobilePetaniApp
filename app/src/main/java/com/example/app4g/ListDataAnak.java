package com.example.app4g;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import butterknife.OnClick;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app4g.petani.Dashboard;
import com.example.app4g.petani.MenuUtama;
import com.example.app4g.users.login.Login;
import com.example.app4g.users.registrasi.Regist;

import butterknife.BindView;
import butterknife.ButterKnife;



public class ListDataAnak extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data_anak);
        ButterKnife.bind(this);
        getSupportActionBar().hide(); //untuk menghilangkan action bar yg di atas
    }
    @Override
    public void onBackPressed() { //ini untuk tombol fisik kembali
        Intent a = new Intent(ListDataAnak.this, MenuUtama.class);
        startActivity(a);
        finish();
    }

    @OnClick(R.id.btnTambahDataAnak)
    void btnTambahDataAnak() {
        Intent a = new Intent(ListDataAnak.this, InputDataAnak.class);
        startActivity(a);
        finish();
    }
}
