package com.example.angad.forecastio.UI;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.angad.forecastio.Adapters.CustomArrayAdapter;
import com.example.angad.forecastio.R;
import com.example.angad.forecastio.model.Day;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyForecastActivity extends ListActivity {

    private Day[] mDays;
    @BindView(R.id.locationLabel) TextView mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);
        ButterKnife.bind(this);
        Intent intent=getIntent();
       mDays= (Day[]) intent.getSerializableExtra(MainActivity.DAILY_FORECAST);
        CustomArrayAdapter adapter=new CustomArrayAdapter(this,mDays);
        setListAdapter(adapter);
        mLocation.setText(mDays[0].getTimezone());
    }
}
