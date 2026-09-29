package com.crs.dal;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class bookingDAO {

    private int bookingId;
    private String bookingCode;
    private int customerId;
    private int carId;
    private int rentalTypeId;
    private Timestamp reservationFrom;
    private BigDecimal depositFee;
    private BigDecimal totalRentalAmount;
    private String bookingStatus;
    private Timestamp createAt;
    private Timestamp updateAt;

    public bookingDAO() {
    }

    public bookingDAO(int bookingId, String bookingCode, int customerId,
                   int carId, int rentalTypeId, Timestamp reservationFrom,
                   BigDecimal depositFee, BigDecimal totalRentalAmount,
                   String bookingStatus, Timestamp createAt,
                   Timestamp updateAt) {
        this.bookingId = bookingId;
        this.bookingCode = bookingCode;
        this.customerId = customerId;
        this.carId = carId;
        this.rentalTypeId = rentalTypeId;
        this.reservationFrom = reservationFrom;
        this.depositFee = depositFee;
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

    public Timestamp getReservationFrom() {
        return reservationFrom;
    }

    public void setReservationFrom(Timestamp reservationFrom) {
        this.reservationFrom = reservationFrom;
    }

    public BigDecimal getDepositFee() {
        return depositFee;
    }

    public void setDepositFee(BigDecimal depositFee) {
        this.depositFee = depositFee;
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

    public Timestamp getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Timestamp createAt) {
        this.createAt = createAt;
    }

    public Timestamp getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Timestamp updateAt) {
        this.updateAt = updateAt;
    }
}
