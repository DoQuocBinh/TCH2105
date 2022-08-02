package fpt.grw.entryapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnOk =  findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sayHi();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        AutoCompleteTextView textView = (AutoCompleteTextView)findViewById(R.id.country);
        textView.setThreshold(1);
        textView.setAdapter(adapter);

    }
    private static  final String[] COUNTRIES = new String[]{
            "Viet nam","France", "The USA"
    };

    private void sayHi() {
        //refer to the textField
        EditText txtName =  findViewById(R.id.txtName);
        //get the user's input
        String name = txtName.getText().toString();
        //display to user via Toast
        Toast.makeText(getApplicationContext(),"Hello " + name,Toast.LENGTH_LONG).show();
    }

}