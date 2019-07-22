package com.example.app4g.petani;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app4g.R;

import butterknife.ButterKnife;

public class Dashboard extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_dashboard, container, false);
        ButterKnife.bind(this, view);
//        session = new SessionManager(getActivity());
//        prefs = getActivity().getSharedPreferences("UserDetails",
//                Context.MODE_PRIVATE);
//
//        token       = prefs.getString("token","");
//        session = new SessionManager(getActivity());
        //main.checkStatus();
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_dashboard);
    }
}
