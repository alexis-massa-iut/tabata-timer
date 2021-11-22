package com.example.massaa_tabata.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.massaa_tabata.ListSeanceActivity;
import com.example.massaa_tabata.R;
import com.example.massaa_tabata.db.Seance;

import java.util.List;

public class ListAdapter extends ArrayAdapter<Seance> {

    public ListAdapter(Context context, List<Seance> seanceList){
        super(context, R.layout.activity_list_seance, seanceList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Seance seance = getItem(position);
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_seance, parent, false);
        }

        TextView tv_name = convertView.findViewById(R.id.tv_name);

        tv_name.setText(seance.getName());

        return super.getView(position, convertView, parent);
    }
}
