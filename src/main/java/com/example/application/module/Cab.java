package com.example.application.module;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection="cabs")

public class Cab {
    @Id
    private String cabId;
    private String driverName;
    private String licensePlate;
    private String cabModel;
    private String currentLocation;
    private String status;

    public String getCabId() {
        return cabId;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public String getCabModel() {
        return cabModel;
    }

    public String getDriverName() {
        return driverName;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public String getStatus() {
        return status;
    }

    public void setCabId(String cabId) {
        this.cabId = cabId;
    }

    public void setCabModel(String cabModel) {
        this.cabModel = cabModel;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public double getCurrentLatitude() {
        String[] latLong = currentLocation.split(",");
        return Double.parseDouble(latLong[0]);
    }
    public double getCurrentLongitude() {
        String[] latLong = currentLocation.split(",");
        return Double.parseDouble(latLong[1]);
    }
}
