package com.example.soberwatch.Main.Card;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.soberwatch.DB_stuff.Consts;
import com.example.soberwatch.DB_stuff.DB;
import com.example.soberwatch.R;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class CardAddActivity extends AppCompatActivity {

    private LinearLayout layoutList;
    private Button buttonAdd;
    private Button buttonSave;
    private DB db = null;

    private String id;
    private ResultSet card_data;

    private ArrayList<String> cards_id;
    private ArrayList<String> delete_cards_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_add);

        setupUI();
        setupListeners();
    }

    private void removeView(View view)
    {
        layoutList.removeView(view);
    }

    private void addView() {
        View cardView = getLayoutInflater().inflate(R.layout.row_add_card, null, false);
        EditText NameText = (EditText)cardView.findViewById(R.id.Card_num_id);
        EditText NumberText = (EditText)cardView.findViewById(R.id.Card_csv_id);
        ImageView removeCard = (ImageView)cardView.findViewById(R.id.Car_remove_id);
        layoutList.addView(cardView);

        removeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeView(view);
            }
        });
    }

    private void getCards()
    {

        card_data =  db.GetCardData(id);
        try {
            while (card_data.next()) {
                View carView = getLayoutInflater().inflate(R.layout.row_add_card, null, false);
                EditText NameText = (EditText)carView.findViewById(R.id.Card_num_id);
                EditText NumberText = (EditText)carView.findViewById(R.id.Card_csv_id);
                NumberText.setEnabled(false);
                ImageView removeCard = (ImageView)carView.findViewById(R.id.Card_remove_id);
                NameText.setText(card_data.getString("card_num"));
                NameText.setText(card_data.getString("csv"));
                layoutList.addView(carView);

                removeCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeView(view);
                    }

                });
                cards_id.add(card_data.getString("id"));
            }
            card_data.first();
        } catch (Exception e){}

    }

    private void setupUI()
    {
        layoutList = findViewById(R.id.card_list);
        buttonAdd = findViewById(R.id.card_add_button_id);
        buttonSave = findViewById(R.id.save_button_id);


        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        db = new DB();

        getCards();
        addView();
    }

    private static boolean check_number_valid(String number) {
        return Pattern.matches("\\d{4} \\d{4} \\d{4} \\d{4}", number);
    }

    private static boolean check_csv_valid(@NonNull String csv) {
        return Pattern.matches("\\d{3}", csv);
    }

    private void save_data()
    {
        ArrayList<ArrayList<String>> Num_csv = new ArrayList<ArrayList<String>>();
        Num_csv.add(new ArrayList<String>());
        Num_csv.add(new ArrayList<String>());


        try {
            boolean valid = false;
            for (int i = 0; i < layoutList.getChildCount(); ++i)
            {
                View view = layoutList.getChildAt(i);
                EditText NumText = (EditText) view.findViewById(R.id.Card_num_id);
                EditText csvText = (EditText) view.findViewById(R.id.Card_csv_id);

                Num_csv.get(0).add(NumText.getText().toString());
                Num_csv.get(1).add(csvText.getText().toString());

                if(!check_number_valid(Num_csv.get(0).get(i).toString())) {
                    NumText.setError("Empty or not valid!");
                    valid = true;
                }
                if(!check_csv_valid(Num_csv.get(1).get(i).toString())){
                    csvText.setError("Empty or not valid!");
                    valid = true;
                }

                if(i < cards_id.size()) {
                    if(!Num_csv.get(0).get(i).equals(card_data.getString("card_num")))
                        delete_cards_id.add(db.GetCarId(id, Num_csv.get(1).get(i).toString()));
                    card_data.next();
                }
            }
            if(valid)
                return;


            for (String x : delete_cards_id) {
                db.RemoveData(x, Consts.USER_BANK_CARD_TABLE);
            }

            card_data = db.GetCardData(id);
            for (int i = 0; i < cards_id.size(); ++i) {
                View view = layoutList.getChildAt(i);

                if (!Num_csv.get(0).get(i).equals(card_data.getString("card_num")))
                    db.UpdateCarData(cards_id.get(i), Num_csv.get(0).get(i).toString());
                card_data.next();
            }

            for (int i = cards_id.size(); i < layoutList.getChildCount(); ++i) {
                View view = layoutList.getChildAt(i);
                db.AddCarData(id, Num_csv.get(0).get(i).toString(), Num_csv.get(1).get(i).toString());
            }

        } catch (Exception e){}
    }

    private void setupListeners()
    {
        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                addView();
            }
        });
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                save_data();
                finish();
            }
        });
    }


}