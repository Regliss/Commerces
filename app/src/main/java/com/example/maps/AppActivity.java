package com.example.maps;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class AppActivity extends AppCompatActivity {

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish(); // fermeture de l'acticity
                break;
            case R.id.action_favorite:
                // User chose the "Settings" item, show the app settings UI...
                Intent myIntentFavoris2 = new Intent(this, FavorisActivity.class);
                startActivity(myIntentFavoris2);
                return true;

            case R.id.ActionShowMap:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Intent myIntentMaps = new Intent(this, MapsActivity.class);
                startActivity(myIntentMaps);
                return true;
            case R.id.ActionShowFavoris:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Intent myIntentFavoris = new Intent(this, FavorisActivity.class);
                startActivity(myIntentFavoris);
                return true;
            case R.id.ActionShowInformation:
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Intent myIntentInformation = new Intent(this, InformationActivity.class);
                startActivity(myIntentInformation);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //affichage du menu

        //chargement du menu
        getMenuInflater().inflate(R.menu.menu_default, menu);

        return super.onCreateOptionsMenu(menu);
    }

}
