package com.example.levantuan.finalproject_android;


public class User {


    public String email;
    public int numOfGames;


    public User() {
        // keep this, you need a blank constructor
    }

    public User(String e, int g) {

        this.email = e;
        this.numOfGames = g;

    }
    public String getEmail() {
        return this.email;
    }
    public int getnumOfGames() {
        return this.numOfGames;
    }
    public void setEmail(String e) {
        this.email = e;
    }
    public void setNumOfGames(int g) {
        this.numOfGames = g;
    }
}
