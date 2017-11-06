package com.example.franklin.converter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class CurrencyList extends AppCompatActivity {
    ArrayList<CurrencyObj> currencyobj = new ArrayList<CurrencyObj>();
    CurrencyAdapter currencyAdapter;
    private Boolean currFrom = false;
    private Boolean currTo = false;
    private String currFromName = "";
    private String currToName = "";
    private String currFromCode = "";
    private String currToCode = "";
    private String currToFlag = "";
    private String currFromFlag = "";
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency_list);

        currFrom = getIntent().getExtras().getBoolean("currFrom");
        currTo = getIntent().getExtras().getBoolean("currTo");
        currFromName = getIntent().getExtras().getString("currFromName");
        currToName = getIntent().getExtras().getString("currToName");
        currFromCode = getIntent().getExtras().getString("currFromCode");
        currToCode = getIntent().getExtras().getString("currToCode");
        currToFlag = getIntent().getExtras().getString("currToFlag");
        currFromFlag = getIntent().getExtras().getString("currFromFlag");


        currencyobj = ConverterUtil.getCountries(this);

        currencyAdapter = new CurrencyAdapter(this,currencyobj);
        //set the list view
        ListView listView = (ListView) findViewById(R.id.listItem);
        listView.setAdapter(currencyAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                CurrencyObj currencyOBJ = currencyAdapter.getItem(position);
                String code = currencyOBJ.getmCurrencyCode();
                String currency = currencyOBJ.getmCurrency();
                String flag = currencyOBJ.getmFlag();
                String receiverType = "";

                Intent intent = new Intent(CurrencyList.this,GeneralConverter.class);


                if(currFrom){
                    receiverType = currFrom ? "from" : "";
                    intent.putExtra("currencyToCode",currToCode);
                    intent.putExtra("currencyToName",currToName);
                    intent.putExtra("currencyToFlag",currToFlag);
                    intent.putExtra("currencyFromCode",code);
                    intent.putExtra("currencyFromName",currency);
                    intent.putExtra("currencyFromFlag",flag);

                }
                if(currTo){
                    receiverType = currTo ? "to" : "";
                    intent.putExtra("currencyToCode",code);
                    intent.putExtra("currencyToFlag",flag);
                    intent.putExtra("currencyToName",currency);
                    intent.putExtra("currencyFromCode",currFromCode);
                    intent.putExtra("currencyFromName",currFromName);
                    intent.putExtra("currencyFromFlag",currFromFlag);
                }
                intent.putExtra("receiverType",receiverType);
                startActivity(intent);
            }
        });
    }
}
