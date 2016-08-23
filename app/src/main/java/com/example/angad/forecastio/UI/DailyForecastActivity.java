package com.example.angad.forecastio.UI;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.angad.forecastio.Adapters.CustomArrayAdapter;
import com.example.angad.forecastio.R;
import com.example.angad.forecastio.model.Day;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DailyForecastActivity extends Activity {

    private Day[] mDays;
    @BindView(R.id.locationLabel) TextView mLocation;
@BindView(android.R.id.list) ListView mListView;
    @BindView(android.R.id.empty) TextView mEmptyText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_forecast);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mDays = (Day[]) intent.getSerializableExtra(MainActivity.DAILY_FORECAST);
        CustomArrayAdapter adapter = new CustomArrayAdapter(this, mDays);
        mListView.setAdapter(adapter);
        mLocation.setText(mDays[0].getTimezone());
        mListView.setEmptyView(mEmptyText);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dayOfTheWeek=mDays[position].getWeekDay();
                String conditions=mDays[position].getSummary();
                String temp=mDays[position].getTemperatureMax()+"";
                String message=String.format("On %s the temp will be %s  and it will be %s",dayOfTheWeek,temp,conditions);
                Toast.makeText(DailyForecastActivity.this,message,Toast.LENGTH_LONG).show();
            }
        });
    }


}