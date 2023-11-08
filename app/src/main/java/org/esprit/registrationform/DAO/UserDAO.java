package org.esprit.registrationform.DAO;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.esprit.registrationform.entities.User;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    public long addUser (User u );

    @Query("SELECT * FROM User")
    List<User> getAll();

}
