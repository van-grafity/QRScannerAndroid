package com.ivansuhendra.barcodescanner.utils;

import android.app.Activity;
import android.util.Log;
import android.webkit.JavascriptInterface;

public class JavaScriptInterface {
    private Activity activity;

    public JavaScriptInterface(Activity activity) {
        this.activity = activity;
    }

    @JavascriptInterface
    public void onSuccess(String text) {
        Log.e("callback", "succes " + text);
    }

    @JavascriptInterface
    public void onFailed(String text) {
        Log.e("callback", "asdasdads " + text);
    }
}
