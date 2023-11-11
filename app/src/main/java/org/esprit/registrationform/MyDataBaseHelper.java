package org.esprit.registrationform;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME= "Pet.db";
    private static final int DATABASE_VERSION= 4;
    private static final String TABLE_NAME= "my_Product";
    private static final String COLUMN_ID="_id";
    private static final String COLUMN_TITLE="product_title";
    private static final String COLUMN_DESCRIPTION="Description";
    private static final String COLUMN_PRICE= "price";

    public MyDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
        this.context= context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COLUMN_TITLE + " TEXT, " + COLUMN_DESCRIPTION + " TEXT, " + COLUMN_PRICE + " TEXT);";
        db.execSQL(query);
        // salma
        String query2 = "CREATE TABLE users (username TEXT PRIMARY KEY, password TEXT);";
        db.execSQL(query2);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
        //salma
        db.execSQL("drop Table if exists users");
    }

// salma
public Boolean insertData(String username, String password){
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues= new ContentValues();
    contentValues.put("username", username);
    contentValues.put("password", password);
    long result = db.insert("users", null, contentValues);
    if(result==-1) return false;
    else
        return true;
}
//salma
    public Boolean checkusername(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    //salma
    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from users where username = ? and password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }
    // salma
    public Boolean checkEmailExists(String email) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{email});
        return cursor.getCount() > 0;
    }

    // salma
    public void updatePassword(String email, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", newPassword);
        db.update("users", contentValues, "username = ?", new String[]{email});
    }
    void  addProduct(String title, String description, String price){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues(); //store our data from our application
        cv.put(COLUMN_TITLE,title );
        cv.put(COLUMN_DESCRIPTION,description );
        cv.put(COLUMN_PRICE,price );
        Long result = db.insert(TABLE_NAME,null, cv);

        if(result==-1){
            Toast.makeText(context, "failed", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "added successfully", Toast.LENGTH_SHORT).show();
        }
    }
    Cursor readAlldata(){
        String query= "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= null;
        if(db != null){
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
}
