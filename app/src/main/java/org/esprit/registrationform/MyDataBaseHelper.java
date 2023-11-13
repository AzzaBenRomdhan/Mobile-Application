package org.esprit.registrationform;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyDataBaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME= "Pet.db";
    private static final int DATABASE_VERSION= 55;
    private static final String TABLE_NAME= "my_Product";
    //sirirne
    public static final String TABLE_NAME_PET = "add_pet";
    private static final String COLUMN_ID="_id";
    private static final String COLUMN_TITLE="product_title";
    private static final String COLUMN_DESCRIPTION="Description";
    private static final String COLUMN_PRICE= "price";
// farouk
// Table pour les réservations
public static final String TABLE_NAME_RESERVATION = "add_reservation";
    public static final String COLUMN_RESERVATION_ID = "_id";
   // public static final String COLUMN_PET_NAME = "PetName";
    public static final String COLUMN_RESERVATION_DATE_DEBUT = "startDate";
    public static final String COLUMN_RESERVATION_DATE_FIN = "endDate";
    //   public static final String COLUMN_RESERVATION_TIME = "ReservationTime";
    public static final String COLUMN_DAYCARE_NAME = "DaycareName";
    public static final String COLUMN_DAYCARE_DESCRIPTION = "DaycareDescription";
    public static final String COLUMN_DAYCARE_PRICE = "DaycarePrice";

    //
    //farouk
// Table pour les réservations
    public static final String TABLE_NAME_DAYCARE_RESERVATION = "daycare_reservations";
    public static final String COLUMN_DAYCARE_ID = "_id";
    public static final String COLUMN_DAYCARE1_NAME = "daycare_name";
    public static final String COLUMN_PET_NAME_1 = "pet_name";
    public static final String COLUMN_START_DATE = "start_date";
    public static final String COLUMN_END_DATE = "end_date";
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
    //salma
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";


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
        String query2 = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_EMAIL + " TEXT);";
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
        //farouk
        // Ajouter cette partie pour créer la table des réservations
        String queryReservation = "CREATE TABLE " + TABLE_NAME_RESERVATION + " (" +
                COLUMN_RESERVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                //   COLUMN_PET_NAME + " TEXT, " +
                //   COLUMN_RESERVATION_DATE + " TEXT, " +
                //    COLUMN_RESERVATION_TIME + " TEXT, " +
                COLUMN_DAYCARE_NAME + " TEXT, " +
                COLUMN_DAYCARE_DESCRIPTION + " TEXT, " +
                COLUMN_DAYCARE_PRICE + " REAL);";
        db.execSQL(queryReservation);
        //farouk
        // Ajouter cette partie pour créer la table des réservations de garderie
        String queryDaycareReservation = "CREATE TABLE " + TABLE_NAME_DAYCARE_RESERVATION + " (" +
                COLUMN_RESERVATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_DAYCARE_NAME + " TEXT, " +
                COLUMN_PET_NAME_1 + " TEXT, " +
                COLUMN_START_DATE + " TEXT, " +
                COLUMN_END_DATE + " TEXT);";
        db.execSQL(queryDaycareReservation);
    }




    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        //salma
        db.execSQL("drop Table if exists users");
        //sirine
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_PET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_COMMENTAIRE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_RESERVATION);
        onCreate(db);
    }

// salma
public boolean insertData(String username, String password, String email) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues contentValues = new ContentValues();
    contentValues.put(COLUMN_USERNAME, username);
    contentValues.put(COLUMN_PASSWORD, password);
    contentValues.put(COLUMN_EMAIL, email);
    return db.insert(TABLE_USERS, null, contentValues) != -1;
}

//salma

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
    // Function to delete a user account based on the username
    public void deleteAccount(String username) {
        Log.d("DeleteAccount", "Deleting account from database: " + username);  // Add this log statement

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("users", "username=?", new String[]{username});
        db.close();
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
    //salma
    Cursor readAlldataUser() {
        String query = "SELECT * FROM " + TABLE_USERS;
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
    //salma
    // Update user information in the database
    public void updateProfile(String currentUsername, String newName, String newMail, String newPwd) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, newName);
        values.put(COLUMN_EMAIL, newMail);
        values.put(COLUMN_PASSWORD, newPwd);

        String whereClause = COLUMN_USERNAME + "=?";
        String[] whereArgs = {currentUsername}; // Use the current username as the selection argument

        // Update the user in the database
        int rowsAffected = db.update(TABLE_USERS, values, whereClause, whereArgs);

        // Check if the update was successful
        if (rowsAffected > 0) {
            Toast.makeText(context, "User updated successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Failed to update user", Toast.LENGTH_SHORT).show();
        }

        db.close();
    }


    //farouk
