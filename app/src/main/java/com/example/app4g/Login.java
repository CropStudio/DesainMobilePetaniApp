package com.example.app4g;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends AppCompatActivity {
    @BindView(R.id.edNik)
    EditText edNik;
    @BindView(R.id.edPass)
    EditText edPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnRegis)
    void btnRegis() {
        Intent a = new Intent(Login.this, Regist.class);
        startActivity(a);
        finish();
    }

    @OnClick(R.id.btnMasuk)
    void btnMasuk() {
        String strNik, strPass;

        strNik = edNik.getText().toString();
        strPass = edPass.getText().toString();

        if (strNik.isEmpty() || strPass.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Lengkapi data !", Toast.LENGTH_LONG).show();
        } else {
            Intent a = new Intent(Login.this, MenuUtama.class);
            Toast.makeText(getApplicationContext(),
                    "Berhasil Masuk", Toast.LENGTH_LONG).show();
            a.putExtra("nik", strNik);
            startActivity(a);
            finish();
        }
    }
}
