package com.robotta.bot.rebuild;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class AutoFrameActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_placeholder);
        ((TextView)findViewById(R.id.title)).setText("Auto Frame");
        ((TextView)findViewById(R.id.status)).setText(
            "Foundation ready. Next module: image picker, frame templates, text overlay and export pipeline.");
    }
}
