package com.example.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ScheduleActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText mRegNo,mSchedule;
    Button mButtonRequest;
    TextView mTextViewLogin;
    Spinner mStandard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        db = new DatabaseHelper(this);

        mRegNo = (EditText)findViewById(R.id.regno);
        mStandard = (Spinner)findViewById(R.id.std);
        mSchedule = (EditText) findViewById(R.id.schedule);

        mTextViewLogin = (TextView)findViewById(R.id.textview_back);
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BackIntent = new Intent(ScheduleActivity.this,AdminActivity.class);
                startActivity(BackIntent);
            }
        });

        mButtonRequest = (Button)findViewById(R.id.button_add);
        mButtonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regno = mRegNo.getText().toString().trim();
                String std = mStandard.getSelectedItem().toString().trim();
                String schedule = mSchedule.getText().toString().trim();

                long val = db.addSchedules(regno,std,schedule);
                if(val <= 0){
                    Toast.makeText(ScheduleActivity.this,"Error in updating schedule!",Toast.LENGTH_SHORT).show();
                    Intent moveToAdmin = new Intent(ScheduleActivity.this,AdminActivity.class);
                    startActivity(moveToAdmin);
                }
                else{
                    Toast.makeText(ScheduleActivity.this,"Schedule updated!",Toast.LENGTH_SHORT).show();
                    Intent moveToAdmin = new Intent(ScheduleActivity.this,AdminActivity.class);
                    startActivity(moveToAdmin);
                }

            }
        });
    }
}