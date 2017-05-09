package com.example.vprasanna.mycustomerapp.async;

import android.os.AsyncTask;

import com.example.vprasanna.mycustomerapp.model.Customer;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.example.vprasanna.mycustomerapp.utilities.NetworkUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * Created by vprasanna on 5/8/17.
 */

public class AsyncFetchData extends AsyncTask<URL, Void, List<Customer>> {
    @Override
    protected List<Customer> doInBackground(URL[] params) {
        URL url = params[0];
        String jsonData = null;
        try {
//            http://stackoverflow.com/questions/6349421/how-to-use-jackson-to-deserialise-an-array-of-objects
            jsonData = NetworkUtils.getResponseFromHttpUrl(url);
            return new ObjectMapper().readValue(jsonData, new TypeReference<List<Customer>>(){});

        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
