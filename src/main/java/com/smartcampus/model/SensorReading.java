/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.smartcampus.model;

import java.util.UUID;

/**
 *
 * @author Randil Henry
 */
public class SensorReading {
    
    private String id;
    private long timeStamp;
    private double value;

    public SensorReading() {
    }

    public SensorReading(double value) {
        this.id = UUID.randomUUID().toString();
        this.timeStamp = System.currentTimeMillis();
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
    
    
}
