package fpt.grw.reviewandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class ExamDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_details);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String date = intent.getStringExtra("exam_date");

        EditText inputName = findViewById(R.id.inputNameDetail);
        EditText inputDate = findViewById(R.id.inputDateDetail);
        inputName.setText(name);
        inputDate.setText(date);
    }
}