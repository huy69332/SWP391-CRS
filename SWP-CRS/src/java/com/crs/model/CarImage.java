package com.crs.model;

import java.time.LocalDateTime;

public class CarImage {
    private int imgId;
    private int carId;
    private String imgUrl;
    private String imgType;
    private boolean primary;
    private LocalDateTime createdAt;

    public CarImage() {
    }

    public int getImgId() { return imgId; }
    public void setImgId(int imgId) { this.imgId = imgId; }
    public int getCarId() { return carId; }
    public void setCarId(int carId) { this.carId = carId; }
    public String getImgUrl() { return imgUrl; }
    public void setImgUrl(String imgUrl) { this.imgUrl = imgUrl; }
    public String getImgType() { return imgType; }
    public void setImgType(String imgType) { this.imgType = imgType; }
    public boolean isPrimary() { return primary; }
    public void setPrimary(boolean primary) { this.primary = primary; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}