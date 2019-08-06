package com.jaywhitsitt.eggsandbakey.utils;

import android.net.Network;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    final static private String TAG = NetworkUtils.class.getSimpleName();
    final static private String jsonUrlString = "https://go.udacity.com/android-baking-app-json";

    public static String recipesResponse() {
        try {
            return getResponseFromUrl(new URL(jsonUrlString));
        } catch (IOException e) {
            Log.w(TAG, "Unable to get response");
            return null;
        }
    }

    private static String getResponseFromUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
