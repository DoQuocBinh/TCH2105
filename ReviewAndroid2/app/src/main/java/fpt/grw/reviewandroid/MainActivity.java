package fpt.grw.reviewandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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
            TextView tv = findViewById(R.id.textView);
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            tv.setText(dbHelper.getExams());
        });
    }
}