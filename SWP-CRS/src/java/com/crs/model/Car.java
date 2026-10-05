package com.crs.model;

import java.math.BigDecimal;

public class Car {
    private int id;
    private int ownerId;
    private String carName;
    private String licensePlate;
    private String brand;
    private String model;
    private Integer seat;
    private String status;
    private BigDecimal pricePerDay;
    private String fuelType;
    private String description;
    private String transmission;
    private BigDecimal reservationFee;
    private BigDecimal depositAmount;

    public Car() {
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getOwnerId() { return ownerId; }
    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }
    public String getCarName() { return carName; }
    public void setCarName(String carName) { this.carName = carName; }
    public String getLicensePlate() { return licensePlate; }
    public void setLicensePlate(String licensePlate) { this.licensePlate = licensePlate; }
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }
    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public Integer getSeat() { return seat; }
    public void setSeat(Integer seat) { this.seat = seat; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public BigDecimal getPricePerDay() { return pricePerDay; }
    public void setPricePerDay(BigDecimal pricePerDay) { this.pricePerDay = pricePerDay; }
    public String getFuelType() { return fuelType; }
    public void setFuelType(String fuelType) { this.fuelType = fuelType; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getTransmission() { return transmission; }
    public void setTransmission(String transmission) { this.transmission = transmission; }
    public BigDecimal getReservationFee() { return reservationFee; }
    public void setReservationFee(BigDecimal reservationFee) { this.reservationFee = reservationFee; }
    public BigDecimal getDepositAmount() { return depositAmount; }
    public void setDepositAmount(BigDecimal depositAmount) { this.depositAmount = depositAmount; }
}