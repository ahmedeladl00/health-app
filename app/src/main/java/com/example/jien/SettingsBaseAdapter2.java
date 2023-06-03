package com.example.jien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingsBaseAdapter2 extends BaseAdapter {

    Context context2;
    String listSettings2[];
    int listImag2[];
    LayoutInflater inflater;
    public SettingsBaseAdapter2(Context ctx2, String[] listSettings2, int[] listImag2){
        this.context2=ctx2;
        this.listSettings2=listSettings2;
        this.listImag2=listImag2;
        inflater=LayoutInflater.from(ctx2);

    }
    @Override
    public int getCount() {
        return listSettings2.length;
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
    public View getView(int position2, View convertview2, ViewGroup parent) {
        convertview2=inflater.inflate(R.layout.activity_settings_view2,null);
        TextView txtview2=(TextView) convertview2.findViewById(R.id.textview2);
        ImageView imgview2=(ImageView) convertview2.findViewById(R.id.imageIcom2);
        txtview2.setText(listSettings2[position2]);
        imgview2.setImageResource(listImag2[position2]);



        return convertview2;
    }
}
