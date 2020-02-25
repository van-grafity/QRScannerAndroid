package com.ivansuhendra.barcodescanner;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ivansuhendra.barcodescanner.utils.JavaScriptInterface;

public class MyWebViewActivity extends AppCompatActivity {
    WebView wvTester;
    String url = "";
    SwipeRefreshLayout swipe;
    ProgressBar progressBar;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_web_view);

        wvTester = findViewById(R.id.wv_tester);
        progressBar = findViewById(R.id.awv_progressBar);
        swipe = findViewById(R.id.swipe);
        LoadWeb();

        progressBar.setMax(100);
        progressBar.setProgress(1);

        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                wvTester.reload();
            }
        });

        wvTester.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {


                progressBar.setProgress(progress);
            }
        });

        wvTester.setWebViewClient(new WebViewClient() {


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressBar.setVisibility(View.VISIBLE);
            }


            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

                wvTester.loadUrl("file:///android_asset/error.html");
            }

            public void onLoadResource(WebView view, String url) { //Doesn't work
                //swipe.setRefreshing(true);
            }

            public void onPageFinished(WebView view, String url) {

                //Hide the SwipeReefreshLayout
                progressBar.setVisibility(View.GONE);
                swipe.setRefreshing(false);
            }

        });
    }

    @Override
    public void onBackPressed() {

        if (wvTester.canGoBack()) {
            wvTester.goBack();
        } else {
            finish();
        }
    }

    public void LoadWeb() {
        JavaScriptInterface jsInterface = new JavaScriptInterface(this);
        wvTester.getSettings().setJavaScriptEnabled(true);
        wvTester.addJavascriptInterface(jsInterface, "JSInterface");
        url = getIntent().getStringExtra("URL_RESULT");
        Log.i("Link Barcode ", "" + url);
        wvTester.loadUrl(url);
        wvTester.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String request) {
//                return super.shouldOverrideUrlLoading(view, request);
                view.loadUrl(request);
                return true;
            }
        });
    }
}
