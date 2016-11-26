package com.example.android.ispitnizadatak;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by android on 26.11.16..
 */

@DatabaseTable( tableName = Notes.TABLE_NAME )
public class Notes {

    public static final String TABLE_NAME = "notes";
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String DETAILS = "details";

    @DatabaseField( columnName = ID, generatedId = true )
    private int id;
    @DatabaseField( columnName = NAME )
    private String name;
    @DatabaseField( columnName = DETAILS )
    private String details;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDetails() {
        return details;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return name;
    }

    public Notes() {

    }
}
