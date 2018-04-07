package com.tpr.andromeda.teenpattireseller;

public class User {

    String userName;
    String playerId;
    String phoneNo;
    String cramount;
    String password;

    public User(String id, String userName, String playerId, String phoneNo, String cramount, String password) {
        this.userName = userName;
        this.playerId = playerId;
        this.phoneNo = phoneNo;
        this.cramount = cramount;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public String getCramount() {
        return cramount;
    }

    public String getPassword() {
        return password;
    }
}
