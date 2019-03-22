package com.example.levantuan.finalproject_android;


public class CurrentGame {

    public String email;
    public String choice;
    public String Gstatus;


    public CurrentGame() {
        // keep this, you need a blank constructor
    }

    public CurrentGame(String e, String c, String Gs) {

        this.email = e;
        this.choice = c;
        this.Gstatus = Gs;

    }
    public String getEmail() {
        return this.email;
    }
    public String getChoice() {
        return this.choice;
    }
    public void setEmail(String e) {
        this.email = e;
    }
    public void setChoice(String c) {
        this.choice = c;
    }
    public String getStatus()  { return this.Gstatus; }
    public String setStatus(String Gs){ return this.Gstatus = Gs; }



}
