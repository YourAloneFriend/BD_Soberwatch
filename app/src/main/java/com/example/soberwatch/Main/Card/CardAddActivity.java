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

import com.example.soberwatch.DB_stuff.Card;
import com.example.soberwatch.DB_stuff.Consts;
import com.example.soberwatch.DB_stuff.DB;
import com.example.soberwatch.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CardAddActivity extends AppCompatActivity {

    private LinearLayout layoutList;
    private Button buttonAdd;
    private Button buttonSave;
    private DB db = null;

    private String id;
    private List<Card> card_data;

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

        removeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeView(view);
            }
        });

        layoutList.addView(cardView);
    }

    private void getCards()
    {
        cards_id = new ArrayList<>();
        card_data =  db.GetCardData(id);
        try {
            for(Card x : card_data) {
                View cardView = getLayoutInflater().inflate(R.layout.row_add_card, null, false);
                EditText NumberText = (EditText)cardView.findViewById(R.id.Card_num_id);
                EditText CsvText = (EditText)cardView.findViewById(R.id.Card_csv_id);
                CsvText.setEnabled(false);
                ImageView removeCard = (ImageView)cardView.findViewById(R.id.Card_remove_id);
                NumberText.setText(x.getCard_num());
                CsvText.setText(x.getCsv());

                removeCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeView(view);
                    }

                });

                layoutList.addView(cardView);
                cards_id.add(db.GetCardId(x));
            }
        } catch (Exception e){}

    }

    private void setupUI()
    {
        layoutList = findViewById(R.id.card_list);
        buttonAdd = findViewById(R.id.card_add_button_id);
        buttonSave = findViewById(R.id.save_button_id);


        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        db = new DB(CardAddActivity.this);

        getCards();
        addView();
    }

    private static boolean check_number_valid(String number) {
        return Pattern.matches("\\d{4} \\d{4} \\d{4} \\d{4}", number);
    }

    private static boolean check_csv_valid(@NonNull String csv) {
        return Pattern.matches("\\d{3}", csv);
    }

    private boolean save_data()
    {
        List<Card> GetCardFromActivity = new ArrayList<>();
        delete_cards_id = new ArrayList<>();

        try {
            boolean valid = false;
            for (int i = 0; i < layoutList.getChildCount(); ++i)
            {
                View view = layoutList.getChildAt(i);
                EditText NumberText = (EditText) view.findViewById(R.id.Card_num_id);
                EditText CsvText = (EditText) view.findViewById(R.id.Card_csv_id);

                GetCardFromActivity.add(new Card(id,  NumberText.getText().toString(), CsvText.getText().toString()));


                if(!check_number_valid(GetCardFromActivity.get(i).getCard_num())) {
                    NumberText.setError("Empty or not valid!");
                    valid = true;
                }
                if(!check_csv_valid(GetCardFromActivity.get(i).getCsv())){
                    CsvText.setError("Empty or not valid!");
                    valid = true;
                }

                if(i < cards_id.size()) {
                    if(!GetCardFromActivity.get(i).getCsv().equals(card_data.get(i).getCsv()))
                        delete_cards_id.add(db.GetCardId(GetCardFromActivity.get(i)));
                }
            }
            if(valid)
                return false;


            for (String x : delete_cards_id) {
                db.RemoveData(x, Consts.USER_BANK_CARD_TABLE);
            }

            card_data = db.GetCardData(id);
            for (int i = 0; i < cards_id.size(); ++i) {
                if (!GetCardFromActivity.get(i).getCard_num().equals(card_data.get(i).getCard_num()))
                    db.UpdateCardData(GetCardFromActivity.get(i));
            }

            for (int i = cards_id.size(); i < layoutList.getChildCount(); ++i) {
                db.AddCardData(GetCardFromActivity.get(i));
            }

        } catch (Exception e){}
        return true;
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
                if(save_data())
                    finish();
            }
        });
    }


}