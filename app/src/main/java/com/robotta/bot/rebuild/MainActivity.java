package com.robotta.bot.rebuild;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btnDataPosting).setOnClickListener(v ->
                startActivity(new Intent(this, DataPostingActivity.class)));
        findViewById(R.id.btnAutoFrame).setOnClickListener(v ->
                startActivity(new Intent(this, AutoFrameActivity.class)));
        findViewById(R.id.btnMessenger).setOnClickListener(v ->
                startActivity(new Intent(this, MessengerActivity.class)));
        findViewById(R.id.btnWeb).setOnClickListener(v ->
                startActivity(new Intent(this, WebViewActivity.class)));
        findViewById(R.id.btnService).setOnClickListener(v -> {
            Intent i = new Intent(this, BotForegroundService.class);
            i.setAction(BotForegroundService.ACTION_START_OR_UPDATE);
            i.putExtra(BotForegroundService.EXTRA_TEXT, "Robotta rebuild service running");
            i.putExtra(BotForegroundService.EXTRA_CURRENT, 0);
            androidx.core.content.ContextCompat.startForegroundService(this, i);
        });
    }
}
