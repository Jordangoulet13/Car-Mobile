package com.example.CarProject;

import java.io.Serializable;

public class Car implements Serializable {

    public String id;
    public String brand;
    public String cost;
    public String model;
    public String year;
    String rented = "0";

    public Car() {}

    public Car(String brand, String model, String year, String cost) {
        this.brand = brand;
        this.cost = cost;
        this.model = model;
        this.year = year;
        this.rented = "0";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCost() {
        return cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getRented() {
        return rented;
    }

    public void setRented(String rented) {
        this.rented = rented;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Brand: " + getBrand() + "\n");
        sb.append("Model: " + getModel() + "\n");
        sb.append("Year: " + getYear() + "\n");
        sb.append("Rented: " + getRented() + "\n");
        sb.append("Cost: " + getCost());

        return sb.toString();
    }
}
