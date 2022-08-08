package fpt.grw.entryapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

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
        AutoCompleteTextView textView = findViewById(R.id.country);
        textView.setThreshold(1);
        textView.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, COUNTRIES);
        AutoCompleteTextView textView2 = findViewById(R.id.country2);
        textView2.setThreshold(1);
        textView2.setAdapter(adapter2);

        BottomNavigationView bottomNavigationView;
        bottomNavigationView = findViewById(R.id.bottomnavigation);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case  R.id.page_1:
                        Toast.makeText(getApplicationContext(),"Page 1",Toast.LENGTH_LONG).show();
                        sendMessage();
                        return  true;
                    case R.id.page_2:
                        Toast.makeText(getApplicationContext(),"Page 2",Toast.LENGTH_LONG).show();
                        return true;
                }
                return false;
            }
        });
    }
    private void sendMessage(){
        Intent intent = new Intent(this,Page1.class);
        //refer to the textField
        EditText txtName =  findViewById(R.id.txtName);
        //get the user's input
        String name = txtName.getText().toString();
        intent.putExtra("userName",name);
        startActivity(intent);
    }
    private static  final String[] COUNTRIES = new String[]{
            "Viet nam","Viet 2","France", "The USA"
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
