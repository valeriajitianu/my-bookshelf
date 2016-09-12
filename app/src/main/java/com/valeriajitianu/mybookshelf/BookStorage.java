package com.valeriajitianu.mybookshelf;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Valeria.Jitianu on 08.09.2016.
 */
public class BookStorage extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "booksdb";
    private static final String TABLE_NAME = "books";
    private static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    "'_id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "title CHAR(50) NOT NULL," +
                    "author CHAR(50)," +
                    "image_path CHAR(50)," +
                    "category INT NOT NULL," +
                    "created INTEGER NOT NULL);";

    private static BookStorage instance;

    public static BookStorage getInstance(Context context) {
        if (null == instance)
            instance = new BookStorage(context);
        return instance;
    }

    private BookStorage(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public boolean insertValues(ContentValues values) {
        if (getWritableDatabase().insert(TABLE_NAME, null, values) == -1)
            return false;
        return true;
    }

    public List<Book> getBooksInCategory(int categoryId) {
        List<Book> listBooks = new ArrayList<>();
        Cursor rows = getReadableDatabase().query(TABLE_NAME, new String[]{"title, author, image_path"},
                "category = " + categoryId,  null, "", "", "");

        while (rows.moveToNext()) {
            Book bookRow = new Book(rows.getString(0), rows.getString(1), rows.getString(2), categoryId);
            listBooks.add(bookRow);
        }

        return listBooks;
    }

    public List<Book> getMostRecentBooks(int limit) {
        List<Book> listBooks = new ArrayList<>();
        Cursor rows = getReadableDatabase().query(TABLE_NAME, new String[]{"title, author, image_path, category"},
                "",  null, "", "", "created DESC", limit + "");

        while (rows.moveToNext()) {
            Book bookRow = new Book(rows.getString(0), rows.getString(1), rows.getString(2), rows.getInt(3));
            listBooks.add(bookRow);
        }

        return listBooks;
    }
}
