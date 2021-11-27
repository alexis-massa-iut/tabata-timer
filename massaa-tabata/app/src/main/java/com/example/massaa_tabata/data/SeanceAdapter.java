package com.example.massaa_tabata.data;

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

    public SeanceAdapter(Context context, List<Seance> seances) {
        super(context, R.layout.list_item, seances);
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
        ib_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSeance(seance);
            }
        });

        ib_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAdd = new Intent(getContext(), AddSeanceActivity.class);
                intentAdd.putExtra("Seance", seance);
                getContext().startActivity(intentAdd);
            }
        });

        ib_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMain = new Intent(getContext(), MainActivity.class);
                intentMain.putExtra("Seance", seance);
                getContext().startActivity(intentMain);
            }
        });

        return rowView;
    }

    private void deleteSeance(Seance seance) {
        // TODO : delete

    }

}