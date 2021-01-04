package com.example.maps;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.maps.models.ApiCommerces;
import com.example.maps.models.ApiFields;
import com.google.gson.Gson;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
public class InformationActivity extends AppActivity {
    private EditText editTextCity;
    private TextView textViewNomDuCommerce;
    private TextView textViewAdresse;
    private TextView textViewTelephone;
    private TextView textViewMail;
    private TextView textViewSiteInternet;
    private TextView textViewTypeDeCommerce;
    private TextView textViewServices;
    private TextView textViewFabriqueAParis;
    private Button saveFavoris;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        editTextCity = findViewById(R.id.editTextCity);
        textViewNomDuCommerce = findViewById(R.id.textViewNomDuCommerce);
        textViewAdresse = findViewById(R.id.textViewAdresse);
        textViewTelephone = findViewById(R.id.textViewTelephone);
        textViewMail = findViewById(R.id.textViewMail);
        textViewSiteInternet = findViewById(R.id.textViewSiteInternet);
        textViewTypeDeCommerce = findViewById(R.id.textViewTypeDeCommerce);
        textViewServices = findViewById(R.id.textViewServices);
        textViewFabriqueAParis = findViewById(R.id.textViewFabriqueAParis);
        saveFavoris = findViewById(R.id.ButtonSaveFavoris);

        saveFavoris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent IntentAddToFavoris = new Intent(InformationActivity.this, FavorisActivity.class);
                Bundle extras = new Bundle();
                extras.putString("nom_du_commerce", textViewNomDuCommerce.getText().toString());
                extras.putString("tel_du_commerce", textViewTelephone.getText().toString());
                IntentAddToFavoris.putExtras(extras);
                startActivity(IntentAddToFavoris);
            }
        });

        // Intent depuis la carte
        if(getIntent().getExtras() != null) {
            ApiFields fields = (ApiFields) getIntent().getExtras().get("objet");
            textViewNomDuCommerce.setText(fields.getNom_du_commerce());
            textViewAdresse.setText(fields.getAdresse());
            textViewFabriqueAParis.setText(fields.getFabrique_a_paris());
            textViewMail.setText(fields.getMail());
            textViewSiteInternet.setText(fields.getSite_internet());
            textViewServices.setText(fields.getServices());
            textViewTelephone.setText(fields.getTelephone());
            textViewTypeDeCommerce.setText(fields.getType_de_commerce());
        }
    }
    public void submitInformation(View view) {
        hideKeyboard();
        if(editTextCity.getText().toString().isEmpty()) {
            FastDialog.showDialog(
                    InformationActivity.this,
                    FastDialog.SIMPLE_DIALOG,
                    "Vous devez renseigner un commerce"
            );
            return;
        }
        if(!Network.isNetworkAvailable(InformationActivity.this)) {
            FastDialog.showDialog(
                    InformationActivity.this,
                    FastDialog.SIMPLE_DIALOG,
                    "Vous devez être connecté"
            );
            return;
        }
        // requête HTTP
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = String.format(Constant.URL, editTextCity.getText().toString(), "metric");
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
        textViewNomDuCommerce.setText(null);
        textViewAdresse.setText(null);
        textViewTelephone.setText(null);
        textViewMail.setText(null);
        textViewSiteInternet.setText(null);
        textViewTypeDeCommerce.setText(null);
        textViewServices.setText(null);
        textViewFabriqueAParis.setText(null);
        // GSON
        ApiCommerces api = new Gson().fromJson(json, ApiCommerces.class);
        if(api.getNhits() > 0) {

            textViewNomDuCommerce.setText(api.getRecords().get(0).getFields().getNom_du_commerce());
            textViewAdresse.setText(api.getRecords().get(0).getFields().getAdresse());
            textViewFabriqueAParis.setText(api.getRecords().get(0).getFields().getFabrique_a_paris());
            textViewMail.setText(api.getRecords().get(0).getFields().getMail());
            textViewSiteInternet.setText(api.getRecords().get(0).getFields().getSite_internet());
            textViewServices.setText(api.getRecords().get(0).getFields().getServices());
            textViewTelephone.setText(api.getRecords().get(0).getFields().getTelephone());
            textViewTypeDeCommerce.setText(api.getRecords().get(0).getFields().getType_de_commerce());

        } else {
            FastDialog.showDialog(
                    InformationActivity.this,
                    FastDialog.SIMPLE_DIALOG,
                    "Pas de résultat"
            );
        }
    }
    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) this.getSystemService(this.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}