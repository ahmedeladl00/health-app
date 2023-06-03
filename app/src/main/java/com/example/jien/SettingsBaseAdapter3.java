package com.example.jien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingsBaseAdapter3 extends BaseAdapter {

    Context ctx3;
    String listSettings3[];
    int listImage3[];
    LayoutInflater inflater;
    public SettingsBaseAdapter3(Context ctx3, String[] listSettings3, int[] listImage3){
        this.ctx3=ctx3;
        this.listSettings3=listSettings3;
        this.listImage3=listImage3;
    }
    @Override
    public int getCount() {
        return listImage3.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position3, View convertview3, ViewGroup parent3) {
        convertview3=inflater.inflate(R.layout.activity_settings_viwe3,null);
        TextView txtview3=(TextView) convertview3.findViewById(R.id.textview3);
        ImageView imgview3=(ImageView) convertview3.findViewById(R.id.imageIcom3);
        txtview3.setText(listSettings3[position3]);
        imgview3.setImageResource(listImage3[position3]);
        return convertview3;
    }
}
