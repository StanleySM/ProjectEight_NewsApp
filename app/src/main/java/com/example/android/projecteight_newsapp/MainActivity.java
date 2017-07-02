package com.example.android.projecteight_newsapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;


public class MainActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<NewsFirst>>, SwipeRefreshLayout.OnRefreshListener{
    private SwipeRefreshLayout refresherMove;
    private NewsAdapeter listAdapter;
    private static final int LOADER_ID = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refresherMove = (SwipeRefreshLayout) findViewById(R.id.refresh_move);
        refresherMove.setOnRefreshListener(this);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listAdapter = new NewsAdapeter(this);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l){
                NewsFirst news = listAdapter.getItem(i);
                assert news != null;
                String readURL = news.url;
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(readURL));
                startActivity(intent);
            }
        });

        getSupportLoaderManager().initLoader(LOADER_ID, null, this);
    }


    @Override
    public void onLoadFinished(Loader<List<NewsFirst>> loader, List<NewsFirst> data){
        refresherMove.setRefreshing(false);
        if(data != null){
            listAdapter.setNotifyOnChange(false);
            listAdapter.clear();
            listAdapter.setNotifyOnChange(true);
            listAdapter.addAll(data);
        }
    }

    @Override
    public Loader<List<NewsFirst>> onCreateLoader(int id, Bundle args){
        return new ItemLoader(this);
    }

    @Override
    public void onRefresh(){
        getSupportLoaderManager().restartLoader(LOADER_ID, null, this);
    }

    @Override
    public void onLoaderReset(Loader<List<NewsFirst>> loader){
    }


}