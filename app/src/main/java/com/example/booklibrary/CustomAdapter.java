package com.example.booklibrary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList<String> bookid, bookTitle, bookAuthor, bookPages;


    public CustomAdapter(Activity activity,Context context, ArrayList<String> bookid, ArrayList<String> bookTitle, ArrayList<String> bookAuthor, ArrayList<String> bookPages) {
        this.activity = activity;
        this.context = context;
        this.bookid = bookid;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookPages = bookPages;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {

        holder.id.setText(bookid.get(position));
        holder.title.setText(bookTitle.get(position));
        holder.author.setText(bookAuthor.get(position));
        holder.pages.setText(bookPages.get(position));

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, UpdateActivity.class);
                i.putExtra("id", bookid.get(position));
                i.putExtra("title", bookTitle.get(position));
                i.putExtra("author", bookAuthor.get(position));
                i.putExtra("pages", bookPages.get(position));
                activity.startActivityForResult(i, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bookid.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView id, title, author, pages;
        ConstraintLayout constraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.bookIdtext);
            title = itemView.findViewById(R.id.bookTitleText);
            author = itemView.findViewById(R.id.bookAutortext);
            pages = itemView.findViewById(R.id.bookPagestext);
            constraintLayout = itemView.findViewById(R.id.mainDesignlayout);
        }
    }
}