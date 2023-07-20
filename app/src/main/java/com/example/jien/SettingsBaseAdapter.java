package com.example.jien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SettingsBaseAdapter extends BaseAdapter {

     Context context;
     String listSettings[];
     int listImage[];
     LayoutInflater inflater;

     public SettingsBaseAdapter(Context ctx, String[] listSettings, int[] listImage){
         this.context=ctx;
         this.listSettings=listSettings;
         this.listImage=listImage;
         inflater=LayoutInflater.from(ctx);
     }

    @Override
    public int getCount() {
        return listSettings.length;
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
    public View getView(int position, View convertview, ViewGroup parent1) {
         convertview=inflater.inflate(R.layout.activity_settings_view,null);
         TextView txtview=(TextView) convertview.findViewById(R.id.textview);
         ImageView imgview=(ImageView) convertview.findViewById(R.id.imageIcon);
         txtview.setText(listSettings[position]);
         imgview.setImageResource(listImage[position]);
         return convertview;
    }
}
