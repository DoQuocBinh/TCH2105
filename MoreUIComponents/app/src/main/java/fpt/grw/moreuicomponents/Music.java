package fpt.grw.moreuicomponents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Music extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        TextView tv = findViewById(R.id.textView);
        Intent intent = getIntent();
        String name = intent.getStringExtra("userName");
        tv.setText(name);
    }
}