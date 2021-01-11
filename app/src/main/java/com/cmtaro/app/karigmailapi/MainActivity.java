package com.cmtaro.app.karigmailapi;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    Button btNext, btTemplate;
    EditText edEmail, edPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btNext = findViewById(R.id.bt_next);
        edEmail = findViewById(R.id.email);
        edPassword = findViewById(R.id.password);

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edEmail.getText().toString().trim();
                String password = edPassword.getText().toString().trim();

                SharedPreferences preferences = getSharedPreferences("personal_data",MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("email",email);
                editor.putString("password", password);
                editor.commit();

                Intent intent = new Intent(MainActivity.this, TemplateActivity.class);
                startActivity(intent);
            }
        });
    }
}