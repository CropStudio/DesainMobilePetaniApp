package com.example.app4g.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app4g.R;
import com.example.app4g.data.DataAnak;

import java.util.List;

public class AdapterAnak extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<DataAnak> item;

    public AdapterAnak(Activity activity, List<DataAnak> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int location) {
        return item.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.content_anak, null);


        TextView tgl           = (TextView) convertView.findViewById(R.id.tglLahir);
        TextView nama          = (TextView) convertView.findViewById(R.id.namaAnak);
        TextView jenisKelamin  = (TextView) convertView.findViewById(R.id.jenisKelamin);

        nama.setText(item.get(position).getNama());
        jenisKelamin.setText(item.get(position).getJenisKelami());
        tgl.setText(item.get(position).getTglLahir());

        return convertView;
    }
}
