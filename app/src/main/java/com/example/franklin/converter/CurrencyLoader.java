package com.example.franklin.converter;

import android.content.Context;
import android.content.AsyncTaskLoader;

import java.util.List;

/**
 * Created by Franklin on 11/5/2017.
 */
public class CurrencyLoader extends AsyncTaskLoader<Float> {
    //url to fetch GitHub Lagos developers
    private static String mUrl=null;
    private static String mTo = null;
    public CurrencyLoader(Context context, String from, String to){
        super(context);
        mUrl = ConverterUtil.buildApiUrl(from,to);
        mTo = to;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public Float loadInBackground() {
        if(mUrl == null){
            return null;
        }
        //List<CurrencyObj> developerObjs = DevelopersUtil.DevelopersList(mUrl);
        return ConverterUtil.Converter(mUrl,mTo);
    }
}
