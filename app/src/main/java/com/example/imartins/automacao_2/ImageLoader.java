package com.example.imartins.automacao_2;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class ImageLoader extends AsyncTask<Object, Void, ArrayList> {


    @Override
    protected ArrayList doInBackground(Object... params) {
        ArrayList<Object> retorno;
        try {
            URL url = new URL((String) params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            retorno = new ArrayList<Object>();
            retorno.add(myBitmap);
            retorno.add(params[1]);


            return retorno;
        } catch (IOException e) {
            Log.d("erro","deu erro");
            return null;
        }

    }

    @Override
    protected void onPostExecute(ArrayList arrayList) {
        ImageView im = (ImageView) arrayList.get(1);
        im.setImageBitmap((Bitmap)arrayList.get(0));
        super.onPostExecute(arrayList);
    }

    /* @Override
    protected Object doInBackground(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            Log.d("erro","deu erro");
            return null;
        }

    }*/



  /*  @Override
    protected Void doInBackground(String... src) {
        try {
            URL url = new URL(src[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            Log.d("erro","deu erro");
            return null;
        }
    }*/
}