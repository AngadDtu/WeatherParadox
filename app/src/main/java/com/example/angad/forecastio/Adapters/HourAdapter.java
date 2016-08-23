package com.example.angad.forecastio.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.angad.forecastio.R;
import com.example.angad.forecastio.model.Hour;

public class HourAdapter extends RecyclerView.Adapter<HourAdapter.HourViewHolder> {
    Hour[] mHours;
    Context mContext;
    public HourAdapter(Context context,Hour[]hours){
        mContext=context;
        mHours=hours;
    }

    @Override
    public HourViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item,null);
        HourViewHolder viewHolder=new HourViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(HourViewHolder holder, int position) {
        //this method is a bridge between adapter and the binHour method in view holder class.Basically
        //it is used to bind data from adapter to views set in viewholder for a particular item position on list
         holder.bindHour(mHours[position]);
    }

    @Override
    public int getItemCount() {
        return mHours.length;
    }




    public class HourViewHolder extends RecyclerView.ViewHolder implements  View.OnClickListener{
        TextView mTimeLabel;
        TextView mSummaryLabel;
        TextView mTemperatureLabel;
        ImageView mIconLabel;
        public HourViewHolder(View itemView) {
            super(itemView);
            mTemperatureLabel=(TextView)itemView.findViewById(R.id.Temperarture);
            mIconLabel=(ImageView)itemView.findViewById(R.id.iconLabel);
            mTimeLabel=(TextView)itemView.findViewById(R.id.timeLabel);
            mSummaryLabel=(TextView)itemView.findViewById(R.id.summaryLabel);
            itemView.setOnClickListener(this);
        }
        public void bindHour(Hour hour){
            mTemperatureLabel.setText(hour.getTemperarture()+"");
            mIconLabel.setImageResource(hour.getIconId());
            mTimeLabel.setText(hour.getHour());
            mSummaryLabel.setText(hour.getSummary());

        }

        @Override
        public void onClick(View v) {
            String temp=mTemperatureLabel.getText().toString();
            String summary=mSummaryLabel.getText().toString();
            String time=mTimeLabel.getText().toString();
            String message=String.format("At %s temp will be %s and it will be %s",time,temp,summary);
            Toast.makeText(mContext,message,Toast.LENGTH_LONG).show();
        }
    }

}
