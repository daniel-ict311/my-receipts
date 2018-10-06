package au.edu.usc.dcd008.myreceipts;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public static final String EXTRA_LONGITUDE =
            "au.edu.usc.dcd008.myreceipts.logitude";
    public static final String EXTRA_LATITUDE =
            "au.edu.usc.dcd008.myreceipts.latitude";

    private final int ZOOM_LEVEL = 15;
    private double mLatitude = 0;
    private double mLongitude = 0;

    public static Intent newIntent(Context packageContext, double latitude, double longitude){
        Intent intent = new Intent(packageContext, MapsActivity.class);
        intent.putExtra(EXTRA_LONGITUDE, longitude);
        intent.putExtra(EXTRA_LATITUDE, latitude);
        return intent;
    }


    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            mLatitude = extras.getDouble(EXTRA_LATITUDE);
            mLongitude = extras.getDouble(EXTRA_LONGITUDE);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng pos = new LatLng(mLatitude, mLongitude);
        mMap.addMarker(new MarkerOptions().position(pos).title(getString(R.string.marker_title)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pos));

        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(pos, ZOOM_LEVEL);
        mMap.animateCamera(update);
    }
}
