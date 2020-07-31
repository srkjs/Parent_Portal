package com.example.loginactivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class ContactTeacher extends AppCompatActivity {

    String regno,name;
    Button send;
    EditText message;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_teacher);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        message = (EditText) findViewById(R.id.msg);
        send = (Button) findViewById(R.id.send);
        regno = getIntent().getStringExtra("rno");
        name = getIntent().getStringExtra("name");
        send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                    String mess = message.getText().toString();
                    if(message.getText().toString().isEmpty())
                    {
                        Toast.makeText(ContactTeacher.this,"Enter All fields!",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                           sendMessage(regno,mess,name);
                           Toast.makeText(ContactTeacher.this,"Message Sent!!",Toast.LENGTH_LONG).show();
                    }

            }
        });

        TextView mTextViewBack;
        mTextViewBack = (TextView)findViewById(R.id.textview_back);
        mTextViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BackIntent = new Intent(ContactTeacher.this, HomeActivity.class);
                BackIntent.putExtra("regno",regno);
                startActivity(BackIntent);
            }
        });

    }
    private void sendMessage(final String reg, final String msg, final String sname) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please wait");
        File file = new File(Environment.getExternalStorageDirectory().getAbsoluteFile()+"/folder/ebrec.pdf");
        Intent intent=new Intent(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        dialog.show();
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("", "");
                    sender.sendMail(regno+" - Parent Message","Student Name: "+ sname+"\n"+"Reg No: "+reg+"\n"+msg,"srks2999@gmail.com","srks2999@gmail.com");
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();

    }


}






