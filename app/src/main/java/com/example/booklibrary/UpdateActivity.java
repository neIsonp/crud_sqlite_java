package com.example.booklibrary;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdateActivity extends AppCompatActivity {

    EditText Etitle, Eauthor, Epages;
    Button buttonUpdate, buttonDelete;

    String id, title, author, pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Etitle = findViewById(R.id.inputTitleUpdate);
        Eauthor = findViewById(R.id.inputBookAuthorUpdate);
        Epages = findViewById(R.id.inputNumberOfPagesUpdate);
        buttonUpdate = findViewById(R.id.buttonUpdateBook);
        buttonDelete = findViewById(R.id.buttonDeleteBook);

        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setTitle(title);

        getAndSetIntentData();

        MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                title = Etitle.getText().toString();
                author = Eauthor.getText().toString();
                pages = Epages.getText().toString();
                myDB.updateData(id, title, author, Integer.parseInt(pages));
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmDialog();

            }
        });
    }

    void getAndSetIntentData(){

        Intent i = getIntent();

        id = i.getStringExtra("id");
        title = i.getStringExtra("title");
        author = i.getStringExtra("author");
        pages = i.getStringExtra("pages");

        Etitle.setText(title);
        Eauthor.setText(author);
        Epages.setText(pages);

    }
    void confirmDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + "?");
        builder.setMessage("Are you sure you want to delete this book?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneBook(id);
                finish();
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