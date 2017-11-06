package com.example.franklin.converter;

/**
 * Created by Franklin on 11/2/2017.
 */

public class CurrencyObj {
    /**
     * Country name
     */
    private String mCountry;
    /**
     * Currency code
     */
    private String mCurrencyCode;
    /**
     * CountryFlag
     */
    private String mFlag;

    public CurrencyObj(String country, String currencyCode, String flag){
        mCountry = country;
        mCurrencyCode = currencyCode;
        mFlag = flag;
    }

    public String getmCurrency(){return mCountry;}

    public String getmCurrencyCode(){return mCurrencyCode;}

    public String getmFlag(){return mFlag;}

}
