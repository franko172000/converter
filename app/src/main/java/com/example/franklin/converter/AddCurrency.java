package com.example.franklin.converter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AddCurrency extends AppCompatActivity {
    ArrayList<CurrencyObj> currencyobj = new ArrayList<CurrencyObj>();
    AddCurrencyAdapter currencyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_currency);

        currencyobj = ConverterUtil.getCountries(this);

        currencyAdapter = new AddCurrencyAdapter(this,currencyobj);
        //set the list view
        ListView listView = (ListView) findViewById(R.id.listItemAdd);
        listView.setAdapter(currencyAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(AddCurrency.this,"Item Checked",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddCurrency.this,GeneralConverter.class);
                startActivity(intent);
            }
        });
    }
}
