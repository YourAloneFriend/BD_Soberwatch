package com.example.soberwatch.DB_stuff;

public class Car {
    String owner_id, whole_name, number;

    public Car(String owner_id, String whole_name, String number) {
        this.owner_id = owner_id;
        this.whole_name = whole_name;
        this.number = number;
    }

    public String getOwner_id() {
        return owner_id;
    }

    public void setOwner_id(String owner_id) {
        this.owner_id = owner_id;
    }

    public String getWhole_name() {
        return whole_name;
    }

    public void setWhole_name(String whole_name) {
        this.whole_name = whole_name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this. number = number;
    }
}
