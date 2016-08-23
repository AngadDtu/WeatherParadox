package com.example.angad.forecastio.UI;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.angad.forecastio.Dialogs.AlertDialogFragment;
import com.example.angad.forecastio.HourlyActivity;
import com.example.angad.forecastio.R;
import com.example.angad.forecastio.model.Current;
import com.example.angad.forecastio.model.Day;
import com.example.angad.forecastio.model.Forecast;
import com.example.angad.forecastio.model.Hour;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity  {

    private static final String TAG =MainActivity.class.getSimpleName() ;
    public static final String DAILY_FORECAST="DAILY_FORECAST";
    public static final String HOURLY_FORECAST ="HOURLY_FORECAST" ;
    private double mLongitude=77.1025;
    private double mLatitude= 28.7041;
    private Forecast mForecast;
    @BindView(R.id.timeZoneLabel) TextView mTimeZoneValue;
    @BindView(R.id.temperatureLabel) TextView mTemperatureValue;
    @BindView(R.id.humidityLabel) TextView mHumidityValue;
    @BindView(R.id.percipLevel) TextView mPrecipValue;
    @BindView(R.id.iconLabel) ImageView mIconImageView;
    @BindView(R.id.timeLabel) TextView mTimeValue;
    @BindView(R.id.summaryLabel) TextView mSummaryLabel;
    @BindView(R.id.refreshLabel) ImageView mRefreshView;
    @BindView(R.id.progressBar) ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mRefreshView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              getForecast(mLatitude,mLongitude);
            }
        });
        getForecast(mLatitude,mLongitude);
        Log.d(TAG,"Main UI is working");
    }




    private void getForecast(double latitude,double longitude) {
        String apiKey="a65e6661daf2ae7d51c04026725ebd54";

        String foreCast="https://api.forecast.io/forecast/" + apiKey + "/" + latitude + "," + longitude;

        if(isNetworkAvailable()) {
              //toggleRefresh();
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshView.setVisibility(View.INVISIBLE);
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(foreCast)
                    .build();
            Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //toggleRefresh();
                            mProgressBar.setVisibility(View.INVISIBLE);
                            mRefreshView.setVisibility(View.VISIBLE);
                        }
                    });
                    Log.v(TAG,"Network connection Failed");
                    alertUserAboutError();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //toggleRefresh();
                            mProgressBar.setVisibility(View.INVISIBLE);
                            mRefreshView.setVisibility(View.VISIBLE);
                        }
                    });
                    try {
                        String JsonData=response.body().string();
                        if (response.isSuccessful()) {
                            Log.v(TAG, JsonData);
                            mForecast =getForeCastDetails(JsonData);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    onUpdateDetails();
                                }
                            });
                        } else {
                            Log.v(TAG, JsonData);
                            alertUserAboutError();
                            /* runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this,"Wrong location input",Toast.LENGTH_LONG).show();
                                }
                            });*/

                        }
                    } catch (IOException e) {
                        Log.e(TAG, "NETWORKING ERRROR",e);
                    } catch (JSONException e) {
                        Log.e(TAG,"NETWORKING ERROR",e);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        else{
            Toast.makeText(this, R.string.network_error_toast_message,Toast.LENGTH_LONG).show();
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshView.setVisibility(View.VISIBLE);
        }
    }

   /* private void toggleRefresh() {
        if(mProgressBar.getVisibility()==View.INVISIBLE) {
            mProgressBar.setVisibility(View.VISIBLE);
            mRefreshView.setVisibility(View.INVISIBLE);
        }
        else{
            mProgressBar.setVisibility(View.INVISIBLE);
            mRefreshView.setVisibility(View.VISIBLE);
        }
    }*/

    private void onUpdateDetails() {
        Current mCurrent=mForecast.getCurrent();
       mTimeZoneValue.setText(mCurrent.getTimeZone());
        mTemperatureValue.setText(mCurrent.getTempInCelcius()+"");
        mHumidityValue.setText(mCurrent.getHumidity()+"");
        mPrecipValue.setText(mCurrent.getPerciChange()+"%");
        mSummaryLabel.setText(mCurrent.getSummary());
        Drawable drawable = ContextCompat.getDrawable(this, mCurrent.getIcon());
        mIconImageView.setImageDrawable(drawable);
        mTimeValue.setText("At "+ mCurrent.getFormattedTime()+" temp is");
    }
