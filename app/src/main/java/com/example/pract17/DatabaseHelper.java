package com.example.pract17;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "User.db";
    private static final String TABLE_NAME = "Users";
    private static final String col_ID = "id";
    private static final String col_NAME = "name";
    private static final String col_EMAIL = "email";
    private static final String col_PROFILE_IMAGE_URL = "profile_image_url";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + col_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + col_NAME + " TEXT, "
                + col_EMAIL + " TEXT, "
                + col_PROFILE_IMAGE_URL + " TEXT)";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public void addUser(User user){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(col_NAME, user.getName());
        cv.put(col_EMAIL, user.getEmail());
        cv.put(col_PROFILE_IMAGE_URL, user.getProfileImageUrl());

        sqLiteDatabase.insert(TABLE_NAME, null, cv);
        sqLiteDatabase.close();
    }

    public void updateUser(User user) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(col_NAME, user.getName());
        cv.put(col_EMAIL, user.getEmail());
        cv.put(col_PROFILE_IMAGE_URL, user.getProfileImageUrl());

        sqLiteDatabase.update(TABLE_NAME, cv, col_ID + "=?", new String[]{String.valueOf(user.getId())});
        sqLiteDatabase.close();
    }

    public ArrayList<User> getUserArray() {
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        ArrayList<User> userList = new ArrayList<>();

        Cursor result = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        if (result != null) {
            while (result.moveToNext()) {
                int id = result.getInt(result.getColumnIndex(col_ID));
                String name = result.getString(result.getColumnIndex(col_NAME));
                String email = result.getString(result.getColumnIndex(col_EMAIL));
                String profileImageUrl = result.getString(result.getColumnIndex(col_PROFILE_IMAGE_URL));

                // Create User object and add it to the list
                User user = new User(id, name, email, profileImageUrl);
                userList.add(user);
            }
            result.close();
        }

        sqLiteDatabase.close();

        return userList;
    }

    public void deleteUser(int personId) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        String selection = col_ID + "=?";
        String[] selectionArgs = {String.valueOf(personId)};

        sqLiteDatabase.delete(TABLE_NAME, selection, selectionArgs);
        sqLiteDatabase.close();
    }
}

