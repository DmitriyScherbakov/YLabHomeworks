package org.example.Repository;

import org.example.Database.DBConnection;
import org.example.Entity.AuditRecord;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp; // Import Timestamp class
import java.util.ArrayList;
import java.util.List;

public class AuditRepository {

    public void addAuditRecord(AuditRecord record) {
        String sql = "INSERT INTO service_tables.audit (user_id, action, timestamp) VALUES (?, ?, ?)";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, record.getUserId());
            statement.setString(2, record.getAction());
            // Set current timestamp
            statement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            statement.executeUpdate();
            System.out.println("Audit record added.");
        } catch (SQLException e) {
            System.out.println("Error adding audit record: " + e.getMessage());
        }
    }

    public List<AuditRecord> getAllAuditRecords() {
        List<AuditRecord> auditRecords = new ArrayList<>();
        String sql = "SELECT * FROM service_tables.audit";
        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int userId = resultSet.getInt("user_id");
                String action = resultSet.getString("action");
                Timestamp timestamp = resultSet.getTimestamp("timestamp"); // Get timestamp from result set
                AuditRecord record = new AuditRecord(id, userId, action, timestamp.toString());
                auditRecords.add(record);
            }
        } catch (SQLException e) {
            System.out.println("Error getting audit records: " + e.getMessage());
        }
        return auditRecords;
    }
}
