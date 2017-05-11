package com.example.vprasanna.mycustomerapp.async;

import android.os.AsyncTask;

import com.example.vprasanna.mycustomerapp.model.Customer;
import com.example.vprasanna.mycustomerapp.utilities.NetworkUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static com.example.vprasanna.mycustomerapp.utilities.NetworkUtils.HTTP_METHOD_PUT;
import static com.example.vprasanna.mycustomerapp.utilities.NetworkUtils.buildUrl;
import static com.example.vprasanna.mycustomerapp.utilities.NetworkUtils.executeWithPayload;


/**
 * Created by vprasanna on 5/8/17.
 */

public class AsyncSaveData extends AsyncTask<Customer, Void, Customer> {
    @Override
    protected Customer doInBackground(Customer[] params) {
        Customer customer = params[0];
        URL url = buildUrl(customer.getId());
        String jsonData = executeWithPayload(HTTP_METHOD_PUT, url, customer);
        try {
            return new ObjectMapper().readValue(jsonData, Customer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
