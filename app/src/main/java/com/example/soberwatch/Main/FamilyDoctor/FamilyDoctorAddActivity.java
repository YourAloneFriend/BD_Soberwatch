package com.example.soberwatch.Main.FamilyDoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.soberwatch.DB_stuff.DB;
import com.example.soberwatch.DB_stuff.FamilyDoctor;
import com.example.soberwatch.R;

public class FamilyDoctorAddActivity extends AppCompatActivity {

    private Button save_data;
    private EditText phonenum;
    private EditText firstname;
    private EditText lastname;

    private DB db = null;

    private String id;
    private FamilyDoctor doc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_doctor_update);

        setupUI();
        setupListeners();
    }

    private void setupUI()
    {
        save_data = findViewById(R.id.update_button_id);
        firstname = findViewById(R.id.doc_firstname_id);
        lastname = findViewById(R.id.doc_lastname_id);
        phonenum = findViewById(R.id.doc_phonenum_id);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        db = new DB(FamilyDoctorAddActivity.this);
    }

    private void save_doc_data()
    {
        db.SetDocToUser(id, doc);
    }

    private void setupListeners()
    {
       save_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String doc_phonenum = phonenum.getText().toString().trim();
                String doc_firstname = firstname.getText().toString().trim();
                String doc_lastname = lastname.getText().toString().trim();

                boolean is_valid = true;

                if(doc.is_valid_phonenum(doc_phonenum))
                {
                    phonenum.setError("Should print a password correctly!");
                    is_valid = false;
                }
                if(doc.is_valid_firstname(doc_firstname))
                {
                    firstname.setError("Should print a firstname correctly!");
                    is_valid  = false;
                }
                if( doc.is_valid_lastname(doc_lastname))
                {
                    doc = new FamilyDoctor(doc_firstname, doc_lastname, doc_phonenum);
                    lastname.setError("Should print a lastname correctly!");
                    is_valid = false;
                }


                if(is_valid) {
                    save_doc_data();
                    finish();
                }
            }
        });
    }
}