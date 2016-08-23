package com.example.angad.forecastio.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Hour implements Serializable{
    private long mTime;
    private  String mSummary;
    private  String mTimeZone;
    private  double mTemperarture;
    private  String mIcon;

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String timeZone) {
        mTimeZone = timeZone;
    }

    public int getTemperarture() {
        double temp=(mTemperarture-32)/1.8;

        return (int)Math.round(temp);
    }

    public void setTemperarture(double temperarture) {
        mTemperarture = temperarture;
    }

    public String getIcon() {
        return mIcon;
    }
    public int getIconId(){
        return Forecast.getIconId(mIcon);

    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getHour(){
        SimpleDateFormat formatter=new SimpleDateFormat("h a");
        Date date=new Date(getTime() * 1000);
       return formatter.format(date);
    }
}
