package fpt.grw.reviewandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ExamDetails extends AppCompatActivity {
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_details);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String date = intent.getStringExtra("exam_date");

        EditText inputName = findViewById(R.id.inputNameDetail);
        EditText inputDate = findViewById(R.id.inputQuestion);
        inputName.setText(name);
        inputDate.setText(date);

        Button btnPrev = findViewById(R.id.btnPrev);
        btnPrev.setOnClickListener(view -> {
            TextView inputPicURL = findViewById(R.id.inputPictureURL);
            ImageView imageView = findViewById(R.id.imageView);
            DownloadImageTask task = new DownloadImageTask(mProgressDialog,this,imageView);
            task.execute(inputPicURL.getText().toString());
        });
    }
}