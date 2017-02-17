package com.h5.h5demo;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by psh on 2/16/17.
 */

public class ImageActivity extends AppCompatActivity {
    private WebView webView;
    private Button imgSelBtn;
    private int previewIdx = 0;
    private boolean isClickMode = false;
    private final String ImgGridUrl = "file:///android_asset/html/image_grid.html";


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_sel);

        JSInterface.img_cache.clear();

        imgSelBtn = (Button) findViewById(R.id.imgSelBtn);
        imgSelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl(ImgGridUrl);
            }
        });
        webView = (WebView) findViewById(R.id.image_wv);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.addJavascriptInterface(new JSInterface(this), JSInterface.EXPOSE_NAME);
//        webView.loadUrl("https://image.baidu.com/");

        webView.loadUrl(ImgGridUrl);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                final String tmpURL = url;
                if (JSInterface.img_cache.contains(tmpURL)) {
                    new AlertDialog.Builder(ImageActivity.this)
                            .setTitle("Select image.")
                            .setMessage("Select this image?")
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    doFinishWithURL(tmpURL);
                                }
                            })
                            .setNegativeButton(android.R.string.no, null)
                            .create()
                            .show();
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                String name = url.substring(url.lastIndexOf("/")).toLowerCase();
                boolean inPreview = url.contains("/image/pic/item/");
                if ((name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".bmp")) && inPreview) {
                    if (JSInterface.img_cache.contains(url))
                        return;

                    Log.e("123456", "Resource: " + url);
                    JSInterface.img_cache.add(url);
                    updateImageCount();
                }

                super.onLoadResource(view, url);
            }
        });

        updateImageCount();
    }

    private void updateImageCount(){
        imgSelBtn.setText("Found " + JSInterface.img_cache.size() + " images, touch to select.");
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                if (webView.getUrl().equalsIgnoreCase(ImgGridUrl) == false){
                    JSInterface.img_cache.clear();
                    updateImageCount();
                }

                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    private void doFinishWithURL(String url) {
        JSInterface.img_cache.clear();
        Intent intent = new Intent();
        intent.putExtra("image_url", url);
        setResult(2002, intent);
        finish();
    }
}
