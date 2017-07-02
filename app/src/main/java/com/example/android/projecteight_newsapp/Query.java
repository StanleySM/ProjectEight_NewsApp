package com.example.android.projecteight_newsapp;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by StanleyPC on 29. 6. 2017.
 */

public class Query {

    private static String createStringUrl() {
        Uri.Builder builder = new Uri.Builder();


        builder.scheme("http")
                .encodedAuthority("content.guardianapis.com")
                .appendPath("search")
                .appendQueryParameter("api-key", "test")
                .appendQueryParameter("show-references", "author")
                .appendQueryParameter("show-tags", "contributor")
                .appendQueryParameter("order-by", "newest")
                .appendQueryParameter("q", "news");
        return builder.build().toString();
    }

    static URL createUrl() {
        String stringUrl = createStringUrl();
        try {
            return new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("QueryUtils", "Error with URL: ", e);
            return null;
        }
    }


    static String makeHttpRequest(URL url) throws IOException {
        String JSONanswer = "";

        if (url == null) {
            return JSONanswer;
        }
        HttpURLConnection connectionCheck = null;
        InputStream readingData = null;

        try {

            connectionCheck = (HttpURLConnection) url.openConnection();
            connectionCheck.setRequestMethod("GET");
            connectionCheck.setReadTimeout(10000);
            connectionCheck.setConnectTimeout(15000);
            connectionCheck.connect();

            if (connectionCheck.getResponseCode() == 200) {
                readingData = connectionCheck.getInputStream();
                JSONanswer = readFromStream(readingData);
            } else {
                Log.e("mainActivity", "Error response code: " + connectionCheck.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("Query", "Error when making HTTP request: ", e);
        } finally {
            if (connectionCheck != null) {
                connectionCheck.disconnect();
            }
            if (readingData != null) {
                readingData.close();
            }
        }

        return JSONanswer;
    }

    private static String formatDate(String suroveDatum) {
        String JSONdataFormat = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        SimpleDateFormat JSONdateSeparation = new SimpleDateFormat(JSONdataFormat, Locale.UK);
        try {

            Date editedDate = JSONdateSeparation.parse(suroveDatum);
            String finalDatePattern = "MMMM dd, yyyy (HH:mm)";
            SimpleDateFormat dateFormatDone = new SimpleDateFormat(finalDatePattern, Locale.UK);
            return dateFormatDone.format(editedDate);

        } catch (ParseException e) {

            Log.e("QueryUtils", "Problem with parsing date: ", e);
            return "";

        }
    }

    static List<NewsFirst> parseJson(String response) {
        ArrayList<NewsFirst> listOfNews = new ArrayList<>();
        try {
            JSONObject JSONanswer = new JSONObject(response);
            JSONObject JSONsummary = JSONanswer.getJSONObject("response");
            JSONArray finalList = JSONsummary.getJSONArray("results");

            for (int i = 0; i < finalList.length(); i++) {
                JSONObject firstResult = finalList.getJSONObject(i);
                String webTitle = firstResult.getString("webTitle");
                String date = firstResult.getString("webPublicationDate");
                String url = firstResult.getString("webUrl");
                date = formatDate(date);
                String section = firstResult.getString("sectionName");
                JSONArray tagsArray = firstResult.getJSONArray("tags");
                String author = "";

                if (tagsArray.length() == 0) {
                    author = null;
                } else {
                    for (int j = 0; j < tagsArray.length(); j++) {
                        JSONObject firstObject = tagsArray.getJSONObject(j);
                        author += firstObject.getString("webTitle") + ". ";
                    }
                }
                listOfNews.add(new NewsFirst(webTitle, author, url, date, section));
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Error with JSON response", e);
        }
        return listOfNews;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }

}