package fpt.grw.persistentdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nameEditText = findViewById(R.id.name);
                String name = nameEditText.getText().toString();
                EditText emailEditText = findViewById(R.id.email);
                String email = emailEditText.getText().toString();

               writePreferences(name, email);

            }
        });
        Button btn2 = findViewById(R.id.buttonShow);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences(
                        getString(R.string.my_file), Context.MODE_PRIVATE);

                String name = pref.getString(getString(R.string.user_name),"");
                String email = pref.getString(getString(R.string.user_email),"");
                Toast.makeText(getApplicationContext(),name + ";" + email,Toast.LENGTH_SHORT).show();
            }
        });

        Button btn3 = findViewById(R.id.button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() ,MyAppSettings.class);
                startActivity(intent);
            }
        });

        Button saveDBBtn = findViewById(R.id.saveDB);
        saveDBBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveDB();
            }
        });
    }

    private void saveDB() {
        DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);
      
        EditText nameTxt = findViewById(R.id.name);
        EditText dobTxt = findViewById(R.id.dob);
        EditText emailTxt = findViewById(R.id.email);

        String name = nameTxt.getText().toString();
        String dob = dobTxt.getText().toString();
        String email = emailTxt.getText().toString();

        long personId = dbHelper.insertDetails(name, dob, email);
        Toast.makeText(MainActivity.this, "Person has been created with id: " + personId,
                Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, DetailsActivity.class);
        startActivity(intent);
    }

    private void writePreferences(String name, String email) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(
                getString(R.string.my_file), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(getString(R.string.user_name), name);
        editor.putString(getString(R.string.user_email), email);
        editor.apply();
    }
}