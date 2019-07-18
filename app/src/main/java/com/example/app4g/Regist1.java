package com.example.app4g;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Regist1 extends AppCompatActivity {
    //    @BindView(R.id.edNik)
//    EditText edNik;
    @BindView(R.id.edPass)
    EditText edPass;
    @BindView(R.id.edNamaAnak)
    EditText edNamaAnak;
    @BindView(R.id.edTgl)
    EditText edTgl;
    @BindView(R.id.edTlp)
    EditText edTlp;

    String strNik, strPass, strNamaAnak, strTgl, strTlp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist1);
        ButterKnife.bind(this);
        //Intent getData = getIntent();

        //strNik = getData.getStringExtra("nik");

        //edNik.setText(strNik);
    }

    @OnClick(R.id.btnRegis)
    void btnRegis() {


        //strNik = edNik.getText().toString();
        strPass = edPass.getText().toString();
        strTgl = edTgl.getText().toString();
        strNamaAnak = edNamaAnak.getText().toString();

        if (strNamaAnak.isEmpty() || strTgl.isEmpty() || strPass.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Lengkapi data !", Toast.LENGTH_LONG).show();
        } else {
            Intent a = new Intent(Regist1.this, Login.class);
            Toast.makeText(getApplicationContext(),
                    "Berhasil Daftar", Toast.LENGTH_LONG).show();
            //a.putExtra("nik", strNik);
            startActivity(a);
            finish();
        }
    }
        @OnClick(R.id.btnMasuk)
        void btnMasuk () {
            Intent a = new Intent(Regist1.this, Login.class);
            startActivity(a);
            finish();
        }
    }
