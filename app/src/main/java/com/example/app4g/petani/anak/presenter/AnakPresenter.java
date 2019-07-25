package com.example.app4g.petani.anak.presenter;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.app4g.petani.anak.model.AnakModel;
import com.example.app4g.petani.anak.model.IAnak;
import com.example.app4g.petani.anak.view.IInputAnakView;
import com.example.app4g.server.AppController;
import com.example.app4g.server.Config_URL;
import com.example.app4g.users.registrasi.view.IRegisterView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AnakPresenter implements IAnakPresenter {

    IInputAnakView iInputAnakView;
    IAnak user;
    Handler handler;
    Activity activity;

    RetryPolicy policy = new DefaultRetryPolicy(5000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    public AnakPresenter(IInputAnakView iInputAnakView, Activity activity){
        this.iInputAnakView = iInputAnakView;
        handler = new android.os.Handler(Looper.getMainLooper());
        this.activity = activity;
    }

    @Override
    public void clear() {
        iInputAnakView.onClearText();
    }

    @Override
    public void doAnak(String nama, String tglLahir, String jenisKelamin, String idUser) {
        checkRegistrasi(nama, tglLahir, jenisKelamin, idUser);
    }

    @Override
    public void setProgressBarVisiblity(int visiblity) {
        iInputAnakView.onSetProgressBarVisibility(visiblity);
    }

    private void checkRegistrasi(final String nama, final String tglLahir, final String jenisKelamin,final String idUser){
        user   = new AnakModel(nama, tglLahir, jenisKelamin,idUser);

        String tag_string_req = "req";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                Config_URL.inputAnak, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("msg", "Response: " + response.toString());

                try {
                    final JSONObject jObj = new JSONObject(response);
                    final boolean status = jObj.getBoolean("status");


                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(status == true){
                                String msg = null;
                                try {
                                    msg = jObj.getString("message");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                iInputAnakView.onInputResult(status, msg);
                            }else {
                                String msg = null;
                                try {
                                    msg = jObj.getString("message");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                iInputAnakView.onInputResult(status, msg);
                            }
                        }
                    }, 1000);

                }catch (JSONException e){
                    //JSON error
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error){
                Log.e("msg", "Login Error : " + error.getMessage());
                error.printStackTrace();
                iInputAnakView.onInputResult(false, "Maaf server tidak meresponse atau periksa koneksi internet anda");
            }
        }){

            @Override
            protected Map<String, String> getParams(){
                Map<String, String> params = new HashMap<String, String>();
                params.put("nama", nama);
                params.put("tanggal_lahir", tglLahir);
                params.put("jenis_kelamin", jenisKelamin);
                params.put("id_user", idUser);
                return params;
            }
        };
        strReq.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
