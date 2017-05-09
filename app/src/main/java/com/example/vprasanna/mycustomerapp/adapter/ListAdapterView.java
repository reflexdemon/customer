package com.example.vprasanna.mycustomerapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.vprasanna.mycustomerapp.R;
import com.example.vprasanna.mycustomerapp.model.Customer;

import java.util.List;

/**
 * Created by vprasanna on 5/8/17.
 */

public class ListAdapterView extends ArrayAdapter<Customer> {

    private List<Customer> customers;
    private Activity context;

    public ListAdapterView(@NonNull Activity context, @LayoutRes int resource, @NonNull List<Customer> customers) {
        super(context, resource, customers);
        this.customers = customers;
        this.context = context;


    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);
        TextView id = (TextView) rowView.findViewById(R.id.id);
        TextView name = (TextView) rowView.findViewById(R.id.name);
        TextView age = (TextView) rowView.findViewById(R.id.age);

        Customer customer = customers.get(position);
        id.setText(customer.getId());
        name.setText(customer.getName());
        age.setText(customer.getAge() + " years old");

        return rowView;
    }
}
