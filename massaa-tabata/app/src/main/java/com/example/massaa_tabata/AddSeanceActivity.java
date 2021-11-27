package com.example.massaa_tabata;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.massaa_tabata.db.DatabaseClient;
import com.example.massaa_tabata.db.Seance;

public class AddSeanceActivity extends BaseActivity {

    // DATABASE INSTANCE
    private DatabaseClient databaseClient;

    // VIEW
    private EditText et_name, et_prepTime, et_nbSequences, et_nbCycles, et_workTime, et_restTime, et_longRestTime;
    private Button btn_save;

    // DATA
    Seance seance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_seance);

        // get DatabaseClient
        databaseClient = DatabaseClient.getInstance(getApplicationContext());

        // get views
        et_name = findViewById(R.id.et_name);
        et_prepTime = findViewById(R.id.et_prepTime);
        et_nbSequences = findViewById(R.id.et_nbSequences);
        et_nbCycles = findViewById(R.id.et_nbCycles);
        et_workTime = findViewById(R.id.et_workTime);
        et_restTime = findViewById(R.id.et_restTime);
        et_longRestTime = findViewById(R.id.et_longRestTime);
        btn_save = findViewById(R.id.btn_save);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            seance = (Seance) extras.getSerializable("Seance");
            et_name.setText(seance.getName());
            et_prepTime.setText(String.valueOf(seance.getPrepTime()));
            et_nbSequences.setText(String.valueOf(seance.getNbSequences()));
            et_nbCycles.setText(String.valueOf(seance.getNbCycles()));
            et_workTime.setText(String.valueOf(seance.getWorkTime()));
            et_restTime.setText(String.valueOf(seance.getRestTime()));
            et_longRestTime.setText(String.valueOf(seance.getLongRestTime()));

        }

        // event listener on save button
        btn_save.setOnClickListener(view -> submit());
    }

    private void submit(){
        if (seance != null)
            updateSeance();
        else
            saveSeance();
    }

    private void saveSeance() {

        // get views info
        final String name = et_name.getText().toString().trim();
        final int prepTime = Integer.parseInt(et_prepTime.getText().toString());
        final int nbSequences = Integer.parseInt(et_nbSequences.getText().toString());
        final int nbCycles = Integer.parseInt(et_nbCycles.getText().toString());
        final int workTime = Integer.parseInt(et_workTime.getText().toString());
        final int restTime = Integer.parseInt(et_restTime.getText().toString());
        final int longRestTime = Integer.parseInt(et_longRestTime.getText().toString());

        // Check inputs
        if (name.isEmpty()) {
            et_name.setError("Nom de séance requis");
            et_name.requestFocus();
            return;
        }

        /**
         * Async class to save the seance
         */
        class SaveSeance extends AsyncTask<Void, Void, Seance> {

            @Override
            protected Seance doInBackground(Void... voids) {

                // creating a seance
                seance = new Seance();
                seance.setName(name);
                seance.setPrepTime(prepTime);
                seance.setNbSequences(nbSequences);
                seance.setNbCycles(nbCycles);
                seance.setWorkTime(workTime);
                seance.setRestTime(restTime);
                seance.setLongRestTime(longRestTime);

                // adding to database
                long id = databaseClient.getAppDatabase()
                        .seanceDAO()
                        .insert(seance);

                // Update seance id : to use the id later in activity
                seance.setId(id);

                return seance;
            }

            @Override
            protected void onPostExecute(Seance seance) {
                super.onPostExecute(seance);

                // Once seance is created, finish activity AddSeanceActivity
                setResult(RESULT_OK);
                finish();
                Toast.makeText(getApplicationContext(), "Séance ajoutée", Toast.LENGTH_LONG).show();
            }
        }

        // execute async request
        SaveSeance saveSeance = new SaveSeance();
        saveSeance.execute();
    }

    private void updateSeance() {

        // get views info
        final String name = et_name.getText().toString().trim();
        final int prepTime = Integer.parseInt(et_prepTime.getText().toString());
        final int nbSequences = Integer.parseInt(et_nbSequences.getText().toString());
        final int nbCycles = Integer.parseInt(et_nbCycles.getText().toString());
        final int workTime = Integer.parseInt(et_workTime.getText().toString());
        final int restTime = Integer.parseInt(et_restTime.getText().toString());
        final int longRestTime = Integer.parseInt(et_longRestTime.getText().toString());

        // Check inputs
        if (name.isEmpty()) {
            et_name.setError("Nom de séance requis");
            et_name.requestFocus();
            return;
        }

        /**
         * Async class to update the seance
         */
        class UpdateSeance extends AsyncTask<Void, Void, Seance> {

            @Override
            protected Seance doInBackground(Void... voids) {

                // creating a seance
                seance.setName(name);
                seance.setPrepTime(prepTime);
                seance.setNbSequences(nbSequences);
                seance.setNbCycles(nbCycles);
                seance.setWorkTime(workTime);
                seance.setRestTime(restTime);
                seance.setLongRestTime(longRestTime);

                // update database
                databaseClient.getAppDatabase()
                        .seanceDAO()
                        .update(seance);

                return seance;
            }

            @Override
            protected void onPostExecute(Seance seance) {
                super.onPostExecute(seance);

                // Once seance is updated, finish activity AddSeanceActivity
                setResult(RESULT_OK);
                finish();
                Toast.makeText(getApplicationContext(), "Séance modifiée", Toast.LENGTH_LONG).show();
            }
        }

        // execute async request
        UpdateSeance saveSeance = new UpdateSeance();
        saveSeance.execute();
    }

}
