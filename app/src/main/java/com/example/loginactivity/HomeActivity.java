package com.example.loginactivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {
    DatabaseHelper mydb;
    TextView studentid,std,studname,studdept,mob,email,address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mydb =new DatabaseHelper(this);
        final String regno = getIntent().getStringExtra("regno");
        final Cursor res = mydb.getStudentData(regno);
        final int count = res.getCount();
        res.moveToFirst();

        studentid = (TextView)findViewById(R.id.sid);
        std = (TextView)findViewById(R.id.std);
        final String standard = res.getString(2);
        studname = (TextView)findViewById(R.id.sname);
        final String studentName = res.getString(1);
        studdept = (TextView)findViewById(R.id.sdept);
        mob = (TextView)findViewById(R.id.mob);
        email = (TextView)findViewById(R.id.email);
        address = (TextView)findViewById(R.id.address);

        if(count == 0)
        {
            Toast.makeText(this,"No data found",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(HomeActivity.this,LoginActivity.class);
            startActivity(intent);
        }
        else
        {
            studentid.setText(res.getString(0));
            studname.setText(res.getString(1));
            std.setText(standard);
            studdept.setText(res.getString(3));
            mob.setText(res.getString(4));
            email.setText(res.getString(5));
            address.setText(res.getString(6));
        }

        Button mButton1 = (Button) findViewById(R.id.button_grades);
        Button mButton2 = (Button) findViewById(R.id.button_attendance);
        Button mButton3 = (Button) findViewById(R.id.button_schedule);
        Button mButton4 = (Button) findViewById(R.id.button_contact);
        Button mButton5 = (Button) findViewById(R.id.button_logout);


        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CheckGrades.class);
                intent.putExtra("regno",regno);
                startActivity(intent);
            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CheckAttendance.class);
                intent.putExtra("regno",regno);
                startActivity(intent);
            }
        });

        mButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, CheckSchedule.class);
                intent.putExtra("regno",regno);
                intent.putExtra("standard",standard);
                startActivity(intent);
            }
        });

        mButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, ContactTeacher.class);
                intent.putExtra("rno",regno);
                intent.putExtra("name",studentName);
                startActivity(intent);
            }
        });

        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}

