package com.example.android.projecteight_newsapp;

/**
 * Created by StanleyPC on 29. 6. 2017.
 */

public class NewsFirst {

    private final String title;
    private final String author;
    public final String url;
    private final String date;
    private final String section;

    public NewsFirst(String title, String author, String url, String date, String section){
        this.title = title;
        this.author = author;
        this.url = url;
        this.date = date;
        this.section = section;
    }

    public String getTitle(){
        return title;
    }

    public String getAuthor(){
        return author;
    }

    public String getDate(){
        return date;
    }

    public String getSection(){
        return section;
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", url='" + url + '\'' +
                ", date='" + date + '\'' +
                ", section='" + section + '\'' +
                '}';
    }

}