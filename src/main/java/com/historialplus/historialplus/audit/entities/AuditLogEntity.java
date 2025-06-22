package com.historialplus.historialplus.audit.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.Instant;

@Document(indexName = "audit_logs")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditLogEntity {
	@Id
	private String id;

	@Field(type = FieldType.Date)
	private Instant timestamp;

	@Field(type = FieldType.Keyword)
	private String username;

	@Field(type = FieldType.Keyword)
	private String method;

	@Field(type = FieldType.Keyword)
	private String endpoint;

	@Field(type = FieldType.Keyword)
	private String ipAddress;

	@Field(type = FieldType.Text)
	private String requestPayload;
}
