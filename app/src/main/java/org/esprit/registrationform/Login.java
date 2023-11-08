package org.esprit.registrationform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import org.esprit.registrationform.database.Appdatabase;
import org.esprit.registrationform.entities.User;

import java.util.ArrayList;
import java.util.List;

public class Login extends AppCompatActivity {
    SharedPreferences myPref;
    public static final String PREF ="MyPref";
    Button save,clear ;
    EditText email , password ;
    private Appdatabase database ;
    private List<User> userList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        email =findViewById(R.id.email);
        password =findViewById(R.id.pwd);
        save =findViewById(R.id.buttonSave);
        clear =findViewById(R.id.buttonClear);
        // cree le fichier sharedpref
        myPref = getSharedPreferences(PREF,MODE_PRIVATE);
        // Manipuler le fichier
        SharedPreferences.Editor editor = myPref.edit();
        Intent intent = new Intent(this, Login2.class);

        if(myPref.contains("email") && myPref.contains("pwd")){
            email.setText(myPref.getString("email",""));
            password.setText(myPref.getString("pwd",""));
        }


        save.setOnClickListener(e->{
          /*   editor.putString("email",email.getText().toString());
             editor.putString("pwd",password.getText().toString());
             editor.commit();
*/
            Appdatabase.getInstance(this).userDAO().addUser(new User());

            startActivity(intent);
        });
        clear.setOnClickListener( e ->{

            editor.clear();
            editor.commit();
            email.setText("");
            password.setText("");
        });


    }
}