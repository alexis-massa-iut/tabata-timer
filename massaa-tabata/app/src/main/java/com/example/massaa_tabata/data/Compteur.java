package com.example.massaa_tabata.data;

import android.os.CountDownTimer;
import android.util.Log;

public class Compteur extends UpdateSource {

    // Default
    private long INITIAL_TIME = 5000;

    // DATA
    private long updatedTime;
    private CountDownTimer timer;


    public Compteur() { updatedTime = INITIAL_TIME; }

    // Lancer le compteur
    public void start() {
        if (timer == null) {

            // Créer le CountDownTimer
            timer = new CountDownTimer(updatedTime, 10) {

                // Callback fired on regular interval
                public void onTick(long millisUntilFinished) {
                    updatedTime = millisUntilFinished;

                    // Mise à jour
                    update();
                }

                // Callback fired when the time is up
                public void onFinish() {
                    updatedTime = 0;

                    // Détruire le timer
                    stop();

                    // Mise à jour
                    update();
                }

            }.start();   // Start the countdown
        }

    }

    // Mettre en pause le compteur
    public void pause() {

        if (timer != null) {

            // Arreter le timer
            stop();

            // Mise à jour
            update();
        }
    }


    // Remettre à le compteur à la valeur initiale
    public void reset() {

        if (timer != null) {

            // Arreter le timer
            stop();
        }

        // Réinitialiser
        updatedTime = INITIAL_TIME;

        // Mise à jour
        update();

    }

    // Arrete l'objet CountDownTimer et l'efface
    private void stop() {
        timer.cancel();
        timer = null;
    }

    public long getTimeDiff() {
        return updatedTime;
    }

    public int getMinutes() {
        return (int) (updatedTime / 1000) / 60;
    }

    public int getSecondes() {
        return (int) (updatedTime / 1000) % 60;
    }

    public int getMillisecondes() {
        return (int) (updatedTime % 1000);
    }

    public int getInitialTime() {
        return (int) INITIAL_TIME;
    }

    public void setInitialTime(int initialTime) {
        INITIAL_TIME = initialTime* 1000L;
        updatedTime = INITIAL_TIME;
    }

}
