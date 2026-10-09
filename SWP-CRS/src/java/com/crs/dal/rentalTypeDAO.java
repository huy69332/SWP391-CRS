package com.crs.dal;

import com.crs.model.RentalType;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class rentalTypeDAO extends DBContext {

    public List<RentalType> getAllRentalTypes() {
        List<RentalType> list = new ArrayList<>();
        String sql = "SELECT rental_type_id, type_name, addition_fee FROM Rental_Type";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to load rental types from the database.", e);
        }
        return list;
    }

    public RentalType getRentalTypeById(int rentalTypeId) {
        String sql = "SELECT rental_type_id, type_name, addition_fee FROM Rental_Type "
                + "WHERE rental_type_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, rentalTypeId);
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

    public boolean addRentalType(RentalType rentalType) {
        String sql = "INSERT INTO Rental_Type (type_name, addition_fee) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, rentalType.getTypeName());
            ps.setBigDecimal(2, rentalType.getAdditionFee());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        rentalType.setRentalTypeId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateRentalType(RentalType rentalType) {
        String sql = "UPDATE Rental_Type SET type_name = ?, addition_fee = ? WHERE rental_type_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, rentalType.getTypeName());
            ps.setBigDecimal(2, rentalType.getAdditionFee());
            ps.setInt(3, rentalType.getRentalTypeId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteRentalType(int rentalTypeId) {
        String sql = "DELETE FROM Rental_Type WHERE rental_type_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, rentalTypeId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private RentalType mapResultSet(ResultSet rs) throws SQLException {
        RentalType rt = new RentalType();
        rt.setRentalTypeId(rs.getInt("rental_type_id"));
        rt.setTypeName(rs.getString("type_name"));
        rt.setAdditionFee(rs.getBigDecimal("addition_fee"));
        return rt;
    }
}
