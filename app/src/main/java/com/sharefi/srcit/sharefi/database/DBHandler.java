package com.sharefi.srcit.sharefi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sharefi.srcit.sharefi.models.Connection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by juan.garza on 13/09/2017.
 */

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Nombre base de datos
    private static final String DATABASE_NAME = "dbsharefi";

    // nombre de tabla
    private static final String TABLE_CONNECTIONS = "connections";

    // columnas
    private static final String KEY_ID = "id";
    private static final String KEY_CONN_NAME = "conn_name";
    private static final String KEY_CONN_PASS = "conn_pass";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONNECTIONS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CONN_NAME + " TEXT,"
                + KEY_CONN_PASS + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONNECTIONS);
        onCreate(db);
    }

    // agregar nueva red
    public void addConnection(Connection conn) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CONN_NAME, conn.getName());
        values.put(KEY_CONN_PASS, conn.getPasscode());

        // Insertar registro
        db.insert(TABLE_CONNECTIONS, null, values);
        db.close();
    }

    // consultar una red
    public Connection getConnection(String connName) {
        Connection conn = null;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONNECTIONS, new String[]{KEY_ID, KEY_CONN_NAME, KEY_CONN_PASS}, KEY_CONN_NAME + "=?", new String[]{String.valueOf(connName)}, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            conn = new Connection(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2));
        }

        return conn;
    }

    // consultar todas las redes
    public List<Connection> getAllConnections() {
        List<Connection> connList = new ArrayList<Connection>();
        // Select All
        String selectQuery = "SELECT  * FROM " + TABLE_CONNECTIONS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Connection conn = new Connection();
                conn.setId(Integer.parseInt(cursor.getString(0)));
                conn.setName(cursor.getString(1));
                conn.setPasscode(cursor.getString(2));

                connList.add(conn);
            } while (cursor.moveToNext());
        }

        return connList;
    }

    // Consultar total de redes
    public int getConnectionsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONNECTIONS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

    // Actualizar red
    public int updateConnection(Connection conn) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CONN_NAME, conn.getName());
        values.put(KEY_CONN_PASS, conn.getPasscode());

        return db.update(TABLE_CONNECTIONS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(conn.getId())});
    }

    // Eliminar red
    public void deleteConnection(Connection conn) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONNECTIONS, KEY_ID + " = ?",
                new String[]{String.valueOf(conn.getId())});
        db.close();
    }
}
