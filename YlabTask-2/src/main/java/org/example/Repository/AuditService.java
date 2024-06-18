package org.example.Repository;

import org.example.Entity.AuditRecord;

import java.util.List;

public class AuditService {

    private final AuditRepository auditRepository;

    public AuditService() {
        this.auditRepository = new AuditRepository();
    }

    public void addAuditRecord(AuditRecord record) {
        auditRepository.addAuditRecord(record);
    }

    public List<AuditRecord> getAllAuditRecords() {
        return auditRepository.getAllAuditRecords();
    }

    public void printAllAuditRecords() {
        List<AuditRecord> auditRecords = auditRepository.getAllAuditRecords();
        System.out.println("All Audit Records:");
        for (AuditRecord record : auditRecords) {
            System.out.println(record);
        }
    }
}
