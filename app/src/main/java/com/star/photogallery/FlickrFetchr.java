package com.star.photogallery;


import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FlickrFetchr {

    private static final String TAG = "FlickrFetchr";
    private static final String API_KEY = "03e55c312c15c20d0b02b48dbf58e646";

    private static final String METHOD_KEY = "method";
    private static final String METHOD_VALUE = "flickr.photos.getRecent";
    private static final String API_KEY_KEY = "api_key";
    private static final String API_KEY_VALUE = API_KEY;
    private static final String FORMAT_KEY = "format";
    private static final String FORMAT_VALUE = "json";
    private static final String NO_JSON_CALL_BACK_KEY = "nojsoncallback";
    private static final String NO_JSON_CALL_BACK_VALUE = "1";
    private static final String EXTRAS_KEY = "extras";
    private static final String EXTRAS_VALUE = "url_s";

    public byte[] getUrlBytes(String urlSpec) throws IOException {
        URL url = new URL(urlSpec);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = httpURLConnection.getInputStream();

            if (httpURLConnection.getResponseCode() != httpURLConnection.HTTP_OK) {
                throw new IOException(httpURLConnection.getResponseMessage() +
                        ": with " + urlSpec);
            }

            int bytesRead = 0;
            byte[] buffer = new byte[1024];

            while ((bytesRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, bytesRead);
            }

            out.close();
            return out.toByteArray();
        } finally {
            httpURLConnection.disconnect();
        }
    }

    public String getUrlString(String urlSpec) throws IOException {
        return new String(getUrlBytes(urlSpec));
    }

    public void fetchItems() {
        try {
            String url = Uri.parse("https://api.flickr.com/services/rest/")
                    .buildUpon()
                    .appendQueryParameter(METHOD_KEY, METHOD_VALUE)
                    .appendQueryParameter(API_KEY_KEY, API_KEY_VALUE)
                    .appendQueryParameter(FORMAT_KEY, FORMAT_VALUE)
                    .appendQueryParameter(NO_JSON_CALL_BACK_KEY, NO_JSON_CALL_BACK_VALUE)
                    .appendQueryParameter(EXTRAS_KEY, EXTRAS_VALUE)
                    .build().toString();

            String jsonString = getUrlString(url);
            Log.i(TAG, "Received JSON: " + jsonString);
        } catch (IOException e) {
            Log.e(TAG, "Failed to fetch items", e);
        }
    }
}
