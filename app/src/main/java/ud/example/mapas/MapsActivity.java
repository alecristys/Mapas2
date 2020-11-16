package ud.example.mapas;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private LatLng Estoyaqui;
    private EditText latitud, longitud;
    private CheckBox colocarmarker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Estoyaqui = new LatLng(location.getLatitude(),location.getLongitude());

            }
        };

        latitud = findViewById(R.id.editTextTextPersonName);
        longitud = findViewById(R.id.editTextTextPersonName1);
        colocarmarker = findViewById(R.id.checkBox);
        requestPermi();

    }

    private void requestPermi() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED
                &&
            ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED){
              //  &&
           // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_WIFI_STATE) !=
               // PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                  //  Manifest.permission.ACCESS_WIFI_STATE
            }, 101);
        }
        else {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locationListener);

        }
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        if(colocarmarker.isChecked()) {
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.setOnMapClickListener((GoogleMap.OnMapClickListener)this);
    }

    public void MapaNormal(View v){
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }
    public void MapaSatelite(View v){
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }
    public void MapaTerreno(View v){
        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
    }
    public void MapaNinguno(View v){
        mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
    }
    public void MapaHybrido(View v){
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
    }
    private final LatLng SAN = new LatLng(12.587151,-81.698713);
    public void IrParaiso(View v){
        if (colocarmarker.isChecked()){
            Marker miMaker = mMap.addMarker(new MarkerOptions()
                    .position(SAN)
                    .title("SAN ANDRES")
                    .snippet("Aqui Muero"));
            miMaker.showInfoWindow();
       mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(SAN, 15));}
    }
    public void irUD(View v){
        LatLng UD = new LatLng(4.6281058,-74.0659999);
        if(colocarmarker.isChecked()) {
            Marker miMaker = mMap.addMarker(new MarkerOptions()
                    .position(UD)
                    .title("UD")
                    .snippet("Universidad Distrital FJDC"));
            miMaker.showInfoWindow();
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(UD, 17));
    }
    public void Borrar(View v){
        mMap.clear();
    }
    public void addMarker(View v){
        LatLng TempLatLng = mMap.getCameraPosition().target;
        mMap.addMarker(new MarkerOptions()
        .position(TempLatLng)
        .icon(BitmapDescriptorFactory.defaultMarker(
                BitmapDescriptorFactory.HUE_AZURE)));
    }

    public void IraEstoyaqui(View v) {
        if (colocarmarker.isChecked()){
            Marker miMaker = mMap.addMarker(new MarkerOptions()
                    .position(Estoyaqui)
                    .title("Aqui Vivo")
                    .snippet("Esta es mi Casa"));
            miMaker.showInfoWindow();}
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Estoyaqui, 19));
    }

    @Override
    public void onMapClick(LatLng latLng) {
        if (colocarmarker.isChecked()){
        mMap.addMarker(new MarkerOptions().position(latLng));}
    }

    public void llevaradestino(View v) {
        double x = Double.valueOf(latitud.getText().toString());
        double y = Double.valueOf(longitud.getText().toString());
        LatLng dada = new LatLng(x, y);
        if(colocarmarker.isChecked()){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(dada, 15));
        mMap.addMarker(new MarkerOptions().position(dada).title("Llevar a destino"));}
    }

}