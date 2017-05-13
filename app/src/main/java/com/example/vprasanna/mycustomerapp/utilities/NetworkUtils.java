/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.vprasanna.mycustomerapp.utilities;

import android.net.Uri;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * These utilities will be used to communicate with the network.
 */
public class NetworkUtils {

    //    final static String BASE_URL =
//            "http://demo-venkatvp.rhcloud.com/services/customer";
    final static String BASE_URL =
            "https://reflexdemon-customer-v1.p.mashape.com/customer";

    public static String MASHAPE_KEY = "pIWoS48nSGmsh8YuvhznZpRZTbCpp1rTRYYjsnmuBi0FoN2cXA";

    public static String HTTP_METHOD_POST = "POST";
    public static String HTTP_METHOD_PUT = "PUT";
    public static String HTTP_METHOD_DELETE = "DELETE";


    public static URL buildUrl() {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon().build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }


    public static URL buildUrl(String id) {
        Uri builtUri = Uri.parse(BASE_URL + "/" + id).buildUpon().build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        Log.println(Log.INFO, "GET", "Endpoint: " + url);
        long startTime = System.currentTimeMillis();
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String response = null;
        try {
            urlConnection.setRequestProperty("X-Mashape-Key", MASHAPE_KEY);
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
            Log.println(Log.INFO, "TIME", "It took " + (System.currentTimeMillis() - startTime) + "ms to complete operation.");
        }
    }

    public static String executeWithPayload(String menthod, URL url, Object payload) {
        Log.println(Log.INFO, menthod, "Endpoint: " + url);
        long startTime = System.currentTimeMillis();
        HttpURLConnection httpcon;
        String result = null;
        try {
            String data = ((null == payload) ? "" : new ObjectMapper().writeValueAsString(payload));
            //Connect
            httpcon = (HttpURLConnection) (url.openConnection());
            httpcon.setDoOutput(true);
            httpcon.setRequestProperty("Content-Type", "application/json");
            httpcon.setRequestProperty("Accept", "application/json");
            httpcon.setRequestProperty("X-Mashape-Key", MASHAPE_KEY);
            httpcon.setRequestMethod(menthod);//"POST" or "PUT" as the case may be
            httpcon.connect();

            //Write
            OutputStream os = httpcon.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(data);
            writer.close();
            os.close();

            //Read
            BufferedReader br = new BufferedReader(new InputStreamReader(httpcon.getInputStream(), "UTF-8"));

            String line = null;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            br.close();
            result = sb.toString();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            Log.println(Log.INFO, "TIME", "It took " + (System.currentTimeMillis() - startTime) + "ms to complete operation.");
        }

        return result;
    }
}