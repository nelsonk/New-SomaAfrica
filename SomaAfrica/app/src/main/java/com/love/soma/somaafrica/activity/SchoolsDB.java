package com.love.soma.somaafrica.activity;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Neli'e on 19/02/2016.
 */
public class SchoolsDB extends SQLiteOpenHelper {

        private static final int DATABASE_VERSION = 10;
        public static final String TABLE_SCHOOLS = "schools";
        public static final String DATABASE_NAME = "admission.db";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "school";
        public static final String COLUMN_MOTTO = "motto";
        public static final String COLUMN_ABOUT = "aboutUS";
        public static final String COLUMN_STATUS = "status";
        public static final String COLUMN_LOGO = "logo";
        public static final String COLUMN_LEVEL = "level";
        public static final String COLUMN_FEES = "fees";
        public static final String COLUMN_FEESNAME = "fees_name";
        public static final String COLUMN_REQUIREMENT = "requirement";
        public static final String COLUMN_REQUIREMENTNAME = "requirement_name";


        //events table variables
        public static final String TABLE_EVENTS = "events";
        public static final String EVENTS_ID = "_id";
        public static final String EVENTS_SCH = "school";
        public static final String EVENTS_NAME = "eventsName";
        public static final String EVENTS_DATE = "eventsDate";
        public static final String EVENTS_DETAILS = "eventsDetails";




        //private static final String CREATE_TABLE_LOGIN ="CREATE TABLE " + TABLE_LOGIN + "(" + LOGIN_ID + " INTEGER PRIMARY KEY, " + LOGIN_NAME + " TEXT, " + LOGIN_PASSWORD + " TEXT)";
        private static final String CREATE_TABLE_SCHOOLS = "CREATE TABLE " + TABLE_SCHOOLS + "(" + COLUMN_ID + " INTEGER PRIMARY KEY, " + COLUMN_NAME + " TEXT, " + COLUMN_MOTTO + " TEXT," + COLUMN_ABOUT + " TEXT, " + COLUMN_STATUS + " TEXT, " + COLUMN_LOGO + " INTEGER, " + COLUMN_LEVEL + " TEXT, " + COLUMN_FEES + " INTEGER," + COLUMN_FEESNAME + " TEXT," + COLUMN_REQUIREMENT + " INTEGER, " + COLUMN_REQUIREMENTNAME + " TEXT)";
        private static final String CREATE_TABLE_EVENTS = "CREATE TABLE " + TABLE_EVENTS + "(" + EVENTS_ID + " INTEGER PRIMARY KEY, " + EVENTS_SCH + " TEXT, " + EVENTS_NAME + " TEXT, " + EVENTS_DATE + " TEXT, " + EVENTS_DETAILS + " TEXT)";
        //private static final String CREATE_TABLE_COURSE = "CREATE TABLE " + TABLE_COURSE + "(" + COURSE_ID + " INTEGER PRIMARY KEY, " + COURSE_SCHNAME + " TEXT, " + COURSE_COURSETYPE + " TEXT, " + COURSE_COMBINATION + " TEXT, " + COURSE_CUTOFF + " TEXT)";
        //private static final String CREATE_TABLE_REGISTRATION = "CREATE TABLE " + TABLE_REGISTRATION + "(" + REGISTRATION_ID + " INTEGER PRIMARY KEY, " + REGISTRATION_SCHNAME + " TEXT, " + REGISTRATION_COURSETYPE + " TEXT, " + REGISTRATION_COMBINATION + " TEXT, " + REGISTRATION_USERNAME + " TEXT, " + REGISTRATION_REF_ID + " TEXT, " + REGISTRATION_STATUS + " TEXT)";




        public SchoolsDB(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db.execSQL(CREATE_TABLE_SCHOOLS);
            db.execSQL(CREATE_TABLE_EVENTS);
            //db.execSQL(CREATE_TABLE_SIGNUP);
           // db.execSQL(CREATE_TABLE_COURSE);
            //db.execSQL(CREATE_TABLE_REGISTRATION);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCHOOLS);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
            //db.execSQL("DROP TABLE IF EXISTS " + TABLE_SIGNUP);
            //db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSE);
           // db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTRATION);
            onCreate(db);

        }

}
