package com.middlesex.quickart;

public class CardDetails {
    String personName,cardUID;

    public CardDetails(String personName, String cardUID) {
        this.personName = personName;
        this.cardUID = cardUID;
    }

    public CardDetails() {
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getCardUID() {
        return cardUID;
    }

    public void setCardUID(String cardUID) {
        this.cardUID = cardUID;
    }
}
