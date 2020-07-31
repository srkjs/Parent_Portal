package com.example.loginactivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddStudent extends AppCompatActivity {
    DatabaseHelper db;
    EditText mRegNo,mName,mParent,mDOB,mBlood,mPass,mAddress;
    EditText mEmail;
    EditText mMob;
    Button mButtonRequest;
    TextView mTextViewLogin;
    Spinner mSTD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addstudent);

        db = new DatabaseHelper(this);

        mRegNo = (EditText)findViewById(R.id.regno);
        mName = (EditText)findViewById(R.id.name);
        mSTD = (Spinner) findViewById(R.id.std);
        mParent = (EditText)findViewById(R.id.parent);
        mDOB = (EditText)findViewById(R.id.dob);
        mBlood = (EditText)findViewById(R.id.blood);
        mPass = (EditText)findViewById(R.id.password);
        mMob = (EditText)findViewById(R.id.mob);
        mEmail = (EditText)findViewById(R.id.email);
        mAddress = (EditText)findViewById(R.id.address);

        mTextViewLogin = (TextView)findViewById(R.id.textview_back);
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BackIntent = new Intent(AddStudent.this,AdminActivity.class);
                startActivity(BackIntent);
            }
        });

        mButtonRequest = (Button)findViewById(R.id.button_add);
        mButtonRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String regno = mRegNo.getText().toString().trim();
                String name = mName.getText().toString().trim();
                String std = mSTD.getSelectedItem().toString().trim();
                String parent = mParent.getText().toString().trim();
                String dob = mDOB.getText().toString().trim();
                String blood = mBlood.getText().toString().trim();
                String pass = mPass.getText().toString().trim();
                String mob = mMob.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String add = mAddress.getText().toString().trim();

                final boolean checkStud = db.checkStudent(regno);

                if (checkStud == false) {
                    long val = db.addStudent(regno, name, std, parent, dob, blood, pass, mob, email, add);
                    if (val <= 0) {
                        Toast.makeText(AddStudent.this, "Error in adding student!", Toast.LENGTH_SHORT).show();
                        Intent moveToAdmin = new Intent(AddStudent.this, AdminActivity.class);
                        startActivity(moveToAdmin);
                    } else {
                        Toast.makeText(AddStudent.this, "Student added!", Toast.LENGTH_SHORT).show();
                        sendMessage(regno,name,std,parent,dob,blood,pass,mob,email,add);
                        Intent moveToAdmin = new Intent(AddStudent.this, AdminActivity.class);
                        startActivity(moveToAdmin);
                    }
                }
                else{
                    boolean updateStud = db.updateStudent(regno, name, std, parent, dob, blood, pass, mob, email, add);
                    if (updateStud == true) {
                        Toast.makeText(AddStudent.this, "Error in updating student!", Toast.LENGTH_SHORT).show();
                        Intent moveToAdmin = new Intent(AddStudent.this, AdminActivity.class);
                        startActivity(moveToAdmin);
                    } else {
                        Toast.makeText(AddStudent.this, "Student updated!", Toast.LENGTH_SHORT).show();
                        sendMessage1(regno,name,std,parent,dob,blood,pass,mob,email,add);
                        Intent moveToAdmin = new Intent(AddStudent.this, AdminActivity.class);
                        startActivity(moveToAdmin);
                    }
                }

            }
        });
    }

    private void sendMessage(final String regno,final String name,final String std,final String parent, final String dob,
                             final String blood,final String pass,final String mob, final String email,final String add)
    {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please wait");
        dialog.show();
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("", "");
                    sender.sendMail("Parent Portal - Student Details", "Login to our parent portal app - \nRegistration Number: " + regno + "\n" + "Password: " + pass +
                            "\n\nStudent name - "+name+"\nStandard -"+std+"\nParent Name - "+parent+"\nDate of Birth - "+dob+"\nBlood Group - "+blood+
                            "\nAddress - "+add+"\nE-Mail - "+email+"\nPhone Number - "+mob, "srks2999@gmail.com", email);
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
    }

    private void sendMessage1(final String regno,final String name,final String std,final String parent, final String dob,
                             final String blood,final String pass,final String mob, final String email,final String add)
    {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please wait");
        dialog.show();
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("", "");
                    sender.sendMail("Parent Portal - Updated Details", "Login to our parent portal app - \nRegistration Number: " + regno + "\n" + "Password: " + pass +
                            "\n\nStudent name - "+name+"\nStandard -"+std+"\nParent Name - "+parent+"\nDate of Birth - "+dob+"\nBlood Group - "+blood+
                            "\nAddress - "+add+"\nE-Mail - "+email+"\nPhone Number - "+mob, "srks2999@gmail.com", email);
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
    }

}