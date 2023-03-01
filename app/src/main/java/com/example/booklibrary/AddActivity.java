package com.example.booklibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {

    EditText inputTitle, inputAutor, inputPages;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        inputTitle = findViewById(R.id.inputTitle);
        inputAutor = findViewById(R.id.inputBookAuthor);
        inputPages = findViewById(R.id.inputNumberOfPages);
        button = findViewById(R.id.buttonAddBook);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDatabaseHelper mydb = new MyDatabaseHelper(AddActivity.this);

                mydb.addBook(inputTitle.getText().toString().trim(),
                        inputAutor.getText().toString().trim(),
                        Integer.parseInt(inputPages.getText().toString().trim())
                );



            }
        });
    }


}