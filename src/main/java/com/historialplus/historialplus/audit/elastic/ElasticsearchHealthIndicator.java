package com.historialplus.historialplus.audit.elastic;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.HealthStatus;
import co.elastic.clients.elasticsearch.cluster.HealthResponse;
import org.springframework.boot.actuate.autoconfigure.health.ConditionalOnEnabledHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnEnabledHealthIndicator("elasticsearch")
public class ElasticsearchHealthIndicator implements HealthIndicator {
	private final ElasticsearchClient elasticsearchClient;

	public ElasticsearchHealthIndicator(ElasticsearchClient elasticsearchClient) {
		this.elasticsearchClient = elasticsearchClient;
	}

	@Override
	public Health health() {
		try {
			HealthResponse resp = elasticsearchClient.cluster().health();
			HealthStatus esStatus = resp.status();
			if (esStatus == HealthStatus.Green) {
				return Health.up()
					.withDetail("esStatus", esStatus)
					.build();
			} else {
				return Health.down()
					.withDetail("esStatus", esStatus)
					.withDetail("number_of_nodes", resp.numberOfNodes())
					.withDetail("unassigned_shards", resp.unassignedShards())
					.build();
			}
		} catch (Exception e) {
			return Health.down(e).build();
		}
	}
}
