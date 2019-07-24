package com.example.app4g.petani;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
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
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
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
import com.example.app4g.R;
import com.example.app4g.server.AppController;
import com.example.app4g.server.Config_URL;
import com.example.app4g.server.VolleyMultipartRequest;
import com.example.app4g.session.SessionManager;
import com.example.app4g.users.login.Login;
import com.example.app4g.users.registrasi.Regist;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    public SharedPreferences prefs;
    public SessionManager session;

    String strId, strNik, strNotelp, strNama, strRole, strToken, strKtp, strKk, strPotoPropil;

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
    @BindView(R.id.fotoProfil)
    CircleImageView fotoprofile;
    @BindView(R.id.progress_login)
    ProgressBar prgBar;

    @BindView(R.id.layout)
    LinearLayout layout;
    RetryPolicy policy = new DefaultRetryPolicy(5000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    private final int CameraR_PP = 1;
    String mCurrentPhotoPath;

    private RequestQueue rQueue;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_profile_fragment, container, false);
        ButterKnife.bind(this, view);
        prefs = getActivity().getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);
        if(Build.VERSION.SDK_INT>=24){
            try{
                Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                m.invoke(null);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
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
            strPotoPropil=prefs.getString("pp","");

            txtNik.setText(strNik);
            txtNama.setText(strNama);
            txtTelp.setText(strNotelp);
            cekPetani(strNik);
            ppCek();

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
        FrameLayout.LayoutParams params=(FrameLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setBackgroundColor(layout.getResources().getColor(R.color.red));
        view.setLayoutParams(params);
        snackbar.show();
    }

    public void snacBarsGreen(String text){
        Snackbar snackbar = Snackbar.make(layout, text, Snackbar.LENGTH_LONG);
        View view = snackbar.getView();
        FrameLayout.LayoutParams params= (FrameLayout.LayoutParams)view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setBackgroundColor(layout.getResources().getColor(R.color.bg_screen3));
        view.setLayoutParams(params);
        snackbar.show();
    }

    public void ppCek(){
        if (strPotoPropil.length() == 4){
            Picasso.get().load(Config_URL.fotoProfilUrl+"noimage.png")
                    .into(fotoprofile, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            if (prgBar != null) {
                                prgBar.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                        }

                    });
        }else {
            Picasso.get().load(Config_URL.fotoProfilUrl+ strPotoPropil)
                    .into(fotoprofile, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            if (prgBar != null) {
                                prgBar.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                        }

                    });
        }
    }

    @OnClick(R.id.addImg)
    void ambilGambar(){
        addPermission();

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Log.i("Tags", "IOException");
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(cameraIntent, CameraR_PP);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == CameraR_PP) {
            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getActivity().getContentResolver(), Uri.parse(mCurrentPhotoPath));
                fotoprofile.setImageBitmap(bitmap);
                uploadPotoProfile(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 20, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private void uploadPotoProfile(final Bitmap bitmap){

        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, Config_URL.uploadFoto+strId,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Log.d("ressssssoo",new String(response.data));
                        rQueue.getCache().clear();
                        try {
                            JSONObject jsonObject = new JSONObject(new String(response.data));

                            if (jsonObject.getString("status").equals("true")) {

                                strPotoPropil = jsonObject.getString("foto");
                                getImageNew(getActivity().getApplicationContext(), strPotoPropil);
                                //Picasso.get().load(Config_URL.base_URL+"/users/foto/"+
                                Picasso.get().load(Config_URL.fotoProfilUrl+
                                        strPotoPropil)
                                        .into(fotoprofile, new com.squareup.picasso.Callback() {
                                            @Override
                                            public void onSuccess() {
                                                if (prgBar != null) {
                                                    prgBar.setVisibility(View.GONE);
                                                }
                                            }

                                            @Override
                                            public void onError(Exception e) {
                                            }

                                        });
                                Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("poto_profile", new DataPart(imagename + ".jpg", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(volleyMultipartRequest);
    }

    public void addPermission(){
        Dexter.withActivity(getActivity())
                .withPermissions(

                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            //Toast.makeText(getActivity(), "All permissions are granted by user!", Toast.LENGTH_SHORT).show();
                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // show alert dialog navigating to Settings

                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).
                withErrorListener(new PermissionRequestErrorListener() {
                    @Override
                    public void onError(DexterError error) {
                        Toast.makeText(getActivity(), "Some Error! ", Toast.LENGTH_SHORT).show();
                    }
                })
                .onSameThread()
                .check();
    }

    private void getImageNew(Context context, String foto) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("pp", foto);
        editor.commit();
    }
}
