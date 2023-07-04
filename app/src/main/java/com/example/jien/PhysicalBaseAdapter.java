package com.example.jien;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PhysicalBaseAdapter extends BaseAdapter {
    Context context;
    String listActivities[];
    String listInfos[];
    LayoutInflater inflater;

    public PhysicalBaseAdapter(Context context,String[] listActivities,String[] listInfos){
        this.context=context;
        this.listActivities=listActivities;
        this.listInfos=listInfos;
        inflater=LayoutInflater.from(context);


    }


    @Override
    public int getCount() {
        return listActivities.length;
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
    public View getView(int i, View view, ViewGroup viewGroup) {
        view=inflater.inflate(R.layout.activity_activities_view,null);
        TextView activityname=(TextView) view.findViewById(R.id.activityname);
        TextView timefrom=(TextView)  view.findViewById(R.id.timefrom);
        TextView timeto=(TextView)  view.findViewById(R.id.timeto);
        TextView day=(TextView)  view.findViewById(R.id.day);
        activityname.setText(listActivities[i]);
        timefrom.setText(listInfos[i]);
        timeto.setText(listInfos[i]);
        day.setText(listInfos[i]);


        return view;
    }
}
