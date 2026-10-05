package com.crs.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CarLocal {
    private int locationId;
    private int carId;
    private Integer bookingId;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private LocalDateTime recordedAt;

    public CarLocal() {
    }

    public int getLocationId() { return locationId; }
    public void setLocationId(int locationId) { this.locationId = locationId; }
    public int getCarId() { return carId; }
    public void setCarId(int carId) { this.carId = carId; }
    public Integer getBookingId() { return bookingId; }
    public void setBookingId(Integer bookingId) { this.bookingId = bookingId; }
    public BigDecimal getLatitude() { return latitude; }
    public void setLatitude(BigDecimal latitude) { this.latitude = latitude; }
    public BigDecimal getLongitude() { return longitude; }
    public void setLongitude(BigDecimal longitude) { this.longitude = longitude; }
    public LocalDateTime getRecordedAt() { return recordedAt; }
    public void setRecordedAt(LocalDateTime recordedAt) { this.recordedAt = recordedAt; }
}