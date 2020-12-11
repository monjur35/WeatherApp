package com.example.weatherapp.Helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class WeatherUtils {
    public static String getFormattedDateOrTime(long date,String format){
        return new SimpleDateFormat(format).format(new Date(date*1000));



    }
    public static final String WEATHER_API_KEY="06c4f7ba24ae64d790a873332d2fe658";
    public static final String ICON_PREFIX="https://openweathermap.org/img/wn/";
    public static final String ICON_SUFFIX="@2x.png";
}
