package com.itbg.calllogresolver;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.widget.Toast;

import org.jetbrains.annotations.Nullable;

@SuppressLint("MissingPermission")
public class CallLogChangeObserverClass extends ContentObserver
{
    Context ctx;
    ContentResolver mContentResolver;


    public CallLogChangeObserverClass(Handler handler, Context ct) {
        super(handler);
        ctx = ct;
        mContentResolver = ct.getContentResolver();

        updateCallLogNames(false);
    }

    public void onChange(boolean selfChange) {

        if (selfChange)
            return;

        updateCallLogNames(true);
    }

    private void updateCallLogNames(boolean onlyNewEntries) {

        String[] projections = new String[]{
                CallLog.Calls._ID,
                CallLog.Calls.CACHED_NAME,
                CallLog.Calls.NUMBER,
                CallLog.Calls.CACHED_FORMATTED_NUMBER,
                CallLog.Calls.CACHED_MATCHED_NUMBER,
                CallLog.Calls.CACHED_NORMALIZED_NUMBER
        };

        // Get all NEW records from CALL_LOG without name (number showing)
        Cursor c =  mContentResolver.query(CallLog.Calls.CONTENT_URI, projections,
                CallLog.Calls.CACHED_NAME + " is null"+( onlyNewEntries ? " and " + CallLog.Calls.NEW + " != 0" : ""),
                null, CallLog.Calls.DATE + " DESC");
        if(c == null)
            return;

        while (c.moveToNext()) {
            for(int i = 2; i < c.getColumnCount(); i++) {
                String number = c.getString(i);
                if(number != null && number.length() > 0) {
                    number = number.substring(number.length()-7);
                    String name = nameLookupQuery(number);
                    //Toast.makeText(ctx.getApplicationContext(), number + " : " + name, Toast.LENGTH_LONG).show();
                    if(name != null && name.length() > 0) {
                        ContentValues values = new ContentValues();
                        values.put(CallLog.Calls.CACHED_NAME,name);
                        mContentResolver.update(CallLog.Calls.CONTENT_URI, values, CallLog.Calls._ID + "=?", new String[] {c.getString(0)});
                        break;
                    }
                }
            }
        }

        c.close();
    }

    private static final String[] PROJECTION = { ContactsContract.PhoneLookup.DISPLAY_NAME };
    private static final int DISPLAY_NAME_COLUMN_INDEX = 0;

    @Nullable
    public String nameLookupQuery(@Nullable String number) {
//            if (!PermissionsUtil.hasPermission(mContext, Manifest.permission.READ_CONTACTS)) {
//                Log.w(TAG, "No READ_CONTACTS permission, returning null for name lookup.");
//                return null;
//            }
        try (Cursor cursor =  mContentResolver.query(
                Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(number)),
                PROJECTION, null, null, null)) {
            if (cursor == null || !cursor.moveToFirst()) {
                return null;
            }
            return cursor.getString(DISPLAY_NAME_COLUMN_INDEX);
        } catch (RuntimeException e) {
            return null;
        }
    }
}