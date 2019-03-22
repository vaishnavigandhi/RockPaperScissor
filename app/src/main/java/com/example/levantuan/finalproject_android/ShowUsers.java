package com.example.levantuan.finalproject_android;

class ShowUsers {
    public String name;
    public String point;
    public String lat;
    public String lg;


    public ShowUsers() {

    }
    public ShowUsers(String name, String point, String lat, String lg) {
        this.name = name;
        this.point = point;
        this.lat = lat;
        this.lg = lg;
    }
    public String getName() {
        return this.name;
    }
    public String getPoint() {
        return this.point;
    }
    public String getLat() { return this.lat;}
    public String getLg() { return this.lg;}
    public void setPoint(String s) {
        this.point = s;
    }
    public void setName(String n) {
        this.name = n;
    }
    public void setLat(String l) {
        this.lat = l;
    }
    public void setLg(String g) {
        this.lg = g;
    }


}
