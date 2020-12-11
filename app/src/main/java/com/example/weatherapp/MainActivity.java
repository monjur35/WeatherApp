package com.example.weatherapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
//import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import com.example.weatherapp.Helpers.WeatherPreference;
import com.example.weatherapp.ViewModels.LocationViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private FusedLocationProviderClient providerClient;
    private LocationViewModel locationViewModel;
    private WeatherPreference weatherPreference;
    private SearchView searchView;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        searchView= (SearchView) menu.findItem(R.id.serchID).getActionView();
        searchView.setQueryHint(getString(R.string.searchCity));
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                convertCityToLocation(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }

    private void convertCityToLocation(String s) {
        final Geocoder geocoder=new Geocoder(this);
        try {
            final List<Address>addressList=geocoder.getFromLocationName(s,1);
            double lat=addressList.get(0).getLatitude();
            double lng=addressList.get(0).getLongitude();
            final Location location=new Location("");
            locationViewModel.setNewLocation(location);
        }catch (Exception e){
            e.printStackTrace();
        }



    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final MenuItem fah_item=menu.findItem(R.id.farenhaitId);
        final MenuItem cel_item=menu.findItem(R.id.celciusId);
        if (weatherPreference.getTempUnit()){
            fah_item.setVisible(false);
            cel_item.setVisible(true);
        }
        else {
            fah_item.setVisible(true);
            cel_item.setVisible(false);
        }



        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.farenhaitId:
                weatherPreference.setTempUnit(true);
                break;
            case R.id.celciusId:
                weatherPreference.setTempUnit(false);
                break;
        }
        getUserCurrentLocation();
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_today, R.id.navigation_forecast)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);





        locationViewModel=new ViewModelProvider(this).get(LocationViewModel.class);
        providerClient= LocationServices.getFusedLocationProviderClient(this);
        weatherPreference=new WeatherPreference(this);



        if (isPermissionGranted()){
            getUserCurrentLocation();
        }
        else {
            requestLocationPermissionFromUser();
        }

    }
    public boolean isPermissionGranted(){
        return checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED;
    }
    public void requestLocationPermissionFromUser(){
        final String [] permissions={Manifest.permission.ACCESS_FINE_LOCATION};
        requestPermissions(permissions,111);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    if (requestCode==111 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
        getUserCurrentLocation();

    }
    else {
        Toast.makeText(this, "Denied by user", Toast.LENGTH_SHORT).show();
    }
    }

    private void getUserCurrentLocation() {
        providerClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location==null){
                    return;
                }
                else {
                    locationViewModel.setNewLocation(location);
                }
            }
        });
    }
}