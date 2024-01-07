package com.example.application.module;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "trips")
public class Trip {
    @Id
    private String tripId;
    private String userId;
    private String cabId;
    private String startLocation;
    private String endLocation;
    private Date startTime;
    private Date endTime;
    private String tripStatus; // Scheduled, In Progress, Completed, Cancelled

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(String endLocation) {
        this.endLocation = endLocation;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    public void setCabId(String cabId) {
        this.cabId = cabId;
    }

    public String getCabId() {
        return cabId;
    }
    public Double getStartLatitude()
    {
        String[] latLong = startLocation.split(",");
        return Double.parseDouble(latLong[0]);
    }
    public Double getStartLongitude()
    {
        String[] latLong = startLocation.split(",");
        return Double.parseDouble(latLong[1]);
    }

}