package com.robotta.bot.rebuild;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DataPostingActivity extends AppCompatActivity {
    @Override protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_placeholder);
        ((TextView)findViewById(R.id.title)).setText("Data Posting");
        ((TextView)findViewById(R.id.status)).setText(
            "Foundation ready. Next module: local posting data model, import/export, queue and validation.");
    }
}
