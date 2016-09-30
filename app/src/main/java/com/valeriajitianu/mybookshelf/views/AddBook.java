package com.valeriajitianu.mybookshelf.views;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.valeriajitianu.mybookshelf.BookStorage;
import com.valeriajitianu.mybookshelf.Categories;
import com.valeriajitianu.mybookshelf.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.*;

public class AddBook extends AppCompatActivity {
    final String CHOOSE_FROM_LIBRARY = "Choose from Library";
    EditText bookTitle, bookAuthor;
    Spinner bookCategory;
    ImageView bookImage;
    long category = 1;
    final int CAPTURE_IMAGE = 1;
    private String imagePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        bookTitle = (EditText) findViewById(R.id.bookTitle);
        bookAuthor = (EditText) findViewById(R.id.bookAuthor);

        populateSpinnerWithCategories();

        bookImage = (ImageView) findViewById(R.id.bookImage);
        bookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImageForBook();
            }
        });

        goBackWhenCancelButtonClicked();

        saveBookWhenAddButtonClicked();
    }

    private void captureImageForBook() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createFileForImage();
            } catch (IOException ex) {
                Toast.makeText(AddBook.this, "Could not save file.", Toast.LENGTH_SHORT).show();
            }

            if (photoFile != null) {
                Uri photoUri = FileProvider.getUriForFile(this, "com.valeriajitianu.mybookshelf.fileprovider", photoFile);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                startActivityForResult(intent, CAPTURE_IMAGE);
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            bookImage.setImageBitmap(imageBitmap);
        }
    }

    private File createFileForImage() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);

        imagePath = "file:" + image.getAbsolutePath();
        return image;
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
                } else
                    Toast.makeText(AddBook.this, "There was a problem. Try again please.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    private ContentValues setInsertValues(String title, String author) {
        ContentValues values = new ContentValues();
        values.put("title", title);
        values.put("author", author);
        values.put("image_path", imagePath);
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
