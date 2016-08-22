package com.example.angad.forecastio.model;

import java.io.Serializable;

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

    public double getTemperarture() {
        return mTemperarture;
    }

    public void setTemperarture(double temperarture) {
        mTemperarture = temperarture;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }
}
