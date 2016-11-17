package com.example.mohammed.newsreader;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewsActivityFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View viewRoot= inflater.inflate(R.layout.fragment_news, container, false);
        WebView webView=(WebView)viewRoot.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl(getActivity().getIntent().getStringExtra("link"));

        return viewRoot;
    }
}
