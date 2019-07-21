package com.example.app4g.users.login.presenter;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.app4g.server.AppController;
import com.example.app4g.server.Config_URL;
import com.example.app4g.users.login.view.ILoginview;
import com.example.app4g.users.model.IUserLogin;
import com.example.app4g.users.model.LoginModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class LoginPresenter implements ILoginPresenter{

    ILoginview iLoginView;
    IUserLogin user;
    Handler handler;

    RetryPolicy policy = new DefaultRetryPolicy(5000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    public LoginPresenter(ILoginview iLoginView) {
       this.iLoginView = iLoginView;
       handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void clear() {
        iLoginView.onClearText();
    }

    @Override
    public void doLogin(String nik, String passwd) {
        //if(email.isEmpty() && passwd.isEmpty()){
            //iLoginView.onLoginResult(false, "Lengkapi data");
        //}else {
        checkLogin(nik, passwd);
        //}
    }

    @Override
    public void setProgressBarVisiblity(int visiblity) {
        iLoginView.onSetProgressBarVisibility(visiblity);
    }

    private void checkLogin(final String nik, final String password){
        user   = new LoginModel(nik, password);

        String tag_string_req = "req_login";
        StringRequest strReq = new StringRequest(Request.Method.POST,
                Config_URL.login, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("msg", "Login Response: " + response.toString());

                try {
                    final JSONObject jObj = new JSONObject(response);
                    final boolean status = jObj.getBoolean("status");

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(status == true){
                                String msg = null;
                                String api_token = null;
                                try {
                                    msg         = jObj.getString("message");
                                    api_token   = jObj.getString("token");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                iLoginView.onLoginResult(status, msg + "/"+ api_token);
                            }else {
                                String msg = null;
                                try {
                                    msg = jObj.getString("message");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                iLoginView.onLoginResult(status, msg + "");
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
                iLoginView.onLoginResult(false, "Maaf server tidak meresponse atau periksa koneksi internet anda");
            }
        }){

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Content-Type", "application/json");
                params.put("nik", nik);
                params.put("password", password);
                return params;
            }
        };
        strReq.setRetryPolicy(policy);
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }
}
