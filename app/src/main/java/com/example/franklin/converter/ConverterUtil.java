package com.example.franklin.converter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by Franklin Anyaso on 11/2/2017.
 * @codecheeter
 * franko172000@gmail.com
 */

public class ConverterUtil {

    private final static String LOG_TAG = ConverterUtil.class.getSimpleName();
    private String toCurrency;
    //initialize constructor
    private ConverterUtil(){
    }

    public static String buildApiUrl(String from,String to){
        String URL = "https://min-api.cryptocompare.com/data/price?fsym="+from+"&tsyms="+to;
        return URL;
    }

    public static ArrayList<CurrencyObj> getCountries(Context context){
        ArrayList<CurrencyObj> countryList = new ArrayList<CurrencyObj>();
        String line;
        InputStreamReader stream = null;
        BufferedReader textReader;
        try{
            stream = new InputStreamReader(context.getAssets().open("countries.txt"));
            textReader = new BufferedReader(stream);
            while((line = textReader.readLine()) != null){
                //break the string returned
                String[] parts = line.split("=>");
                String country = parts[0];
                String code = parts[1];
                String flag = country.replace(" ","_");
                String countryAndCode = country+" ("+code+")";
                countryList.add(new CurrencyObj(countryAndCode,code,flag.toLowerCase()));
            }
        }catch(IOException e){
            Log.e(LOG_TAG,"Unable to open file "+e);
        }finally {
            if(stream != null){
                try {
                    stream.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG,"Error closing stream "+e);
                }
            }
        }
        return countryList;
    }

    public static boolean isCurrencyOnFile(Context context, String checkVal){
        String line;
        Boolean status = false;
        InputStreamReader stream = null;
        BufferedReader textReader;
        try{
            stream = new InputStreamReader(context.getAssets().open("home_cards.txt"));
            textReader = new BufferedReader(stream);
            while((line = textReader.readLine()) != null){
                if(line.equals(checkVal)) {status = true; break;}
                else{continue;}
            }
        }catch(IOException e){
            Log.e(LOG_TAG,"Unable to open file "+e);
        }finally {
            if(stream != null){
                try {
                    stream.close();
                } catch (IOException e) {
                    Log.e(LOG_TAG,"Error closing stream "+e);
                }
            }
        }
        return status;
    }

    public static void appendText(Context context, String args) {

        try {
            String MYFILE = "home_cards.txt";
            String strText = "\n"+args;

            // MODE_APPEND, MODE_WORLD_READABLE, MODE_WORLD_WRITEABLE
            // append to file
            FileOutputStream fos = context.openFileOutput(MYFILE, context.MODE_APPEND);
            fos.write(strText.getBytes());
            fos.close();
        } catch (IOException e) {
           // e.toString();
        }
    }


    /**
     * Create a new url object from the url string. will throw an error if there is an issue with the url
     * @param GitUrl url to be created
     * @return url Object
     */
    private static URL createUrl(String GitUrl){
        URL url = null;
        try{
            url = new URL(GitUrl);
        }catch(MalformedURLException e){
            Log.e(LOG_TAG,": Url error",e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException{
        String jsonResponse = "";
        //exit further execution if the url is not set
        if(url == null){
            return null;
        }

        HttpURLConnection httpConnection = null;
        InputStream inputStream = null;
        try{
            //open http connection
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setReadTimeout(10000);
            httpConnection.setConnectTimeout(15000);
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();
            //check http connection status
            if(httpConnection.getResponseCode() == 200){//connection was successful, proceed with further execution
                inputStream = httpConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
                Log.e(LOG_TAG,"Http connection error. Returned response code:"+httpConnection.getResponseCode());
            }
        }catch (IOException e){
            Log.e(LOG_TAG,"Http connection error",e);
        }finally {
            //close opened connections
            if(httpConnection != null){
                httpConnection.disconnect();
            }
            if(inputStream != null){
                inputStream.close();;
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    public static float Converter(String url, String to){
        //create a url object from the string
        URL httpUrl = createUrl(url);
        //create variable to hold the json response
        String jsonResponse = null;
        try{
            jsonResponse = makeHttpRequest(httpUrl);
        }catch(IOException e){
            Log.e(LOG_TAG,"Http request error",e);
        }
        return getJsonResult(jsonResponse,to);
    }

    private static float getJsonResult (String jsonResponse, String to){
        float data = 0;
        //exit further execution if the json response is not set
        if(TextUtils.isEmpty(jsonResponse)){
           return data;
        }
        try{
            JSONObject baseObject = new JSONObject(jsonResponse);

            //extract the relevant developers information
            data = (float) baseObject.getDouble(to);
            //return new developers object
            return data;

        }catch(JSONException e){
            Log.e(LOG_TAG,"JSON Error",e);
        }
        return data;
    }
}
