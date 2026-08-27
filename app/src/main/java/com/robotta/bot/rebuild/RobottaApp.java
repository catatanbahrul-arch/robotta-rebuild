package com.robotta.bot.rebuild;

import android.app.Application;
import android.webkit.WebView;

public class RobottaApp extends Application {
    @Override public void onCreate() {
        super.onCreate();
        if (android.os.Build.VERSION.SDK_INT >= 28) {
            String process = Application.getProcessName();
            if (process != null && process.contains(":")) {
                try { WebView.setDataDirectorySuffix(process.substring(process.indexOf(':') + 1)); }
                catch (IllegalStateException ignored) {}
            }
        }
    }
}
