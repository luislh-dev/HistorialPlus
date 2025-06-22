package com.historialplus.historialplus.audit.repository;

import com.historialplus.historialplus.audit.entities.AuditLogEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuditLogRepository extends ElasticsearchRepository<AuditLogEntity, String> {
}