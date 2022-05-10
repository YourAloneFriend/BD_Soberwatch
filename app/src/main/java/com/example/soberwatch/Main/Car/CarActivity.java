package com.example.soberwatch.Main.Car;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.soberwatch.DB_stuff.Car;
import com.example.soberwatch.DB_stuff.DB;
import com.example.soberwatch.R;

import java.util.List;

public class CarActivity extends AppCompatActivity {

    private Button update_data;
    private ImageButton back_to_main_menu;
    private TextView scroll_car_data;
    private DB db = null;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        setupUI();
        print_cars();
        setupListeners();
    }

    private void setupUI()
    {
        update_data = findViewById(R.id.update_button_id);
        back_to_main_menu = findViewById(R.id.back_button_id);
        scroll_car_data = findViewById(R.id.text_car_data_id);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        db = new DB(CarActivity.this);
    }

    private void print_cars()
    {
        int num = 1;
        String text = "";
        List<Car> car_data =  db.GetCarData(id);
        try {
            for(Car x : car_data) {
                text +=  num + ": " + x.getWhole_name() + " - " + x.getNumber() + "\n\n";
            }
        } catch (Exception e){}

        scroll_car_data.setText(text);
    }

    private void setupListeners()
    {
        update_data.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CarActivity.this, CarAddActivity.class);
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