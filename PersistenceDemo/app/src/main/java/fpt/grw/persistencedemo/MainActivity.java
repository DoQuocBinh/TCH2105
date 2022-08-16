package fpt.grw.persistencedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(view -> {
            EditText nameText = findViewById(R.id.name);
            EditText  emailText = findViewById(R.id.email);
            EditText dobText = findViewById(R.id.dob);

            String name = nameText.getText().toString();
            String email = emailText.getText().toString();
            String dob = dobText.getText().toString();
            saveSharedPrefrences(name,email,dob);
        });

        Button btn2 = findViewById(R.id.button3);
        btn2.setOnClickListener(view ->{
            SharedPreferences pref = getApplicationContext().getSharedPreferences("myfile2", Context.MODE_PRIVATE);
            String name = pref.getString("user_name","not exist!");
            Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
        });

        Button btn3 = findViewById(R.id.button2);
        btn3.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,AppSetttings.class);
            startActivity(intent);
        });

        Button btn4 = findViewById(R.id.button4);
        btn4.setOnClickListener(view ->{
            DatabaseHelper helper  = new DatabaseHelper(MainActivity.this);
            EditText nameText = findViewById(R.id.name);
            EditText  emailText = findViewById(R.id.email);
            EditText dobText = findViewById(R.id.dob);

            String name = nameText.getText().toString();
            String email = emailText.getText().toString();
            String dob = dobText.getText().toString();
            helper.insertDetails(name,dob,email);

            String everything = helper.getDetails();
            TextView tv = findViewById(R.id.textView);
            tv.setText(everything);
        });
    }

    private void saveSharedPrefrences(String name, String email, String dob) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences("myfile2", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();
        editor.putString("user_name", name);
        editor.putString("user_email", email);
        editor.putString("user_dob",dob);
        editor.apply();
        Toast.makeText(getApplicationContext(),"Preferences saved!",Toast.LENGTH_SHORT).show();
    }
}