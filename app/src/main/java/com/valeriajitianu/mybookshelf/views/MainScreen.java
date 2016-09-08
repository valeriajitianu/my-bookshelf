package com.valeriajitianu.mybookshelf.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.valeriajitianu.mybookshelf.Categories;
import com.valeriajitianu.mybookshelf.CustomList;
import com.valeriajitianu.mybookshelf.R;

public class MainScreen extends AppCompatActivity {
    String[] textRecent = {
            "Item 1",
            "Item 2",
            "Item 3",
            "Item 4",
            "Item 5"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        ListView listView = (ListView) findViewById(R.id.listCategories);
        listView.setAdapter(new CustomList(this, Categories.getCategoryValues(), R.drawable.arrow));

        ListView listRecent = (ListView) findViewById(R.id.listRecent);
        listRecent.setAdapter(new CustomList(this, textRecent, R.drawable.arrow));

        ImageButton addBook = (ImageButton) findViewById(R.id.addBook);
        addBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addNewBook = new Intent();
                addNewBook.setClass(getApplicationContext(), AddBook.class);
                startActivity(addNewBook);
            }
        });
    }
}
