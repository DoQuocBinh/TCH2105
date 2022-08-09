package fpt.grw.moreui;

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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,countries);
        Spinner sp = findViewById(R.id.spinner);
        sp.setAdapter(adapter);

        BottomNavigationView nav = findViewById(R.id.bottomNavigationView);
        nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.music:
                        Toast.makeText(getApplicationContext(),"Music",Toast.LENGTH_SHORT).show();
                        goToMusic();
                        return true;
                    case R.id.movie:
                        Toast.makeText(getApplicationContext(),"Movie",Toast.LENGTH_SHORT).show();
                        return true;
                }
                return false;
            }
        });
    }

    private void goToMusic() {
        TextInputLayout input = findViewById(R.id.textInputLayout);
        String name = input.getEditText().getText().toString();

        Intent intent = new Intent(this,Music.class);
        intent.putExtra("userName",name);
        startActivity(intent);

    }

    private String[] countries = new String[]{"Viet nam","Malaysia","Indonesia"};

}