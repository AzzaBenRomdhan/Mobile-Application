package org.esprit.registrationform.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.esprit.registrationform.entities.User;

import org.esprit.registrationform.entities.User;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    public void addUser (User u );

    @Query("SELECT * FROM User")
    List<User> getAll();

}
