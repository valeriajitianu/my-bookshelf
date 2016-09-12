package com.valeriajitianu.mybookshelf.views;

import android.content.ContentValues;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.valeriajitianu.mybookshelf.BookStorage;
import com.valeriajitianu.mybookshelf.Categories;
import com.valeriajitianu.mybookshelf.R;

import java.util.Date;

public class AddBook extends AppCompatActivity {
    EditText bookTitle, bookAuthor;
    Spinner bookCategory;
    long category = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        bookTitle = (EditText) findViewById(R.id.bookTitle);
        bookAuthor = (EditText) findViewById(R.id.bookAuthor);

        populateSpinnerWithCategories();

        ImageButton bookImage = (ImageButton) findViewById(R.id.bookImage);
        bookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        goBackWhenCancelButtonClicked();

        saveBookWhenAddButtonClicked();
    }

    private void saveBookWhenAddButtonClicked() {
        Button addBookButton = (Button) findViewById(R.id.addBook);
        addBookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = bookTitle.getText().toString();
                String author = bookAuthor.getText().toString();

                if (title.isEmpty()) {
                    Toast.makeText(AddBook.this, "The title cannot be empty.", Toast.LENGTH_SHORT).show();
                    return;
                }

                ContentValues values = setInsertValues(title, author);

                BookStorage booksDB = BookStorage.getInstance(getApplicationContext());
                if (booksDB.insertValues(values)) {
                    Toast.makeText(AddBook.this, "Your book was saved.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                    Toast.makeText(AddBook.this, "There was a problem. Try again please.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    private ContentValues setInsertValues(String title, String author) {
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("author", author);
        values.put("image_path", "");
        values.put("category", category);
        values.put("created", new Date().getTime());
        return values;
    }

    private void goBackWhenCancelButtonClicked() {
        Button cancelButton = (Button) findViewById(R.id.cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void populateSpinnerWithCategories() {
        bookCategory = (Spinner) findViewById(R.id.categoryChoice);
        bookCategory.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Categories.getCategoryValues()));
        bookCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = bookCategory.getSelectedItemId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                category = bookCategory.getSelectedItemId();
            }
        });
    }
}
