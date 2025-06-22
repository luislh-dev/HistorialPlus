package com.historialplus.historialplus.audit.elastic;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.Arrays;

@Configuration
@EnableElasticsearchRepositories
public class ElasticsearchConfig   {

	@Value("${spring.elasticsearch.host:localhost}")
	private String host;

	@Value("${spring.elasticsearch.port:9201}")
	private int port;

	@Value("${spring.elasticsearch.username:fakeUser}")
	private String username;

	@Value("${spring.elasticsearch.password:fakePassword}")
	private String password;

	@Bean
	public ElasticsearchClient elasticsearchClient() {
		final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY,
			new UsernamePasswordCredentials(username, password));

		RestClient restClient = RestClient.builder(new HttpHost(host, port, "https"))
			.setHttpClientConfigCallback(httpClientBuilder ->
				httpClientBuilder
					.setDefaultCredentialsProvider(credentialsProvider)
					.setDefaultHeaders(Arrays.asList(
						new BasicHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON.toString()),
						new BasicHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.toString()))
					)
					.addInterceptorLast((HttpResponseInterceptor)(response, context) ->
						response.addHeader("X-Elastic-Product", "Elasticsearch")
					)
			)
			.build();

		ElasticsearchTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
		return new ElasticsearchClient(transport);
	}
}