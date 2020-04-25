package com.example.taskforu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBhelper extends SQLiteOpenHelper {

    public DBhelper(Context _context){
        super(_context,Database.DATABASE_NAME,null,Database.DB_VERSION);
    }

    @Override

    //ID, TITLE, DESCRIPTION, DATE, CATEGORY
    public void onCreate(SQLiteDatabase db) {

        String CREATE = "CREATE TABLE " + Database.DbColumns.TABLE_NAME +
                "(" + Database.DbColumns.ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Database.DbColumns.TITLE +
                " TEXT NOT NULL, " + Database.DbColumns.DESCRIPTION + " TEXT, "+ Database.DbColumns.CATEGORY +
                " TEXT NOT NULL, " + Database.DbColumns.DATE + " TEXT);";
        db.execSQL(CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Database.DbColumns.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean insertTask(String title, String desc, String category, String date){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(Database.DbColumns.TITLE, title);
        contentValues.put(Database.DbColumns.DESCRIPTION,desc);
        contentValues.put(Database.DbColumns.CATEGORY, category);
        contentValues.put(Database.DbColumns.DATE, date);
        long result = db.insert(Database.DbColumns.TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }


    public void deleteTask(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM " + Database.DbColumns.TABLE_NAME + " WHERE " + Database.DbColumns.ID + "='" + id + "'" + " AND "
                + Database.DbColumns.TITLE + "= '" + name + "'";

        db.execSQL(sql);

    }

    public void updateDescription(String name, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql =   "UPDATE " + Database.DbColumns.TABLE_NAME + " SET " + Database.DbColumns.DESCRIPTION +
                "='" + name +"'" + " WHERE " + Database.DbColumns.ID + "='"+ id + "'";
        db.execSQL(sql);
    }

    public Cursor getDescription (String name , int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return  db.rawQuery("SELECT " + Database.DbColumns.DESCRIPTION + " FROM " + Database.DbColumns.TABLE_NAME+
                " WHERE " + Database.DbColumns.ID + "='"+ id + "'" +
                " AND " + Database.DbColumns.TITLE + "='" + name + "'",null);
    }

    public Cursor getDate (String name , int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return  db.rawQuery("SELECT " + Database.DbColumns.DATE + " FROM " + Database.DbColumns.TABLE_NAME+
                " WHERE " + Database.DbColumns.ID + "='"+ id + "'" +
                " AND " + Database.DbColumns.TITLE + "='" + name + "'",null);
    }
    public Cursor getCategory (String name , int id){
        SQLiteDatabase db = this.getWritableDatabase();
        return  db.rawQuery("SELECT " + Database.DbColumns.CATEGORY + " FROM " + Database.DbColumns.TABLE_NAME+
                " WHERE " + Database.DbColumns.ID + "='"+ id + "'" +
                " AND " + Database.DbColumns.TITLE + "='" + name + "'",null);
    }

    public Cursor SelectAll(){
        SQLiteDatabase db = this.getWritableDatabase();
       return  db.rawQuery("SELECT * FROM " + Database.DbColumns.TABLE_NAME,null);
    }

    public Cursor getDataID(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT " + Database.DbColumns.ID + " FROM " + Database.DbColumns.TABLE_NAME +
                " WHERE " + Database.DbColumns.TITLE + "='" + name + "'", null);
    }

}
