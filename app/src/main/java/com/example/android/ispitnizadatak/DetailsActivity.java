package com.example.android.ispitnizadatak;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

public class DetailsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DatabaseHelper databaseHelper;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        setToolbar();
        id = getIntent().getIntExtra("id", 0 );
        showDetails();
    }

    private void setToolbar() {
        toolbar = ( Toolbar ) findViewById( R.id.toolbar );
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_details, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ( item.getItemId() ) {
            case R.id.action_edit:
                break;
            case R.id.action_delete:
                deleteNoteFromDatabase();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteNoteFromDatabase() {
        Notes note;
        try {
            note = getDatabaseHelper().getNotesDao().queryForId( id );
            getDatabaseHelper().getNotesDao().delete( note );
            onBackPressed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    private void showDetails() {
        TextView name = ( TextView ) findViewById( R.id.textview_details_name );
        TextView more_details = ( TextView ) findViewById( R.id.textview_details_more );
        Notes note;
        try {
            note = getDatabaseHelper().getNotesDao().queryForId( id );
            name.setText( note.getName() );
            more_details.setText(note.getDetails());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
