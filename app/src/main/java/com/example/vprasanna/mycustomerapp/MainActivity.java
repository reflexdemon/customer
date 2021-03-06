package com.example.vprasanna.mycustomerapp;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vprasanna.mycustomerapp.adapter.ListAdapterView;
import com.example.vprasanna.mycustomerapp.async.AsyncFetchData;
import com.example.vprasanna.mycustomerapp.model.Customer;
import com.example.vprasanna.mycustomerapp.utilities.NetworkUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class MainActivity extends AppCompatActivity {

    private ListView customerListView = null;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customerListView = (ListView) findViewById(R.id.list);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToDetailView(null);
            }
        });
        getCustomerData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            getCustomerData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getCustomerData() {
        ActionMenuItemView item = (ActionMenuItemView) findViewById(R.id.action_search);
        try {
            if (null != item) {
                item.setEnabled(false);
            }
            URL apiEndpoint = NetworkUtils.buildUrl();
            Toast.makeText(getApplicationContext(), "Refresing the list", Toast.LENGTH_SHORT).show();
            final List<Customer> customers = new AsyncFetchData().execute(apiEndpoint).get();
            List<String> names = new ArrayList<>();

//            http://stackoverflow.com/questions/18079977/android-display-the-value-in-listview-dynamically-using-adaptor
            final ArrayAdapter<Customer> ListObject = new ListAdapterView(this,
                    android.R.layout.simple_expandable_list_item_1, customers);

            if (null != item) {
                item.setEnabled(true);
            }
            customerListView.setAdapter(ListObject);
            customerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Customer slecteditem= customers.get(position);
                    Toast.makeText(getApplicationContext(), slecteditem.getName(), Toast.LENGTH_SHORT).show();
                    switchToDetailView(slecteditem);
                }
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    void switchToDetailView(Customer slecteditem) {
        Intent intent = new Intent(getApplicationContext(), DetailActivity.class);
        if (null != slecteditem) {
            intent.putExtra("id", slecteditem.getId());
            intent.putExtra("name", slecteditem.getName());
            intent.putExtra("age", slecteditem.getAge());
        }
//                    startActivity(intent);
        startActivityForResult(intent, 100); // 100 is some code to identify the returning result
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100 || resultCode == 300) {
            getCustomerData();
        }

    }

}
