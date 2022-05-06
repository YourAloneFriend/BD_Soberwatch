package com.example.soberwatch.Main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.soberwatch.Main.Car.CarActivity;
import com.example.soberwatch.Main.Card.CardActivity;
import com.example.soberwatch.Main.FamilyDoctor.FamilyDoctorActivity;
import com.example.soberwatch.R;
import com.example.soberwatch.registration_and_authorization.AuthorizationActivity;

public class MainWindowActivity extends AuthorizationActivity {

    private TextView alco_lvl;
    private TextView pulse_lvl;
    private TextView card_permission;
    private TextView car_permission;
    private ImageButton exit;

    private ImageButton Card_settings;
    private ImageButton Car_settings;
    private ImageButton Doc_settings;

    private String id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_window);

        setupUI();
        setupListeners();

    }

    private void setupUI()
    {
        alco_lvl = findViewById(R.id.alco_info_id);
        pulse_lvl = findViewById(R.id.puls_info_id);
        card_permission = findViewById(R.id.card_permission_access_id);
        car_permission = findViewById(R.id.car_permission_access_id);
        exit = findViewById(R.id.exit_button_id);

        Card_settings = findViewById(R.id.card_button_id);
        Car_settings = findViewById(R.id.car_button_id);
        Doc_settings = findViewById(R.id.medical_button_id);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");
    }

    private void setupListeners()
    {
        Card_settings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainWindowActivity.this, CardActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        Car_settings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainWindowActivity.this, CarActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        Doc_settings.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainWindowActivity.this, FamilyDoctorActivity.class);
                intent.putExtra("id", id);
                startActivity(intent);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                finish();
            }
        });
    }

}