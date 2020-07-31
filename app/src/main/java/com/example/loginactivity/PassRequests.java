package com.example.loginactivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class PassRequests extends AppCompatActivity {
    DatabaseHelper mydb;
    TableLayout tl;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pass_requests);
        mydb =new DatabaseHelper(this);
        final Cursor res = mydb.getAllPassRequests();
        res.moveToFirst();
        final int count = res.getCount();
        if(count == 0)
        {
            Toast.makeText(this,"No Password Requests!",Toast.LENGTH_LONG).show();
        }
        else {
            tl = (TableLayout) findViewById(R.id.requests);
            TableRow row1 = new TableRow(this);
            TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            row1.setBackgroundColor(Color.TRANSPARENT);
            row1.setPadding(5,0,5,5);
            row1.setLayoutParams(lp1);

            TextView studid = new TextView(this);
            studid.setText(" ID ");
            studid.setBackgroundResource(R.drawable.cell_shape);
            studid.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(studid);

            TextView stname = new TextView(this);
            stname.setText(" Reg No ");
            stname.setBackgroundResource(R.drawable.cell_shape);
            stname.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(stname);

            TextView depart = new TextView(this);
            depart.setText(" Mail ");
            depart.setBackgroundResource(R.drawable.cell_shape);
            depart.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(depart);

            TextView dishonor= new TextView(this);
            dishonor.setText(" Phone ");
            dishonor.setBackgroundResource(R.drawable.cell_shape);
            dishonor.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(dishonor);

            TextView dishonor1= new TextView(this);
            dishonor1.setText(" APPROVE ");
            dishonor1.setBackgroundResource(R.drawable.cell_shape);
            dishonor1.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(dishonor1);

            tl.addView(row1);
            res.moveToFirst();
            do {
                TableRow row = new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                row.setBackgroundColor(Color.TRANSPARENT);
                row.setPadding(5,0,5,5);
                row.setLayoutParams(lp);

                TextView sid = new TextView(this);
                final String id = res.getString(0);
                sid.setText(res.getString(0));
                sid.setBackgroundResource(R.drawable.cell_shape);
                sid.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(sid);

                TextView sname = new TextView(this);
                sname.setText(res.getString(1));
                final String regno = res.getString(1);
              //  final String ename = res.getString(1);
                sname.setBackgroundResource(R.drawable.cell_shape);
                sname.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(sname);

                TextView dept = new TextView(this);
                dept.setText(res.getString(2));
                final String email = res.getString(2);
                dept.setBackgroundResource(R.drawable.cell_shape);
                dept.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(dept);

                TextView bm = new TextView(this);
                bm.setText(res.getString(3));
                final String mob = res.getString(3);
                bm.setBackgroundResource(R.drawable.cell_shape);
                bm.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(bm);

                TextView btn = new TextView(this);
                btn.setText(" SEND ");
                btn.setTextColor((getResources().getColor(R.color.redColor, null)));
                btn.setBackgroundResource(R.drawable.cell_shape);
                btn.setTextAppearance(android.R.style.TextAppearance_Medium);
                btn.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        final Cursor getPassword = mydb.getPass(regno,email,mob);
                        getPassword.moveToFirst();
                        int count = getPassword.getCount();
                        if (count>0){
                            String pass = getPassword.getString(0);
                            sendMessage(regno,pass,email);
                            Toast.makeText(PassRequests.this,"Password Sent!",Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(PassRequests.this,"Details Incorrect!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                row.addView(btn);

                tl.addView(row);
            }while(res.moveToNext());
        }


        TextView mTextViewLogin;
        mTextViewLogin = (TextView)findViewById(R.id.textview_back);
        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BackIntent = new Intent(PassRequests.this,AdminActivity.class);
                startActivity(BackIntent);
            }
        });


        res.close();

    }

    private void sendMessage(final String regno, final String pass, final String email) {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please wait");
        dialog.show();
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("", "");
                    sender.sendMail("Parent Portal Password", "Registration Number: " + regno + "\n" + "Password: " + pass, "srks2999@gmail.com", email);
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
    }

}

