package com.naldana.pokedesk.utilities;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class NetworkUtils {

    public static final String POKEMON_API_BASE_URL = "https://international-coins.herokuapp.com";
    public static final String POKEMON_INFO = "pokemon";

    private static final String TAG = NetworkUtils.class.getSimpleName();

    public static URL buildUrl(String pokeID) {
        Uri builtUri = Uri.parse(POKEMON_API_BASE_URL)
                .buildUpon()
                .appendPath(POKEMON_INFO)
                .appendPath(pokeID)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString()); //se construye una url a partir de un string
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.d("qwerty", "Built URI: " + url);

        return url;
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
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