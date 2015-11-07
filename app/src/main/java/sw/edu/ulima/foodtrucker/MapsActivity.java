package sw.edu.ulima.foodtrucker;

import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;


import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    public void onSearch(View view) {
        EditText location_tf = (EditText) findViewById(R.id.eteAddress);
        String location = location_tf.getText().toString();
        List<Address> addressList = null;

        if (location != null || !location.equals("")) {
            Geocoder geocoder = new Geocoder(this);
            try {
                addressList = geocoder.getFromLocationName(location, 1);

            } catch (IOException e) {
                e.printStackTrace();
            }

            Address address = addressList.get(0);
            LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(total, 11));
        mMap.setMyLocationEnabled(true);
        mMap.setTrafficEnabled(true);
        mMap.setOnMarkerClickListener(this);

        UiSettings u = mMap.getUiSettings();
        u.setZoomControlsEnabled(true);
        LatLng gringo=new LatLng(-12.07658,-77.03546);
        LatLng hitnrun=new LatLng(-12.085219,-76.977340);
        LatLng limaSabrosa=new LatLng(-12.12781,-77.00943);
        mMap.addMarker(new MarkerOptions().position(gringo).title("El Gringo").icon(BitmapDescriptorFactory.fromResource(R.drawable.iconeg)));
        mMap.addMarker(new MarkerOptions().position(hitnrun).title("Hit'n Run").icon(BitmapDescriptorFactory.fromResource(R.drawable.iconhr)));
        mMap.addMarker(new MarkerOptions().position(limaSabrosa).title("Lima Sabrosa").icon(BitmapDescriptorFactory.fromResource(R.drawable.iconls)));
    }

    @Override
    public void onBackPressed() {
        MapsActivity.this.finish();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getTitle().equals("El Gringo")) {
            Intent intent = new Intent(this, ElGringoActivity.class);
            startActivity(intent);
            return true;
        }else if(marker.getTitle().equals("Hit'n Run")){
            Intent intent = new Intent(this, HitnRunActivity.class);
            startActivity(intent);
            return true;
        }else if(marker.getTitle().equals("Lima Sabrosa")){
            Intent intent = new Intent(this, LimaSabrosaActivity.class);
            startActivity(intent);
            return true;
        }else{
            return false;
        }
    }
}
