// app/src/main/java/com/yourcompany/farmmanagement/database/FarmDBHelper.java
package com.jkm.SFarmer.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class FarmDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FarmManagement.db";
    private static final int DATABASE_VERSION = 1;

    // أسماء الجداول
    public static final String TABLE_FARMS = "farms";
    public static final String TABLE_YARDS = "yards";
    public static final String TABLE_EMPLOYEES = "employees";
    public static final String TABLE_EQUIPMENT = "equipment";

    // أسماء الأعمدة المشتركة
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_CREATED_AT = "created_at";

    public FarmDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createFarmsTable(db);
        createYardsTable(db);
        createEmployeesTable(db);
        createEquipmentTable(db);
    }

    private void createFarmsTable(SQLiteDatabase db) {
        String CREATE_FARMS_TABLE = "CREATE TABLE " + TABLE_FARMS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT NOT NULL,"
                + "area REAL,"
                + "total_yards INTEGER,"
                + "address TEXT,"
                + "logo BLOB,"
                + COLUMN_CREATED_AT + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                + ")";
        db.execSQL(CREATE_FARMS_TABLE);
    }

    private void createYardsTable(SQLiteDatabase db) {
        String CREATE_YARDS_TABLE = "CREATE TABLE " + TABLE_YARDS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "farm_id INTEGER,"
                + "yard_number TEXT,"
                + "area REAL,"
                + "workers_count INTEGER,"
                + "crop_type TEXT,"
                + "trees_count INTEGER,"
                + "production REAL,"
                + "planting_date TEXT,"
                + "expected_harvest_date TEXT,"
                + "FOREIGN KEY(farm_id) REFERENCES " + TABLE_FARMS + "(" + COLUMN_ID + ")"
                + ")";
        db.execSQL(CREATE_YARDS_TABLE);
    }

    private void createEmployeesTable(SQLiteDatabase db) {
        String CREATE_EMPLOYEES_TABLE = "CREATE TABLE " + TABLE_EMPLOYEES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT NOT NULL,"
                + "phone TEXT,"
                + "address TEXT,"
                + "position TEXT,"
                + "salary REAL,"
                + "hire_date TEXT,"
                + "farm_id INTEGER,"
                + "FOREIGN KEY(farm_id) REFERENCES " + TABLE_FARMS + "(" + COLUMN_ID + ")"
                + ")";
        db.execSQL(CREATE_EMPLOYEES_TABLE);
    }

    private void createEquipmentTable(SQLiteDatabase db) {
        String CREATE_EQUIPMENT_TABLE = "CREATE TABLE " + TABLE_EQUIPMENT + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT NOT NULL,"
                + "type TEXT,"
                + "purchase_date TEXT,"
                + "cost REAL,"
                + "status TEXT,"
                + "maintenance_date TEXT,"
                + "farm_id INTEGER,"
                + "FOREIGN KEY(farm_id) REFERENCES " + TABLE_FARMS + "(" + COLUMN_ID + ")"
                + ")";
        db.execSQL(CREATE_EQUIPMENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FARMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_YARDS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EQUIPMENT);
        onCreate(db);
    }
}