private Forecast getForeCastDetails(String jsonData) throws Exception{
    Forecast forecast=new Forecast();
    forecast.setCurrent(getCurrentDetails(jsonData));
    forecast.setHour(getHourDetails(jsonData));
    forecast.setDay(getDayDetails(jsonData));
    return forecast;

}

    private Day[] getDayDetails(String jsonData) throws Exception {
        JSONObject forecast=new JSONObject(jsonData);
        String timezone=forecast.getString("timezone");
        JSONObject daily=forecast.getJSONObject("daily");
        JSONArray data=daily.getJSONArray("data");
        Day[] days=new Day[data.length()];
        for(int i=0;i<data.length();i++){
            JSONObject object=data.getJSONObject(i);
            Day day=new Day();
            day.setTimezone(timezone);
            day.setIcon(object.getString("icon"));
            day.setSummary(object.getString("summary"));
            day.setTime(object.getLong("time"));
            day.setTemperatureMax(object.getDouble("temperatureMax"));
            days[i]=day;
        }
        return days;
    }

    private Hour[] getHourDetails(String jsonData)  throws  Exception{
        JSONObject forecast = new JSONObject(jsonData);
        String timezone = forecast.getString("timezone");
        JSONObject hourly = forecast.getJSONObject("hourly");
        JSONArray data = hourly.getJSONArray("data");
        Hour[] hours=new Hour[data.length()];
        for(int i=0;i<data.length();i++){
            JSONObject object=data.getJSONObject(i);
            Hour hour=new Hour();
            hour.setTime(object.getLong("time"));
            hour.setTemperarture(object.getDouble("temperature"));
            hour.setTimeZone(timezone);
            hour.setSummary(object.getString("summary"));
            hour.setIcon(object.getString("icon"));
            hours[i]=hour;
        }
         return hours;
    }

    private Current getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast=new JSONObject(jsonData);
        String timezone=forecast.getString("timezone");
        JSONObject currently=forecast.getJSONObject("currently");
        Current current =new Current();
        current.setTimeZone(timezone);
        current.setIcon(currently.getString("icon"));
        current.setTemperature(currently.getDouble("temperature"));
        current.setTime(currently.getLong("time"));
        current.setHumidity(currently.getDouble("humidity"));
        current.setPerciChange(currently.getDouble("precipProbability"));
        current.setSummary(currently.getString("summary"));
        String formattedTime= current.getFormattedTime();
        Log.v(TAG,"Time: "+formattedTime);

        return current;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=manager.getActiveNetworkInfo();
        boolean isActive=false;
        if(networkInfo!=null && networkInfo.isConnected()){
            isActive=true;
        }
        return  isActive;
    }

    private void alertUserAboutError() {
        AlertDialogFragment dialog=new AlertDialogFragment();
        dialog.show(getFragmentManager(),"Alert Dialog Message");
    }
 @OnClick(R.id.DaysButton)
    public void startDaysActivity(View view) {
     Intent intent = new Intent(this, DailyForecastActivity.class);
         intent.putExtra(DAILY_FORECAST, mForecast.getDay());
         startActivity(intent);
 }
    @OnClick(R.id.HourlyButton)
    public void startHoursActivity(View view) {
        Intent intent = new Intent(MainActivity.this, HourlyActivity.class);
            intent.putExtra(HOURLY_FORECAST, mForecast.getHour());
            startActivity(intent);
    }

}
