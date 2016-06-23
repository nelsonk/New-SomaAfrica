package com.love.soma.somaafrica.provider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import com.love.soma.somaafrica.activity.SchoolsDB;
import com.love.soma.somaafrica.adapter.Schools;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Neli'e on 19/02/2016.
 */
public class SchoolsDBProvider extends ContentProvider {


        private SchoolsDB database;
        // used for the UriMacher
        private static final int SCHOOLS = 10;
        private static final int SCHOOL_ID = 20;

    private static final int EVENTS = 30;
    private static final int EVENTS_ID = 40;

        private static final String AUTHORITY = "com.love.soma.somaafrica.provider";

        private static final String BASE_PATH = "schools";
        public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY
                + "/" + BASE_PATH);

    private static final String EVENTS_PATH = "events";
    public static final Uri EVENTS_CONTENT_URI = Uri.parse("content://" + AUTHORITY
            + "/" + EVENTS_PATH);

        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/schools";
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/school";

        private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        static {
            sURIMatcher.addURI(AUTHORITY, BASE_PATH, SCHOOLS);
            sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", SCHOOL_ID);

            sURIMatcher.addURI(AUTHORITY, EVENTS_PATH, EVENTS);
            sURIMatcher.addURI(AUTHORITY, EVENTS_PATH + "/#", EVENTS_ID);

        }

        @Override
        public boolean onCreate() {
            database = new SchoolsDB(getContext());
            return false;
        }

        @Override
        public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

            // Uisng SQLiteQueryBuilder instead of query() method
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

            // check if the caller has requested a column which does not exists
            checkColumns(projection);



            int uriType = sURIMatcher.match(uri);

            switch (uriType) {
                case SCHOOLS:
                    // Set the table
                    queryBuilder.setTables(SchoolsDB.TABLE_SCHOOLS);
                    break;
                case SCHOOL_ID:
                    // adding the ID to the original query
                    queryBuilder.appendWhere(SchoolsDB.COLUMN_ID + "="
                            + uri.getLastPathSegment());
                    break;

                case EVENTS:
                    // Set the table
                    queryBuilder.setTables(SchoolsDB.TABLE_EVENTS);
                    break;
                case EVENTS_ID:
                    // adding the ID to the original query
                    queryBuilder.appendWhere(SchoolsDB.EVENTS_ID + "="
                            + uri.getLastPathSegment());
                    break;

                default:
                    throw new IllegalArgumentException("Unknown URI: " + uri);
            }

            SQLiteDatabase db = database.getWritableDatabase();
            Cursor cursor = queryBuilder.query(db, projection, selection,
                    selectionArgs, null, null, sortOrder);
            // make sure that potential listeners are getting notified
            cursor.setNotificationUri(getContext().getContentResolver(), uri);

            return cursor;


        }

        private void checkColumns(String[] projection) {
            String[] available = { SchoolsDB.COLUMN_NAME,
                    SchoolsDB.COLUMN_STATUS, SchoolsDB.COLUMN_MOTTO, SchoolsDB.COLUMN_ABOUT, SchoolsDB.COLUMN_LOGO, SchoolsDB.COLUMN_ID,
                    SchoolsDB.COLUMN_LEVEL, SchoolsDB.COLUMN_FEES, SchoolsDB.COLUMN_FEESNAME, SchoolsDB.COLUMN_REQUIREMENT, SchoolsDB.COLUMN_REQUIREMENTNAME, SchoolsDB.EVENTS_ID, SchoolsDB.EVENTS_SCH, SchoolsDB.EVENTS_NAME, SchoolsDB.EVENTS_DATE, SchoolsDB.EVENTS_DETAILS
            };
            if (projection != null) {
                HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
                HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
                // check if all columns which are requested are available
                if (!availableColumns.containsAll(requestedColumns)) {
                    throw new IllegalArgumentException("Unknown columns in projection");
                }
            }
        }

        @Override
        public String getType(Uri uri) {
            return null;
        }

        @Override
        public Uri insert(Uri uri, ContentValues values) {
            Uri results= Uri.parse("");

            int uriType = sURIMatcher.match(uri);
            SQLiteDatabase sqlDB = database.getWritableDatabase();
            int rowsDeleted = 0;
            long id = 0;
            switch (uriType) {
                case SCHOOLS:
                    id = sqlDB.insert(SchoolsDB.TABLE_SCHOOLS, null, values);
                    results = Uri.parse(BASE_PATH + "/" + id);
                    break;
                case EVENTS:
                    id = sqlDB.insert(SchoolsDB.TABLE_EVENTS, null, values);
                    results = Uri.parse(EVENTS_PATH + "/" + id);
                    break;

                default:
                    throw new IllegalArgumentException("Unknown URI: " + uri);
            }

            getContext().getContentResolver().notifyChange(uri, null);
            return results;

        }

        @Override
        public int delete(Uri uri, String selection, String[] selectionArgs) {

            int uriType = sURIMatcher.match(uri);
            SQLiteDatabase sqlDB = database.getWritableDatabase();
            int rowsDeleted = 0;
            switch (uriType) {
                case SCHOOLS:
                    rowsDeleted = sqlDB.delete(SchoolsDB.TABLE_SCHOOLS, selection,
                            selectionArgs);
                    break;
                case SCHOOL_ID:
                    String id = uri.getLastPathSegment();
                    if (TextUtils.isEmpty(selection)) {
                        rowsDeleted = sqlDB.delete(SchoolsDB.TABLE_SCHOOLS,
                                SchoolsDB.COLUMN_ID + "=" + id,
                                null);
                    } else {
                        rowsDeleted = sqlDB.delete(SchoolsDB.TABLE_SCHOOLS,
                                SchoolsDB.COLUMN_ID + "=" + id
                                        + " and " + selection,
                                selectionArgs);
                    }
                    break;
                case EVENTS:
                    rowsDeleted = sqlDB.delete(SchoolsDB.TABLE_EVENTS, selection,
                            selectionArgs);
                    break;
                case EVENTS_ID:
                    String ids = uri.getLastPathSegment();
                    if (TextUtils.isEmpty(selection)) {
                        rowsDeleted = sqlDB.delete(SchoolsDB.TABLE_EVENTS,
                                SchoolsDB.EVENTS_ID + "=" + ids,
                                null);
                    } else {
                        rowsDeleted = sqlDB.delete(SchoolsDB.TABLE_EVENTS,
                                SchoolsDB.EVENTS_ID + "=" + ids
                                        + " and " + selection,
                                selectionArgs);
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Unknown URI: " + uri);
            }
            getContext().getContentResolver().notifyChange(uri, null);
            return rowsDeleted;


        }

        @Override
        public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

            int uriType = sURIMatcher.match(uri);
            SQLiteDatabase sqlDB = database.getWritableDatabase();
            int rowsUpdated = 0;
            switch (uriType) {
                case SCHOOLS:
                    rowsUpdated = sqlDB.update(SchoolsDB.TABLE_SCHOOLS,
                            values,
                            selection,
                            selectionArgs);
                    break;
                case SCHOOL_ID:
                    String id = uri.getLastPathSegment();
                    if (TextUtils.isEmpty(selection)) {
                        rowsUpdated = sqlDB.update(SchoolsDB.TABLE_SCHOOLS,
                                values,
                                SchoolsDB.COLUMN_ID + "=" + id,
                                null);
                    } else {
                        rowsUpdated = sqlDB.update(SchoolsDB.TABLE_SCHOOLS,
                                values,
                                SchoolsDB.COLUMN_ID + "=" + id
                                        + " and "
                                        + selection,
                                selectionArgs);
                    }
                    break;

                case EVENTS:
                    rowsUpdated = sqlDB.update(SchoolsDB.TABLE_EVENTS,
                            values,
                            selection,
                            selectionArgs);
                    break;
                case EVENTS_ID:
                    String ids = uri.getLastPathSegment();
                    if (TextUtils.isEmpty(selection)) {
                        rowsUpdated = sqlDB.update(SchoolsDB.TABLE_EVENTS,
                                values,
                                SchoolsDB.EVENTS_ID + "=" + ids,
                                null);
                    } else {
                        rowsUpdated = sqlDB.update(SchoolsDB.TABLE_EVENTS,
                                values,
                                SchoolsDB.EVENTS_ID + "=" + ids
                                        + " and "
                                        + selection,
                                selectionArgs);
                    }
                    break;

                default:
                    throw new IllegalArgumentException("Unknown URI: " + uri);
            }
            getContext().getContentResolver().notifyChange(uri, null);
            return rowsUpdated;


        }



}
