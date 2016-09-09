package com.valeriajitianu.mybookshelf.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.valeriajitianu.mybookshelf.Book;
import com.valeriajitianu.mybookshelf.R;

public class BookDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);

        Book bookDetails = (Book) getIntent().getSerializableExtra("bookDetails");

        TextView tBook = (TextView) findViewById(R.id.tBook);
        tBook.setText(bookDetails.getTitle());

        TextView aBook = (TextView) findViewById(R.id.aBook);
        aBook.setText(bookDetails.getAuthor());

        TextView cBook = (TextView) findViewById(R.id.cBook);
        cBook.setText(Category.getCategoryName(bookDetails.getCategory()));
    }
}
