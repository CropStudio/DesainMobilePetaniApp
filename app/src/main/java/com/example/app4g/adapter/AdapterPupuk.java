package com.example.app4g.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.animation.Positioning;
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
import com.example.app4g.data.DataPupuk;
import com.example.app4g.data.DataPupuk;

import java.util.List;

public class AdapterPupuk extends BaseAdapter{
    private Activity activity;
    private LayoutInflater inflater;
    private List<DataPupuk> item;

    public AdapterPupuk(Activity activity, List<DataPupuk> item) {
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
            convertView = inflater.inflate(R.layout.content_pupuk, null);//buat xml layout content


        TextView namaPupuk = (TextView) convertView.findViewById(R.id.namaPupuk);
        TextView jenisPupuk = (TextView) convertView.findViewById(R.id.jenisPupuk);
        TextView jmlhPupuk = (TextView) convertView.findViewById(R.id.jmlhPupuk);
        TextView komoDitas = (TextView) convertView.findViewById(R.id.komoDitas);

        namaPupuk.setText(item.get(position).getNamaPupuk());
        jenisPupuk.setText(item.get(position).getJenisPupuk());
        jmlhPupuk.setText(item.get(position).getJmlhPupuk());
        if (item.get(position).getKomoDitas().equals("null")) {
            komoDitas.setText("-");
        }else {
            komoDitas.setText(item.get(position).getKomoDitas());
        }
        return convertView;
    }
}
