package com.example.massaa_tabata;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.massaa_tabata.data.SeanceAdapter;
import com.example.massaa_tabata.db.DatabaseClient;
import com.example.massaa_tabata.db.Seance;

import java.util.ArrayList;
import java.util.List;

public class ListSeanceActivity extends BaseActivity {

    // DATA
    DatabaseClient databaseClient;
    SeanceAdapter adapter;

    // VIEW
    ListView listSeances;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_seance);


        // get DatabaseClient
        databaseClient = DatabaseClient.getInstance(getApplicationContext());

        // Get views
        listSeances = findViewById(R.id.listview_seances);


        // Link adapter to listview
        adapter = new SeanceAdapter(this, new ArrayList<>());
        listSeances.setAdapter(adapter);

    }


    /**
     *
     */
    private void getSeances() {
        // Asynchronous class to get seances and update listview
        class GetSeances extends AsyncTask<Void, Void, List<Seance>> {

            @Override
            protected List<Seance> doInBackground(Void... voids) {
                return databaseClient.getAppDatabase()
                        .seanceDAO()
                        .getAll();
            }

            @Override
            protected void onPostExecute(List<Seance> seances) {
                super.onPostExecute(seances);

                // update adapter with seance list
                adapter.clear();
                adapter.addAll(seances);

                // Now, notify the adapter of the change in source
                adapter.notifyDataSetChanged();
            }
        }

        // Create async object and send asynchronous request
        GetSeances getSeances = new GetSeances();
        getSeances.execute();
    }


    @Override
    protected void onStart() {
        super.onStart();

        // Update seances
        getSeances();
    }
}
