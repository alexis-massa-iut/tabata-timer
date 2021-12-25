package com.example.massaa_tabata;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.massaa_tabata.data.Compteur;
import com.example.massaa_tabata.data.OnUpdateListener;
import com.example.massaa_tabata.data.Pair;
import com.example.massaa_tabata.db.Seance;

import java.util.List;

public class MainActivity extends BaseActivity implements OnUpdateListener {

    private static final String TAG = "MainActivity";

    // VIEW
    private TextView timerValue, p2label, p2time, p1label, p1time, cLabel, cTime, n1label, n1time, n2label, n2time;
    private ImageView sound, stop, restart, fastForward, playPause;
    private ProgressBar progressBar;

    // DATA
    private Compteur compteur;
    private Seance seance;
    private List<Pair<String, Integer>> timeArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Récupérer la view
        timerValue = (TextView) findViewById(R.id.timerValue);
        p2label = (TextView) findViewById(R.id.p2label);
        p2time = (TextView) findViewById(R.id.p2time);
        p1label = (TextView) findViewById(R.id.p1label);
        p1time = (TextView) findViewById(R.id.p1time);
        cLabel = (TextView) findViewById(R.id.cLabel);
        cTime = (TextView) findViewById(R.id.cTime);
        n1label = (TextView) findViewById(R.id.n1label);
        n1time = (TextView) findViewById(R.id.n1time);
        n2label = (TextView) findViewById(R.id.n2label);
        n2time = (TextView) findViewById(R.id.n2time);
        sound = (ImageView) findViewById(R.id.ic_sound);
        stop = (ImageView) findViewById(R.id.ic_stop);
        restart = (ImageView) findViewById(R.id.ic_restart);
        fastForward = (ImageView) findViewById(R.id.ic_fast_forward);
        playPause = (ImageView) findViewById(R.id.ic_play_pause);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        // Récupérer les extras
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            seance = (Seance) extras.getSerializable("Seance");
            if (seance != null) {
                Log.d(TAG, seance.toString());
                Toast.makeText(getApplicationContext(), "Séance chargée", Toast.LENGTH_LONG).show();
                timeArray = seance.getRoutine();
//                Log.d(TAG, timeArray.toString());
                play(timeArray, 4);
            }
        }


        // Initialiser l'objet Compteur
        compteur = new Compteur();

        // Abonner l'activité au compteur pour "suivre" les événements
        compteur.addOnUpdateListener(this);

        // Mise à jour graphique
        miseAJour();


    }

    /**
     * @param timeArray liste des activités à réaliser (paires :
     * @param i         position courante dans la séance
     */
    private void play(List<Pair<String, Integer>> timeArray, Integer i) {
        int size = timeArray.size();
        timerValue = (TextView) findViewById(R.id.timerValue);

        if (i >= 2) {
            p2label.setText(timeArray.get(i - 2).getLabel());
            p2time.setText(String.valueOf(timeArray.get(i - 2).getTime()));
        }
        if (i >= 1) {
            p1label.setText(timeArray.get(i - 1).getLabel());
            p1time.setText(String.valueOf(timeArray.get(i - 1).getTime()));
        }
        cLabel.setText(timeArray.get(i).getLabel());
        cTime.setText(String.valueOf(timeArray.get(i).getTime()));
        if (i < size) {
            n1label.setText(timeArray.get(i + 1).getLabel());
            n1time.setText(String.valueOf(timeArray.get(i + 1).getTime()));
        }
        if (i < size - 1) {
            n2label.setText(timeArray.get(i + 2).getLabel());
            n2time.setText(String.valueOf(timeArray.get(i + 2).getTime()));
        }
    }


    // Lancer le compteur
    public void onStart(View view) {
        compteur.start();
        playPause.setImageResource(R.drawable.ic_pause);
        playPause.setOnClickListener(v -> onPause(view));
    }

    // Mettre en pause le compteur
    public void onPause(View view) {
        compteur.pause();
        playPause.setImageResource(R.drawable.ic_play);
        playPause.setOnClickListener(v -> onStart(view));

    }

    // Remettre à zéro le compteur
    public void onReset(View view) {
        compteur.reset();
    }

    // TODO : Skip to next position in list
    public void onFastforward(View view) {
        compteur.reset();
    }

    // Restart timer from 0
    public void onRestart(View view) {
        compteur.reset();
        compteur.start();
    }

    // Mute sound
    // TODO : MUTE SOUND
    public void onMute(View view) {
        sound.setBackgroundResource(R.drawable.ic_sound_off);
        sound.setOnClickListener(v -> onUnMute(view));
    }

    // Unmute sound
    // TODO : UNMUTE SOUND
    public void onUnMute(View view) {
        sound.setBackgroundResource(R.drawable.ic_sound_on);
        sound.setOnClickListener(v -> onMute(view));
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
