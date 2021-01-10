package com.cmtaro.app.karigmailapi;

import android.app.Activity;
import android.content.Intent;
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
        btTemplate = findViewById(R.id.template);

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = edEmail.getText().toString().trim();
                String password = edPassword.getText().toString().trim();

                Intent intent = new Intent(MainActivity.this, EmailActivity.class);
                intent.putExtra("email",email);
                intent.putExtra("password", password);
                startActivity(intent);
            }
        });

        btTemplate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TemplateActivity.class);
                startActivity(intent);
            }
        });
    }
}