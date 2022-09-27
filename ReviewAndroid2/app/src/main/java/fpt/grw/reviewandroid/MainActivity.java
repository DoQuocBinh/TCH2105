package fpt.grw.reviewandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Calendar;
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

        inputExamDate.setOnFocusChangeListener((view, b) -> {
            if(b){
                MyDatePicker dlg = new MyDatePicker();
                dlg.setDateField(inputExamDate);
                dlg.show(getSupportFragmentManager(),"my date time!");
            }
        });

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
                Intent intent = new Intent(this, ExamDetailsActivity.class);
                intent.putExtra("id",selectedExam.getId());
                intent.putExtra("name",selectedExam.getName());
                intent.putExtra("exam_date",selectedExam.getExam_date());
                startActivity(intent);

            });
        });
    }
    public static class MyDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        public EditText getDateField() {
            return dateField;
        }

        public void setDateField(EditText dateField) {
            this.dateField = dateField;
        }
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(requireContext(), this, year, month, day);
        }
        private EditText dateField;

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
            String dateReturn = i2 + "/" + i1 + "/" + i;
            dateField.setText(dateReturn);

        }
    }
}