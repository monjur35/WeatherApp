package com.example.weatherapp.Repositoru;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.weatherapp.CurrenModel.CurrentWeatheModel;
import com.example.weatherapp.ForecastModel.ForecastWeatheModel;
import com.example.weatherapp.Helpers.WeatherService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherRepository {
    private Context context;
    private WeatherService weatherService;
    private String TAG="Checked";


    public WeatherRepository( WeatherService weatherService) {
        this.weatherService = weatherService;
    }


    public MutableLiveData<CurrentWeatheModel> fetchCurrentData(String endUrl){
        MutableLiveData<CurrentWeatheModel> currentWeatheModelMutableLiveData=new MutableLiveData<>();

        weatherService.getWeatherResponse(endUrl).enqueue(new Callback<CurrentWeatheModel>() {
            @Override
            public void onResponse(Call<CurrentWeatheModel> call, Response<CurrentWeatheModel> response) {
                if (response.isSuccessful()){
                    final CurrentWeatheModel currentWeatherResponse =response.body();
                    currentWeatheModelMutableLiveData.postValue(currentWeatherResponse);
                    //Log.e(TAG,"successful");
                }

            }

            @Override
            public void onFailure(Call<CurrentWeatheModel> call, Throwable t) {
                Toast.makeText(context, ""+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        return currentWeatheModelMutableLiveData;
    }





    public MutableLiveData<ForecastWeatheModel> fetchForcastData(String endUrl){
        MutableLiveData<ForecastWeatheModel> forecastWeatheModelMutableLiveData=new MutableLiveData<>();
        weatherService.getForcastData(endUrl).enqueue(new Callback<ForecastWeatheModel>() {
            @Override
            public void onResponse(Call<ForecastWeatheModel> call, Response<ForecastWeatheModel> response) {
                if (response.isSuccessful()){
                    final ForecastWeatheModel forecastWeatheModel=response.body();
                    forecastWeatheModelMutableLiveData.postValue(forecastWeatheModel);
                    Log.e(TAG,"successful in fetching forecast ");
                }
            }

            @Override
            public void onFailure(Call<ForecastWeatheModel> call, Throwable t) {
                Log.e(TAG,"UNsuccessful in fetching forecast  "+t.getLocalizedMessage() );
            }
        });
        return forecastWeatheModelMutableLiveData;
    }
}
