package com.example.mohammed.firebaseapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
        * A placeholder fragment containing a simple view.
        */

public class MainActivityFragment extends Fragment  {

    List<CardviewModel> listdata;


    class DownloadData1 extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... urls) {

            String result = "";
            URL url;
            HttpURLConnection urlConnection = null;

            try {

                url = new URL(urls[0]);

                urlConnection = (HttpURLConnection)url.openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while (data != -1) {

                    char current = (char) data;

                    result += current;

                    data = reader.read();
                }

                return result;

            }
            catch (Exception e) {

                e.printStackTrace();

            }

            return null;

        }



    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View Vroot=  inflater.inflate(R.layout.fragment_main, container, false);

        listdata=new ArrayList<CardviewModel>();
        String str=null;
       DownloadData1 downloadData=new DownloadData1();
        try {
             str=downloadData.execute("http://wuzzuf.net/a/Customer-Service-Jobs-in-Egypt").get();
            Log.d("dataii",str);
            String[] splitStr=str.split("<div class=\"col-md-10 col-sm-8 col-xs-12 item-details\">");

            Pattern p1 = Pattern.compile("href=\"(.*?)\">");
            Matcher m1 = p1.matcher(splitStr[0]);

            Pattern p2 = Pattern.compile("src=\"(.*?)\"");
            Matcher m2 = p2.matcher(splitStr[0]);

            Pattern p3 = Pattern.compile("title=\"(.*?)\">");
            Matcher m3 = p3.matcher(splitStr[0]);


            Pattern p4 = Pattern.compile("<span itemprop=\"name\">(.*?)</span></a>");
            Matcher m4 = p4.matcher(splitStr[0]);


            Pattern p5 = Pattern.compile("<span itemprop=\"name\">(.*?)</span>");
            Matcher m5 = p5.matcher(splitStr[0]);


            while (m1.find() && m2.find()&& m3.find()&& m4.find() ) {

                listdata.add(new CardviewModel(m2.group(1),m3.group(1),m4.group(4),m5.group(5),m1.group(1)));
            }


        }
        catch (InterruptedException e) {
            Log.d("error", e.getMessage());
        }
        catch (ExecutionException e) {
            Log.d("error", e.getMessage());
        }

       // listdata.add(new CardviewModel("https://s3-eu-west-1.amazonaws.com/wuzzuf/files/company_logo/OPPO-Egypt-For-Trade-and-Distribution-Egypt-7132-1464267805-xs.jpg","grfgdrdfg","gdfgdfgdfg","Gfdgdfgfdgfd","gfdgdfgdfgdf"));

        RecyclerView recyclerView=(RecyclerView)Vroot.findViewById(R.id.recycler);
        RecyclerAdapter recyclerAdapter=new RecyclerAdapter(getActivity(),listdata);
        recyclerView.setAdapter(recyclerAdapter);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        return Vroot;
    }




}
