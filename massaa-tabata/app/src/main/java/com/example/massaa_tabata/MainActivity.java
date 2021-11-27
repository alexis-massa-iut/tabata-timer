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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Seance seance;
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            seance = null;
        } else {
            seance = (Seance) extras.getSerializable("Seance");
        }

    }

    @Override
    public void onUpdate() {

    }
}
