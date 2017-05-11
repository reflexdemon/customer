package com.example.vprasanna.mycustomerapp.async;

import android.os.AsyncTask;

import com.example.vprasanna.mycustomerapp.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;

import static com.example.vprasanna.mycustomerapp.utilities.NetworkUtils.HTTP_METHOD_DELETE;
import static com.example.vprasanna.mycustomerapp.utilities.NetworkUtils.HTTP_METHOD_POST;
import static com.example.vprasanna.mycustomerapp.utilities.NetworkUtils.HTTP_METHOD_PUT;
import static com.example.vprasanna.mycustomerapp.utilities.NetworkUtils.buildUrl;
import static com.example.vprasanna.mycustomerapp.utilities.NetworkUtils.executeWithPayload;


/**
 * Created by vprasanna on 5/8/17.
 */

public class AsyncDeleteData extends AsyncTask<String, Void, Customer> {
    @Override
    protected Customer doInBackground(String[] params) {
        String id = params[0];
        String method = null;
        URL url = null;
        url = buildUrl(id);
        method = HTTP_METHOD_DELETE;
        String jsonData = executeWithPayload(method, url, null);
        try {
            return new ObjectMapper().readValue(jsonData, Customer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
