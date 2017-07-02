package com.example.android.projecteight_newsapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by StanleyPC on 29. 6. 2017.
 */

public class NewsAdapeter extends ArrayAdapter<NewsFirst> {

    public NewsAdapeter(Context information) {
        super(information, -1, new ArrayList<NewsFirst>());
    }

    @NonNull
    @Override
    public View getView(int position, View changeView, @NonNull ViewGroup parent) {
        if (changeView == null) {
            changeView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, parent, false);
        }

        NewsFirst currentNews = getItem(position);

        TextView title = (TextView) changeView.findViewById(R.id.news_title);
        title.setText(currentNews.getTitle());

        TextView author = (TextView) changeView.findViewById(R.id.news_author);
        author.setText(currentNews.getAuthor());

        TextView section = (TextView) changeView.findViewById(R.id.news_section);
        section.setText(currentNews.getSection());

        TextView date = (TextView) changeView.findViewById(R.id.news_date);
        date.setText(currentNews.getDate());



        return changeView;
    }
}