package com.robotta.bot.rebuild;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_web);
        WebView w = findViewById(R.id.webView);
        WebSettings s = w.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setDatabaseEnabled(true);
        w.setWebViewClient(new WebViewClient());
        w.loadUrl("https://www.facebook.com/");
    }
    @Override protected void onDestroy() {
        WebView w = findViewById(R.id.webView);
        if (w != null) w.destroy();
        super.onDestroy();
    }
}
