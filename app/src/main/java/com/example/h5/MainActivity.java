package com.example.h5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class MainActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);

        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);

//        webView.loadUrl("https://www.baidu.com/");
        webView.loadUrl("file:///android_asset/html.html");
        setContentView(webView);
    }
}
