package com.example.vprasanna.mycustomerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vprasanna.mycustomerapp.async.AsyncDeleteData;
import com.example.vprasanna.mycustomerapp.async.AsyncSaveData;
import com.example.vprasanna.mycustomerapp.model.Customer;

import static com.example.vprasanna.mycustomerapp.utilities.NetworkUtils.*;

import java.net.URL;
import java.util.concurrent.ExecutionException;

/**
 * Created by vprasanna on 5/10/17.
 */

public class DetailActivity extends AppCompatActivity {

    EditText id, name, age;
    Button buttonBack, buttonSave, buttonDelete;
    Customer customer;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.detail_view);
        id = (EditText) findViewById(R.id.detail_id);
        age = (EditText) findViewById(R.id.detail_age);
        name = (EditText) findViewById(R.id.detail_name);

        //This makes the  textbox readonly
        id.setKeyListener(null);


        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        buttonDelete = (Button) findViewById(R.id.buttonDelete);

        customer = getCustomerFromIntent();


        buttonBack.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent i = getIntent();
                        // Setting resultCode to 100 to identify on old activity
                        setResult(200, i);
                        //close and get back
                        finish();
                    }
                }
        );

        buttonDelete.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        //Delete the customer
                        deleteCustomerData(getCustomerFromPage());
                        Intent i = getIntent();
                        // Setting resultCode to 100 to identify on old activity
                        setResult(300, i);
                        //close and get back
                        finish();
                    }
                }
        );
        buttonSave.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        //save the record
                        saveCustomerData(getCustomerFromPage());
                        Intent i = getIntent();
                        // Setting resultCode to 100 to identify on old activity
                        setResult(100, i);
                        //close and get back
                        finish();
                    }
                }
        );
        if (null == customer.getId()) {
            buttonDelete.setVisibility(View.INVISIBLE);
        }
        assignValuesToText(customer);
        super.onCreate(savedInstanceState);
    }

    Customer getCustomerFromIntent() {
        Customer customer = new Customer();
        Intent i = getIntent();
        customer.setId(i.getStringExtra("id"));
        customer.setName(i.getStringExtra("name"));
        customer.setAge(i.getStringExtra("age"));
        return customer;

    }

    Customer getCustomerFromPage() {
        Customer customer = new Customer();
        customer.setId(id.getText().toString());
        customer.setName(name.getText().toString());
        customer.setAge(age.getText().toString());
        return customer;

    }

    void assignValuesToText(Customer customer) {


        if (id != null) {
            id.setText(customer.getId());
        }

        if (name != null) {
            name.setText(customer.getName());
        }

        if (age != null) {
            age.setText(customer.getAge());
        }


    }

    Customer saveCustomerData(Customer customer) {

        try {
            Customer c = new AsyncSaveData().execute(customer).get();
            Toast.makeText(getApplicationContext(), "Saving...", Toast.LENGTH_SHORT).show();
            return c;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    String deleteCustomerData(Customer customer) {

        try {
            String cId = new AsyncDeleteData().execute(customer.getId()).get();
            Toast.makeText(getApplicationContext(), "Deleting..." + customer.getName(), Toast.LENGTH_SHORT).show();
            return cId;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    protected void onStart() {
        super.onStart();
    }
}
