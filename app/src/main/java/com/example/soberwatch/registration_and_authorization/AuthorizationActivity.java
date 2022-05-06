package com.example.soberwatch.registration_and_authorization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.soberwatch.DB_stuff.DB;
import com.example.soberwatch.DB_stuff.User;
import com.example.soberwatch.Functions_Class;
import com.example.soberwatch.Main.MainWindowActivity;
import com.example.soberwatch.R;

import java.sql.ResultSet;
import java.util.Set;

public class AuthorizationActivity extends AppCompatActivity {

    private EditText Email;
    private EditText Password;
    private Button sign_in;
    private Button sign_up;
    private TextView error_text;
    private String[] user_data = new String[2];
    private DB db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization);

        setupUI();
        setupListeners();
    }

    private void setupUI()
    {
        Email = findViewById(R.id.Email_id);
        Password = findViewById(R.id.Password_id);
        sign_in = findViewById(R.id.sign_in_id);
        sign_up = findViewById(R.id.sign_up_id);
        error_text = findViewById(R.id.error_id);

        db = new DB();
    }

    private void setupListeners()
    {
        sign_in.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    if (CheckDataEntered()) {
                        SetInputData();
                        if (CheckDataEntered()) {
                            User user = new User(Email.getText().toString(), Password.getText().toString());
                            ResultSet authorizated = db.logInUser(user);
                            if (authorizated == null)
                                error_text.setText("Wrong email or password!!!");
                            Intent intent = new Intent(AuthorizationActivity.this, MainWindowActivity.class);
                            intent.putExtra("id", String.valueOf(authorizated.getInt(1)));
                            startActivity(intent);
                        }
                    }
                } catch (Exception e){}
            }
        });
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(AuthorizationActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    protected void SetInputData()
    {
        user_data[0] = Email.getText().toString();
        user_data[1] = Password.getText().toString();
    }

    protected boolean CheckDataEntered()
    {
        boolean isValid = true;

        if(Functions_Class.isEmpty(Email)){
            Email.setError("You must enter email to login!");
            isValid = false;
        } else {
            if (!Functions_Class.isEmail(Email))
            {
                Email.setError("Enter valid email!");
                isValid = false;
            }
        }

        if(Functions_Class.isEmpty(Password)){
            Password.setError("You must enter password to login!");
            isValid = false;
        } else {
            if (!Functions_Class.isPassword(Password))
            {
                Password.setError("Enter valid password!");
                isValid = false;
            }
        }

        return isValid;
    }
    

}