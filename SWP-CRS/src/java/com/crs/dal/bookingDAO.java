package com.crs.dal;

import com.crs.model.Booking;
import com.crs.dal.DBContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class bookingDAO extends DBContext {

    public List<Booking> getAllBookings() {
        List<Booking> list = new ArrayList<>();
        String sql = "SELECT booking_id, booking_code, customer_id, car_id, rental_type_id, "
                + "pickup_location_id, return_location_id, start_datetime, end_datetime, "
                + "reservation_fee, deposit_fee, rental_price_per_day, total_rental_amount, "
                + "booking_status, create_at, update_at FROM Booking";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Booking getBookingById(int bookingId) {
        String sql = "SELECT booking_id, booking_code, customer_id, car_id, rental_type_id, "
                + "pickup_location_id, return_location_id, start_datetime, end_datetime, "
                + "reservation_fee, deposit_fee, rental_price_per_day, total_rental_amount, "
                + "booking_status, create_at, update_at FROM Booking WHERE booking_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Booking getBookingByCode(String bookingCode) {
        String sql = "SELECT booking_id, booking_code, customer_id, car_id, rental_type_id, "
                + "pickup_location_id, return_location_id, start_datetime, end_datetime, "
                + "reservation_fee, deposit_fee, rental_price_per_day, total_rental_amount, "
                + "booking_status, create_at, update_at FROM Booking WHERE booking_code = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, bookingCode);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addBooking(Booking booking) {
        String sql = "INSERT INTO Booking (booking_code, customer_id, car_id, rental_type_id, "
                + "pickup_location_id, return_location_id, start_datetime, end_datetime, "
                + "reservation_fee, deposit_fee, rental_price_per_day, total_rental_amount, "
                + "booking_status, create_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, booking.getBookingCode());
            ps.setInt(2, booking.getCustomerId());
            ps.setInt(3, booking.getCarId());
            ps.setInt(4, booking.getRentalTypeId());
            ps.setInt(5, booking.getPickupLocationId());
            ps.setInt(6, booking.getReturnLocationId());
            ps.setTimestamp(7, Timestamp.valueOf(booking.getStartDatetime()));
            ps.setTimestamp(8, Timestamp.valueOf(booking.getEndDatetime()));
            ps.setBigDecimal(9, booking.getReservationFee());
            ps.setBigDecimal(10, booking.getDepositFee());
            ps.setBigDecimal(11, booking.getRentalPricePerDay());
            ps.setBigDecimal(12, booking.getTotalRentalAmount());
            ps.setString(13, booking.getBookingStatus());
            ps.setTimestamp(14, Timestamp.valueOf(booking.getCreateAt() != null ? booking.getCreateAt() : java.time.LocalDateTime.now()));

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        booking.setBookingId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateBookingStatus(int bookingId, String status) {
        String sql = "UPDATE Booking SET booking_status = ?, update_at = NOW() WHERE booking_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, bookingId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateBooking(Booking booking) {
        String sql = "UPDATE Booking SET customer_id=?, car_id=?, rental_type_id=?, "
                + "pickup_location_id=?, return_location_id=?, start_datetime=?, end_datetime=?, "
                + "reservation_fee=?, deposit_fee=?, rental_price_per_day=?, total_rental_amount=?, "
                + "booking_status=?, update_at = NOW() WHERE booking_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, booking.getCustomerId());
            ps.setInt(2, booking.getCarId());
            ps.setInt(3, booking.getRentalTypeId());
            ps.setInt(4, booking.getPickupLocationId());
            ps.setInt(5, booking.getReturnLocationId());
            ps.setTimestamp(6, Timestamp.valueOf(booking.getStartDatetime()));
            ps.setTimestamp(7, Timestamp.valueOf(booking.getEndDatetime()));
            ps.setBigDecimal(8, booking.getReservationFee());
            ps.setBigDecimal(9, booking.getDepositFee());
            ps.setBigDecimal(10, booking.getRentalPricePerDay());
            ps.setBigDecimal(11, booking.getTotalRentalAmount());
            ps.setString(12, booking.getBookingStatus());
            ps.setInt(13, booking.getBookingId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Booking mapResultSet(ResultSet rs) throws SQLException {
        Booking b = new Booking();
        b.setBookingId(rs.getInt("booking_id"));
        b.setBookingCode(rs.getString("booking_code"));
        b.setCustomerId(rs.getInt("customer_id"));
        b.setCarId(rs.getInt("car_id"));
        b.setRentalTypeId(rs.getInt("rental_type_id"));
        b.setPickupLocationId(rs.getInt("pickup_location_id"));
        b.setReturnLocationId(rs.getInt("return_location_id"));
        b.setStartDatetime(rs.getTimestamp("start_datetime").toLocalDateTime());
        b.setEndDatetime(rs.getTimestamp("end_datetime").toLocalDateTime());
        b.setReservationFee(rs.getBigDecimal("reservation_fee"));
        b.setDepositFee(rs.getBigDecimal("deposit_fee"));
        b.setRentalPricePerDay(rs.getBigDecimal("rental_price_per_day"));
        b.setTotalRentalAmount(rs.getBigDecimal("total_rental_amount"));
        b.setBookingStatus(rs.getString("booking_status"));
        if (rs.getTimestamp("create_at") != null) {
            b.setCreateAt(rs.getTimestamp("create_at").toLocalDateTime());
        }
        if (rs.getTimestamp("update_at") != null) {
            b.setUpdateAt(rs.getTimestamp("update_at").toLocalDateTime());
        }
        return b;
    }
}