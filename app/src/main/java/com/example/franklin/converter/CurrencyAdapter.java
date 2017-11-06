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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Franklin on 11/2/2017.
 */

public class CurrencyAdapter extends ArrayAdapter<CurrencyObj> {
    public CurrencyAdapter(@NonNull Context context, ArrayList<CurrencyObj> currencyObj) {
        super(context,0,currencyObj);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listLayout = convertView;//using recycled view

        //inflate a new view if we don't have an existing view to be recycled.
        if(listLayout == null){
            listLayout = LayoutInflater.from(getContext()).inflate(R.layout.country_list,parent,false);
        }
        CurrencyObj currencyObj = getItem(position);

        //set country name
        TextView country = (TextView) listLayout.findViewById(R.id.country);
        country.setText(currencyObj.getmCurrency());
        //set country flag
        ImageView flag  = (ImageView) listLayout.findViewById(R.id.flag);
        Resources resources = listLayout.getResources();

        int imageID = resources.getIdentifier(currencyObj.getmFlag(),"drawable",getContext().getPackageName());
        Drawable Img_drawable = resources.getDrawable(imageID);
        flag.setImageDrawable(Img_drawable);

        return listLayout;
    }
}
