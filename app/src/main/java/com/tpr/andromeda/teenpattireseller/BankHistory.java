package com.tpr.andromeda.teenpattireseller;

class BankHistory {
    private String playerId;
    private String reciverId;
    private float chips;
    private String type;
    private String time;

    public BankHistory(String playerId, String reciverId, float chips, String type, String time) {
        this.playerId = playerId;
        this.reciverId = reciverId;
        this.chips = chips;
        this.type = type;
        this.time = time;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getReciverId() {
        return reciverId;
    }

    public float getChips() {
        return chips;
    }

    public String getType() {
        return type;
    }

    public String getTime() {
        return time;
    }
}
