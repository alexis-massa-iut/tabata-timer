package com.example.massaa_tabata;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.massaa_tabata.data.Compteur;
import com.example.massaa_tabata.data.OnUpdateListener;
import com.example.massaa_tabata.data.SeanceAdapter;
import com.example.massaa_tabata.db.Seance;

import java.util.List;

public class MainActivity extends BaseActivity implements OnUpdateListener {

    // VIEW
    private TextView timerValue;

    // DATA
    private Compteur compteur;
    private Seance seance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            seance = null;
        } else {
            seance = (Seance) extras.getSerializable("Seance");
        }

        // Récupérer la view
        timerValue = (TextView) findViewById(R.id.timerValue);

        // Initialiser l'objet Compteur
        compteur = new Compteur();

        // Abonner l'activité au compteur pour "suivre" les événements
        compteur.addOnUpdateListener(this);

        // Mise à jour graphique
        miseAJour();
    }


    // Lancer le compteur
    public void onStart(View view) {
        compteur.start();
    }

    // Mettre en pause le compteur
    public void onPause(View view) {
        compteur.pause();
    }


    // Remettre à zéro le compteur
    public void onReset(View view) {
        compteur.reset();
    }

    // TODO
    public void onFastforward(View view) {
        compteur.reset();
    }

    // TODO
    public void onRewind(View view) {
        compteur.reset();
    }

    // Mise à jour graphique
    private void miseAJour() {

        // Affichage des informations du compteur
        timerValue.setText("" + compteur.getMinutes() + ":"
                + String.format("%02d", compteur.getSecondes()) + ":"
                + String.format("%03d", compteur.getMillisecondes()));

    }

    /**
     * Méthode appelée à chaque update du compteur (l'activité est abonnée au compteur)
     **/

    @Override
    public void onUpdate() {
        miseAJour();
    }


}
