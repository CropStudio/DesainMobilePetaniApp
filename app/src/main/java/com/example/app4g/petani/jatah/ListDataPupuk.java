package com.example.app4g.petani.jatah;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import butterknife.BindView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app4g.R;
import com.example.app4g.adapter.AdapterPupuk;
import com.example.app4g.data.DataPupuk;
import com.example.app4g.petani.MenuUtama;
import com.example.app4g.server.AppController;
import com.example.app4g.server.Config_URL;
import com.example.app4g.session.SessionManager;
import com.example.app4g.users.login.Login;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.ButterKnife;


public class ListDataPupuk extends AppCompatActivity {
    @BindView(R.id.main_list_pupuk)
    ListView list;

    public SharedPreferences prefs;
    public SessionManager session;

    ProgressDialog pDialog;

    String strId, strNik, strNotelp, strNama, strRole, strToken, strKtp, strKk, strPotoPropil;
//    ArrayList<DataAnak> newsList = new ArrayList<DataAnak>();
//    AdapterAnak adapter;
    String strIdPupuk, strNamaPupuk, strJenisPupuk, strJmlhPupuk;
    ArrayList<DataPupuk> newsList = new ArrayList<DataPupuk>();
    AdapterPupuk adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data_pupuk);
        ButterKnife.bind(this);
        getSupportActionBar().hide(); //untuk menghilangkan action bar yg di atas

        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        newsList.clear();
        adapter = new AdapterPupuk(this, newsList);
        list.setAdapter(adapter);
        //list.setEmptyView(view.findViewById(R.id.textNodata));

        prefs = getSharedPreferences(
                "UserDetails",
                Context.MODE_PRIVATE);
        isLogin();
        getNamaPupuk();

    }

    private void getData() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Config_URL.dataPupuk+strIdPupuk, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        DataPupuk pupuk = new DataPupuk();
                        pupuk.setNamaPupuk(jsonObject.getString("nama_pupuk"));
                        pupuk.setJenisPupuk(jsonObject.getString("jenis"));
                        pupuk.setJmlhPupuk(jsonObject.getString("jumlah"));
                        newsList.add(pupuk);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                    }
                }
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());
                progressDialog.dismiss();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    // Fungsi get JSON Mahasiswa
    private void getNamaPupuk() {

        String tag_string_req = "req";

        pDialog.setMessage("Loading.....");
        showDialog();

        String tag_json_obj = "json_obj_req";
        StringRequest strReq = new StringRequest(Request.Method.GET,
                Config_URL.dataPupuk+"4",
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.e("Response: ", response.toString());
                        hideDialog();

                        try {
                            JSONObject jObj = new JSONObject(response);
                            boolean status = jObj.getBoolean("status");

                            if(status == true){

                                String getObject = jObj.getString("message");
                                JSONArray jsonArray = new JSONArray(getObject);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    final JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    DataPupuk pupuk = new DataPupuk();
                                    pupuk.setNamaPupuk(jsonObject.getString("nama_pupuk"));
                                    pupuk.setJenisPupuk(jsonObject.getString("jenis"));
                                    pupuk.setJmlhPupuk(jsonObject.getString("jumlah"));

                                    newsList.add(pupuk);
                                }
                            }

                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                Log.e(String.valueOf(getApplication()), "Error : " + error.getMessage());
                error.printStackTrace();
//                ImageView image = new ImageView(ListPembayaran.this);
//                image.setImageResource(R.drawable.no_internet);
//                AlertDialog.Builder builder = new AlertDialog.Builder(ListPembayaran.this);
//                builder.setTitle(Html.fromHtml("<font color='#2980B9'><b></b></font>"))
//                        .setCancelable(false)
//                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                Intent a = new Intent(ListPembayaran.this, ListPembayaran.class);
//                                startActivity(a);
//                                finish();
//                            }
//                        }).setView(image)
//                        .show();
                hideDialog();
            }
        });
//        {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> headers = new HashMap<>();
//                headers.put("Authorization", "Bearer " + main.token);
//                return headers;
//            }
//        };

        AppController.getInstance().addToRequestQueue(strReq, tag_json_obj);
    }

    @Override
    public void onBackPressed() { //ini untuk tombol fisik kembali
        Intent a = new Intent(ListDataPupuk.this, MenuUtama.class);
        startActivity(a);
        finish();
    }
//
//    @OnClick(R.id.btnTambahDataAnak)
//    void btnTambahDataAnak() {
//        Intent a = new Intent(com.example.app4g.petani.anak.ListDataAnak.this, InputDataAnak.class);
//        startActivity(a);
//        finish();
//    }

    public void isLogin(){
        // Session manager
        session = new SessionManager(this);
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
            strPotoPropil=prefs.getString("pp","");

        }else{
            Intent a = new Intent(getApplicationContext(), Login.class);
            startActivity(a);
            finish();
        }

    }

    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
