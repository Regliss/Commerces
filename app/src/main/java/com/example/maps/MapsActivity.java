package com.example.maps;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText editTextSearch;
    private TextView textViewNomDuCommerce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.menu);
        //Intent intent = getIntent();
        //String value = intent.getStringExtra("isInformation");
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
                editTextSearch = findViewById(R.id.editTextSearch);
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

        // Add a marker in Sydney and move the camera
        LatLng paris = new LatLng(48.8534, 2.3488);
        LatLng Velisi = new LatLng(48.8, 2.1833);
        mMap.addMarker(new MarkerOptions().position(paris).title(getString(R.string.marker)));
        mMap.addMarker(new MarkerOptions().position(Velisi).title(getString(R.string.marker)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(paris));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Velisi));
    }

    public void submit(View view) {
        if(editTextSearch.getText().toString().isEmpty()) {
            FastDialog.showDialog(
                    MapsActivity.this,
                    FastDialog.SIMPLE_DIALOG,
                    "Vous devez renseigner un commerce"
            );
            return;
        }
        if(!Network.isNetworkAvailable(MapsActivity.this)) {
            FastDialog.showDialog(
                    MapsActivity.this,
                    FastDialog.SIMPLE_DIALOG,
                    "Vous devez être connecté"
            );
            return;
        }
    }
}