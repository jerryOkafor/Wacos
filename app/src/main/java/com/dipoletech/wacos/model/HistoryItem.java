package com.dipoletech.wacos.model;/**
 * Created by DABBY(3pleMinds) on 27-Jan-16.
 */

/**
 * DABBY(3pleMinds) 27-Jan-16 3:35 AM 2016 01
 * 27 03 35 Wacos
 **/
public class HistoryItem {
    long date;
    private String purpose, amount, myId;

    //empty constructor
    public HistoryItem() {

    }

    public void setDate(long date) {
        this.date = date;
    }

    public long getDate() {
        return date;

    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setMyId(String myId) {
        this.myId = myId;
    }

    public String getMyId() {
        return myId;
    }

    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }

    public String getPurpose() {
        return purpose;
    }
}
