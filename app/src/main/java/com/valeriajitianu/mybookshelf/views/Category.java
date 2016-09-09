package com.valeriajitianu.mybookshelf.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.valeriajitianu.mybookshelf.Book;
import com.valeriajitianu.mybookshelf.BookStorage;
import com.valeriajitianu.mybookshelf.CustomList;
import com.valeriajitianu.mybookshelf.R;

import java.util.ArrayList;
import java.util.List;

import static com.valeriajitianu.mybookshelf.Categories.*;

public class Category extends AppCompatActivity {
    String categoryName;
    int categoryId;
    List<Book> booksList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);
        categoryId = getIntent().getIntExtra("category", 0);
        categoryName = getCategoryName(categoryId);
        booksList = BookStorage.getInstance(getApplicationContext()).getBooksInCategory(categoryId);

        TextView categoryText = (TextView) findViewById(R.id.categoryName);
        categoryText.setText(categoryName);

        ListView books = (ListView) findViewById(R.id.listBooksInCategory);
        books.setAdapter(new CustomList(this, getBookTitles(), R.drawable.arrow));
        books.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent viewBook = new Intent();
                viewBook.putExtra("bookDetails", booksList.get(position));
                viewBook.setClass(getApplicationContext(), BookDetails.class);
                startActivity(viewBook);
            }
        });

        ImageButton backButton = (ImageButton) findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private String[] getBookTitles() {
        List<String> titlesList = new ArrayList<>();
        for (Book book : booksList)
            titlesList.add(book.getTitle());

        return titlesList.toArray(new String[titlesList.size()]);
    }

    public static String getCategoryName(int category) {
        String categoryName = WISHLIST.getName();
        switch (category) {
            case 0 : categoryName = WISHLIST.getName(); break;
            case 1 : categoryName = READ.getName(); break;
            case 2 : categoryName = CURRENTLY_READING.getName(); break;
        }
        return categoryName;
    }
}
