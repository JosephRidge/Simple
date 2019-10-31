package com.example.authenticate;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

public class DashBoard extends AppCompatActivity {
    ProgressBar progressBar;
    private WebView WebView;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        WebView = (WebView) findViewById(R.id.webview);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);

        WebSettings webSettings = WebView.getSettings();//allows us to connect to the websettings
        webSettings.setJavaScriptEnabled(true);//// Allows loading  web page(s) that use JavaScript

        WebView.loadUrl("http://www.targetwebpage.com"); //Load target URL, in my case i have loaded targetwebpage.com

        WebView.setWebViewClient(new MyWebViewClient());// we create an instance that basically provides a webclient that opens links clicked by user


    }

    private class MyWebViewClient extends WebViewClient {


        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, String url) {//basically controls where where the clicked link loads
            webView.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);

            progressBar.setVisibility(view.GONE);
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) { //allows navigation backward/forward
        if ((keyCode == KeyEvent.KEYCODE_BACK) && WebView.canGoBack()) {//works if there exists previously visited page
            WebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);//will exit main activity if there is not default location, in my case there exists none
    }
}

