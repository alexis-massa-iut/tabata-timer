package com.example.massaa_tabata;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.example.massaa_tabata.data.ListAdapter;
import com.example.massaa_tabata.databinding.ActivityListSeanceBinding;
import com.example.massaa_tabata.db.DatabaseClient;
import com.example.massaa_tabata.db.Seance;

import java.util.List;

public class ListSeanceActivity extends BaseActivity {

    // DATABASE INSTANCE
    private DatabaseClient databaseClient;
    List<Seance> seances = databaseClient.getAppDatabase()
            .seanceDAO()
            .getAll();
    // VIEW
    private ActivityListSeanceBinding binding;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityListSeanceBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        listAdapter = new ListAdapter(ListSeanceActivity.this, seances);
        binding.list.setAdapter(listAdapter);
        binding.list.setClickable(true);
        binding.list.setOnClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intentMain = new Intent(ListSeanceActivity.this, MainActivity.class);
                intentMain.putExtra(seances[position]);
                startActivity(intentMain);
            }
        });

    }
}
