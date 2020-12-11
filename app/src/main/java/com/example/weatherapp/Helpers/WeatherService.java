package com.example.weatherapp.Helpers;

import com.example.weatherapp.CurrenModel.CurrentWeatheModel;
import com.example.weatherapp.ForecastModel.ForecastWeatheModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface WeatherService {
    @GET
    Call<CurrentWeatheModel> getWeatherResponse(@Url String endUrl);

    @GET
    Call<ForecastWeatheModel> getForcastData(@Url String endURl);
}
