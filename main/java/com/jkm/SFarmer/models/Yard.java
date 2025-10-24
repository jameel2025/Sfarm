// app/src/main/java/com/yourcompany/farmmanagement/models/Yard.java
package com.jkm.SFarmer.models;

public class Yard {
    private int id;
    private int farmId;
    private String yardNumber;
    private double area;
    private int workersCount;
    private String cropType;
    private int treesCount;
    private double production;
    private String plantingDate;
    private String expectedHarvestDate;

    public Yard() {}

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getFarmId() { return farmId; }
    public void setFarmId(int farmId) { this.farmId = farmId; }

    public String getYardNumber() { return yardNumber; }
    public void setYardNumber(String yardNumber) { this.yardNumber = yardNumber; }

    public double getArea() { return area; }
    public void setArea(double area) { this.area = area; }

    public int getWorkersCount() { return workersCount; }
    public void setWorkersCount(int workersCount) { this.workersCount = workersCount; }

    public String getCropType() { return cropType; }
    public void setCropType(String cropType) { this.cropType = cropType; }

    public int getTreesCount() { return treesCount; }
    public void setTreesCount(int treesCount) { this.treesCount = treesCount; }

    public double getProduction() { return production; }
    public void setProduction(double production) { this.production = production; }

    public String getPlantingDate() { return plantingDate; }
    public void setPlantingDate(String plantingDate) { this.plantingDate = plantingDate; }

    public String getExpectedHarvestDate() { return expectedHarvestDate; }
    public void setExpectedHarvestDate(String expectedHarvestDate) { this.expectedHarvestDate = expectedHarvestDate; }
}