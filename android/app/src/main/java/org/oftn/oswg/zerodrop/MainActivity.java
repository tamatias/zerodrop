package org.oftn.oswg.zerodrop;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    private WebView mWebView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView = findViewById(R.id.webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient());

        final Intent intent = getIntent();
        if (intent != null) {
            loadWebApp(intent);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        loadWebApp(intent);
    }

    private void loadWebApp(Intent intent) {
        mWebView.stopLoading();
        mWebView.addJavascriptInterface(new ShareIntentInterface(this, intent), "ShareIntent");
        mWebView.loadUrl(getString(R.string.zerodrop_url));
    }

}
