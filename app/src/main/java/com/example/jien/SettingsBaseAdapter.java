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
     String listSettings1[];
     int listImage1[];

     Context context2;
     String listSettings2[];
     int listImage2[];
     LayoutInflater inflater;

     public SettingsBaseAdapter(Context ctx, String[] listSettings1, int[] listImage1){
         this.context=ctx;
         this.listSettings1=listSettings1;
         this.listImage1=listImage1;
         inflater=LayoutInflater.from(ctx);
     }

    @Override
    public int getCount() {
        return listSettings1.length;
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
    public View getView(int position1, View convertview1, ViewGroup parent1) {
         convertview1=inflater.inflate(R.layout.activity_settings_view1,null);
        TextView txtview1=(TextView) convertview1.findViewById(R.id.textview1);
        ImageView imgview1=(ImageView) convertview1.findViewById(R.id.imageIcom1);
        txtview1.setText(listSettings1[position1]);
        imgview1.setImageResource(listImage1[position1]);


        return convertview1;
    }
}
