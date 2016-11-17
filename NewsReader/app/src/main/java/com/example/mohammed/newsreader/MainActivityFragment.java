package com.example.mohammed.newsreader;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */

public class MainActivityFragment extends Fragment {


    ArrayList<String> articleTitle=new ArrayList< String>() ;
    ArrayList<String> articleUrl=new ArrayList< String>() ;
    ArrayList<Integer> articleIds=new ArrayList<Integer>();
    ArrayList<String> titles=new ArrayList<String>();
    ArrayList<String> urls=new ArrayList<String>();
    SQLiteDatabase articleDB;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRoot =inflater.inflate(R.layout.fragment_main, container, false);


         articleDB=getActivity().openOrCreateDatabase("articles", Context.MODE_PRIVATE,null);
        articleDB.execSQL("CREATE TABLE IF NOT EXISTS articles(id INTEGER PRIMARY KEY ,articleID INTEGER,titles VARCHAR,urls VARCHAR )");

        ListView listView=(ListView)viewRoot.findViewById(R.id.listView);
        ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,articleTitle);

    DownloadContent downloadContent= new DownloadContent();
    try {
        String content=downloadContent.execute("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty").get();
        JSONArray jsonArray=new JSONArray(content);
        articleDB.execSQL("DELETE  FROM articles");
        for (int i=0;i<jsonArray.length();i++){
            String articleId=jsonArray.getString(i);
            DownloadContent downloadContent2=new DownloadContent();
            String content2=downloadContent2.execute("https://hacker-news.firebaseio.com/v0/item/"+articleId+".json?print=pretty").get();
            JSONObject jsonObject=new JSONObject(content2);
            articleIds.add(Integer.valueOf(articleId));
           articleTitle.add(jsonObject.getString("title"));
            articleUrl.add(jsonObject.getString("url"));

            String sql="INSERT INTO articles(articleID,titles,urls)VALUES (?,?,?)";
            SQLiteStatement sqLiteStatement=articleDB.compileStatement(sql);
            sqLiteStatement.bindString(1,articleId);
            sqLiteStatement.bindString(2,jsonObject.getString("title"));
            sqLiteStatement.bindString(3,jsonObject.getString("url"));
            sqLiteStatement.execute();


        }

        Cursor c=articleDB.rawQuery("SELECT * FROM articles ORDER BY articleID DESC",null);
        int idIndex=c.getColumnIndex("articleID");
        int titleIndex=c.getColumnIndex("titles");
        int urlIndex=c.getColumnIndex("urls");
        c.moveToFirst();
        titles.clear();
        urls.clear();
        while (c!=null){
            titles.add(c.getString(titleIndex));
            urls.add(c.getString(urlIndex));
            Log.i("articletitles",c.getString(titleIndex));
            Log.i("articleurl",c.getString(urlIndex));

            c.moveToNext();
        }
    arrayAdapter.notifyDataSetChanged();
    }catch (Exception e){
        e.printStackTrace();
    }

    listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(getActivity(),NewsActivity.class);
                i.putExtra("link",articleUrl.get(position));
                startActivity(i);
            }
        });
        return viewRoot;
    }

    public class DownloadContent extends AsyncTask<String ,Void ,String >{

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

                Log.i("download ",e.getMessage());

            }

            return null;

        }
    }
}
