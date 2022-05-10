package com.example.soberwatch.DB_stuff;

public class Card {
    String user_id, card_num, csv;

    public Card(String user_id, String card_num, String csv) {
        this.user_id = user_id;
        this.card_num = card_num;
        this.csv = csv;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCard_num() {
        return card_num;
    }

    public void setCard_num(String card_num) {
        this.card_num = card_num;
    }

    public String getCsv() {
        return csv;
    }

    public void setCsv(String csv) {
        this.csv = csv;
    }
}
