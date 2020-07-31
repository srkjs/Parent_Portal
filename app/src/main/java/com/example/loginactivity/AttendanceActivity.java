package com.example.loginactivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AttendanceActivity extends AppCompatActivity {
    DatabaseHelper db;
    EditText mRegNo,mName,mSTD,mParent,mDOB,mBlood,mPass,mAddress;
    EditText mAttedance,mTotal,mPortal;
    EditText mMob;
    Button mButtonRequest;
    TextView mTextViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        db = new DatabaseHelper(this);

        mRegNo = (EditText)findViewById(R.id.regno);
        mAttedance = (EditText)findViewById(R.id.attendance);
        mTotal = (EditText) findViewById(R.id.total);
        mPortal = (EditText)findViewById(R.id.portal);

        mTextViewLogin = (TextView)findViewById(R.id.textview_back);
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BackIntent = new Intent(AttendanceActivity.this,AdminActivity.class);
                startActivity(BackIntent);
            }
        });

        mButtonRequest = (Button)findViewById(R.id.button_add);
        mButtonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regno = mRegNo.getText().toString().trim();
                String att = mAttedance.getText().toString().trim();
                String tot = mTotal.getText().toString().trim();
                String portal = mPortal.getText().toString().trim();

                final boolean checkStudentInTable = db.checkStudent(regno);
                final boolean checkStudentInAttendanceTable = db.checkStudentInAttendanceTab(regno,portal);

                if (checkStudentInTable == true && checkStudentInAttendanceTable==true)
                {
                        boolean updateAtt = db.updateAttendance(regno,portal,att,tot);
                        if(updateAtt==true){
                            Toast.makeText(AttendanceActivity.this,"Existing Attendance updated!",Toast.LENGTH_SHORT).show();
                            Intent moveToAdmin = new Intent(AttendanceActivity.this,AdminActivity.class);
                            startActivity(moveToAdmin);
                        }
                        else{
                            Toast.makeText(AttendanceActivity.this,"Error in updating existing attendance!",Toast.LENGTH_SHORT).show();
                            Intent moveToAdmin = new Intent(AttendanceActivity.this,AdminActivity.class);
                            startActivity(moveToAdmin);
                        }
                }
                else if(checkStudentInTable==true && checkStudentInAttendanceTable==false)
                {
                        long val = db.addAttendance(regno,portal,att,tot);
                        if(val <= 0){
                            Toast.makeText(AttendanceActivity.this,"Error in updating attendance!",Toast.LENGTH_SHORT).show();
                            Intent moveToAdmin = new Intent(AttendanceActivity.this,AdminActivity.class);
                            startActivity(moveToAdmin);
                        }
                        else{
                            Toast.makeText(AttendanceActivity.this,"Attendance updated!",Toast.LENGTH_SHORT).show();
                            Intent moveToAdmin = new Intent(AttendanceActivity.this,AdminActivity.class);
                            startActivity(moveToAdmin);
                        }
                }

                else{
                    Toast.makeText(AttendanceActivity.this,"Please check the entered Register Number!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}