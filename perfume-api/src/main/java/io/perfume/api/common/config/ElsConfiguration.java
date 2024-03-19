package io.perfume.api.common.config;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import co.elastic.clients.util.ContentType;
import java.util.List;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.message.BasicHeader;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.RefreshPolicy;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(
    basePackages = {"io.perfume.api.perfume.adapter.out.persistence.perfumeSearch"})
public class ElsConfiguration {

  /* ep:
  - Low Level Rest Client 설정
  */
  @Bean
  public ElasticsearchClient elasticsearchClient() {
    final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
    credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("", ""));

    //        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200))
    //                .setHttpClientConfigCallback(httpClientBuilder -> {
    //                    httpClientBuilder.disableAuthCaching();
    //                    httpClientBuilder.setDefaultHeaders(List.of(
    //                            new BasicHeader(
    //                                    HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_JSON)));
    //                    httpClientBuilder.addInterceptorLast((HttpResponseInterceptor)
    //                            (response, context) ->
    //                                    response.addHeader("X-Elastic-Product", "Elasticsearch"));
    //                    return
    // httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
    //                })
    //                .build();

    RestClient restClient =
        RestClient.builder(new HttpHost("localhost", 9200))
            .setHttpClientConfigCallback(
                httpClientBuilder ->
                    httpClientBuilder
                        .setDefaultHeaders(
                            List.of(
                                new BasicHeader(
                                    HttpHeaders.CONTENT_TYPE,
                                    ContentType.APPLICATION_JSON.toString())))
                        .addInterceptorFirst(
                            (HttpResponseInterceptor)
                                (response, context) ->
                                    response.addHeader("X-Elastic-Product", "Elasticsearch")))
            .build();

    RestClientTransport restClientTransport =
        new RestClientTransport(restClient, new JacksonJsonpMapper());
    ElasticsearchClient client = new ElasticsearchClient(restClientTransport);
    return client;
  }

  @Bean(name = {"elasticsearchOperations", "elasticsearchTemplate"})
  public ElasticsearchOperations elasticsearchTemplate(
      ElasticsearchConverter elasticsearchConverter) {
    ElasticsearchTemplate elasticsearchTemplate =
        new ElasticsearchTemplate(elasticsearchClient(), elasticsearchConverter);
    elasticsearchTemplate.setRefreshPolicy(RefreshPolicy.WAIT_UNTIL);
    //        elasticsearchTemplate.setRefreshPolicy(refreshPolicy());
    return elasticsearchTemplate;
  }
}
