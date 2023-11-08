package org.esprit.registrationform.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public  int id ;
    @ColumnInfo
    public  String email ;
    @ColumnInfo
    public String pwd;

    public User() {
    }

    public User( String email, String pwd) {

        this.email = email;
        this.pwd = pwd;
    }
    // Add a constructor that takes three parameters
    public User(int id, String email, String pwd) {
        this.id = id;
        this.email = email;
        this.pwd = pwd;
    }
}
