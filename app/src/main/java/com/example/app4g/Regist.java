package com.example.app4g;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Regist extends AppCompatActivity {
    @BindView(R.id.edNik)
    EditText edNik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
    }
    @OnClick(R.id.btnLanjut)
    void btnLanjut() {
        String strNik, strPass;

        strNik = edNik.getText().toString();

        if (strNik.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Lengkapi data !", Toast.LENGTH_LONG).show();
        } else {
            Intent a = new Intent(Regist.this, Regist1.class);
            startActivity(a);
            finish();
        }
    }
    @OnClick(R.id.btnCari)
    void btnCari() {
        String strNik;

        strNik = edNik.getText().toString();

        if (strNik.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "Lengkapi data !", Toast.LENGTH_LONG).show();
        } else {
            Intent a = new Intent(Regist.this, Regist1.class);
            startActivity(a);
            finish();
        }
    }
}
