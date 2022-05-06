package com.example.soberwatch.Main.Card;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.soberwatch.DB_stuff.DB;
import com.example.soberwatch.Main.Car.CarActivity;
import com.example.soberwatch.Main.Car.CarAddActivity;
import com.example.soberwatch.R;

import java.sql.ResultSet;

public class CardActivity extends AppCompatActivity {

    private Button update_data;
    private ImageButton back_to_main_menu;
    private TextView scroll_card_data;
    private DB db = null;

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);

        setupUI();
        print_cards();
        setupListeners();
    }


    private void setupUI()
    {
        update_data = findViewById(R.id.update_button_id);
        back_to_main_menu = findViewById(R.id.back_button_id);
        scroll_card_data = findViewById(R.id.text_card_data_id);

        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        db = new DB();
    }

    private void print_cards()
    {
        int num = 1;
        String text = "";
        ResultSet card_data =  db.GetCardData(id);
        try {
            while (card_data.next()) {
                text +=  String.valueOf(num) + ": " + card_data.getString("card_num") + " - " + card_data.getString("csv") + "\n\n";
            }
        } catch (Exception e){}

        scroll_card_data.setText(text);
    }

    private void setupListeners()
    {
        update_data.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CardActivity.this, CardAddActivity.class);
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