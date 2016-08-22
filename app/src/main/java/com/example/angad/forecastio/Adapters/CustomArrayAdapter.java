package com.example.angad.forecastio.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.angad.forecastio.R;
import com.example.angad.forecastio.model.Day;

public class CustomArrayAdapter extends BaseAdapter {
    Context mContext;
    Day[] mDays;

    public CustomArrayAdapter(Context context,Day[] days){
        mContext=context;
        mDays=days;
    }

    @Override
    public int getCount() {
        return mDays.length;
    }

    @Override
    public Object getItem(int position) {
        return mDays[position];
    }

    @Override
    public long getItemId(int position) {
        //will not be using
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        viewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_view, null);
            holder = new viewHolder();
            holder.temperature = (TextView) convertView.findViewById(R.id.temperatureLabel);
            holder.weekDay = (TextView) convertView.findViewById(R.id.dayValue);
            holder.icon = (ImageView) convertView.findViewById(R.id.iconView);
            convertView.setTag(holder);
        }
        else{
        holder = (viewHolder) convertView.getTag();
    }
        Day day=mDays[position];
        holder.icon.setImageResource(day.getIcon());
        holder.temperature.setText(day.getTemperatureMax()+"");
        if(position==0){
            holder.weekDay.setText("Today");
        }else {
            holder.weekDay.setText(day.getWeekDay());
        }
        return convertView;
    }
    public static class viewHolder{
        TextView temperature;
        TextView weekDay;
        ImageView icon;
    }
}
