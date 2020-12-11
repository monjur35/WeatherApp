package com.example.weatherapp.Helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.security.PrivateKey;

public class WeatherPreference {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private final String TAG="temp_unit";

    public WeatherPreference(Context context) {
        sharedPreferences=context.getSharedPreferences("weather_prefs",Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }
    public void setTempUnit(boolean tag){
        editor.putBoolean(TAG,tag);
        editor.commit();
    }

    public Boolean getTempUnit(){
        return sharedPreferences.getBoolean(TAG,false);

    }
}
