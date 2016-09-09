package com.valeriajitianu.mybookshelf;

import java.io.Serializable;

/**
 * Created by Valeria.Jitianu on 09.09.2016.
 */
public class Book implements Serializable{
    private String title, author, image_path;
    private int category;

    public Book(String title, String author, String image_path, int category) {
        this.title = title;
        this.author = author;
        this.image_path = image_path;
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getImage_path() {
        return image_path;
    }

    public int getCategory() {
        return category;
    }
}
