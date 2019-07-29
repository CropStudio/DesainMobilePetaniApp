package com.example.app4g.petani;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.example.app4g.R;
import com.example.app4g.petani.anak.ListDataAnak;
import com.example.app4g.session.SessionManager;
import com.example.app4g.users.login.Login;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Dashboard extends Fragment {

    public SharedPreferences prefs;
    public SessionManager session;

    private Animation fab_open,fab_close,rotate_forward,rotate_backward;

    @BindView(R.id.fab)
    FloatingActionButton fab;
    @BindView(R.id.fabs)
    FloatingActionButton fabs;
    @BindView(R.id.linearFab1)
    LinearLayout linearFab1;
    @BindView(R.id.linearFab2)
    LinearLayout linearFab2;

    private Boolean isFabOpen = false;

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_dashboard, container, false);
        ButterKnife.bind(this, view);
        session = new SessionManager(getActivity());
        prefs = getActivity().getSharedPreferences("UserDetails",
                Context.MODE_PRIVATE);

        fab_open = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getActivity(),R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate_backward);
        return view;
    }



    @OnClick(R.id.fab)
    void openFab(){
        animateFAB();
    }

    @OnClick(R.id.linearFab1)
    void keluar(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(Html.fromHtml("<font color='#009688'><b>Yakin ingin keluar ?</b></font>")).
                setIcon(R.drawable.lampung_coa)
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        keluar();
                        session.setLogin(false);
                        session.setSkip(false);
                        session.setSessid(0);
                        Intent intent = new Intent(getActivity(), Login.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(DialogInterface dialog, int which) {
                builder.setCancelable(true);
                fab.setVisibility(View.VISIBLE);
                fabs.setVisibility(View.GONE);
                fab.startAnimation(rotate_backward);
                linearFab1.startAnimation(fab_close);
                linearFab2.startAnimation(fab_close);
                linearFab1.setClickable(false);
                linearFab2.setClickable(false);
                isFabOpen = false;
            }
        })
                .show();
    }
    @OnClick(R.id.linearFab2)
    void dataanak(){
//        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle(Html.fromHtml("<font color='#009688'><b>Yakin ingin keluar ?</b></font>"))
//                .setCancelable(false)
//                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        keluar();
//                        session.setLogin(false);
//                        session.setSkip(false);
//                        session.setSessid(0);
                        Intent intent = new Intent(getActivity(), ListDataAnak.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
//                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @SuppressLint("RestrictedApi")
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                builder.setCancelable(true);
//                fab.setVisibility(View.VISIBLE);
//                fabs.setVisibility(View.GONE);
//                fab.startAnimation(rotate_backward);
//                linearFab1.startAnimation(fab_close);
//                linearFab2.startAnimation(fab_close);
//                linearFab1.setClickable(false);
//                linearFab2.setClickable(false);
//                isFabOpen = false;
//            }
//        })
//                .show();


    @SuppressLint("RestrictedApi")
    public void animateFAB(){

        if(isFabOpen){
            fab.setVisibility(View.VISIBLE);
            fabs.setVisibility(View.GONE);
            fab.startAnimation(rotate_backward);
            linearFab1.startAnimation(fab_close);
            linearFab2.startAnimation(fab_close);
            linearFab1.setClickable(false);
            linearFab2.setClickable(false);
            isFabOpen = false;
        } else {
            fab.setVisibility(View.GONE);
            fabs.setVisibility(View.VISIBLE);
            fab.startAnimation(rotate_forward);
            linearFab1.startAnimation(fab_open);
            linearFab2.startAnimation(fab_open);
            linearFab1.setClickable(true);
            linearFab2.setClickable(true);
            isFabOpen = true;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_dashboard);
    }
}
