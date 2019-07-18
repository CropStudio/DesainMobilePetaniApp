package com.example.app4g;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import moe.feng.common.stepperview.VerticalStepperItemView;


public class Regist extends AppCompatActivity {

    private VerticalStepperItemView mSteppers[] = new VerticalStepperItemView[3];
    private Button mNextBtn0, mNextBtn1, mPrevBtn1, mNextBtn2, mPrevBtn2;

    private int mActivatedColorRes = R.color.bg_screen3;
    private int mDoneIconRes = R.drawable.ic_done_white_16dp;

    @BindView(R.id.edNik)
    EditText edNik;
    @BindView(R.id.edTgl)
    EditText edTanggal;
    @BindView(R.id.tglan)
    LinearLayout txtTgl;

    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        getSupportActionBar().hide();
        ButterKnife.bind(this);


        mSteppers[0] = findViewById(R.id.stepper_0);
        mSteppers[1] = findViewById(R.id.stepper_1);
        mSteppers[2] = findViewById(R.id.stepper_2);

        VerticalStepperItemView.bindSteppers(mSteppers);

        mNextBtn0 = findViewById(R.id.button_next_0);
        mNextBtn0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nik = edNik.getText().toString();
                if(nik.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nik tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else {
                    mSteppers[0].nextStep();
                }
            }
        });

        findViewById(R.id.button_test_error).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mSteppers[0].getErrorText() != null) {
                    mSteppers[0].setErrorText(null);
                } else {
                    mSteppers[0].setErrorText("Test error!");
                }
            }
        });

        mPrevBtn1 = findViewById(R.id.button_prev_1);
        mPrevBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSteppers[1].prevStep();
            }
        });

        mNextBtn1 = findViewById(R.id.button_next_1);
        mNextBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSteppers[1].nextStep();
            }
        });

        mPrevBtn2 = findViewById(R.id.button_prev_2);
        mPrevBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSteppers[2].prevStep();
            }
        });

        mNextBtn2 = findViewById(R.id.button_next_2);
        mNextBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Finish!", Snackbar.LENGTH_LONG).show();
            }
        });

        findViewById(R.id.btn_change_point_color).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mActivatedColorRes == R.color.material_blue_500) {
                    mActivatedColorRes = R.color.material_deep_purple_500;
                } else {
                    mActivatedColorRes = R.color.material_blue_500;
                }
                for (VerticalStepperItemView stepper : mSteppers) {
                    stepper.setActivatedColorResource(mActivatedColorRes);
                }
            }
        });

        findViewById(R.id.btn_change_done_icon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mDoneIconRes == R.drawable.ic_done_white_16dp) {
                    mDoneIconRes = R.drawable.ic_done_white_16dp;
                } else {
                    mDoneIconRes = R.drawable.ic_done_white_16dp;
                }
                for (VerticalStepperItemView stepper : mSteppers) {
                    stepper.setDoneIconResource(mDoneIconRes);
                }
            }
        });

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        txtTgl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent a = new Intent(Regist.this, Login.class);
        startActivity(a);
        finish();
    }

    private void showDateDialog(){

        /**
         * Calendar untuk mendapatkan tanggal sekarang
         */
        Calendar newCalendar = Calendar.getInstance();

        /**
         * Initiate DatePicker dialog
         */
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                /**
                 * Method ini dipanggil saat kita selesai memilih tanggal di DatePicker
                 */

                /**
                 * Set Calendar untuk menampung tanggal yang dipilih
                 */
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);

                /**
                 * Update TextView dengan tanggal yang kita pilih
                 */
                edTanggal.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        /**
         * Tampilkan DatePicker dialog
         */
        datePickerDialog.show();
    }
}
