package com.crs.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Booking {
    private int bookingId;
    private String bookingCode;
    private int customerId;
    private int carId;
    private int rentalTypeId;
    private int pickupLocationId;
    private int returnLocationId;
    private LocalDateTime startDatetime;
    private LocalDateTime endDatetime;
    private BigDecimal reservationFee;
    private BigDecimal depositFee;
    private BigDecimal rentalPricePerDay;
    private BigDecimal totalRentalAmount;
    private String bookingStatus;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    public Booking() {
    }

    public Booking(int bookingId, String bookingCode, int customerId, int carId,
            int rentalTypeId, int pickupLocationId, int returnLocationId,
            LocalDateTime startDatetime, LocalDateTime endDatetime,
            BigDecimal reservationFee, BigDecimal depositFee,
            BigDecimal rentalPricePerDay, BigDecimal totalRentalAmount,
            String bookingStatus, LocalDateTime createAt, LocalDateTime updateAt) {
        this.bookingId = bookingId;
        this.bookingCode = bookingCode;
        this.customerId = customerId;
        this.carId = carId;
        this.rentalTypeId = rentalTypeId;
        this.pickupLocationId = pickupLocationId;
        this.returnLocationId = returnLocationId;
        this.startDatetime = startDatetime;
        this.endDatetime = endDatetime;
        this.reservationFee = reservationFee;
        this.depositFee = depositFee;
        this.rentalPricePerDay = rentalPricePerDay;
        this.totalRentalAmount = totalRentalAmount;
        this.bookingStatus = bookingStatus;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingCode() {
        return bookingCode;
    }

    public void setBookingCode(String bookingCode) {
        this.bookingCode = bookingCode;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getRentalTypeId() {
        return rentalTypeId;
    }

    public void setRentalTypeId(int rentalTypeId) {
        this.rentalTypeId = rentalTypeId;
    }

    public int getPickupLocationId() {
        return pickupLocationId;
    }

    public void setPickupLocationId(int pickupLocationId) {
        this.pickupLocationId = pickupLocationId;
    }

    public int getReturnLocationId() {
        return returnLocationId;
    }

    public void setReturnLocationId(int returnLocationId) {
        this.returnLocationId = returnLocationId;
    }

    public LocalDateTime getStartDatetime() {
        return startDatetime;
    }

    public void setStartDatetime(LocalDateTime startDatetime) {
        this.startDatetime = startDatetime;
    }

    public LocalDateTime getEndDatetime() {
        return endDatetime;
    }

    public void setEndDatetime(LocalDateTime endDatetime) {
        this.endDatetime = endDatetime;
    }

    public BigDecimal getReservationFee() {
        return reservationFee;
    }

    public void setReservationFee(BigDecimal reservationFee) {
        this.reservationFee = reservationFee;
    }

    public BigDecimal getDepositFee() {
        return depositFee;
    }

    public void setDepositFee(BigDecimal depositFee) {
        this.depositFee = depositFee;
    }

    public BigDecimal getRentalPricePerDay() {
        return rentalPricePerDay;
    }

    public void setRentalPricePerDay(BigDecimal rentalPricePerDay) {
        this.rentalPricePerDay = rentalPricePerDay;
    }

    public BigDecimal getTotalRentalAmount() {
        return totalRentalAmount;
    }

    public void setTotalRentalAmount(BigDecimal totalRentalAmount) {
        this.totalRentalAmount = totalRentalAmount;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }
}