package com.example.loginactivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class CheckSchedule extends AppCompatActivity {
    DatabaseHelper mydb;
    TableLayout tl;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_schedule);
        mydb =new DatabaseHelper(this);
        final String regno = getIntent().getStringExtra("regno");
        final String standard=getIntent().getStringExtra("standard");

        TextView regid = (TextView) findViewById(R.id.regno);
        regid.setText(regno);


        final Cursor res = mydb.getSchedule(standard,regno);
        res.moveToFirst();
        final int count = res.getCount();
        if(count == 0)
        {
            Toast.makeText(this,"Schedule not updated!",Toast.LENGTH_LONG).show();
        }
        else {
            tl = (TableLayout) findViewById(R.id.students);
            TableRow row1 = new TableRow(this);
            TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            row1.setBackgroundColor(Color.TRANSPARENT);
            row1.setPadding(5,0,5,5);
            row1.setLayoutParams(lp1);

            TextView studid = new TextView(this);
            studid.setText("      Schedules      ");
            studid.setBackgroundResource(R.drawable.cell_shape);
            studid.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(studid);

            tl.addView(row1);
            res.moveToFirst();
            do {
                TableRow row = new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                row.setBackgroundColor(Color.TRANSPARENT);
                row.setPadding(5,0,5,5);
                row.setLayoutParams(lp);

                TextView sid = new TextView(this);
                sid.setText(res.getString(0));
                sid.setBackgroundResource(R.drawable.cell_shape);
                sid.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(sid);

                tl.addView(row);
            }while(res.moveToNext());
        }

        TextView mTextViewBack;
        mTextViewBack = (TextView)findViewById(R.id.textview_back);
        mTextViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BackIntent = new Intent(CheckSchedule.this, HomeActivity.class);
                BackIntent.putExtra("regno",regno);
                startActivity(BackIntent);
            }
        });

        res.close();
    }

}

