package com.crs.dal;

import com.crs.model.Car;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class carDAO extends DBContext {

    public List<Car> getAllCars() {
        List<Car> list = new ArrayList<>();
        String sql = "SELECT id, owner_id, car_name, license_plate, brand, model, seat, status, "
                + "price_per_day, fuel_type, description, transmission, reservation_fee, deposit_amount "
                + "FROM Cars";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to load cars from the database.", e);
        }
        return list;
    }

    public Car getCarById(int id) {
        String sql = "SELECT id, owner_id, car_name, license_plate, brand, model, seat, status, "
                + "price_per_day, fuel_type, description, transmission, reservation_fee, deposit_amount "
                + "FROM Cars WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
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

    public List<Car> getCarsByOwnerId(int ownerId) {
        List<Car> list = new ArrayList<>();
        String sql = "SELECT id, owner_id, car_name, license_plate, brand, model, seat, status, "
                + "price_per_day, fuel_type, description, transmission, reservation_fee, deposit_amount "
                + "FROM Cars WHERE owner_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, ownerId);
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

    public boolean addCar(Car car) {
        String sql = "INSERT INTO Cars (owner_id, car_name, license_plate, brand, model, seat, status, "
                + "price_per_day, fuel_type, description, transmission, reservation_fee, deposit_amount) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, car.getOwnerId());
            ps.setString(2, car.getCarName());
            ps.setString(3, car.getLicensePlate());
            ps.setString(4, car.getBrand());
            ps.setString(5, car.getModel());
            if (car.getSeat() != null) {
                ps.setInt(6, car.getSeat());
            } else {
                ps.setNull(6, Types.INTEGER);
            }
            ps.setString(7, car.getStatus() != null ? car.getStatus() : "PENDING");
            ps.setBigDecimal(8, car.getPricePerDay());
            ps.setString(9, car.getFuelType());
            ps.setString(10, car.getDescription());
            ps.setString(11, car.getTransmission());
            ps.setBigDecimal(12, car.getReservationFee());
            ps.setBigDecimal(13, car.getDepositAmount());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        car.setId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCar(Car car) {
        String sql = "UPDATE Cars SET owner_id=?, car_name=?, license_plate=?, brand=?, model=?, seat=?, "
                + "status=?, price_per_day=?, fuel_type=?, description=?, transmission=?, "
                + "reservation_fee=?, deposit_amount=? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, car.getOwnerId());
            ps.setString(2, car.getCarName());
            ps.setString(3, car.getLicensePlate());
            ps.setString(4, car.getBrand());
            ps.setString(5, car.getModel());
            if (car.getSeat() != null) {
                ps.setInt(6, car.getSeat());
            } else {
                ps.setNull(6, Types.INTEGER);
            }
            ps.setString(7, car.getStatus());
            ps.setBigDecimal(8, car.getPricePerDay());
            ps.setString(9, car.getFuelType());
            ps.setString(10, car.getDescription());
            ps.setString(11, car.getTransmission());
            ps.setBigDecimal(12, car.getReservationFee());
            ps.setBigDecimal(13, car.getDepositAmount());
            ps.setInt(14, car.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCarStatus(int id, String status) {
        String sql = "UPDATE Cars SET status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCar(int id) {
        String sql = "DELETE FROM Cars WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Car mapResultSet(ResultSet rs) throws SQLException {
        Car c = new Car();
        c.setId(rs.getInt("id"));
        c.setOwnerId(rs.getInt("owner_id"));
        c.setCarName(rs.getString("car_name"));
        c.setLicensePlate(rs.getString("license_plate"));
        c.setBrand(rs.getString("brand"));
        c.setModel(rs.getString("model"));
        int seat = rs.getInt("seat");
        if (!rs.wasNull()) {
            c.setSeat(seat);
        }
        c.setStatus(rs.getString("status"));
        c.setPricePerDay(rs.getBigDecimal("price_per_day"));
        c.setFuelType(rs.getString("fuel_type"));
        c.setDescription(rs.getString("description"));
        c.setTransmission(rs.getString("transmission"));
        c.setReservationFee(rs.getBigDecimal("reservation_fee"));
        c.setDepositAmount(rs.getBigDecimal("deposit_amount"));
        return c;
    }
}
