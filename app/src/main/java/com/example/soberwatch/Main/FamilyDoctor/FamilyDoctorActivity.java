package com.example.soberwatch.Main.FamilyDoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.soberwatch.DB_stuff.DB;
import com.example.soberwatch.DB_stuff.FamilyDoctor;
import com.example.soberwatch.R;

public class FamilyDoctorActivity extends AppCompatActivity {

    private Button update_data;
    private ImageButton back_to_main_menu;
    private TextView firstname, lastname, phonenum;
    private DB db = null;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_family_doctor);


        setupUI();
        print_docs();
        setupListeners();
    }

    private void setupUI()
    {
        update_data = findViewById(R.id.update_button_id);
        back_to_main_menu = findViewById(R.id.back_button_id);
        firstname = findViewById(R.id.doc_firstname_id);
        lastname = findViewById(R.id.doc_lastname_id);
        phonenum = findViewById(R.id.doc_phonenum_id);


        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        db = new DB(FamilyDoctorActivity.this);
    }

    private void print_docs()
    {
        FamilyDoctor doc_data =  db.GetDocData(db.GetDocId(id));
        try {
            firstname.setText(doc_data.getFirstname());
            lastname.setText(doc_data.getLastname());
            phonenum.setText(doc_data.getPhonenum());

        } catch (Exception e){}
    }

    private void setupListeners()
    {
        update_data.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FamilyDoctorActivity.this, FamilyDoctorAddActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        back_to_main_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }
}