public void addDaycareReservation(String daycareName, String daycareDescription, double daycarePrice) {
    SQLiteDatabase db = this.getWritableDatabase();
    ContentValues cv = new ContentValues();
    //  cv.put(COLUMN_PET_NAME, petName);
    //   cv.put(COLUMN_RESERVATION_DATE, reservationDate);
    // cv.put(COLUMN_RESERVATION_TIME, reservationTime);
    cv.put(COLUMN_DAYCARE_NAME, daycareName);
    cv.put(COLUMN_DAYCARE_DESCRIPTION, daycareDescription);
    cv.put(COLUMN_DAYCARE_PRICE, daycarePrice);
    long result = db.insert(TABLE_NAME_RESERVATION, null, cv);
    if (result == -1) {
        Toast.makeText(context, "Failed to add daycare reservation", Toast.LENGTH_SHORT).show();
    } else {
        Toast.makeText(context, "Daycare reservation added successfully", Toast.LENGTH_SHORT).show();
    }
}
    // Function to add daycare reservation to the database
    public void addDaycare(String dayCare, String animalName, String startDate, String endDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_DAYCARE_NAME, dayCare);
        cv.put(COLUMN_PET_NAME_1, animalName);
        cv.put(COLUMN_START_DATE, startDate);
        cv.put(COLUMN_END_DATE, endDate);

        // Add the reservation to the database
        long result = db.insert(TABLE_NAME_DAYCARE_RESERVATION, null, cv);

        // Check if the operation was successful
        if (result == -1) {

        } else {
            Toast.makeText(context, "Daycare reservation added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAlldataDayCare() {
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(query, null);
        }
        return cursor;
    }
    public Cursor getAllReservations() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query(TABLE_NAME_RESERVATION, null, null, null, null, null, null);
    }

    // Méthode pour récupérer toutes les réservations de garderie
    public Cursor getAllDaycareReservations() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.query(TABLE_NAME_DAYCARE_RESERVATION, null, null, null, null, null, null);
    }


    public void updateReservation(int reservationId, String newDate, String newDayCareName) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();values.put(COLUMN_DAYCARE_NAME, newDayCareName);
        values.put(COLUMN_DAYCARE_PRICE, newDate);


        String whereClause = COLUMN_RESERVATION_ID + "=?";
        String[] whereArgs = {String.valueOf(reservationId)};

        // Assurez-vous d'utiliser le bon nom de table dans la méthode update
        db.update(TABLE_NAME_RESERVATION, values, whereClause, whereArgs);

        db.close();
    }



    // Méthode pour supprimer une réservation
    public void deleteReservation(int reservationId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_RESERVATION, COLUMN_RESERVATION_ID + "=?", new String[]{String.valueOf(reservationId)});
        db.close();
    }

    public ArrayList<String> getAllComments() {
        ArrayList<String> commentsList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        // Correction ici, utilisez le bon nom de table
        String selectQuery = "SELECT * FROM " + TABLE_NAME_COMMENTAIRE;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // Parcourir le curseur et ajouter les commentaires à la liste
        if (cursor.moveToFirst()) {
            do {
                // Correction ici, utilisez la bonne colonne
                String comment = cursor.getString(cursor.getColumnIndex(COLUMN_COMMENTAIRE_TEXT));
                commentsList.add(comment);
            } while (cursor.moveToNext());
        }

        // Fermer le curseur
        cursor.close();

        // Fermer la base de données
        db.close();

        return commentsList;
    }
    public void deleteComment(String comment) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Utilisez la méthode delete avec la clause where directement
        int rowsDeleted = db.delete(TABLE_NAME_COMMENTAIRE, COLUMN_COMMENTAIRE_TEXT + "=?", new String[]{comment});

        // Ajoutez des journaux pour déboguer
        Log.d("MyDataBaseHelper", "Deleting comment: " + comment);
        Log.d("MyDataBaseHelper", "Rows deleted: " + rowsDeleted);

        db.close();
    }
    public void updateComment(String oldComment, String newComment) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_COMMENTAIRE_TEXT, newComment);

        String whereClause = COLUMN_COMMENTAIRE_TEXT + "=?";
        String[] whereArgs = {oldComment};

        // Utilisez la méthode update pour mettre à jour le commentaire
        int rowsUpdated = db.update(TABLE_NAME_COMMENTAIRE, values, whereClause, whereArgs);

        // Ajoutez des journaux pour déboguer
        Log.d("MyDataBaseHelper", "Updating comment: " + oldComment + " to " + newComment);
        Log.d("MyDataBaseHelper", "Rows updated: " + rowsUpdated);

        db.close();
    }


}


