package com.example.maps;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class FavorisActivity extends AppActivity {
    private ListView listViewFavoris;
    private ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoris);
        listViewFavoris = findViewById(R.id.listViewFavoris);
        adapter = new ArrayAdapter<String>(
                FavorisActivity.this,
                android.R.layout.simple_list_item_1,
                ListFavoris.stringList
        );
        // To Add the favoris icon on action bar
        // setContentView(R.layout.activity_favoris);
        // Get the transferred data from source activity.
        Intent intent = getIntent();
        String nomDuCommerce = intent.getStringExtra("nom_du_commerce");

        if (nomDuCommerce!=null){
            Log.i("test nom commerce", "Value: " +nomDuCommerce);
            ListFavoris.stringList.add(nomDuCommerce);
            adapter.notifyDataSetChanged();
        }
        listViewFavoris.setAdapter(adapter);
    }
}
