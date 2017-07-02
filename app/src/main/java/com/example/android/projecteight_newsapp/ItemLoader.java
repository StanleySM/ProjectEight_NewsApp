package com.example.android.projecteight_newsapp;


import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by StanleyPC on 29. 6. 2017.
 */

public class ItemLoader extends AsyncTaskLoader<List<NewsFirst>> {

    public ItemLoader(Context information){
        super(information);
    }

    @Override
    public List<NewsFirst> loadInBackground(){
        List<NewsFirst> groupOfNews = null;
        try {
            URL mUrl = Query.createUrl();
            String JSONanswer = Query.makeHttpRequest(mUrl);
            groupOfNews = Query.parseJson(JSONanswer);

        } catch (IOException e){
            Log.e("QueryUtils", "Error with Loader LoadInBackground: ", e);
        }
        return groupOfNews;
    }

    @Override
    protected void onStartLoading(){
        super.onStartLoading();
        forceLoad();
    }
}
