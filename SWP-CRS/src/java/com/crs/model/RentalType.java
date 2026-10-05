package com.crs.model;

import java.math.BigDecimal;

public class RentalType {
    private int rentalTypeId;
    private String typeName;
    private BigDecimal additionFee;

    public RentalType() {
    }

    public int getRentalTypeId() { return rentalTypeId; }
    public void setRentalTypeId(int rentalTypeId) { this.rentalTypeId = rentalTypeId; }
    public String getTypeName() { return typeName; }
    public void setTypeName(String typeName) { this.typeName = typeName; }
    public BigDecimal getAdditionFee() { return additionFee; }
    public void setAdditionFee(BigDecimal additionFee) { this.additionFee = additionFee; }
}