package com.example.soberwatch.Main.FamilyDoctor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.soberwatch.DB_stuff.DB;
import com.example.soberwatch.R;

import java.sql.ResultSet;
import java.util.regex.Pattern;

public class FamilyDoctorAddActivity extends AppCompatActivity {

    private Button save_data;
    private EditText phonenum;
    private DB db = null;

    private String id, doc_id;


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
        phonenum = findViewById(R.id.doc_phonenum_id);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        db = new DB();
    }

    private boolean is_valid_phonenum(String phonenum)
    {
        if(Pattern.matches("\\+380\\d{9}", phonenum)) {
            doc_id = db.GetDocId(phonenum);
            return !doc_id.isEmpty();
        }
        return false;
    }

    private void save_doc_data()
    {
        db.SetDocToUser(id, doc_id);
    }

    private void setupListeners()
    {
       save_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String doc_phonenum = phonenum.getText().toString();
                if(doc_phonenum.length() == 0)
                    finish();
                else
                    if(is_valid_phonenum(doc_phonenum)) {
                        save_doc_data();
                        finish();
                    }else
                        phonenum.setError("Doc with this phonenum doesn't exist!");
            }
        });
    }
}