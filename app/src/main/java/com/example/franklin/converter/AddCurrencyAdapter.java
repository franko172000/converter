package com.example.franklin.converter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Franklin on 11/2/2017.
 */

public class AddCurrencyAdapter extends ArrayAdapter<CurrencyObj> {
    public AddCurrencyAdapter(@NonNull Context context, ArrayList<CurrencyObj> currencyObj) {
        super(context,0,currencyObj);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listLayout = convertView;//using recycled view

        //inflate a new view if we don't have an existing view to be recycled.
        if(listLayout == null){
            listLayout = LayoutInflater.from(getContext()).inflate(R.layout.add_currency_layout,parent,false);
        }
        CurrencyObj currencyObj = getItem(position);
        //set country name
        TextView currency = (TextView) listLayout.findViewById(R.id.add_currency);
        currency.setText(currencyObj.getmCurrency());
        //set country flag
        ImageView flag  = (ImageView) listLayout.findViewById(R.id.add_flag);
        Resources resources = listLayout.getResources();

        int imageID = resources.getIdentifier(currencyObj.getmFlag(),"drawable",getContext().getPackageName());
        Drawable Img_drawable = resources.getDrawable(imageID);
        flag.setImageDrawable(Img_drawable);

//        CheckBox checkBox = (CheckBox) listLayout.findViewById(R.id.add_checkBox);
//        String splitVal = currencyObj.getmFlag().replace("_"," ");
//        String checkVal = currencyObj.getmCurrency()+"=>"+currencyObj.getmCurrencyCode();
//        //checkBox.setText(checkVal);
//        if(ConverterUtil.isCurrencyOnFile(getContext(),checkVal)){
//            checkBox.setChecked(true);
//        }

        return listLayout;
    }
}
