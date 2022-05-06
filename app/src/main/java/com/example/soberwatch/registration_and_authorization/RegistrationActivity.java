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
import com.example.soberwatch.R;


public class RegistrationActivity extends AppCompatActivity {

    private EditText Name;
    private EditText Email;
    private EditText Password_1;
    private EditText Password_2;
    private Button sign_up;
    private Button back;
    private String[] user_data = new String[3];
    private TextView error_text;
    private DB db = null;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        Name = findViewById(R.id.Name_id);
        Email = findViewById(R.id.Email_id);
        Password_1 = findViewById(R.id.Password_1_id);
        Password_2 = findViewById(R.id.Password_2_id);
        sign_up = findViewById(R.id.sign_up_id);
        back = findViewById(R.id.back_id);
        error_text = findViewById(R.id.error_id);

        db = new DB();

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetInputData();
                if(CheckDataEntered()) {
                    User user = new User(user_data[0], user_data[1], user_data[2]);
                    db.signUpUser(user);
                    error_text.setText("Successfully registered!");
                }
                else
                    error_text.setText("Wrong data!");
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( RegistrationActivity.this, AuthorizationActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }

    protected void SetInputData()
    {
        user_data[0] = Name.getText().toString();
        user_data[1] = Email.getText().toString();
        user_data[2] = Password_1.getText().toString();
    }

    protected boolean CheckDataEntered()
    {
        boolean isPrinted = false;
        if(Functions_Class.isEmpty(Name))
        {
            Name.setError("Should print a password!");
            isPrinted = true;
        }
        if(Functions_Class.isEmpty(Email))
        {
            Email.setError("Should print an email!");
            isPrinted = true;
        }
        if(Functions_Class.isEmpty(Password_1))
        {
            Password_1.setError("Should print a password!");
            isPrinted = true;
        }
        if(Functions_Class.isEmpty(Password_2))
        {
            Password_2.setError("Should print a password!");
            isPrinted = true;
        }

        if(isPrinted)
            return false;

        if(!Functions_Class.isEmail(Email)) {
            Email.setError("Enter valid email!");
            return false;
        }

        if(!Functions_Class.isPassword(Password_1)) {
            Password_1.setError("Enter valid password!");
            return false;
        }

        if(!Functions_Class.isPassword(Password_2) && !Password_1.toString().equals(Password_2.toString())) {
            Password_2.setError("Don't equal to first password!");
            return false;
        }
        return true;
    }

}
