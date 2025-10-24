// app/src/main/java/com/yourcompany/farmmanagement/models/Farm.java
package com.jkm.SFarmer.models;

public class Farm {
    private int id;
    private String name;
    private double area;
    private int totalYards;
    private String address;
    private byte[] logo;

    public Farm() {}

    public Farm(String name, double area, int totalYards, String address) {
        this.name = name;
        this.area = area;
        this.totalYards = totalYards;
        this.address = address;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getArea() { return area; }
    public void setArea(double area) { this.area = area; }

    public int getTotalYards() { return totalYards; }
    public void setTotalYards(int totalYards) { this.totalYards = totalYards; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public byte[] getLogo() { return logo; }
    public void setLogo(byte[] logo) { this.logo = logo; }
}