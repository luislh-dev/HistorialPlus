package com.historialplus.historialplus;

import com.historialplus.historialplus.audit.aspect.AuditLogAspect;
import com.historialplus.historialplus.audit.repository.AuditLogRepository;
import com.historialplus.historialplus.audit.service.AuditLogServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class HistorialPlusApplicationTests {

    @MockBean
    private ElasticsearchOperations elasticsearchOperations;

    @MockBean
    private AuditLogRepository auditLogRepository;

    @MockBean
    private AuditLogServiceImpl auditLogService;

    @MockBean
    private AuditLogAspect auditLogAspect;

    @Test
    void contextLoads() {}

}
