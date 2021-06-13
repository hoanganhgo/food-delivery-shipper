package com.hcmus.fit.shipper;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.hcmus.fit.shipper.constant.Constant;
import com.hcmus.fit.shipper.models.OrderManager;
import com.hcmus.fit.shipper.models.ShipperInfo;
import com.hcmus.fit.shipper.network.MySocket;
import com.hcmus.fit.shipper.network.SignInNetwork;
import com.hcmus.fit.shipper.util.NotifyUtil;

public class MainActivity extends AppCompatActivity implements LocationListener {

    //permission
    private static final int MY_PERMISSIONS_REQUEST_COARSE_LOCATION = 124;
    private static final int MY_PERMISSIONS_REQUEST_FINE_LOCATION = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_order, R.id.navigation_income, R.id.navigation_message,
                R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        SignInNetwork.getUserInfo(this);
        MySocket.getInstance();
        OrderManager.getInstance().setActivity(this);
        OrderManager.getInstance().setOrderDialog( getOrderDialog());

        checkPermission();
        Location location = getCurrentLocation();
        if (location != null) {
            ShipperInfo.getInstance().setLatitude(location.getLatitude());
            ShipperInfo.getInstance().setLongitude(location.getLongitude());
        }

        NotifyUtil.init(this);
    }

    private Dialog getOrderDialog() {
        Dialog dialog = new Dialog(this, R.style.FullScreen);
        dialog.setContentView(R.layout.activity_recieve_order);
        return dialog;
    }

    private void checkPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_FINE_LOCATION);
            }
        }

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_COARSE_LOCATION);
            }
        }
    }

    private Location getCurrentLocation() {
        //GPS
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        assert locationManager != null;
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return null;
        }

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                Constant.TIME_UPDATE_LOCATION, Constant.DISTANCE_UPDATE_LOCATION, this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                Constant.TIME_UPDATE_LOCATION, Constant.DISTANCE_UPDATE_LOCATION, this);

        Criteria criteria = new Criteria();

        return locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        ShipperInfo.getInstance().setLatitude(location.getLatitude());
        ShipperInfo.getInstance().setLongitude(location.getLongitude());
        MySocket.updateCoor(location.getLatitude(), location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}