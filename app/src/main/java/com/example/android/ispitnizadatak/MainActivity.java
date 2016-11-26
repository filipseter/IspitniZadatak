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

import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.sql.SQLException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setToolbar();
        refreshNoteList();
    }

    private void setToolbar() {
        toolbar = ( Toolbar ) findViewById( R.id.toolbar );
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ( item.getItemId() ) {
            case R.id.action_add:
                callAddActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void refreshNoteList() {
        ListView listView = ( ListView ) findViewById( R.id.listview_notes );
        ListAdapter adapter;
        List<Notes> list;
        try {
            list = getDatabaseHelper().getNotesDao().queryForAll();
            adapter = new ArrayAdapter<>( this, R.layout.textview_list_item, list );
            listView.setAdapter( adapter );
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

    private void callAddActivity() {
        Intent intent = new Intent( MainActivity.this, AddActivity.class );
        startActivity( intent );
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshNoteList();
    }
}
