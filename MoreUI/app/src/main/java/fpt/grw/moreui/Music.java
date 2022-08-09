package fpt.grw.moreui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

public class Music extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        TextView text = findViewById(R.id.textView);
        Intent intent = getIntent();
        String name = intent.getStringExtra("userName");
        text.setText(name);

    }
}