package com.example.alciphron.settings;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.alciphron.R;

public class SettingsActivity extends AppCompatActivity {

    private Button buttonSettingsBack;
    private Button buttonSettingsSave;

    private EditText editTextFinder;
    private EditText editTextEmail;

    public static final String SHARED_PREFS = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);



        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setIcon(R.drawable.banerlogo);
        actionBar.setTitle("");

        ColorDrawable cd = new ColorDrawable(Color.parseColor("#ffffff"));
        actionBar.setBackgroundDrawable(cd);

        buttonSettingsBack = findViewById(R.id.buttonSettingsBack);
        buttonSettingsSave = findViewById(R.id.buttonSettingsSave);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextFinder = findViewById(R.id.editTextFinder);



        buttonSettingsBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        buttonSettingsSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
                finish();
            }
        });

        //loading data from settings database shared preference
        loadData();

    }

    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("finder",editTextFinder.getEditableText().toString());
        editor.putString("email",editTextEmail.getEditableText().toString());

        editor.apply();
    }

    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);

        editTextFinder.setText(sharedPreferences.getString("finder",""));
        editTextEmail.setText(sharedPreferences.getString("email",""));

    }
}