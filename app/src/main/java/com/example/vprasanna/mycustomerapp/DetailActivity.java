package com.example.vprasanna.mycustomerapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by vprasanna on 5/10/17.
 */

public class DetailActivity extends AppCompatActivity {

    EditText id, name, age;
    Button buttonBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.detail_view);
        id = (EditText) findViewById(R.id.detail_id);
        age = (EditText) findViewById(R.id.detail_age);
        name = (EditText) findViewById(R.id.detail_name);

        id.setKeyListener(null);


        buttonBack = (Button) findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        //close and get back
                        finish();
                    }
                }
        );
        assignValuesToText();
        super.onCreate(savedInstanceState);
    }

    void assignValuesToText() {
        Intent i = getIntent();

        if (id != null && i.getStringExtra("id") != null) {
            id.setText(i.getStringExtra("id"));
        }

        if (name != null && i.getStringExtra("name") != null) {
            name.setText(i.getStringExtra("name"));
        }

        if (age != null && i.getStringExtra("age") != null) {
            age.setText(i.getStringExtra("age"));
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
    }
}
