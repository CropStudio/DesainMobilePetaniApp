package com.example.app4g.users.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.request.RequestOptions;
import com.example.app4g.MenuUtama;
import com.example.app4g.R;
import com.example.app4g.session.SessionManager;
import com.example.app4g.users.login.presenter.ILoginPresenter;
import com.example.app4g.users.login.presenter.LoginPresenter;
import com.example.app4g.users.login.view.ILoginview;
import com.example.app4g.users.registrasi.Regist;
import com.glide.slider.library.Animations.DescriptionAnimation;
import com.glide.slider.library.SliderLayout;
import com.glide.slider.library.SliderTypes.BaseSliderView;
import com.glide.slider.library.SliderTypes.TextSliderView;
import com.glide.slider.library.Tricks.ViewPagerEx;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends AppCompatActivity implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener, ILoginview {

    @BindView(R.id.slider)
    SliderLayout mDemoSlider;

    @BindView(R.id.nik)
    EditText edNik;
    @BindView(R.id.password)
    EditText edPassword;
    @BindView(R.id.progress_login)
    ProgressBar prgBar;

    ILoginPresenter loginPresenter;
    SharedPreferences prefs;
    private SessionManager session;

    String strId, strNik, strNotelp, strNama, strRole, strToken, strKtp, strKk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

        loginPresenter = new LoginPresenter(this);
        loginPresenter.setProgressBarVisiblity(View.GONE);

        prefs = getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);

        session     = new SessionManager(getApplicationContext());
        strId       = prefs.getString("id","");
        strNik      = prefs.getString("nik","");
        strNotelp   = prefs.getString("notelp", "");
        strNama     = prefs.getString("nama", "");
        strRole     = prefs.getString("role", "");
        strToken    = prefs.getString("token", "");
        strKtp      = prefs.getString("ktp", "");
        strKk       = prefs.getString("kk","");


        if (session.isLoggedIn()){
            if (strRole.equals("2")){
                Intent a = new Intent(Login.this, MenuUtama.class);
                a.putExtra("id", strId);
                a.putExtra("nik", strNik);
                a.putExtra("notelp", strNotelp);
                a.putExtra("nama", strNama);
                a.putExtra("role",strRole);
                a.putExtra("token",strToken);
                a.putExtra("ktp", strKtp);
                a.putExtra("kk", strKk);
                startActivity(a);
                finish();
            }else {
                Toast.makeText(getApplicationContext(), "Anda tidak diizinkan mengakses aplikasi ini", Toast.LENGTH_LONG).show();
            }
        }

        ArrayList<String> listUrl = new ArrayList<>();
        ArrayList<String> listName = new ArrayList<>();

        listUrl.add("http://kartupetaniberjaya.com/wp-content/uploads/2019/07/fffghgf.jpg");
        listName.add("Pemodalan Usaha Tani");

        listUrl.add("http://kartupetaniberjaya.com/wp-content/uploads/2019/07/cropped-PicsArt_03-29-09.36.17-1.jpg");
        listName.add("Kepastian Pupuk");

        listUrl.add("http://kartupetaniberjaya.com/wp-content/uploads/2019/07/044465100_1539706125-PADI_ORGANIK-Muhamad_Ridlo.jpg");
        listName.add("Kemudahan Benih");

        listUrl.add("http://kartupetaniberjaya.com/wp-content/uploads/2019/07/cropped-Marketplace-Pertanian-01-Petani-Indonesia-Finansialku-1.jpg");
        listName.add("Kesejahteraan Petani");

        RequestOptions requestOptions = new RequestOptions();
        //requestOptions.centerCrop();
        //.diskCacheStrategy(DiskCacheStrategy.NONE)
        //.placeholder(R.drawable.placeholder)
        //.error(R.drawable.placeholder);

        for (int i = 0; i < listUrl.size(); i++) {
            TextSliderView sliderView = new TextSliderView(this);
            // if you want show image only / without description text use DefaultSliderView instead

            // initialize SliderLayout
            sliderView
                    .image(listUrl.get(i))
                    .description(listName.get(i))
                    .setRequestOption(requestOptions)
                    .setProgressBarVisible(true)
                    .setOnSliderClickListener(this);

            //add your extra information
            sliderView.bundle(new Bundle());
            sliderView.getBundle().putString("extra", "");
            mDemoSlider.addSlider(sliderView);
        }

        // set Slider Transition Animation
        // mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);

        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);
        mDemoSlider.addOnPageChangeListener(this);
    }

    @OnClick(R.id.signup)
    void signUp(){
        Intent a = new Intent(Login.this, Regist.class);
        startActivity(a);
        finish();
    }

    @Override
    protected void onStop() {
        // To prevent a memory leak on rotation, make sure to call stopAutoCycle() on the slider before activity or fragment is destroyed
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this, slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onBackPressed() {
        System.exit(0);
    }



    @Override
    public void onClearText() {

    }

    @OnClick(R.id.signin)
    void login(){
        String nik = edNik.getText().toString();
        String pass = edPassword.getText().toString();

        if(nik.isEmpty()){
            Toast.makeText(getApplicationContext(), "Nik tidak boleh kosong", Toast.LENGTH_LONG).show();
        }else if(pass.isEmpty()){
            Toast.makeText(getApplicationContext(), "Password tidak boleh kosong", Toast.LENGTH_LONG).show();
        }else {
            loginPresenter.setProgressBarVisiblity(View.VISIBLE);
            loginPresenter.doLogin(nik,pass);
        }
    }

    @Override
    public void onLoginResult(Boolean result, String msg) {
        loginPresenter.setProgressBarVisiblity(View.GONE);
        if (result){
//            try {
//                JSONObject jObj     = new JSONObject(msg);
//                strId       = jObj.getString("id");
//                strNik      = jObj.getString("nik");
//                strNotelp   = jObj.getString("no_hp");
//                strNama     = jObj.getString("nama");
//                strRole     = jObj.getString("role");
//                strToken    = jObj.getString("token");
//                strKtp      = jObj.getString("ktp");
//                strKk       = jObj.getString("kartukeluarga");
//
//                storeRegIdinSharedPref(getApplicationContext(),strId,strNik,strNotelp, strNama, strRole, strToken, strKtp, strKk);
//
//                if(strRole.equals("2")){
//                    session.setLogin(true);
//                    Intent a = new Intent(Login.this, MenuUtama.class);
//                    a.putExtra("id", strId);
//                    a.putExtra("nik", strNik);
//                    a.putExtra("notelp", strNotelp);
//                    a.putExtra("nama", strNama);
//                    a.putExtra("role",strRole);
//                    a.putExtra("token",strToken);
//                    a.putExtra("ktp", strKtp);
//                    a.putExtra("kk", strKk);
//                    startActivity(a);
//                    finish();
//                }else {
//                    Toast.makeText(getApplicationContext(), "Anda tidak diizinkan mengakses aplikasi ini", Toast.LENGTH_LONG).show();
//                }
//
//            }catch (JSONException e){
//                e.printStackTrace();
//            }
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onSetProgressBarVisibility(int visibility) {
        prgBar.setVisibility(visibility);
    }

    private void storeRegIdinSharedPref(Context context, String strId, String strNik, String strNotelp, String strNama,
                                        String strRole, String strToken, String strKtp, String strKk) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("id", strId);
        editor.putString("nik", strNik);
        editor.putString("notelp", strNotelp);
        editor.putString("nama", strNama);
        editor.putString("role", strRole);
        editor.putString("token", strToken);
        editor.putString("ktp", strKtp);
        editor.putString("kk", strKk);
        editor.commit();
    }
}
