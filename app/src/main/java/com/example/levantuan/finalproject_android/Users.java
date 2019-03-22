package com.example.levantuan.finalproject_android;

class Users {
    String name;
    String point;
    String lat;
    String lg;

    public Users(String point, String name, String lat, String lg) {
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
    public void setPoint(String s) {
        this.point = s;
    }
    public void setName(String n) {
        this.name = n;
    }

}
