package com.example.massaa_tabata.data;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.massaa_tabata.AddSeanceActivity;
import com.example.massaa_tabata.MainActivity;
import com.example.massaa_tabata.R;
import com.example.massaa_tabata.db.DatabaseClient;
import com.example.massaa_tabata.db.Seance;

import java.util.List;

public class SeanceAdapter extends ArrayAdapter<Seance> {

    // DATABASE INSTANCE
    private DatabaseClient databaseClient;


    public SeanceAdapter(Context context, List<Seance> seances) {
        super(context, R.layout.list_item, seances);
        databaseClient = DatabaseClient.getInstance(getContext());
    }

    /**
     * Fill listview row with infos
     *
     * @param position    position in list
     * @param convertView convertview
     * @param parent      list parent
     * @return filled view
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // Récupération de la multiplication
        final Seance seance = getItem(position);

        // Load template
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.list_item, parent, false);

        // Get template content
        TextView textViewTask = (TextView) rowView.findViewById(R.id.tv_name);
        ImageButton ib_delete = (ImageButton) rowView.findViewById(R.id.ib_delete);
        ImageButton ib_edit = (ImageButton) rowView.findViewById(R.id.ib_edit);
        ImageButton ib_play = (ImageButton) rowView.findViewById(R.id.ib_play);

        // Set text
        textViewTask.setText(seance.getName());

        // Set on click actions
        ib_delete.setOnClickListener(v -> deleteSeance(seance));

        ib_edit.setOnClickListener(v -> {
            Intent intentAdd = new Intent(getContext(), AddSeanceActivity.class);
            intentAdd.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            intentAdd.putExtra("Seance", seance);
            getContext().startActivity(intentAdd);
        });

        ib_play.setOnClickListener(v -> {
            Intent intentMain = new Intent(getContext(), MainActivity.class);
            intentMain.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            intentMain.putExtra("Seance", seance);
            getContext().startActivity(intentMain);
        });

        return rowView;
    }

    private void deleteSeance(Seance seance) {

        /**
         * Async class to delete the seance
         */
        class DeleteSeance extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                databaseClient.getAppDatabase()
                        .seanceDAO()
                        .delete(seance);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);

                SeanceAdapter.this.remove(seance);
                SeanceAdapter.this.notifyDataSetChanged();
                Toast.makeText(SeanceAdapter.this.getContext(), "Séance supprimée", Toast.LENGTH_LONG).show();
            }

        }

        // execute async request
        DeleteSeance deleteSeance = new DeleteSeance();
        deleteSeance.execute();

    }

}