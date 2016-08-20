package com.example.angad.forecastio.UI;

import android.content.Context;
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
import com.example.angad.forecastio.R;
import com.example.angad.forecastio.model.Current;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity  {

    private static final String TAG =MainActivity.class.getSimpleName() ;
    private Current mCurrent;
    private double mLongitude=77.1025;
    private double mLatitude= 28.7041;
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
                            mCurrent =getCurrentDetails(JsonData);
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
       mTimeZoneValue.setText(mCurrent.getTimeZone());
        mTemperatureValue.setText(mCurrent.getTempInCelcius()+"");
        mHumidityValue.setText(mCurrent.getHumidity()+"");
        mPrecipValue.setText(mCurrent.getPerciChange()+"%");
        mSummaryLabel.setText(mCurrent.getSummary());
        Drawable drawable = ContextCompat.getDrawable(this, mCurrent.getIcon());
        mIconImageView.setImageDrawable(drawable);
        mTimeValue.setText("At "+ mCurrent.getFormattedTime()+" temp will be");
    }

    private Current getCurrentDetails(String jsonData) throws JSONException {
        JSONObject forecast=new JSONObject(jsonData);
        JSONObject currently=forecast.getJSONObject("currently");
        Current current =new Current();
        current.setTimeZone(forecast.getString("timezone"));
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


}
