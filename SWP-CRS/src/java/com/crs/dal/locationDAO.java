package com.crs.dal;

import com.crs.model.Location;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class locationDAO extends DBContext {

    public List<Location> getAllLocations() {
        List<Location> list = new ArrayList<>();
        String sql = "SELECT location_id, location_name, address, province, district, "
                + "latitude, longitude, status FROM Location";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to load locations from the database.", e);
        }
        return list;
    }

    public Location getLocationById(int locationId) {
        String sql = "SELECT location_id, location_name, address, province, district, "
                + "latitude, longitude, status FROM Location WHERE location_id = ?";
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

    public boolean addLocation(Location location) {
        String sql = "INSERT INTO Location (location_name, address, province, district, "
                + "latitude, longitude, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, location.getLocationName());
            ps.setString(2, location.getAddress());
            ps.setString(3, location.getProvince());
            ps.setString(4, location.getDistrict());
            ps.setBigDecimal(5, location.getLatitude());
            ps.setBigDecimal(6, location.getLongitude());
            ps.setString(7, location.getStatus() != null ? location.getStatus() : "ACTIVE");

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        location.setLocationId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateLocation(Location location) {
        String sql = "UPDATE Location SET location_name=?, address=?, province=?, district=?, "
                + "latitude=?, longitude=?, status=? WHERE location_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, location.getLocationName());
            ps.setString(2, location.getAddress());
            ps.setString(3, location.getProvince());
            ps.setString(4, location.getDistrict());
            ps.setBigDecimal(5, location.getLatitude());
            ps.setBigDecimal(6, location.getLongitude());
            ps.setString(7, location.getStatus());
            ps.setInt(8, location.getLocationId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteLocation(int locationId) {
        String sql = "DELETE FROM Location WHERE location_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, locationId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Location mapResultSet(ResultSet rs) throws SQLException {
        Location l = new Location();
        l.setLocationId(rs.getInt("location_id"));
        l.setLocationName(rs.getString("location_name"));
        l.setAddress(rs.getString("address"));
        l.setProvince(rs.getString("province"));
        l.setDistrict(rs.getString("district"));
        l.setLatitude(rs.getBigDecimal("latitude"));
        l.setLongitude(rs.getBigDecimal("longitude"));
        l.setStatus(rs.getString("status"));
        return l;
    }
}
