package com.example.massaa_tabata;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Class to handle menu across all activities
 * Every Activity that needs the top bar needs to extend this class
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.back:
                Intent intentMain = new Intent(this, MainActivity.class);
                intentMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentMain);
                break;
            case R.id.workout_list:
                Intent intentListSeance = new Intent(this, ListSeanceActivity.class);
                intentListSeance.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intentListSeance);
                break;
            case R.id.create_workout:
                Intent intentAddSeance = new Intent(this, AddSeanceActivity.class);
                intentAddSeance.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentAddSeance);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
