package com.valeriajitianu.mybookshelf;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Valeria.Jitianu on 01.09.2016.
 */
public class CustomList extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] texts;
    private final Integer image;
    private static LayoutInflater inflater = null;

    public CustomList(Activity context, String[] texts, Integer image) {
        super(context, R.layout.list_single, texts);
        this.context = context;
        this.image = image;
        this.texts = texts;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public class Holder
    {
        TextView tv;
        ImageView img;
    }

    public View getView(int position, View view, ViewGroup parent) {
        Holder holder=new Holder();
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null);

        holder.tv = (TextView) rowView.findViewById(R.id.txt);
        holder.img = (ImageView) rowView.findViewById(R.id.img);
        holder.tv.setText(texts[position]);
        holder.img.setImageResource(image);

        return rowView;
    }
}
