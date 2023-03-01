package com.example.booklibrary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FloatingActionButton floatingActionButton;

    MyDatabaseHelper myDatabaseHelper;
    ArrayList<String> bookid, bookTitle, bookAuthor, bookPages;
    CustomAdapter customAdapter;

    ImageView imageView;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        floatingActionButton = findViewById(R.id.addButton);
        imageView = findViewById(R.id.imageViewAddNewBook);
        textView = findViewById(R.id.textViewAddNewBook);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddActivity.class);
                startActivity(i);
            }
        });

        myDatabaseHelper = new MyDatabaseHelper(MainActivity.this);
        bookid = new ArrayList<>();
        bookTitle = new ArrayList<>();
        bookAuthor = new ArrayList<>();
        bookPages = new ArrayList<>();

        storeDataInArrayList();

        recyclerView = findViewById(R.id.recyclerView);

        customAdapter = new CustomAdapter(MainActivity.this, this, bookid, bookTitle, bookAuthor, bookPages);

        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 1){
            super.onResume();
            recreate();
        }
    }
    void storeDataInArrayList(){
        Cursor cursor = myDatabaseHelper.readAllData();

        if(cursor.getCount() == 0){
            imageView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.VISIBLE);

        }else{
            while (cursor.moveToNext()){
                bookid.add(cursor.getString(0));
                bookTitle.add(cursor.getString(1));
                bookAuthor.add(cursor.getString(2));
                bookPages.add(cursor.getString(3));
            }
            imageView.setVisibility(View.GONE);
            textView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.delete_all){
            confirmDialog();
            bookid.clear();
            bookTitle.clear();
            bookAuthor.clear();
            bookPages.clear();
            Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();

        }
        return super.onOptionsItemSelected(item);
    }

    void confirmDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all the books?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                myDatabaseHelper.deleteAllBooks();

            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }
}