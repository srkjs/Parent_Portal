package com.example.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText mRegNo;
    EditText mEmail;
    EditText mMob;
    Button mButtonRequest;
    TextView mTextViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = new DatabaseHelper(this);
        mRegNo = (EditText)findViewById(R.id.regno);
        mEmail = (EditText)findViewById(R.id.email);
        mMob = (EditText)findViewById(R.id.mob);
        mButtonRequest = (Button)findViewById(R.id.button_request);
        mTextViewLogin = (TextView)findViewById(R.id.textview_login);
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent LoginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(LoginIntent);
            }
        });

        mButtonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regno = mRegNo.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String mob = mMob.getText().toString().trim();

                long val = db.requestNewPassword(regno,email,mob);
                if(val > 0){
                Toast.makeText(RegisterActivity.this,"We will reach you shortly!",Toast.LENGTH_SHORT).show();
                Intent moveToLogin = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(moveToLogin);
                }

            }
        });
    }
}