package com.example.loginactivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
public class StudentDetails extends AppCompatActivity {
    DatabaseHelper mydb;
    TextView std,studname,parent,mob,email,address,dob,blood,pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        mydb =new DatabaseHelper(this);

        final EditText regno = (EditText) findViewById(R.id.regno);
        Button mButton5 = (Button) findViewById(R.id.button_getDetails);
        mButton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String strRegno = regno.getText().toString().trim();
                final Cursor res = mydb.getStudentDetails(strRegno);
                final int count = res.getCount();
                res.moveToFirst();

                studname = (TextView)findViewById(R.id.sname);
                std = (TextView)findViewById(R.id.std);
                parent = (TextView)findViewById(R.id.sdept);
                mob = (TextView)findViewById(R.id.mob);
                email = (TextView)findViewById(R.id.email);
                address = (TextView)findViewById(R.id.address);
                dob = (TextView)findViewById(R.id.dob);
                blood = (TextView)findViewById(R.id.blood);
                pass = (TextView)findViewById(R.id.pass);



                if(count == 0){
                    Toast.makeText(StudentDetails.this, "No data found!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    studname.setText(res.getString(0));
                    std.setText(res.getString(1));
                    parent.setText(res.getString(2));
                    mob.setText(res.getString(3));
                    email.setText(res.getString(4));
                    address.setText(res.getString(5));
                    dob.setText(res.getString(6));
                    blood.setText(res.getString(7));
                    pass.setText(res.getString(8));
                }
            }
        });





        TextView mTextViewLogin;
        mTextViewLogin = (TextView)findViewById(R.id.textview_back);
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BackIntent = new Intent(StudentDetails.this,AdminActivity.class);
                startActivity(BackIntent);
            }
        });
    }
}
