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

public class CheckGrades extends AppCompatActivity {
    DatabaseHelper mydb;
    TableLayout tl;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_grades);
        mydb =new DatabaseHelper(this);
        final String regno = getIntent().getStringExtra("regno");
        
        TextView regid = (TextView) findViewById(R.id.regno);
        regid.setText(regno);

        final Cursor res = mydb.getGrades(regno);
        res.moveToFirst();
        final int count = res.getCount();
        if(count == 0)
        {
            Toast.makeText(this,"Grades not updated!",Toast.LENGTH_LONG).show();
        }
        else {
            tl = (TableLayout) findViewById(R.id.students);
            TableRow row1 = new TableRow(this);
            TableRow.LayoutParams lp1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
            row1.setBackgroundColor(Color.TRANSPARENT);
            row1.setPadding(6,0,6,6);
            row1.setLayoutParams(lp1);

            TextView studid = new TextView(this);
            studid.setText(" Portal ");
            studid.setBackgroundResource(R.drawable.cell_shape);
            studid.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(studid);

            TextView stname = new TextView(this);
            stname.setText(" LANG ");
            stname.setBackgroundResource(R.drawable.cell_shape);
            stname.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(stname);

            TextView depart = new TextView(this);
            depart.setText(" ENG ");
            depart.setBackgroundResource(R.drawable.cell_shape);
            depart.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(depart);

            TextView depart1 = new TextView(this);
            depart1.setText(" MAT ");
            depart1.setBackgroundResource(R.drawable.cell_shape);
            depart1.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(depart1);

            TextView depart2 = new TextView(this);
            depart2.setText(" SCI ");
            depart2.setBackgroundResource(R.drawable.cell_shape);
            depart2.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(depart2);

            TextView depart3 = new TextView(this);
            depart3.setText(" SOC ");
            depart3.setBackgroundResource(R.drawable.cell_shape);
            depart3.setTextAppearance(android.R.style.TextAppearance_Medium);
            row1.addView(depart3);

            tl.addView(row1);
            res.moveToFirst();
            do {
                TableRow row = new TableRow(this);
                TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);
                row.setBackgroundColor(Color.TRANSPARENT);
                row.setPadding(6,0,6,6);
                row.setLayoutParams(lp);

                TextView sid = new TextView(this);
                sid.setText(res.getString(0));
                sid.setBackgroundResource(R.drawable.cell_shape);
                sid.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(sid);

                TextView sname = new TextView(this);
                sname.setText(res.getString(1));
                sname.setBackgroundResource(R.drawable.cell_shape);
                sname.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(sname);

                TextView dept = new TextView(this);
                dept.setText(res.getString(2));
                dept.setBackgroundResource(R.drawable.cell_shape);
                dept.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(dept);

                TextView dept1 = new TextView(this);
                dept1.setText(res.getString(3));
                dept1.setBackgroundResource(R.drawable.cell_shape);
                dept1.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(dept1);

                TextView dept2 = new TextView(this);
                dept2.setText(res.getString(4));
                dept2.setBackgroundResource(R.drawable.cell_shape);
                dept2.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(dept2);

                TextView dept3 = new TextView(this);
                dept3.setText(res.getString(5));
                dept3.setBackgroundResource(R.drawable.cell_shape);
                dept3.setTextAppearance(android.R.style.TextAppearance_Medium);
                row.addView(dept3);

                tl.addView(row);
            }while(res.moveToNext());
        }


        TextView mTextViewBack;
        mTextViewBack = (TextView)findViewById(R.id.textview_back);
        mTextViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent BackIntent = new Intent(CheckGrades.this, HomeActivity.class);
                BackIntent.putExtra("regno",regno);
                startActivity(BackIntent);
            }
        });

        res.close();
    }

}

