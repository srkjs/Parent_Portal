package com.example.loginactivity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME ="register.db";
    public static final String TABLE_NAME_1 ="students";
    public static final String COL_1 ="STUID";
    public static final String COL_2 ="regno";
    public static final String COL_3 ="password";
    public static final String COL_4 ="ADMIN_ID";
    public static final String COL_5 ="user";
    public static final String COL_6 ="pass";
    public static final String COL_7 ="regno";
    public static final String TABLE_NAME_2 = "adminLogin";
    public static final String COL_10 ="ID";
    public static final String COL_11 ="regno";
    public static final String COL_12 ="portal";
    public static final String TABLE_NAME_3 ="attendance";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //Table 1
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE requestnewpassword (ID INTEGER PRIMARY  KEY AUTOINCREMENT, regno TEXT, email TEXT, mob TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE students (STUID INTEGER PRIMARY KEY AUTOINCREMENT,regno TEXT, name TEXT, std TEXT, parent TEXT, dob TEXT, blood TEXT, password TEXT,mob TEXT, email TEXT, address TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE attendance (ID INTEGER PRIMARY  KEY AUTOINCREMENT,regno TEXT, portal TEXT, att TEXT, total TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE marks (ID INTEGER PRIMARY  KEY AUTOINCREMENT,regno TEXT,portal TEXT,sub1 TEXT,sub2 TEXT,sub3 TEXT,sub4 TEXT,sub5 TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE schedule (regno TEXT, std TEXT, message TEXT)");
        sqLiteDatabase.execSQL("CREATE TABLE adminLogin (ADMIN_ID INTEGER PRIMARY  KEY AUTOINCREMENT, user TEXT, pass TEXT)");
        sqLiteDatabase.execSQL("INSERT INTO adminLogin (user,pass) VALUES ('admin','admin123')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS requestnewpassword");
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS students");
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS attendance");
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS marks");
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS messages");
        sqLiteDatabase.execSQL(" DROP TABLE IF EXISTS schedule");
        onCreate(sqLiteDatabase);
    }


    public long requestNewPassword(String regNo, String eMail, String mobPhone){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("regno", regNo);
        contentValues.put("email", eMail);
        contentValues.put("mob",mobPhone);
        long res = db.insert("requestnewpassword",null,contentValues);
        db.close();
        return  res;
    }

    public boolean checkStudent(String regno){
        String[] columns = { "STUID" };
        SQLiteDatabase db = getReadableDatabase();
        String selection = "regno=?";
        String[] selectionArgs = { regno };
        Cursor cursor = db.query("students",columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return  true;
        else
            return  false;
    }

    public boolean updateStudent(String regNo, String  name, String std, String parent, String dob, String blood, String password,String mob ,String email, String address){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("UPDATE students SET name='"+name+"',std='"+std+"',parent='"+parent+"',dob='"+dob+"',blood='"+blood+
                        "',password='"+password+"',mob="+mob+",email='"+email+"',address='"+address+"' WHERE regno="+regNo, null);
        int count = res.getCount();
        res.close();
        db.close();

        if(count>0)
            return  true;
        else
            return  false;
    }

    public long addStudent(String regNo, String  name, String std, String parent, String dob, String blood, String password,String mob ,String email, String address){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("regno", regNo);
        contentValues.put("name",name);
        contentValues.put("std",std);
        contentValues.put("parent",parent);
        contentValues.put("dob",dob);
        contentValues.put("blood",blood);
        contentValues.put("password",password);
        contentValues.put("mob",mob);
        contentValues.put("email", email);
        contentValues.put("address",address);
        long res = db.insert("students",null,contentValues);
        db.close();
        return  res;
    }

    public boolean checkStudentInAttendanceTab(String regno, String portal){
        String[] columns = { COL_10 };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_11 + "=?" + " and " + COL_12 + "=?";
        String[] selectionArgs = { regno,portal };
        Cursor cursor = db.query(TABLE_NAME_3,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if(count>0)
            return  true;
        else
            return  false;
    }



    public boolean updateAttendance(String regNo, String portal,String attendance, String total){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("UPDATE attendance SET att="+attendance+",total="+total+" WHERE regno="+regNo+" AND portal="+portal, null);
        int count = res.getCount();
        res.close();
        db.close();
        if(count>0)
            return  false;
        else
            return  true;
    }

    public long addAttendance(String regNo, String portal,String attendance, String total){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("regno", regNo);
        contentValues.put("portal", portal);
        contentValues.put("att",attendance);
        contentValues.put("total",total);
        long res = db.insert("attendance",null,contentValues);
        db.close();
        return  res;
    }

    public boolean checkStudentInMarksTab(String regno, String portal){
        String[] columns = { "ID" };
        SQLiteDatabase db = getReadableDatabase();
        String selection = "regno" + "=?" + " and " + "portal" + "=?";
        String[] selectionArgs = { regno,portal };
        Cursor cursor = db.query("marks",columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if(count>0)
            return  true;
        else
            return  false;
    }

    public boolean updateMarks(String regNo, String portal, String sub1, String sub2, String sub3, String sub4, String sub5){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("UPDATE marks SET sub1='"+sub1+"',sub2='"+sub2+"',sub3='"+sub3+"',sub4='"+sub4+"',sub5='"+sub5+"' WHERE regno="+regNo+" AND portal="+portal, null);
        int count = res.getCount();
        res.close();
        db.close();
        if(count>0)
            return  false;
        else
            return  true;
    }

    public long addMarks(String regNo, String portal, String sub1, String sub2, String sub3, String sub4, String sub5){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("regno", regNo);
        contentValues.put("portal", portal);
        contentValues.put("sub1", sub1);
        contentValues.put("sub2", sub2);
        contentValues.put("sub3", sub3);
        contentValues.put("sub4", sub4);
        contentValues.put("sub5", sub5);
        long res = db.insert("marks",null,contentValues);
        db.close();
        return  res;
    }


    public long addSchedules(String regNo, String std, String message){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("regno", regNo);
        contentValues.put("std", std);
        contentValues.put("message",message);
        long res = db.insert("schedule",null,contentValues);
        db.close();
        return  res;
    }


    public boolean checkUser(String username, String password){
        String[] columns = { COL_1 };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " + COL_3 + "=?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_NAME_1,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return  true;
        else
            return  false;
    }

    public boolean checkAdmin(String username, String password){
        String[] columns = { COL_4 };
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_5 + "=?" + " and " + COL_6 + "=?";
        String[] selectionArgs = { username, password };
        Cursor cursor = db.query(TABLE_NAME_2,columns,selection,selectionArgs,null,null,null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count>0)
            return  true;
        else
            return  false;
    }

    public Cursor getStudentData(String regno)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT regno,name,std,parent,mob,email,address FROM students WHERE regno="+regno,null);
        return res;
    }

    public Cursor getStudentDetails(String regno)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT name,std,parent,mob,email,address,dob,blood,password FROM students WHERE regno="+regno,null);
        return res;
    }

    public Cursor getAllPassRequests() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM requestnewpassword", null);
        return res;
    }

    public Cursor getAttendance(String regno) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT portal,att,total FROM attendance WHERE regno="+regno+" ORDER BY portal", null);
        return res;
    }

    public Cursor getGrades(String regno) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT portal,sub1,sub2,sub3,sub4,sub5 FROM marks WHERE regno="+regno+" ORDER BY portal", null);
        return res;
    }

    public Cursor getSchedule(String standard, String regno) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT message FROM schedule WHERE std='"+standard+"' OR regno="+regno, null);
        return res;
    }

    public Cursor getPass(String regno, String email, String mob)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT password FROM students WHERE regno="+regno+" AND email='"+email+"' AND mob="+mob,null);
        return res;
    }
}