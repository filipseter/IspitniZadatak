package com.example.android.ispitnizadatak;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;

/**
 * Created by android on 26.11.16..
 */
public class AddActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setToolbar();

        final EditText note_name = ( EditText ) findViewById( R.id.edittext_insert_name );
        final EditText note_details = ( EditText ) findViewById( R.id.edittext_insert_details );
        Button save = ( Button ) findViewById( R.id.button_add_save );
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNoteToDatabase(
                        note_name.getText().toString(),
                        note_details.getText().toString()
                );
            }
        });
        Button cancel = ( Button ) findViewById( R.id.button_add_cancel );
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void saveNoteToDatabase(String note_name, String note_detail) {
        Notes note = new Notes();
        note.setName( note_name );
        note.setDetails(note_detail);
        try {
            getDatabaseHelper().getNotesDao().create( note );
        } catch (SQLException e) {
            e.printStackTrace();
        }
        onBackPressed();
    }

    private void setToolbar() {
        toolbar = ( Toolbar ) findViewById( R.id.toolbar );
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if ( databaseHelper != null ) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }

    public DatabaseHelper getDatabaseHelper() {
        if ( databaseHelper == null ) {
            databaseHelper = OpenHelperManager.getHelper( this, DatabaseHelper.class );
        }
        return databaseHelper;
    }

}
