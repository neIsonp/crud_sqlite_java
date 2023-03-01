package com.example.booklibrary;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private final Context context;
    private static final String databaseName = "booklibrary.db";
    private static final int databaseVersion = 1;
    private static final String table_name = "myLibrary";
    private static final String column_id = "_id", column_title = "book_title", column_autor = "book_author", column_pages = "book_pages";


    public MyDatabaseHelper(@Nullable Context context) {
        super(context, databaseName, null, databaseVersion);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + table_name +
                " (" + column_id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + column_title + " TEXT, "
                + column_autor + " TEXT, "
                + column_pages + " INTEGER);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + table_name);
        onCreate(db);

    }

    public void addBook(String title, String author, int pages){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(column_title, title);
        cv.put(column_autor, author);
        cv.put(column_pages, pages);

        long result = db.insert(table_name, null, cv);

        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    public Cursor readAllData(){

        String query = "SELECT * FROM " + table_name;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
            cursor = db.rawQuery(query, null);
        }

        return cursor;
    }

    public void updateData(String row_id, String title, String author, int pages){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(column_title, title);
        cv.put(column_autor, author);
        cv.put(column_pages, pages);

        long result = db.update(table_name, cv, "_id = ?", new String[]{row_id});

        if(result == -1){
            Toast.makeText(context, "Failed to update", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Updated!", Toast.LENGTH_SHORT).show();

        }
    }

    public void deleteOneBook(String row_id){
        SQLiteDatabase database = this.getWritableDatabase();
        long result = database.delete(table_name, "_id = ?", new String[]{row_id});

        if(result == -1){
            Toast.makeText(context, "Failed to Delete the Book", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted!", Toast.LENGTH_SHORT).show();

        }
    }

    public void deleteAllBooks(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + table_name);
    }
}