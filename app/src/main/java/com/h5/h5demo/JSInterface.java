package com.h5.h5demo;

import android.content.Context;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by psh on 2/16/17.
 */

public class JSInterface {

    public static final String EXPOSE_NAME = "android";
    public static final List<String> img_cache = new ArrayList<String>();
    private Context context;
    public JSInterface(Context context){
        this.context = context;
    }

    @JavascriptInterface
    public String testStrings(){
        return "Hello world!!! (" + new Random().nextInt(999999) + ")";
    }

    @JavascriptInterface
    public void toast(String str) {
        Toast.makeText(this.context, str, Toast.LENGTH_SHORT).show();
    }

    @JavascriptInterface
    public void showSelImage(){
        ((MainActivity)context).openSelImageActivity();
    }

    @JavascriptInterface
    public String getImages(){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < img_cache.size(); i++) {
            sb.append(img_cache.get(i));
            if (i < img_cache.size() - 1){
                sb.append(",");
            }
        }
        return sb.toString();
    }
}
