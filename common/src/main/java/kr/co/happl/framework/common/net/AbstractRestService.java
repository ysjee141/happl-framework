/*
 * Copyright (c) $year, HAPPL Developer Group. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is a framework for developing JAVA based solutions through Spring Framework (Spring Boot).
 * This code may be used for any projects, but may not be suitable and makes no warranty.
 *
 * This code is publicly available and can be used with any solution.
 * However, HAPPL Developer Group is not responsible for any problems arising from duplication, modification
 * and redistribution of this code.
 *
 * If you have any questions regarding the reproduction, modification, redistribution or use of this code,
 * please contact us via the contact details below.
 *
 * @url https://velog.io/@ysjee141
 * @author JI YOONSEONG
 * @email ysjee141@gmail.com
 */

package kr.co.happl.framework.common.net;

import kr.co.happl.framework.common.service.net.RestService;
import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * Connection Pool 적용된 Rest Template 사용 서비스 추상 클래스
 */
@SuppressWarnings("unused")
public abstract class AbstractRestService implements RestService {

  private RestTemplate rest;
  private int connectionTimeout = 5000;
  private int specificMaxConnection = -1;
  private int maxPool = 100;
  private int maxPerRoute = 20;
  private String[] specificUrls = {};
  private boolean isSsl = false;
  private HttpHeaders headers = new HttpHeaders();

  /**
   * 클래스 생성자
   */
  public AbstractRestService() {
    this.rest = createRestTemplate();
  }

  /**
   * 클래스 생성자
   *
   * @param connectionTimeOut Connection Timeout
   */
  public AbstractRestService(int connectionTimeOut) {
    this.connectionTimeout = connectionTimeOut;
    this.rest = createRestTemplate();
  }

  /**
   * 클래스 생성자
   *
   * @param specificUrls          예외 Time Out 지정할 URL 배열
   * @param specificMaxConnection Connection Timeout
   */
  public AbstractRestService(String[] specificUrls, int specificMaxConnection) {
    this.specificUrls = specificUrls;
    this.specificMaxConnection = specificMaxConnection;
    this.rest = createRestTemplate();
  }

  /**
   * Rest Template SSL 사용 유무
   *
   * @param isSsl SSL 사용 유무
   */
  protected void setIsSsl(boolean isSsl) {
    this.isSsl = isSsl;
  }

  /**
   * Rest Template Connection Timeout 설정
   *
   * @param connectionTimeout Connection Timeout
   */
  protected void setConnectionTimeout(int connectionTimeout) {
    this.connectionTimeout = connectionTimeout;
  }

  /**
   * 특정 URL Connection Timeout 지정
   *
   * @param specificUrls          Timeout 지정할 URL
   * @param specificMaxConnection Connection Timeout
   */
  protected void setSpecificTimeout(String[] specificUrls, int specificMaxConnection) {
    this.specificUrls = specificUrls;
    this.specificMaxConnection = specificMaxConnection;
  }

  /**
   * Rest Template 설정 변경 후 갱신
   */
  protected void refreshRestTemplate() {
    this.rest = createRestTemplate();
  }

  /**
   * Connection Max Pool 설정
   *
   * @param maxPool Max Pool Size
   */
  private void setMaxPool(int maxPool) {
    this.maxPool = maxPool;
  }

  /**
   * URL 당 Max Pool 설정
   *
   * @param maxPerRoute Max Pool Size
   */
  private void setMaxPerRoute(int maxPerRoute) {
    this.maxPerRoute = maxPerRoute;
  }

  private RestTemplate createRestTemplate() {
    return new RestTemplate(getHttpRequestFactory());
  }

  /**
   * Connection Pool 적용된 Request Factory 생성
   *
   * @return Request Factory (with Pool)
   */
  private HttpComponentsClientHttpRequestFactory getHttpRequestFactory() {

    HttpComponentsClientHttpRequestFactory factory;
    factory = new HttpComponentsClientHttpRequestFactory();
    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    cm.setMaxTotal(maxPool);
    cm.setDefaultMaxPerRoute(maxPerRoute);

    for (String url : specificUrls) {
      HttpHost host = new HttpHost(url);
      cm.setMaxPerRoute(new HttpRoute(host), specificMaxConnection);
    }

    RequestConfig config = RequestConfig.custom()
        .setConnectTimeout(connectionTimeout)
        .setConnectionRequestTimeout(connectionTimeout)
        .setSocketTimeout(connectionTimeout).build();

    HttpClientBuilder httpClientBuilder = HttpClients.custom()
        .setConnectionManager(cm)
        .setDefaultRequestConfig(config);

    if (isSsl) {
      httpClientBuilder.setSSLSocketFactory(getSslSocketFactory());
    }

    factory.setHttpClient(httpClientBuilder.build());

    return factory;
  }

  private SSLConnectionSocketFactory getSslSocketFactory() {
    SSLConnectionSocketFactory sslsf = null;
    TrustManager easyTrustManager = new X509TrustManager() {

      @Override
      public X509Certificate[] getAcceptedIssuers() {
        // no-op
        return null;
      }

      @Override
      public void checkServerTrusted(X509Certificate[] chain, String authType) {
      }

      @Override
      public void checkClientTrusted(X509Certificate[] chain, String authType) {
      }
    };

    try {
      SSLContext sslcontext = SSLContext.getInstance("SSL");
      sslcontext.init(null, new TrustManager[]{easyTrustManager}, null);
      sslsf = new SSLConnectionSocketFactory(sslcontext, NoopHostnameVerifier.INSTANCE);
    } catch (NoSuchAlgorithmException | KeyManagementException e) {
      e.printStackTrace();
    }

    return sslsf;
  }

  public void setHeaders(HttpHeaders headers) {
    this.headers = headers;
  }

  /**
   * Rest API Call 실행
   *
   * @param method API Method Type
   * @param args   API Request Body
   * @return API Call Response
   */
  public final ResponseEntity<?> exchange(HttpMethod method, RestArgs args) {
    args.setHeaders(this.headers);
    return rest.exchange(
        args.getRequestEntity(method),
        args.getResponseType()
    );
  }

}
