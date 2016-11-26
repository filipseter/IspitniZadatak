package com.example.android.ispitnizadatak;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    public static final String FILE_NAME = "file.db";
    public static final int DATABASE_VERSION = 1;

    private Dao<Notes, Integer> notesDao;

    public DatabaseHelper(Context context) {
        super(context, FILE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable( connectionSource, Notes.class );
        } catch (SQLException e) {
            throw new RuntimeException( e );
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable( connectionSource, Notes.class, true );
            onCreate( database, connectionSource );
        } catch (SQLException e) {
            throw new RuntimeException( e );
        }
    }

    @Override
    public void close() {
        notesDao = null;
        super.close();
    }

    public Dao<Notes, Integer> getNotesDao() throws SQLException {
        if ( notesDao == null ) {
            notesDao = getDao( Notes.class );
        }
        return notesDao;
    }

}
