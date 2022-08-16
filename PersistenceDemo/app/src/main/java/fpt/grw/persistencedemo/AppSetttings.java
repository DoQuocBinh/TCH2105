package fpt.grw.persistencedemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class AppSetttings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_setttings);

        //load Fragment into the Container
        getSupportFragmentManager()
                .beginTransaction()
                //SettingsFragment will be loaded into MyLayout
                .replace(R.id.MyLayout, new SettingsFragment())
                .commit();
        Button btn = findViewById(R.id.btnShowName);
        btn.setOnClickListener(view -> {
            //get the value from root_preferences.xml file
            String msg = PreferenceManager.getDefaultSharedPreferences(AppSetttings.this)
                    .getString("signature", null);

            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
        });
    }
}