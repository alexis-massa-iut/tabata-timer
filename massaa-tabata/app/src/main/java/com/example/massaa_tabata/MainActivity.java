package com.example.massaa_tabata;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

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
    private int currentPosition;
    private List<Pair<String, Integer>> timeArray;
    private MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mp = MediaPlayer.create(getApplicationContext(), R.raw.bell);
        currentPosition = 0;

        // Récupérer la view
        timerValue = findViewById(R.id.timerValue);
        p2label = findViewById(R.id.p2label);
        p2time = findViewById(R.id.p2time);
        p1label = findViewById(R.id.p1label);
        p1time = findViewById(R.id.p1time);
        cLabel = findViewById(R.id.cLabel);
        cTime = findViewById(R.id.cTime);
        n1label = findViewById(R.id.n1label);
        n1time = findViewById(R.id.n1time);
        n2label = findViewById(R.id.n2label);
        n2time = findViewById(R.id.n2time);
        sound = findViewById(R.id.ic_sound);
        stop = findViewById(R.id.ic_stop);
        restart = findViewById(R.id.ic_restart);
        fastForward = findViewById(R.id.ic_fast_forward);
        playPause = findViewById(R.id.ic_play_pause);
        progressBar = findViewById(R.id.progressBar);


        /*
        Si instance sauvée : reprendre
        Sinon :
            Si seance dans extras : jouer
            Sinon : Compteur par défaut
         */
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("currentPosition");
            seance = (Seance) savedInstanceState.getSerializable("seance");
            play(currentPosition);
        } else {
            // Récupérer les extras
            Bundle extras = getIntent().getExtras();
            if (extras != null) {

                seance = (Seance) extras.getSerializable("Seance");
                if (seance != null) {
                    Toast.makeText(getApplicationContext(), "Séance chargée", Toast.LENGTH_LONG).show();
                    timeArray = seance.getRoutine();

                    // Initialiser l'objet Compteur au premier élément
                    compteur = new Compteur();

                    // Abonner l'activité au compteur pour "suivre" les événements
                    compteur.addOnUpdateListener(this);

                    play(currentPosition);

                }
            } else {
                // Initialiser l'objet Compteur par défaut
                compteur = new Compteur();

                // Abonner l'activité au compteur pour "suivre" les événements
                compteur.addOnUpdateListener(this);

                // Mise à jour graphique
                miseAJour();
            }

        }
    }

    /**
     * Joue l'activité à la position i
     *
     * @param i position courante dans la séance
     */
    private void play(int i) {
        int size = timeArray.size();

//        Log.d(TAG, String.valueOf(currentPosition));

//      Affichage labels + temps
        if (i >= 2) {
            p2label.setText(timeArray.get(i - 2).getLabel());
            p2time.setText(String.valueOf(timeArray.get(i - 2).getTime()));
        } else {
            p2label.setText("");
            p2time.setText("");
        }
        if (i >= 1) {
            p1label.setText(timeArray.get(i - 1).getLabel());
            p1time.setText(String.valueOf(timeArray.get(i - 1).getTime()));
        } else {
            p1label.setText("");
            p1time.setText("");
        }
        cLabel.setText(timeArray.get(i).getLabel());
        cTime.setText(String.valueOf(timeArray.get(i).getTime()));
        if (i < size) {
            n1label.setText(timeArray.get(i + 1).getLabel());
            n1time.setText(String.valueOf(timeArray.get(i + 1).getTime()));
        } else {
            n1label.setText("");
            n1time.setText("");
        }
        if (i < size - 1) {
            n2label.setText(timeArray.get(i + 2).getLabel());
            n2time.setText(String.valueOf(timeArray.get(i + 2).getTime()));
        } else {
            n2label.setText("");
            n2time.setText("");
        }

        // Lancer timer courrant
        compteur.setInitialTime(timeArray.get(i).getTime());
        // Mise à jour graphique
        miseAJour();

        if (i != 0) {
            compteur.start();
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
        playPause.setImageResource(R.drawable.ic_play);
        playPause.setOnClickListener(v -> onStart(view));

    }

    // Skip to next position in list
    public void onFastforward(View view) {
        compteur.reset();
        currentPosition++;
        play(currentPosition);
    }

    // Restart timer from 0
    public void onRestart(View view) {
        compteur.reset();
        compteur.start();
    }

    // Mute sound
    public void onMute(View view) {
        mp.setVolume(0, 0);
        sound.setBackgroundResource(R.drawable.ic_sound_off);
        sound.setOnClickListener(v -> onUnMute(view));
    }

    // Unmute sound
    public void onUnMute(View view) {
        mp.setVolume(1, 1);
        sound.setBackgroundResource(R.drawable.ic_sound_on);
        sound.setOnClickListener(v -> onMute(view));
    }

    // Mise à jour graphique
    private void miseAJour() {

        // Affichage des informations du compteur
        timerValue.setText("" + compteur.getMinutes() + ":"
                + String.format("%02d", compteur.getSecondes()) + ":"
                + String.format("%03d", compteur.getMillisecondes()));

        // Affichage progress bar
        long progress = (100 * compteur.getTimeDiff()) / compteur.getInitialTime();
        progressBar.setProgress((int) progress);

        // Si le timer est terminé et qu'une activité est chargée : avancer d'une activité
        if (compteur.getTimeDiff() == 0 && seance != null) {
            mp.start();
            currentPosition++;
            play(currentPosition);
        }

    }

    /**
     * Méthode appelée à chaque update du compteur (l'activité est abonnée au compteur)
     **/

    @Override
    public void onUpdate() {
        miseAJour();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle savedInstanceState) {
        // save state of activity
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putSerializable("seance", seance);
        savedInstanceState.putInt("currentPosition", currentPosition);
    }

}
