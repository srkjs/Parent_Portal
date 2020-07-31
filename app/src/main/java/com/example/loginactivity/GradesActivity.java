package com.example.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class GradesActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText mRegNo,msub1,msub2,msub3,msub4,msub5;
    EditText mPortal;
    Button mButtonRequest;
    TextView mTextViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        db = new DatabaseHelper(this);

        mRegNo = (EditText)findViewById(R.id.regno);
        msub1 = (EditText)findViewById(R.id.sub1);
        msub2 = (EditText)findViewById(R.id.sub2);
        msub3 = (EditText)findViewById(R.id.sub3);
        msub4 = (EditText)findViewById(R.id.sub4);
        msub5 = (EditText)findViewById(R.id.sub5);
        mPortal = (EditText)findViewById(R.id.portal);

        mTextViewLogin = (TextView)findViewById(R.id.textview_back);
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BackIntent = new Intent(GradesActivity.this,AdminActivity.class);
                startActivity(BackIntent);
            }
        });

        mButtonRequest = (Button)findViewById(R.id.button_add);
        mButtonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regno = mRegNo.getText().toString().trim();
                String portal = mPortal.getText().toString().trim();
                String sub1 = msub1.getText().toString().trim();
                String sub2 = msub2.getText().toString().trim();
                String sub3 = msub3.getText().toString().trim();
                String sub4 = msub4.getText().toString().trim();
                String sub5 = msub5.getText().toString().trim();

                final boolean checkStudentInTable = db.checkStudent(regno);
                final boolean checkStudentInMarksTable = db.checkStudentInMarksTab(regno, portal);

                if (checkStudentInTable == true && checkStudentInMarksTable == true) {
                    boolean updateAtt = db.updateMarks(regno, portal, sub1, sub2, sub3, sub4, sub5);
                    if (updateAtt == true) {
                        Toast.makeText(GradesActivity.this, "Existing grades updated!", Toast.LENGTH_SHORT).show();
                        Intent moveToAdmin = new Intent(GradesActivity.this, AdminActivity.class);
                        startActivity(moveToAdmin);
                    } else {
                        Toast.makeText(GradesActivity.this, "Error in updating existing grades!", Toast.LENGTH_SHORT).show();
                        Intent moveToAdmin = new Intent(GradesActivity.this, AdminActivity.class);
                        startActivity(moveToAdmin);
                    }
                } else if (checkStudentInTable == true && checkStudentInMarksTable == false) {

                    long val = db.addMarks(regno, portal, sub1, sub2, sub3, sub4, sub5);
                    if (val <= 0) {
                        Toast.makeText(GradesActivity.this, "Error in updating grades!", Toast.LENGTH_SHORT).show();
                        Intent moveToAdmin = new Intent(GradesActivity.this, AdminActivity.class);
                        startActivity(moveToAdmin);
                    } else {
                        Toast.makeText(GradesActivity.this, "Grades updated!", Toast.LENGTH_SHORT).show();
                        Intent moveToAdmin = new Intent(GradesActivity.this, AdminActivity.class);
                        startActivity(moveToAdmin);
                    }
                } else {
                    Toast.makeText(GradesActivity.this, "Please check the entered Register Number!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}


