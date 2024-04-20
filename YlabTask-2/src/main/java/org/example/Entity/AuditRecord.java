package org.example.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditRecord {
    private int id;
    private int userId;
    private String action;
    private String timestamp;

    public AuditRecord(int userId, String action, String timestamp) {
        this.userId = userId;
        this.action = action;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "AuditRecord{" +
                "id=" + id +
                ", userId=" + userId +
                ", action='" + action + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }
}
