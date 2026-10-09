package com.crs.dal;

import com.crs.model.HandoverInspection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class handoverInspectionDAO extends DBContext {

    public List<HandoverInspection> getAllInspections() {
        List<HandoverInspection> list = new ArrayList<>();
        String sql = "SELECT inspection_id, booking_id, inspected_by, inspection_type, mileage, "
                + "fuel_level, car_additional, note, evidence_url, inspected_at "
                + "FROM Handover_Inspection";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSet(rs));
            }
        } catch (SQLException e) {
            throw new IllegalStateException("Failed to load inspections from the database.", e);
        }
        return list;
    }

    public HandoverInspection getInspectionById(int inspectionId) {
        String sql = "SELECT inspection_id, booking_id, inspected_by, inspection_type, mileage, "
                + "fuel_level, car_additional, note, evidence_url, inspected_at "
                + "FROM Handover_Inspection WHERE inspection_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, inspectionId);
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

    public List<HandoverInspection> getInspectionsByBookingId(int bookingId) {
        List<HandoverInspection> list = new ArrayList<>();
        String sql = "SELECT inspection_id, booking_id, inspected_by, inspection_type, mileage, "
                + "fuel_level, car_additional, note, evidence_url, inspected_at "
                + "FROM Handover_Inspection WHERE booking_id = ?";
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

    public boolean addInspection(HandoverInspection inspection) {
        String sql = "INSERT INTO Handover_Inspection (booking_id, inspected_by, inspection_type, "
                + "mileage, fuel_level, car_additional, note, evidence_url, inspected_at) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, inspection.getBookingId());
            ps.setInt(2, inspection.getInspectedBy());
            ps.setString(3, inspection.getInspectionType());
            ps.setBigDecimal(4, inspection.getMileage());
            ps.setBigDecimal(5, inspection.getFuelLevel());
            ps.setString(6, inspection.getCarAdditional());
            ps.setString(7, inspection.getNote());
            ps.setString(8, inspection.getEvidenceUrl());
            if (inspection.getInspectedAt() != null) {
                ps.setTimestamp(9, Timestamp.valueOf(inspection.getInspectedAt()));
            } else {
                ps.setTimestamp(9, Timestamp.valueOf(java.time.LocalDateTime.now()));
            }

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        inspection.setInspectionId(rs.getInt(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateInspection(HandoverInspection inspection) {
        String sql = "UPDATE Handover_Inspection SET booking_id=?, inspected_by=?, inspection_type=?, "
                + "mileage=?, fuel_level=?, car_additional=?, note=?, evidence_url=?, inspected_at=? "
                + "WHERE inspection_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, inspection.getBookingId());
            ps.setInt(2, inspection.getInspectedBy());
            ps.setString(3, inspection.getInspectionType());
            ps.setBigDecimal(4, inspection.getMileage());
            ps.setBigDecimal(5, inspection.getFuelLevel());
            ps.setString(6, inspection.getCarAdditional());
            ps.setString(7, inspection.getNote());
            ps.setString(8, inspection.getEvidenceUrl());
            if (inspection.getInspectedAt() != null) {
                ps.setTimestamp(9, Timestamp.valueOf(inspection.getInspectedAt()));
            } else {
                ps.setNull(9, Types.TIMESTAMP);
            }
            ps.setInt(10, inspection.getInspectionId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteInspection(int inspectionId) {
        String sql = "DELETE FROM Handover_Inspection WHERE inspection_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, inspectionId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private HandoverInspection mapResultSet(ResultSet rs) throws SQLException {
        HandoverInspection h = new HandoverInspection();
        h.setInspectionId(rs.getInt("inspection_id"));
        h.setBookingId(rs.getInt("booking_id"));
        h.setInspectedBy(rs.getInt("inspected_by"));
        h.setInspectionType(rs.getString("inspection_type"));
        h.setMileage(rs.getBigDecimal("mileage"));
        h.setFuelLevel(rs.getBigDecimal("fuel_level"));
        h.setCarAdditional(rs.getString("car_additional"));
        h.setNote(rs.getString("note"));
        h.setEvidenceUrl(rs.getString("evidence_url"));
        if (rs.getTimestamp("inspected_at") != null) {
            h.setInspectedAt(rs.getTimestamp("inspected_at").toLocalDateTime());
        }
        return h;
    }
}
