package org.esprit.registrationform;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.Arrays;
import java.util.List;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME= "Pet.db";
    private static final int DATABASE_VERSION= 50;
    private static final String TABLE_NAME= "my_Product";
    //sirirne
    public static final String TABLE_NAME_PET = "add_pet";
    private static final String COLUMN_ID="_id";
    private static final String COLUMN_TITLE="product_title";
    private static final String COLUMN_DESCRIPTION="Description";
    private static final String COLUMN_PRICE= "price";

    //sirine
    // Nouvelle table comment
    public static final String TABLE_NAME_COMMENTAIRE = "add_commentaire";
    public static final String COLUMN_COMMENTAIRE_ID = "_id";
    public static final String COLUMN_COMMENTAIRE_TEXT = "CommentaireText";
// Ajoutez d'autres colonnes au besoin

    public static final String COLUMN_ID_PET = "_id";
    public static final String COLUMN_NAME = "PetName";
    public static final String COLUMN_BREED = "BreedName";
    public static final String COLUMN_GENDERPET = "Gender";
    public static final String COLUMN_AGETPET = "Age";
    public static final String COLUMN_COLORPET = "Color";
    public static final String COLUMN_HEIGHTPET = "Height";
    public static final String COLUMN_WEIGHTPET = "Weight";

    public MyDataBaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
        this.context= context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query= "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COLUMN_TITLE + " TEXT, " + COLUMN_DESCRIPTION + " TEXT, " + COLUMN_PRICE + " TEXT );";
        db.execSQL(query);
        // salma
        String query2 = "CREATE TABLE users (username TEXT PRIMARY KEY, password TEXT);";
        db.execSQL(query2);
        //Sirine
        String query1 = "CREATE TABLE " + TABLE_NAME_PET + " (" + COLUMN_ID_PET + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_BREED + " TEXT, " +
                COLUMN_GENDERPET + " TEXT, " +
                COLUMN_AGETPET + " INTEGER, " +
                COLUMN_COLORPET + " TEXT, " +
                COLUMN_HEIGHTPET + " INTEGER, " +
                COLUMN_WEIGHTPET + " INTEGER);";
        db.execSQL(query1);

        // Ajoutez cette partie pour créer la nouvelle table
        String queryCommentaire = "CREATE TABLE " + TABLE_NAME_COMMENTAIRE + " (" +
                COLUMN_COMMENTAIRE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_COMMENTAIRE_TEXT + " TEXT);";
        db.execSQL(queryCommentaire);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        //salma
        db.execSQL("drop Table if exists users");
        //sirine
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_COMMENTAIRE);
        onCreate(db);
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

    //Sirine
    void addPet(String name, String breed, String gender, int age, String color, int height, int weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_BREED, breed);
        cv.put(COLUMN_GENDERPET, gender);
        cv.put(COLUMN_AGETPET, age);
        cv.put(COLUMN_COLORPET, color);
        cv.put(COLUMN_HEIGHTPET, height);
        cv.put(COLUMN_WEIGHTPET, weight);
        long result = db.insert(TABLE_NAME_PET, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Failed to add pet", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Pet added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    void addCommentaire(String commentaireText) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Vérifier la présence de gros mots
        if (containsProfanity(commentaireText)) {
            Toast.makeText(context, "Comment contains bad words. Please use appropriate language.", Toast.LENGTH_SHORT).show();
        } else {
            ContentValues cv = new ContentValues();
            cv.put(COLUMN_COMMENTAIRE_TEXT, commentaireText);
            long result = db.insert(TABLE_NAME_COMMENTAIRE, null, cv);
            if (result == -1) {
                Toast.makeText(context, "Comment added successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Comment added successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }
    // Méthode pour vérifier la présence de gros mots dans un texte
    private boolean containsProfanity(String text) {
        // Liste de mots interdits (gros mots)
        List<String> profanityList = Arrays.asList("caca", "pipi", "loser");

        for (String profanity : profanityList) {
            if (text.toLowerCase().contains(profanity.toLowerCase())) {
                return true; // Gros mot détecté
            }
        }
        return false; // Aucun gros mot détecté
    }

    Cursor readAlldataPET() {
        String query = "SELECT * FROM " + TABLE_NAME_PET;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    Cursor readAllCommentaires() {
        String query = "SELECT * FROM " + TABLE_NAME_COMMENTAIRE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }

    public void deletePet(int petId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = COLUMN_ID_PET + " = ?";
        String[] whereArgs = {String.valueOf(petId)};
        db.delete(TABLE_NAME_PET, whereClause, whereArgs);
        db.close();
    }
    public void updatePet(int petId, String newName, String newBreed) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, newName);
        values.put(COLUMN_BREED, newBreed);

        db.update(TABLE_NAME_PET, values, COLUMN_ID_PET + "=?", new String[]{String.valueOf(petId)});

        db.close();
    }


}