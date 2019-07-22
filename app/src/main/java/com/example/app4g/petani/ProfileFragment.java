package com.example.app4g.petani;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.app4g.R;
import com.example.app4g.server.AppController;
import com.example.app4g.server.Config_URL;
import com.example.app4g.session.SessionManager;
import com.example.app4g.users.login.Login;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileFragment extends Fragment {

    public SharedPreferences prefs;
    public SessionManager session;

    String strId, strNik, strNotelp, strNama, strRole, strToken, strKtp, strKk;

    @BindView(R.id.nik)
    TextView txtNik;
    @BindView(R.id.namaLengkap)
    TextView txtNama;
    @BindView(R.id.nohp)
    TextView txtTelp;
    @BindView(R.id.alamat)
    TextView alamat;
    @BindView(R.id.poktan)
    TextView poktan;

    @BindView(R.id.layout)
    LinearLayout layout;
    RetryPolicy policy = new DefaultRetryPolicy(5000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_profile_fragment, container, false);
        ButterKnife.bind(this, view);
        prefs = getActivity().getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);
        isLogin();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_profile_fragment);
    }

    public void isLogin(){
        // Session manager
        session = new SessionManager(getActivity());
        //Session Login
        if(session.isLoggedIn()){
            strId       = prefs.getString("id","");
            strNik      = prefs.getString("nik","");
            strNotelp   = prefs.getString("notelp", "");
            strNama     = prefs.getString("nama", "");
            strRole     = prefs.getString("role", "");
            strToken    = prefs.getString("token", "");
            strKtp      = prefs.getString("ktp", "");
            strKk       = prefs.getString("kk","");

            txtNik.setText(strNik);
            txtNama.setText(strNama);
            txtTelp.setText(strNotelp);
            cekPetani(strNik);

        }else{
            Intent a = new Intent(getActivity().getApplicationContext(), Login.class);
            startActivity(a);
            getActivity().finish();
        }
    }

    public void cekPetani(String nikPetani){
        String tag_string_req = "req_login";
        StringRequest strReq = new StringRequest(Request.Method.GET,
                Config_URL.cekPetani+nikPetani, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("msg", "Data petani: " + response.toString());

                try {
                    final JSONObject jObj = new JSONObject(response);
                    final boolean status = jObj.getBoolean("status");

                    if(status == true){

                        String msg = jObj.getString("message");
                        JSONObject jsonObject = new JSONObject(msg);
                        String nama             = jsonObject.getString("nama");
                        String poktans           = jsonObject.getString("nama_poktan");
                        String kab              = jsonObject.getString("kabupaten");
                        String kec              = jsonObject.getString("kecamatan");
                        String des              = jsonObject.getString("desa");
                        String jenis_kelamin    = jsonObject.getString("jenis_kelamin");
                        final String niks    = jsonObject.getString("nik");

                        if(!poktans.equals("null")){
                            poktan.setText(" "+poktans);
                        }else {
                            poktan.setText(" -");
                        }
                        if(!kab.equals("null") && !kec.equals("null") && !des.equals("null")){
                            alamat.setText(" "+kab+", "+kec+", "+des);
                        }else if(!kab.equals("null") && !kec.equals("null")){
                            alamat.setText(" "+kab+", "+kec);
                        }else if(!kec.equals("null") && !des.equals("null")){
                            alamat.setText(" "+kec+", "+des);
                        }else if(!kab.equals("null") && !des.equals("null")){
                            alamat.setText(" "+kab+", "+des);
                        }else if(!kab.equals("null")){
                            alamat.setText(" "+kab);
                        }else if(!kec.equals("null")){
                            alamat.setText(" "+kec);
                        }else if(!des.equals("null")){
                            alamat.setText(" "+des);
                        }else{
                            alamat.setText(" -");
                        }


                    }else {

                    }

                }catch (JSONException e){

                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                Log.e("msg", "Login Error : " + error.getMessage());
                error.printStackTrace();
                snacBars("Ups server tidak meresponse");
            }
        });
        strReq.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    public void snacBars(String text){
        Snackbar snackbar = Snackbar.make(layout, text, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        CoordinatorLayout.LayoutParams params=(CoordinatorLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setBackgroundColor(layout.getResources().getColor(R.color.red));
        view.setLayoutParams(params);
        snackbar.show();
    }

    public void snacBarsGreen(String text){
        Snackbar snackbar = Snackbar.make(layout, text, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        CoordinatorLayout.LayoutParams params=(CoordinatorLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setBackgroundColor(layout.getResources().getColor(R.color.bg_screen3));
        view.setLayoutParams(params);
        snackbar.show();
    }

}
