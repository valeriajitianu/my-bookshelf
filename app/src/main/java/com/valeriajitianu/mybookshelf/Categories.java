package com.valeriajitianu.mybookshelf;

/**
 * Created by Valeria.Jitianu on 07.09.2016.
 */
public enum Categories {
    WISHLIST("Wishlist"), READ("Read"), CURRENTLY_READING("Currently reading");

    String name;

    Categories(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static String[] getCategoryValues() {
        String[] categories = {WISHLIST.getName(), READ.getName(), CURRENTLY_READING.getName()};
        return categories;
    }
}
