package fpt.grw.reviewandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import entities.Exam;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText inputName = findViewById(R.id.inputName);
        EditText inputExamDate = findViewById(R.id.inputExamDate);
        EditText inputDesc = findViewById(R.id.inputDescription);

        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(view -> {
            String name = inputName.getText().toString();
            String exam_Date = inputExamDate.getText().toString();
            String description = inputDesc.getText().toString();
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            dbHelper.insertExam(name,exam_Date,description);
            Toast.makeText(this,"Saved " + name ,Toast.LENGTH_SHORT).show();
        });

        Button btnView = findViewById(R.id.btnView);
        btnView.setOnClickListener(view -> {
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            List<Exam> exams = dbHelper.getExams();
            ArrayAdapter<Exam> adapter = new ArrayAdapter<Exam>(this,
                        android.R.layout.simple_list_item_1,exams);
            ListView listView = findViewById(R.id.listView);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener((adapterView, view1, i, l) -> {
                Exam selectedExam = exams.get(i);
                Intent intent = new Intent(this,ExamDetails.class);
                intent.putExtra("id",selectedExam.getId());
                intent.putExtra("name",selectedExam.getName());
                intent.putExtra("exam_date",selectedExam.getExam_date());
                startActivity(intent);

            });
        });
    }
}