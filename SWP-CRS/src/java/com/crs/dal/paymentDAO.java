package com.crs.dal;

import com.crs.model.Payment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class paymentDAO extends DBContext {

    public List<Payment> getAllPayments() {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT payment_id, booking_id, payment_status, transaction_id, "
                + "paid_amount, paid_at, payment_method, refunded_amount, refunded_at FROM Payment";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to load payments from the database.", e);
        }
        return list;
    }

    public Payment getPaymentById(int paymentId) {
        String sql = "SELECT payment_id, booking_id, payment_status, transaction_id, "
                + "paid_amount, paid_at, payment_method, refunded_amount, refunded_at "
                + "FROM Payment WHERE payment_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, paymentId);
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

    public List<Payment> getPaymentsByBookingId(int bookingId) {
        List<Payment> list = new ArrayList<>();
        String sql = "SELECT payment_id, booking_id, payment_status, transaction_id, "
                + "paid_amount, paid_at, payment_method, refunded_amount, refunded_at "
                + "FROM Payment WHERE booking_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, bookingId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSet(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addPayment(Payment payment) {
        String sql = "INSERT INTO Payment (booking_id, payment_status, transaction_id, "
                + "paid_amount, paid_at, payment_method, refunded_amount, refunded_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, payment.getBookingId());
            ps.setString(2, payment.getPaymentStatus());
            ps.setString(3, payment.getTransactionId());
            ps.setBigDecimal(4, payment.getPaidAmount());
            if (payment.getPaidAt() != null) {
                ps.setTimestamp(5, Timestamp.valueOf(payment.getPaidAt()));
            } else {
                ps.setNull(5, Types.TIMESTAMP);
            }
            ps.setString(6, payment.getPaymentMethod());
            ps.setBigDecimal(7, payment.getRefundedAmount());
            if (payment.getRefundedAt() != null) {
                ps.setTimestamp(8, Timestamp.valueOf(payment.getRefundedAt()));
            } else {
                ps.setNull(8, Types.TIMESTAMP);
            }

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        payment.setPaymentId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePayment(Payment payment) {
        String sql = "UPDATE Payment SET booking_id=?, payment_status=?, transaction_id=?, "
                + "paid_amount=?, paid_at=?, payment_method=?, refunded_amount=?, refunded_at=? "
                + "WHERE payment_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, payment.getBookingId());
            ps.setString(2, payment.getPaymentStatus());
            ps.setString(3, payment.getTransactionId());
            ps.setBigDecimal(4, payment.getPaidAmount());
            if (payment.getPaidAt() != null) {
                ps.setTimestamp(5, Timestamp.valueOf(payment.getPaidAt()));
            } else {
                ps.setNull(5, Types.TIMESTAMP);
            }
            ps.setString(6, payment.getPaymentMethod());
            ps.setBigDecimal(7, payment.getRefundedAmount());
            if (payment.getRefundedAt() != null) {
                ps.setTimestamp(8, Timestamp.valueOf(payment.getRefundedAt()));
            } else {
                ps.setNull(8, Types.TIMESTAMP);
            }
            ps.setInt(9, payment.getPaymentId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePaymentStatus(int paymentId, String status) {
        String sql = "UPDATE Payment SET payment_status = ? WHERE payment_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, paymentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deletePayment(int paymentId) {
        String sql = "DELETE FROM Payment WHERE payment_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, paymentId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Payment mapResultSet(ResultSet rs) throws SQLException {
        Payment p = new Payment();
        p.setPaymentId(rs.getInt("payment_id"));
        p.setBookingId(rs.getInt("booking_id"));
        p.setPaymentStatus(rs.getString("payment_status"));
        p.setTransactionId(rs.getString("transaction_id"));
        p.setPaidAmount(rs.getBigDecimal("paid_amount"));
        if (rs.getTimestamp("paid_at") != null) {
            p.setPaidAt(rs.getTimestamp("paid_at").toLocalDateTime());
        }
        p.setPaymentMethod(rs.getString("payment_method"));
        p.setRefundedAmount(rs.getBigDecimal("refunded_amount"));
        if (rs.getTimestamp("refunded_at") != null) {
            p.setRefundedAt(rs.getTimestamp("refunded_at").toLocalDateTime());
        }
        return p;
    }
}
