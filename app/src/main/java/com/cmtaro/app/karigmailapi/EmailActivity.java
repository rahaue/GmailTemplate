package com.cmtaro.app.karigmailapi;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.xml.transform.Templates;

public class EmailActivity extends AppCompatActivity {

    EditText etTo,etSubject, etMessage;
    Button btSend;
    String sEmail, sPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        etTo = findViewById(R.id.ed_to);
        etSubject = findViewById(R.id.et_subject);
        etMessage = findViewById(R.id.et_message);
        btSend = findViewById(R.id.bt_send);

        // Emailとパスワード
        SharedPreferences preferences = getSharedPreferences("personal_data",MODE_PRIVATE);
        String email = preferences.getString("email", "");
        String password = preferences.getString("password", "");

        // SubjectとText
        Intent intent = getIntent();
        String subject = intent.getStringExtra("subject");
        String text = intent.getStringExtra("text");

        etSubject.setText(subject);
        etMessage.setText(text);

        // GOOGLEのアカウントを以下に記入
        sEmail = email; //your email
        sPassword = password; // your password

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Properties properties = new Properties();
                properties.put("mail.smtp.auth", "true");
                properties.put("mail.smtp.starttls.enable", "true");
                properties.put("mail.smtp.host", "smtp.gmail.com");
                properties.put("mail.smtp.port", "587");

                Session session = Session.getInstance(properties, new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(sEmail,sPassword);
                    }
                });

                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(sEmail));
                    message.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse(etTo.getText().toString().trim()));
                    message.setSubject(etSubject.getText().toString().trim());
                    message.setText(etMessage.getText().toString().trim());
                    new SendMail().execute(message);

                } catch (MessagingException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    private class SendMail extends AsyncTask<Message,String,String> {
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // ProgressDialog : https://techacademy.jp/magazine/3719
            progressDialog = ProgressDialog.show(EmailActivity.this,
                    "Please Wait", "Sending Mail...", true, false);
        }

        @Override
        protected String doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
                return "Success";
            } catch (MessagingException e) {
                e.printStackTrace();
                return "Error";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressDialog.dismiss();

            if (s.equals("Success")) {

                AlertDialog.Builder builder = new AlertDialog.Builder(EmailActivity.this);
                builder.setCancelable(false);
                builder.setTitle(Html.fromHtml("<font color='#509324'>Success</font>"));
                builder.setMessage("Mail send successully.");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // 初期化
                        etTo.setText("");
                        etSubject.setText("");
                        etMessage.setText("");
                    }
                });
                // show alert dialog
                builder.show();
            } else {
                // when Error
                Toast.makeText(getApplicationContext(),
                        "Something went wrong ?", Toast.LENGTH_SHORT).show();
            }
        }
    }
}