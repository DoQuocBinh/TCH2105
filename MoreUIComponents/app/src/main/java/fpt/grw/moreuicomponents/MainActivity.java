package fpt.grw.moreuicomponents;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner sp = findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,countries);
        sp.setAdapter(adapter);

        //register events for BottomNavigation
        BottomNavigationView nav =  findViewById(R.id.bottomAppBar);
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.page1:
                        sendMessageToPage1();
                        return  true;
                    case R.id.page2:
                        Toast.makeText(getApplicationContext(),"Page 2 clicked",Toast.LENGTH_LONG).show();
                        return true;
                }
                return false;
            }
        });

    }

    private void sendMessageToPage1() {
        TextInputLayout nameInput = findViewById(R.id.userName);
        String name = nameInput.getEditText().getText().toString();

        Intent intent = new Intent(this,Music.class);
        intent.putExtra("userName",name);
        startActivity(intent);

    }

    private String[] countries = new String[]{"VietNam","India","Singapor","Malaysia"};
}