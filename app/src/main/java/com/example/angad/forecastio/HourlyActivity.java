package com.example.angad.forecastio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.angad.forecastio.UI.MainActivity;
import com.example.angad.forecastio.model.Hour;

public class HourlyActivity extends AppCompatActivity {
Hour[] mHours;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly);
        Intent intent=getIntent();
        mHours= (Hour[]) intent.getSerializableExtra(MainActivity.HOURLY_FORECAST);

    }
}
