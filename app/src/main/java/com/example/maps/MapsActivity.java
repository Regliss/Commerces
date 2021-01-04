package com.example.maps;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.maps.models.ApiCommerces;
import com.example.maps.models.ApiFields;
import com.example.maps.models.ApiRecords;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends AppActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private EditText editTextSearch;
    private TextView textViewNomDuCommerce;
    private Map<String, ApiFields> markers = new HashMap<String, ApiFields>();
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
        //LatLng paris = new LatLng(48.8534, 2.3488);
        //LatLng Velisi = new LatLng(48.8, 2.1833);
        //mMap.addMarker(new MarkerOptions().position(paris).title(getString(R.string.marker)));
        //mMap.addMarker(new MarkerOptions().position(Velisi).title(getString(R.string.marker)));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(paris));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(Velisi));

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                ApiFields fields = markers.get(marker.getId());
                // TODO : créer une Activity DetailsActivity et envoyer l'objet Fields

                Intent intentInfos = new Intent(MapsActivity.this, InformationActivity.class);
                intentInfos.putExtra("objet", fields);

                startActivity(intentInfos);
                Toast.makeText(MapsActivity.this, "ID: "+marker.getId()+" - "+fields.getNom_du_commerce(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void submitMaps(View view) {
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

        // requête HTTP
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = String.format(Constant.URL, editTextSearch.getText().toString());
        Log.e("volley", url);
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("volley", "onResponse: "+response);
                        parseJson(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("volley", "onErrorResponse: "+error);
                parseJson(new String(error.networkResponse.data));
            }
        });
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    private void parseJson(String json) {
        // GSON
        ApiCommerces api = new Gson().fromJson(json, ApiCommerces.class);
        if(api.getNhits() > 0) {

            //textViewNomDuCommerce.setText(api.getRecords().get(i).getFields().getNom_du_commerce());

            mMap.clear();
            for(int i=0; i<api.getRecords().size(); i++){
                ApiRecords commerce = api.getRecords().get(i);

                LatLng position = new LatLng(commerce.getFields().getGeo_point_2d()[0], commerce.getFields().getGeo_point_2d()[1]);
                Marker marker = mMap.addMarker(new MarkerOptions().position(position).title(commerce.getFields().getNom_du_commerce()));
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 14));
                markers.put(marker.getId(), commerce.getFields()); // pour associer l'identifiant d'un Market aux données (de l'objet Fields)
            }


        } else {
            FastDialog.showDialog(
                    MapsActivity.this,
                    FastDialog.SIMPLE_DIALOG,
                    "Pas de résultat"
            );
        }
    }
}