package com.crs.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class HandoverInspection {
    private int inspectionId;
    private int bookingId;
    private int inspectedBy;
    private String inspectionType;
    private BigDecimal mileage;
    private BigDecimal fuelLevel;
    private String carAdditional;
    private String note;
    private String evidenceUrl;
    private LocalDateTime inspectedAt;

    public HandoverInspection() {
    }

    public int getInspectionId() { return inspectionId; }
    public void setInspectionId(int inspectionId) { this.inspectionId = inspectionId; }
    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public int getInspectedBy() { return inspectedBy; }
    public void setInspectedBy(int inspectedBy) { this.inspectedBy = inspectedBy; }
    public String getInspectionType() { return inspectionType; }
    public void setInspectionType(String inspectionType) { this.inspectionType = inspectionType; }
    public BigDecimal getMileage() { return mileage; }
    public void setMileage(BigDecimal mileage) { this.mileage = mileage; }
    public BigDecimal getFuelLevel() { return fuelLevel; }
    public void setFuelLevel(BigDecimal fuelLevel) { this.fuelLevel = fuelLevel; }
    public String getCarAdditional() { return carAdditional; }
    public void setCarAdditional(String carAdditional) { this.carAdditional = carAdditional; }
    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }
    public String getEvidenceUrl() { return evidenceUrl; }
    public void setEvidenceUrl(String evidenceUrl) { this.evidenceUrl = evidenceUrl; }
    public LocalDateTime getInspectedAt() { return inspectedAt; }
    public void setInspectedAt(LocalDateTime inspectedAt) { this.inspectedAt = inspectedAt; }
}