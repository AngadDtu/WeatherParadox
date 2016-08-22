package com.example.angad.forecastio.model;

import com.example.angad.forecastio.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Current {
    private  String mIcon;
    private  String mTimeZone;
    private long mTime;
    private double mTemperature;
    private double mTempInCelcius;

    public int getTempInCelcius() {
         mTempInCelcius=(getTemperature() - 32)/1.8;
        return (int)Math.round(mTempInCelcius);
    }

    public void setTempInCelcius(double tempInCelcius) {
        mTempInCelcius = tempInCelcius;
    }

    private double mHumidity;
    private double mPerciChange;
    private  String mSummary;


    public String getFormattedTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("h:mm a");
        formatter.setTimeZone(TimeZone.getTimeZone(getTimeZone()));
        Date dateTime = new Date(getTime() * 1000);
        String timeString = formatter.format(dateTime);

        return timeString;
    }


    public int getIcon() {
       return Forecast.getIconId(mIcon);
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getTimeZone() {
        return mTimeZone;
    }

    public void setTimeZone(String location) {
        mTimeZone = location;
    }

    public long getTime() {
        return mTime;
    }

    public void setTime(long time) {
        mTime = time;
    }

    public double getTemperature() {
        return mTemperature;
    }

    public void setTemperature(double temperature) {
        mTemperature = temperature;
    }

    public double getHumidity() {
        return mHumidity;
    }

    public void setHumidity(double humidity) {
        mHumidity = humidity;
    }

    public int getPerciChange() {
        return (int)Math.round(mPerciChange);
    }

    public void setPerciChange(double perciChange) {
        mPerciChange = perciChange;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }
}
