package com.example.weatherapp.ui.Forcast;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherapp.Adapter.ForecastAdapter;
import com.example.weatherapp.ForecastModel.ForecastList;
import com.example.weatherapp.ForecastModel.ForecastWeatheModel;
import com.example.weatherapp.R;
import com.example.weatherapp.ViewModels.LocationViewModel;
import com.example.weatherapp.ViewModels.WeatherViewModel;
import com.example.weatherapp.Helpers.WeatherUtils;
import com.example.weatherapp.databinding.FragmentForecastBinding;

import java.util.ArrayList;
import java.util.List;

public class ForecastFragment extends Fragment {
    private LocationViewModel locationViewModel;
    private WeatherViewModel weatherViewModel;
    private ForecastAdapter adapter;
    private String unit="metric";
    FragmentForecastBinding binding;
    //List<ForecastList>forecastList;
    private final String TAG="Checked";



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        locationViewModel=new ViewModelProvider(requireActivity()).get(LocationViewModel.class);
        weatherViewModel=new ViewModelProvider(requireActivity()).get(WeatherViewModel.class);

        binding=FragmentForecastBinding.inflate(inflater);


        View root = inflater.inflate(R.layout.fragment_forecast, container, false);

        /*locationViewModel.locationMutableLiveData.observe(getViewLifecycleOwner(), new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                getData(location);
            }
        });*/
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        locationViewModel.locationMutableLiveData.observe(getViewLifecycleOwner(), new Observer<Location>() {
            @Override
            public void onChanged(Location location) {
                getData(location);
            }
        });

    }

    public void getData(Location location) {

        final String endUrl=String.format("forecast?lat=%f&lon=%f&units=%s&appid=%s",location.getLatitude(),location.getLongitude(),unit, WeatherUtils.WEATHER_API_KEY);
        weatherViewModel.getForcastData(endUrl).observe(getViewLifecycleOwner(), new Observer<ForecastWeatheModel>() {
            @Override
            public void onChanged(ForecastWeatheModel forecastWeatheModel) {
                final List<ForecastList> forecastListList=forecastWeatheModel.getList();
                adapter=new ForecastAdapter(getActivity(),forecastListList);
                final LinearLayoutManager llm=new LinearLayoutManager(getActivity());
                binding.recyclerViewRV.setLayoutManager(llm);
                binding.recyclerViewRV.setAdapter(adapter);
                Log.e(TAG,"getDAtaAdapter"+forecastListList.size());




            }
        });
    }
}