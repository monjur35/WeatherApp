package com.example.weatherapp.ui.Current_weather;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.weatherapp.CurrenModel.CurrentWeatheModel;
import com.example.weatherapp.R;
import com.example.weatherapp.ViewModels.LocationViewModel;
import com.example.weatherapp.ViewModels.WeatherViewModel;
import com.example.weatherapp.Helpers.WeatherPreference;
import com.example.weatherapp.Helpers.WeatherUtils;
import com.example.weatherapp.databinding.FragmentCurrentBinding;
import com.squareup.picasso.Picasso;

public class CurrentFragment extends Fragment {
    private LocationViewModel locationViewModel;
    private WeatherViewModel weatherViewModel;
    private FragmentCurrentBinding binding;
    private String unit="metric";
    private WeatherPreference weatherPreference;
    private String tempUnitSymbol="C";




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        locationViewModel=new ViewModelProvider(requireActivity()).get(LocationViewModel.class);
        weatherViewModel=new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);
        weatherPreference=new WeatherPreference(getActivity());

        binding=FragmentCurrentBinding.inflate(inflater);





        locationViewModel.locationMutableLiveData.observe(getViewLifecycleOwner(), new Observer<Location>() {
            @Override
            public void onChanged(Location location) {

                getData(location);
            }
        });
        return binding.getRoot();



    }








    private void getData(Location location) {

        unit=weatherPreference.getTempUnit() ? "imperial" : "metric";
        tempUnitSymbol=weatherPreference.getTempUnit() ?  "F":"C" ;
        final String endUrl=String.format("weather?lat=%f&lon=%f&units=%s&appid=%s",location.getLatitude(),location.getLongitude(),unit,WeatherUtils.WEATHER_API_KEY);

        weatherViewModel.getCurrentData(endUrl).observe(getViewLifecycleOwner(), new Observer<CurrentWeatheModel>() {
            @Override
            public void onChanged(CurrentWeatheModel currentWeatheModel) {
                binding.tempTV.setText(currentWeatheModel.getMain().getTemp()+"\u00B0"+tempUnitSymbol);
                binding.feelsLikeTV.setText("Feels Like : "+currentWeatheModel.getMain().getFeelsLike()+"\u00B0"+tempUnitSymbol);
                binding.dateTV.setText(WeatherUtils.getFormattedDateOrTime(currentWeatheModel.getDt(),"hh:mm a,MMM dd yyyy"));
                binding.cityTV.setText(currentWeatheModel.getName()+","+currentWeatheModel.getSys().getCountry());
                binding.conditionTV.setText(currentWeatheModel.getWeather().get(0).getMain()+","+currentWeatheModel.getWeather().get(0).getDescription());
                final String imageIcon=currentWeatheModel.getWeather().get(0).getIcon();
                Picasso.get().load(WeatherUtils.ICON_PREFIX+imageIcon+WeatherUtils.ICON_SUFFIX).into(binding.iconTV);
                binding.sunriseTV.setText("Sunrise : "+WeatherUtils.getFormattedDateOrTime(currentWeatheModel.getSys().getSunrise(),"hh:mm a" ));
                binding.sunSetTv.setText("Sunset : "+WeatherUtils.getFormattedDateOrTime(currentWeatheModel.getSys().getSunset(),"hh:mm a"));


            }
        });
    }



}