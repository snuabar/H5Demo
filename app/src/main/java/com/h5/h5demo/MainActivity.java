package com.h5.h5demo;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private WebView main_wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_wv = (WebView) findViewById(R.id.main_wv);
        main_wv.getSettings().setJavaScriptEnabled(true);
        main_wv.addJavascriptInterface(new JSInterface(this), JSInterface.EXPOSE_NAME);

        main_wv.loadUrl("file:///android_asset/html/test.html");
    }

    public void openSelImageActivity(){
        Intent intent = new Intent(this, ImageActivity.class);
        startActivityForResult(intent, 2002);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("123456", "Result: " + resultCode);
        if (resultCode == 2002) {
            String url = data.getStringExtra("image_url");
            Log.e("123456", "url: " + url);
            this.main_wv.evaluateJavascript("setURLToTextField('" + url + "')", null);
        }
    }
}
