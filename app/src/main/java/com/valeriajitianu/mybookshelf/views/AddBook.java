package com.valeriajitianu.mybookshelf.views;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
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
import java.util.Date;

public class AddBook extends AppCompatActivity {
    final String CHOOSE_FROM_LIBRARY = "Choose from Library";
    final String ANDROID_SYSTEM_UPGRADE_MESSAGE = "Your current Android system doesn't support adding images. Please upgrade.";
    EditText bookTitle, bookAuthor;
    Spinner bookCategory;
    ImageView bookImage;
    long category = 1;
    final int OPEN_IMAGE = 1;
    private String imagePath = "";
    final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

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
                if (isKitKat)
                    selectImageForBook();
                else
                    Toast.makeText(AddBook.this, ANDROID_SYSTEM_UPGRADE_MESSAGE, Toast.LENGTH_SHORT).show();
            }
        });

        goBackWhenCancelButtonClicked();

        saveBookWhenAddButtonClicked();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OPEN_IMAGE)
            if (resultCode == Activity.RESULT_OK) {
                imagePath = getImagePath(data);
                File imageFile = new File(imagePath);
                if (imageFile.exists()) {
                    Bitmap bitmapImage = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                    bookImage.setImageBitmap(bitmapImage);
                }
            }
    }

    private void selectImageForBook() {
        if (Utility.checkPermission(getApplicationContext())) {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(Intent.createChooser(intent, CHOOSE_FROM_LIBRARY), OPEN_IMAGE);
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private String getImagePath(Intent data) {
        Uri imageUri = data.getData();
        if (DocumentsContract.isDocumentUri(getApplicationContext(), imageUri)) {
            if (isMediaDocument(imageUri)) {
                final String docId = DocumentsContract.getDocumentId(imageUri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type))
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

            }
                String[] projection = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(imageUri, projection, null, null, null);
                if (cursor != null) {
                    int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    return cursor.getString(column_index);
                }
            getContentResolver().query(imageUri, projection, "_id=?", new String[]{DocumentsContract.getDocumentId(imageUri).split(":")[1]}, null);
            return imageUri.getPath();
        }
        return null;
        }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
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
