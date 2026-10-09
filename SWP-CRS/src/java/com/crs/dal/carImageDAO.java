package com.crs.dal;

import com.crs.model.CarImage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class carImageDAO extends DBContext {

    public List<CarImage> getAllCarImages() {
        List<CarImage> list = new ArrayList<>();
        String sql = "SELECT img_id, car_id, img_url, img_type, is_primary, created_at FROM Car_image";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to load car images from the database.", e);
        }
        return list;
    }

    public CarImage getCarImageById(int imgId) {
        String sql = "SELECT img_id, car_id, img_url, img_type, is_primary, created_at "
                + "FROM Car_image WHERE img_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, imgId);
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

    public List<CarImage> getCarImagesByCarId(int carId) {
        List<CarImage> list = new ArrayList<>();
        String sql = "SELECT img_id, car_id, img_url, img_type, is_primary, created_at "
                + "FROM Car_image WHERE car_id = ?";
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

    public boolean addCarImage(CarImage carImage) {
        String sql = "INSERT INTO Car_image (car_id, img_url, img_type, is_primary, created_at) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, carImage.getCarId());
            ps.setString(2, carImage.getImgUrl());
            ps.setString(3, carImage.getImgType());
            ps.setBoolean(4, carImage.isPrimary());
            ps.setTimestamp(5, Timestamp.valueOf(carImage.getCreatedAt() != null ? carImage.getCreatedAt() : java.time.LocalDateTime.now()));

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        carImage.setImgId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateCarImage(CarImage carImage) {
        String sql = "UPDATE Car_image SET car_id=?, img_url=?, img_type=?, is_primary=? "
                + "WHERE img_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, carImage.getCarId());
            ps.setString(2, carImage.getImgUrl());
            ps.setString(3, carImage.getImgType());
            ps.setBoolean(4, carImage.isPrimary());
            ps.setInt(5, carImage.getImgId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteCarImage(int imgId) {
        String sql = "DELETE FROM Car_image WHERE img_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, imgId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private CarImage mapResultSet(ResultSet rs) throws SQLException {
        CarImage ci = new CarImage();
        ci.setImgId(rs.getInt("img_id"));
        ci.setCarId(rs.getInt("car_id"));
        ci.setImgUrl(rs.getString("img_url"));
        ci.setImgType(rs.getString("img_type"));
        ci.setPrimary(rs.getBoolean("is_primary"));
        if (rs.getTimestamp("created_at") != null) {
            ci.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        }
        return ci;
    }
}
