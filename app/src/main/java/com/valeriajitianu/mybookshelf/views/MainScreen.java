package com.valeriajitianu.mybookshelf.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.valeriajitianu.mybookshelf.Book;
import com.valeriajitianu.mybookshelf.BookStorage;
import com.valeriajitianu.mybookshelf.Categories;
import com.valeriajitianu.mybookshelf.CustomList;
import com.valeriajitianu.mybookshelf.R;

import java.util.ArrayList;
import java.util.List;

public class MainScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        ListView categoryList = (ListView) findViewById(R.id.listCategories);
        categoryList.setAdapter(new CustomList(this, Categories.getCategoryValues(), R.drawable.arrow));
        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent viewBooksInCategory = new Intent();
                viewBooksInCategory.putExtra("category", position);
                viewBooksInCategory.setClass(getApplicationContext(), Category.class);
                startActivity(viewBooksInCategory);
            }
        });

        List<Book> booksList = BookStorage.getInstance(this).getMostRecentBooks(5);
        ListView listRecent = (ListView) findViewById(R.id.listRecent);
        listRecent.setAdapter(new CustomList(this, getBookTitles(booksList), R.drawable.arrow));

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

    private String[] getBookTitles(List<Book> booksList) {
        List<String> titlesList = new ArrayList<>();
        for (Book book : booksList)
            titlesList.add(book.getTitle());

        return titlesList.toArray(new String[titlesList.size()]);
    }
}
