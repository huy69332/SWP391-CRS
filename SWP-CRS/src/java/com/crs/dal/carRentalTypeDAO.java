package com.crs.dal;

import com.crs.model.CarRentalType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class carRentalTypeDAO extends DBContext {

    public List<CarRentalType> getAllCarRentalTypes() {
        List<CarRentalType> list = new ArrayList<>();
        String sql = "SELECT car_id, rental_type_id FROM Car_Rental_Type";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to load car rental types from the database.", e);
        }
        return list;
    }

    public List<CarRentalType> getCarRentalTypesByCarId(int carId) {
        List<CarRentalType> list = new ArrayList<>();
        String sql = "SELECT car_id, rental_type_id FROM Car_Rental_Type WHERE car_id = ?";
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

    public List<CarRentalType> getCarRentalTypesByRentalTypeId(int rentalTypeId) {
        List<CarRentalType> list = new ArrayList<>();
        String sql = "SELECT car_id, rental_type_id FROM Car_Rental_Type WHERE rental_type_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, rentalTypeId);
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

    public boolean addCarRentalType(CarRentalType carRentalType) {
        String sql = "INSERT INTO Car_Rental_Type (car_id, rental_type_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, carRentalType.getCarId());
            ps.setInt(2, carRentalType.getRentalTypeId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCarRentalType(int carId, int rentalTypeId) {
        String sql = "DELETE FROM Car_Rental_Type WHERE car_id = ? AND rental_type_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, carId);
            ps.setInt(2, rentalTypeId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private CarRentalType mapResultSet(ResultSet rs) throws SQLException {
        CarRentalType crt = new CarRentalType();
        crt.setCarId(rs.getInt("car_id"));
        crt.setRentalTypeId(rs.getInt("rental_type_id"));
        return crt;
    }
}
