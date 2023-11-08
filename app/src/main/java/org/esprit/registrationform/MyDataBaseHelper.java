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
    private static final int DATABASE_VERSION= 1;
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
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
