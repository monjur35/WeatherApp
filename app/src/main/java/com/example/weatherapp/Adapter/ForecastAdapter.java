package com.example.weatherapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.ForecastModel.ForecastList;
import com.example.weatherapp.ForecastModel.ForecastWeatheModel;
import com.example.weatherapp.Helpers.WeatherPreference;
import com.example.weatherapp.Helpers.WeatherUtils;
import com.example.weatherapp.R;

import java.util.List;

public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder> {


    private Context context;
    private final List<ForecastList> forecastListList;


    private final String TAG="Checked";
    private String tempUnitSymbol="C";
    private WeatherPreference weatherPreference;




    public ForecastAdapter(Context context, List<ForecastList> forecastListList) {
        this.context = context;
        this.forecastListList = forecastListList;
        Log.e(TAG,"successful in Constructor "+forecastListList.size());
    }

    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.forecast_row, parent, false);
        weatherPreference=new WeatherPreference(context);
        return new ForecastViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder holder, int position) {
        tempUnitSymbol=weatherPreference.getTempUnit() ?  "F":"C" ;
        holder.day.setText(WeatherUtils.getFormattedDateOrTime(forecastListList.get(position).getDt(),"hh:mm a,MMM dd yyyy"));
        //holder.conditon.setText(forecastListList.get(position).getVisibility());
        holder.temperature.setText(forecastListList.get(position).getMain().getTemp().toString()+"\u00B0" +tempUnitSymbol);
        Log.e(TAG,"Succesfull in onBindViewHolder"+forecastListList.size());
    }

    @Override
    public int getItemCount() {
        return forecastListList.size();
    }




    class ForecastViewHolder extends RecyclerView.ViewHolder {
        TextView day,temperature,conditon;
        public ForecastViewHolder(@NonNull View itemView) {
            super(itemView);

            day=itemView.findViewById(R.id.dayNameTV);
            temperature=itemView.findViewById(R.id.forcasttempTV);
            conditon=itemView.findViewById(R.id.forcastConditionTV);
            Log.e(TAG,"successful in viewHolder ");


        }
    }



}
