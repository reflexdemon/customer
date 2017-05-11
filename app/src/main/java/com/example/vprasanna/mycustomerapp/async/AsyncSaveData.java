package com.example.vprasanna.mycustomerapp.async;

import android.os.AsyncTask;

import com.example.vprasanna.mycustomerapp.model.Customer;
import com.example.vprasanna.mycustomerapp.utilities.NetworkUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import static com.example.vprasanna.mycustomerapp.utilities.NetworkUtils.HTTP_METHOD_POST;
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
        String method = null;
        URL url = null;
        if (null == customer.getId() || customer.getId().trim().isEmpty()) {
            url = buildUrl();
            method = HTTP_METHOD_POST;
        } else {
            url = buildUrl(customer.getId());
            method = HTTP_METHOD_PUT;
        }
        String jsonData = executeWithPayload(method, url, customer);
        try {
            return new ObjectMapper().readValue(jsonData, Customer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
