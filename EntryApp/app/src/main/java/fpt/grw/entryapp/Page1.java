package fpt.grw.entryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Page1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page1);

        Intent intent = getIntent();
        String message = intent.getStringExtra("userName");

        TextView tv = findViewById(R.id.textView);
        tv.setText(message);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,COUNTRIES);
        Spinner sp = findViewById(R.id.spinner2);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new SpinnerItemSelected());
    }
    private static  final String[] COUNTRIES = new String[]{
            "Viet nam","Viet 2","France", "The USA"
    };
    class SpinnerItemSelected implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Toast.makeText(getApplicationContext(),COUNTRIES[i].toString(),Toast.LENGTH_LONG).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    }
}