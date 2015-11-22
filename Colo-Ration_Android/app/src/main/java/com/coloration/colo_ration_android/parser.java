package com.coloration.colo_ration_android;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ophelie on 20/11/2015.
 */
public class parser {

   public static void giveJson(){
       // These two need to be declared outside the try/catch
       // so that they can be closed in the finally block.
       HttpURLConnection urlConnection = null;
       BufferedReader reader = null;
       // Will contain the raw JSON response as a string.
       String infoJsonStr = null;
       try {
           // using Uri.Builder for maintainable requests
           Uri.Builder uri_info = new Uri.Builder();
           uri_info.scheme("http");
           uri_info.authority("coloration-1114.appspot.com");
           uri_info.appendEncodedPath("DB");
           uri_info.appendEncodedPath("DBjson.json");
           //Log.d("DEBUG:",uri_info.build().toString());
           URL url = new URL(uri_info.build().toString());
           urlConnection = (HttpURLConnection) url.openConnection();
           urlConnection.setRequestMethod("GET");
           urlConnection.connect();
           // Read the input stream into a String
           InputStream inputStream = urlConnection.getInputStream();
           StringBuffer buffer = new StringBuffer();
           if (inputStream == null) {
               // Nothing to do.
               infoJsonStr = null;
           }
           reader = new BufferedReader(new InputStreamReader(inputStream));
           String line;
           while ((line = reader.readLine()) != null) {
               // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
               // But it does make debugging a *lot* easier if you print out the completed
               // buffer for debugging.
               buffer.append(line + "\n");
               Log.v("PARSER", line);
           }
           if (buffer.length() == 0) {
               // Stream was empty. No point in parsing.
               infoJsonStr = null;
           }
           infoJsonStr = buffer.toString();
       } catch (IOException e) {
           Log.e("PlaceholderFragment", "Error ", e);
           // If the code didn't successfully get the weather data, there's no point in attempting
           // to parse it.
           infoJsonStr = null;
       } finally{
           if (urlConnection != null) {
               urlConnection.disconnect();
           }
           if (reader != null) {
               try {
                   reader.close();
               } catch (final IOException e) {
                   Log.e("PlaceholderFragment", "Error closing stream", e);
               }
           }
       }
       /*if ( infoJsonStr == null )
           return null;
       return infoJsonStr;*/
   }
}
