package com.tpr.andromeda.teenpattireseller;

public class User {

    private String userId;
    private String userName;
    private String playerId;
    private String phoneNo;
    private float creditAmount;
    private String user_pass;

    User(String userId, String userName, String playerId, String phoneNo, float creditAmount, String password) {
        this.userId = userId;
        this.userName = userName;
        this.playerId = playerId;
        this.phoneNo = phoneNo;
        this.creditAmount = creditAmount;
        this.user_pass = password;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public float getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(float creditAmount) {
        this.creditAmount = creditAmount;
    }

    public String getUser_pass() {
        return user_pass;
    }

    public void setUser_pass(String user_pass) {
        this.user_pass = user_pass;
    }
}
