package com.example.camerakt.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.camerakt.database.model.OCTTable;
import com.example.camerakt.response.Field;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "octtable_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(OCTTable.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + OCTTable.TABLE_NAME);

        onCreate(db);
    }

    public long insertOCTTable(List<String> octTableCase){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(OCTTable.COLUMN_NUM, Integer.valueOf(octTableCase.get(0)));
        values.put(OCTTable.COLUMN_CODE, octTableCase.get(1));
        values.put(OCTTable.COLUMN_ORIGIN, octTableCase.get(2));
        values.put(OCTTable.COLUMN_CULTIVAR, octTableCase.get(3));
        values.put(OCTTable.COLUMN_INDATE, octTableCase.get(4));
        values.put(OCTTable.COLUMN_OUTDATE, octTableCase.get(5));
        values.put(OCTTable.COLUMN_WEIGHT, Integer.valueOf(octTableCase.get(6)));
        values.put(OCTTable.COLUMN_COUNT, Integer.valueOf(octTableCase.get(7)));
        values.put(OCTTable.COLUMN_PRICE, octTableCase.get(8));
        values.put(OCTTable.COLUMN_WON, octTableCase.get(9));
        values.put(OCTTable.COLUMN_EXTRA, octTableCase.get(10));

        long id = db.insert(OCTTable.TABLE_NAME,null, values);

        db.close();

        return id;
    }

    @SuppressLint("Range")
    public OCTTable getOne(long id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(OCTTable.TABLE_NAME,
                new String[]{OCTTable.COLUMN_NUM, OCTTable.COLUMN_CODE, OCTTable.COLUMN_ORIGIN, OCTTable.COLUMN_CULTIVAR, OCTTable.COLUMN_INDATE, OCTTable.COLUMN_OUTDATE, OCTTable.COLUMN_WEIGHT, OCTTable.COLUMN_COUNT, OCTTable.COLUMN_COUNT, OCTTable.COLUMN_PRICE, OCTTable.COLUMN_WON, OCTTable.COLUMN_EXTRA},
                OCTTable.COLUMN_NUM + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        OCTTable octTable = new OCTTable();
        octTable.setNum(cursor.getInt(cursor.getColumnIndex(OCTTable.COLUMN_NUM)));
        octTable.setCode(cursor.getString(cursor.getColumnIndex(OCTTable.COLUMN_CODE)));
        octTable.setOrigin(cursor.getString(cursor.getColumnIndex(OCTTable.COLUMN_ORIGIN)));
        octTable.setCultivar(cursor.getString(cursor.getColumnIndex(OCTTable.COLUMN_CULTIVAR)));
        octTable.setIndate(cursor.getString(cursor.getColumnIndex(OCTTable.COLUMN_INDATE)));
        octTable.setOutdate(cursor.getString(cursor.getColumnIndex(OCTTable.COLUMN_OUTDATE)));
        octTable.setWeight(cursor.getInt(cursor.getColumnIndex(OCTTable.COLUMN_WEIGHT)));
        octTable.setCount(cursor.getInt(cursor.getColumnIndex(OCTTable.COLUMN_COUNT)));
        octTable.setPrice(cursor.getString(cursor.getColumnIndex(OCTTable.COLUMN_PRICE)));
        octTable.setWon(cursor.getString(cursor.getColumnIndex(OCTTable.COLUMN_WON)));
        octTable.setExtra(cursor.getString(cursor.getColumnIndex(OCTTable.COLUMN_EXTRA)));



        cursor.close();

        return octTable;
    }

    @SuppressLint("Range")
    public List<OCTTable> getAllTables() {
        List<OCTTable> result = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + OCTTable.TABLE_NAME + " ORDER BY "
                + OCTTable.COLUMN_NUM + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                OCTTable octTable = new OCTTable();
                octTable.setNum(cursor.getInt(cursor.getColumnIndex(OCTTable.COLUMN_NUM)));
                octTable.setCode(cursor.getString(cursor.getColumnIndex(OCTTable.COLUMN_CODE)));
                octTable.setOrigin(cursor.getString(cursor.getColumnIndex(OCTTable.COLUMN_ORIGIN)));
                octTable.setCultivar(cursor.getString(cursor.getColumnIndex(OCTTable.COLUMN_CULTIVAR)));
                octTable.setIndate(cursor.getString(cursor.getColumnIndex(OCTTable.COLUMN_INDATE)));
                octTable.setOutdate(cursor.getString(cursor.getColumnIndex(OCTTable.COLUMN_OUTDATE)));
                octTable.setWeight(cursor.getInt(cursor.getColumnIndex(OCTTable.COLUMN_WEIGHT)));
                octTable.setCount(cursor.getInt(cursor.getColumnIndex(OCTTable.COLUMN_COUNT)));
                octTable.setPrice(cursor.getString(cursor.getColumnIndex(OCTTable.COLUMN_PRICE)));
                octTable.setWon(cursor.getString(cursor.getColumnIndex(OCTTable.COLUMN_WON)));
                octTable.setExtra(cursor.getString(cursor.getColumnIndex(OCTTable.COLUMN_EXTRA)));

                result.add(octTable);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return result;
    }
}
