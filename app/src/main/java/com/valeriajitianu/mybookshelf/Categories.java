package com.valeriajitianu.mybookshelf;

/**
 * Created by Valeria.Jitianu on 07.09.2016.
 */
public enum Categories {
    WISHLIST("Wishlist"), READ("Read"), CURRENTLY_READING("Currently reading");

    String label;
    Categories(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static String[] getCategoryValues() {
        String[] categories = {WISHLIST.getLabel(), READ.getLabel(), CURRENTLY_READING.getLabel()};
        return categories;
    }
}
