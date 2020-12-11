package com.example.weatherapp.ViewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.weatherapp.CurrenModel.CurrentWeatheModel;
import com.example.weatherapp.ForecastModel.ForecastWeatheModel;
import com.example.weatherapp.Retrofit.RetrofitClient;
import com.example.weatherapp.Repositoru.WeatherRepository;
import com.example.weatherapp.Helpers.WeatherService;

public class WeatherViewModel extends AndroidViewModel {
    private WeatherRepository weatherRepository;
    private WeatherService weatherService;
    private String TAG="Checked";


    public WeatherViewModel(@NonNull Application application) {
        super(application);
        weatherService = RetrofitClient.getClient().create(WeatherService.class);
        weatherRepository=new WeatherRepository(weatherService);

    }

    public MutableLiveData<CurrentWeatheModel> getCurrentData(String endUrl){
        return weatherRepository.fetchCurrentData(endUrl);
    }

    public MutableLiveData<ForecastWeatheModel> getForcastData(String endUrl){
        Log.e(TAG,"successful in WeatherView Model get Forecast data ");
        return weatherRepository.fetchForcastData(endUrl);

    }
}
