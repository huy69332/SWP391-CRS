package com.crs.dal;

import com.crs.model.CarLocal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class carLocalDAO extends DBContext {

    public List<CarLocal> getAllCarLocals() {
        List<CarLocal> list = new ArrayList<>();
        String sql = "SELECT location_id, car_id, booking_id, latitude, longitude, recorded_at "
                + "FROM Car_Local";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to load car locations from the database.", e);
        }
        return list;
    }

    public CarLocal getCarLocalById(int locationId) {
        String sql = "SELECT location_id, car_id, booking_id, latitude, longitude, recorded_at "
                + "FROM Car_Local WHERE location_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, locationId);
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

    public List<CarLocal> getCarLocalsByBookingId(int bookingId) {
        List<CarLocal> list = new ArrayList<>();
        String sql = "SELECT location_id, car_id, booking_id, latitude, longitude, recorded_at "
                + "FROM Car_Local WHERE booking_id = ?";
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

    public List<CarLocal> getCarLocalsByCarId(int carId) {
        List<CarLocal> list = new ArrayList<>();
        String sql = "SELECT location_id, car_id, booking_id, latitude, longitude, recorded_at "
                + "FROM Car_Local WHERE car_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, carId);
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

    public boolean addCarLocal(CarLocal carLocal) {
        String sql = "INSERT INTO Car_Local (car_id, booking_id, latitude, longitude, recorded_at) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, carLocal.getCarId());
            if (carLocal.getBookingId() != null) {
                ps.setInt(2, carLocal.getBookingId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setBigDecimal(3, carLocal.getLatitude());
            ps.setBigDecimal(4, carLocal.getLongitude());
            if (carLocal.getRecordedAt() != null) {
                ps.setTimestamp(5, Timestamp.valueOf(carLocal.getRecordedAt()));
            } else {
                ps.setTimestamp(5, Timestamp.valueOf(java.time.LocalDateTime.now()));
            }

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        carLocal.setLocationId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCarLocal(CarLocal carLocal) {
        String sql = "UPDATE Car_Local SET car_id=?, booking_id=?, latitude=?, longitude=?, "
                + "recorded_at=? WHERE location_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, carLocal.getCarId());
            if (carLocal.getBookingId() != null) {
                ps.setInt(2, carLocal.getBookingId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }
            ps.setBigDecimal(3, carLocal.getLatitude());
            ps.setBigDecimal(4, carLocal.getLongitude());
            if (carLocal.getRecordedAt() != null) {
                ps.setTimestamp(5, Timestamp.valueOf(carLocal.getRecordedAt()));
            } else {
                ps.setNull(5, Types.TIMESTAMP);
            }
            ps.setInt(6, carLocal.getLocationId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCarLocal(int locationId) {
        String sql = "DELETE FROM Car_Local WHERE location_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, locationId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private CarLocal mapResultSet(ResultSet rs) throws SQLException {
        CarLocal c = new CarLocal();
        c.setLocationId(rs.getInt("location_id"));
        c.setCarId(rs.getInt("car_id"));
        int bookingId = rs.getInt("booking_id");
        if (!rs.wasNull()) {
            c.setBookingId(bookingId);
        }
        c.setLatitude(rs.getBigDecimal("latitude"));
        c.setLongitude(rs.getBigDecimal("longitude"));
        if (rs.getTimestamp("recorded_at") != null) {
            c.setRecordedAt(rs.getTimestamp("recorded_at").toLocalDateTime());
        }
        return c;
    }
}
