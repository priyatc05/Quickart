package com.middlesex.quickart;

public class rfid_data {
    String card_data;
    int card_id;

    public rfid_data(String card_data, int card_id) {
        this.card_data = card_data;
        this.card_id = card_id;
    }

    public String getCard_data() {
        return card_data;
    }

    public void setCard_data(String card_data) {
        this.card_data = card_data;
    }

    public int getCard_id() {
        return card_id;
    }

    public void setCard_id(int card_id) {
        this.card_id = card_id;
    }

    public rfid_data() {
    }
}