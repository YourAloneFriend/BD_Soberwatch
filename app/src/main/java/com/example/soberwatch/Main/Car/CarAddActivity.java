package com.example.soberwatch.Main.Car;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.soberwatch.DB_stuff.Car;
import com.example.soberwatch.DB_stuff.Consts;
import com.example.soberwatch.DB_stuff.DB;
import com.example.soberwatch.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CarAddActivity extends AppCompatActivity {

    private LinearLayout layoutList;
    private Button buttonAdd;
    private Button buttonSave;
    private DB db = null;

    private String id;
    private List<Car> car_data;

    private ArrayList<String> cars_id;
    private ArrayList<String> delete_cars_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_add);

        setupUI();
        setupListeners();

    }

    private void removeView(View view)
    {
        layoutList.removeView(view);
    }

    private void addView() {
        View carView = getLayoutInflater().inflate(R.layout.row_add_car, null, false);
        EditText NameText = (EditText)carView.findViewById(R.id.Car_name_id);
        EditText NumberText = (EditText)carView.findViewById(R.id.Car_number_id);
        ImageView removeCar = (ImageView)carView.findViewById(R.id.Car_remove_id);

        removeCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeView(view);
            }
        });

        layoutList.addView(carView);
    }

    private void getCars()
    {
        cars_id = new ArrayList<>();
        car_data = db.GetCarData(id);
        try {
            for(Car x : car_data) {
                View carView = getLayoutInflater().inflate(R.layout.row_add_car, null, false);
                EditText NameText = (EditText)carView.findViewById(R.id.Car_name_id);
                EditText NumberText = (EditText)carView.findViewById(R.id.Car_number_id);
                NumberText.setEnabled(false);
                ImageView removeCar = (ImageView)carView.findViewById(R.id.Car_remove_id);
                NameText.setText(x.getWhole_name());
                NumberText.setText(x.getNumber());

                removeCar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        removeView(view);
                    }

                });

                layoutList.addView(carView);
                cars_id.add(db.GetCarId(x));
            }
        } catch (Exception e){}

    }

    private void setupUI()
    {
        layoutList = findViewById(R.id.car_list);
        buttonAdd = findViewById(R.id.car_add_button_id);
        buttonSave = findViewById(R.id.save_button_id);


        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        db = new DB(CarAddActivity.this);

        getCars();
        addView();
    }

    private static boolean check_number_valid(String number) {
        return Pattern.matches("[A-Z]{2}\\s\\d{2}\\s[A-Z]{2}", number);
    }

    private static boolean check_name_valid(@NonNull String name)
    {
        return name.trim().length() >= 3;
    }

    private void save_data()
    {
        List<Car> GetCarFromActivity = new ArrayList<>();
        delete_cars_id = new ArrayList<>();


        try {
            boolean valid = false;
            for (int i = 0; i < layoutList.getChildCount(); ++i)
            {
                View view = layoutList.getChildAt(i);
                EditText NameText = (EditText) view.findViewById(R.id.Car_name_id);
                EditText NumberText = (EditText) view.findViewById(R.id.Car_number_id);

                GetCarFromActivity.add(new Car(id, NameText.getText().toString(), NumberText.getText().toString()));


                if(!check_name_valid(GetCarFromActivity.get(i).getWhole_name())) {
                    NameText.setError("Empty or not valid!");
                    valid = true;
                }
                if(!check_number_valid(GetCarFromActivity.get(i).getNumber())){
                    NumberText.setError("Empty or not valid!");
                    valid = true;
                }

                if(i < cars_id.size()) {
                    if(!GetCarFromActivity.get(i).getNumber().equals(car_data.get(i).getNumber()))
                        delete_cars_id.add(db.GetCarId(GetCarFromActivity.get(i)));

                }
            }
            if(valid)
                return;


            for (String x : delete_cars_id) {
                db.RemoveData(x, Consts.USER_CAR_TABLE);
            }

            car_data = db.GetCarData(id);
            for (int i = 0; i < cars_id.size(); ++i) {

                if (!GetCarFromActivity.get(i).getNumber().equals(car_data.get(i).getWhole_name()))
                    db.UpdateCarData(GetCarFromActivity.get(i));
            }
            for (int i = cars_id.size(); i < layoutList.getChildCount(); ++i) {
                Car tempCar = new Car(String.valueOf(id), GetCarFromActivity.get(i).getWhole_name(),GetCarFromActivity.get(i).getNumber());
                db.AddCarData(tempCar);
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