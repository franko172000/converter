package com.example.franklin.converter;

import android.content.Intent;
import android.content.res.Resources;
import android.app.LoaderManager;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class GeneralConverter extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Float> {
    private float exchangeRate=0;
    private String currency_from = "BTC";
    private String currency_to = "NGN";
    private String currency_from_name = "Bitcoin";
    private String currency_to_name = "Nigerian Naira";
    private String currency_to_flag = "nigerian_naira";
    private String currency_from_flag = "bitcoin";
    private String currencyName = "";
    private String receiverType = "";
    private LoaderManager  loaderManager;
    ProgressBar progressBar;
    private Boolean startActivity = true;
    TextView defaultTextView;
    TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_converter);

        resultTextView = (TextView) findViewById(R.id.result_converted);
        defaultTextView = (TextView) findViewById(R.id.defaultText);

        //create loader manager
        loaderManager = getLoaderManager();
        loaderManager.initLoader(1,null,this);
        //track info from previous activity
        if(getIntent().getExtras() != null){
            receiverType = getIntent().getExtras().getString("receiverType");
            currency_to = getIntent().getExtras().getString("currencyToCode");
            currency_from = getIntent().getExtras().getString("currencyFromCode");
            currency_from_name = getIntent().getExtras().getString("currencyFromName");
            currency_to_name = getIntent().getExtras().getString("currencyToName");
            currency_to_flag = getIntent().getExtras().getString("currencyToFlag");
            currency_from_flag = getIntent().getExtras().getString("currencyFromFlag");
            loaderManager.restartLoader(1,null,this);
        }else{
            loaderManager.initLoader(1,null,this);
        }

        LinearLayout to_layout = (LinearLayout) findViewById(R.id.to);
        LinearLayout from_layout = (LinearLayout) findViewById(R.id.from);

        TextView fromName = (TextView) findViewById(R.id.from_name);
        TextView toName = (TextView) findViewById(R.id.to_name);
        ImageView fromFlag = (ImageView) findViewById(R.id.from_flag);
        ImageView toFlag = (ImageView) findViewById(R.id.to_flag);

        Resources resources = this.getResources();
        //convert string to image name
        String fromFlagName = currency_from_flag;
        String toFlagName = currency_to_flag;
        //set image
        fromFlag.setImageDrawable(resources.getDrawable(resources.getIdentifier(fromFlagName,"drawable",this.getPackageName())));
        toFlag.setImageDrawable(resources.getDrawable(resources.getIdentifier(toFlagName,"drawable",this.getPackageName())));
        //set text
        fromName.setText(currency_from_name);
        toName.setText(currency_to_name);

        to_layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GeneralConverter.this,CurrencyList.class);

                intent.putExtra("currTo",true);
                intent.putExtra("currToCode",currency_to);
                intent.putExtra("currFromCode",currency_from);
                intent.putExtra("currFromName",currency_from_name);
                intent.putExtra("currToName",currency_to_name);
                intent.putExtra("currToFlag",currency_to_flag);
                intent.putExtra("currFromFlag",currency_from_flag);

                startActivity(intent);
            }
        });

        from_layout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GeneralConverter.this,CurrencyList.class);

                intent.putExtra("currFrom",true);
                intent.putExtra("currToCode",currency_to);
                intent.putExtra("currFromCode",currency_from);
                intent.putExtra("currFromName",currency_from_name);
                intent.putExtra("currToName",currency_to_name);
                intent.putExtra("currToFlag",currency_to_flag);
                intent.putExtra("currFromFlag",currency_from_flag);

                startActivity(intent);
            }
        });

        //track button
        EditText txt = (EditText) findViewById(R.id.text_input);
        txt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                resultTextView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    float result = Float.parseFloat(s.toString()) * exchangeRate;
                    NumberFormat formatter = NumberFormat.getInstance();
                    String formatted = formatter.format((double) result);
                    resultTextView.setText(currency_to+" "+formatted);
                }
            }

            @Override
            public void afterTextChanged(Editable s){
                if (s.length() == 0){
                    resultTextView.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<Float> loader) {

    }

    @Override
    public void onLoadFinished(Loader<Float> loader, Float data){
            progressBar = (ProgressBar) findViewById(R.id.progress);
            progressBar.setVisibility(View.GONE);
        exchangeRate = data;
        defaultTextView.setVisibility(View.VISIBLE);
        defaultTextView.setText("1 "+currency_from+" = "+data+" "+currency_to);
        resultTextView.setText(data+" "+currency_to);
    }

    @Override
    public Loader<Float> onCreateLoader(int id, Bundle args){
        if(!AppStatus.getInstance(GeneralConverter.this).isOnline()){
            Toast.makeText(GeneralConverter.this,"You are not connected",Toast.LENGTH_SHORT).show();
        }else{
            progressBar = (ProgressBar) findViewById(R.id.progress);
            progressBar.setVisibility(View.VISIBLE);
        }
        return new CurrencyLoader(this,currency_from,currency_to);
    }
}
