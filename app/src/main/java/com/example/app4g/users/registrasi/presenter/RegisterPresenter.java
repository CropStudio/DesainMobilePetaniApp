package com.example.app4g.users.registrasi.presenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.app4g.server.AppController;
import com.example.app4g.server.Config_URL;
import com.example.app4g.server.VolleyMultipartRequest;
import com.example.app4g.users.model.IUserRegister;
import com.example.app4g.users.model.RegisterModel;
import com.example.app4g.users.registrasi.view.IRegisterView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class RegisterPresenter implements IRegisterPresenter {

    IRegisterView iRegisterView;
    IUserRegister user;
    Handler handler;
    Activity activity;

    RetryPolicy policy = new DefaultRetryPolicy(5000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    private RequestQueue rQueue;

    public RegisterPresenter(IRegisterView iRegisterView, Activity activity){
        this.iRegisterView = iRegisterView;
        handler = new android.os.Handler(Looper.getMainLooper());
        this.activity = activity;
    }


    @Override
    public void clear() {
        iRegisterView.onClearText();
    }

    @Override
    public void doRegistrasi(String nik, String nama, String noHp, String role, String password, Bitmap ktp, Bitmap kk) {
        user   = new RegisterModel(nik, nama, noHp, role, password, ktp, kk);
        registrasi(nik, nama, noHp, role, password,ktp, kk);
    }

    @Override
    public void setProgressBarVisiblity(int visiblity) {
        iRegisterView.onSetProgressBarVisibility(visiblity);
    }

    public void registrasi(final String nik, final String nama, final String noHp, final String role, final String password, final Bitmap ktps, final Bitmap kk){
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Config_URL.registrasi,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.d("ressssssoo",new String(response.data));
                        rQueue.getCache().clear();
                        try {
                            final JSONObject jsonObject = new JSONObject(new String(response.data));
                            final boolean status = jsonObject.getBoolean("status");
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    if(status == true){
                                        String msg = null;
                                        try {
                                            msg = jsonObject.getString("message");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        iRegisterView.onRegisterResult(status, msg);
                                    }else {
                                        String msg = null;
                                        try {
                                            msg = jsonObject.getString("message");
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        iRegisterView.onRegisterResult(status, msg);
                                    }
                                }
                            }, 1000);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iRegisterView.onRegisterResult(false, "Maaf server tidak meresponse atau periksa koneksi internet anda");
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nik", nik);
                params.put("nama", nama);
                params.put("notelp", noHp);
                params.put("password", password);
                params.put("role", role);
                return params;
            }

            /*
             *pass files using below method
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("fotoktp", new DataPart(imagename + ".png", getFileDataFromDrawable(ktps)));
                params.put("fotokk", new DataPart(imagename + ".png", getFileDataFromDrawable(kk)));
                return params;
            }
        };

        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
        );
        rQueue = Volley.newRequestQueue(activity);
        rQueue.add(volleyMultipartRequest);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
}
