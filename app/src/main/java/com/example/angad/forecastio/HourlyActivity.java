package com.example.angad.forecastio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.angad.forecastio.Adapters.HourAdapter;
import com.example.angad.forecastio.UI.MainActivity;
import com.example.angad.forecastio.model.Hour;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HourlyActivity extends AppCompatActivity {
Hour[] mHours;
    @BindView(R.id.recyclerView) RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hourly);
        ButterKnife.bind(this);
        Intent intent=getIntent();
        mHours= (Hour[]) intent.getSerializableExtra(MainActivity.HOURLY_FORECAST);
        HourAdapter adapter=new HourAdapter(this,mHours);
        mRecyclerView.setAdapter(adapter);
//adding a layout manager is compulsory
        RecyclerView.LayoutManager manager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);

        //for performance
        mRecyclerView.setHasFixedSize(true);

    }
}
