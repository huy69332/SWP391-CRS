package com.crs.dal;

import com.crs.model.UserRole;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class userRoleDAO extends DBContext {

    public List<UserRole> getAllUserRoles() {
        List<UserRole> list = new ArrayList<>();
        String sql = "SELECT user_id, role_id FROM user_roles";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to load user roles from the database.", e);
        }
        return list;
    }

    public List<UserRole> getUserRolesByUserId(int userId) {
        List<UserRole> list = new ArrayList<>();
        String sql = "SELECT user_id, role_id FROM user_roles WHERE user_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
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

    public List<UserRole> getUserRolesByRoleId(int roleId) {
        List<UserRole> list = new ArrayList<>();
        String sql = "SELECT user_id, role_id FROM user_roles WHERE role_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, roleId);
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

    public boolean addUserRole(UserRole userRole) {
        String sql = "INSERT INTO user_roles (user_id, role_id) VALUES (?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userRole.getUserId());
            ps.setInt(2, userRole.getRoleId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteUserRole(int userId, int roleId) {
        String sql = "DELETE FROM user_roles WHERE user_id = ? AND role_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, roleId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private UserRole mapResultSet(ResultSet rs) throws SQLException {
        UserRole ur = new UserRole();
        ur.setUserId(rs.getInt("user_id"));
        ur.setRoleId(rs.getInt("role_id"));
        return ur;
    }
}
