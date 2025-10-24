package com.jkm.SFarmer.models;

public class Equipment {
    private String name;
    private String status; // e.g., "In Use", "Available", "Needs Repair"

    public Equipment(String name, String status) {
        this.name = name;this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }
}
