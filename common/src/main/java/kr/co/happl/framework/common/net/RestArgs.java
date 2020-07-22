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

import kr.co.happl.framework.common.utils.CommonUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * Rest API Call 처리를 위한 Arguments Class
 */
@SuppressWarnings("unused")
public class RestArgs {

  private String url;
  private Class<?> responseType;
  private MultiValueMap<String, String> queryParam;
  private Object requestBody;
  private Map<String, ?> uriArgs;
  private HttpHeaders headers;

  public RestArgs() {
  }

  /**
   * Rest Argument 객체 생성
   *
   * @param builder Rest Argument Builder 객체
   */
  public RestArgs(RestArgsBuilder builder) {
    this.url = builder.url;
    this.responseType = builder.responseType;
    this.queryParam = builder.queryParam;
    this.requestBody = builder.requestBody;
    this.uriArgs = builder.uriArgs;
    this.headers = builder.headers;
  }

  public HttpHeaders getHeaders() {
    return headers;
  }

  /**
   * Rest API Request Header 지정
   *
   * @param headers Request Header
   */
  public void setHeaders(HttpHeaders headers) {
    if (this.headers != null) {
      this.headers = new HttpHeaders();
    }

    headers.forEach((k, v) -> this.headers.addAll(k, v));
  }

  /**
   * URI 가져오기
   *
   * @return URI 정보
   */
  public URI getUri() {
    URI uri;

    UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromUriString(this.url);

    if (this.queryParam.size() > 0) {
      uriBuilder.queryParams(this.queryParam);
    }

    if (this.uriArgs.size() > 0) {
      uri = uriBuilder.build(this.uriArgs);
    } else {
      uri = uriBuilder.build().toUri();
    }
    return uri;
  }

  /**
   * Requeset Entity 객체 생성 후 반환
   *
   * @param method 생성할 HTTP Method
   * @return 생성된 Request Entity
   */
  public RequestEntity<?> getRequestEntity(HttpMethod method) {
    RequestEntity.BodyBuilder builder = RequestEntity.method(method, this.getUri());

    if (this.requestBody != null) {
      builder.body(this.requestBody);
    }

    if (this.headers.size() > 0) {
      builder.headers(this.headers);
    }

    return builder.build();
  }

  public Class<?> getResponseType() {
    return this.responseType;
  }

  public static class RestArgsBuilder {

    private final HttpHeaders headers = new HttpHeaders();
    private String url;
    private Class<?> responseType;
    private MultiValueMap<String, String> queryParam;
    private Object requestBody;
    private Map<String, ?> uriArgs;

    /**
     * Rest API Url 문자열 지정
     *
     * @param url Rest API Url 문자열
     * @return Builder 객체
     */
    public RestArgsBuilder url(String url) {
      this.url = url;
      return this;
    }

    public RestArgsBuilder responseType(Class<?> responseType) {
      this.responseType = responseType;
      return this;
    }

    /**
     * Query String 추가
     *
     * @param param 추가할 Query String Map
     * @return Builder 객체
     */
    public RestArgsBuilder queryParam(Map<String, String> param) {
      this.queryParam = CommonUtils.convertMapToMultivalue(param);
      return this;
    }

    /**
     * Query String 추가
     *
     * @param param 추가할 Query String 객체 (POJO)
     * @param <R>   Query String 객체 클래스 타입
     * @return Builder 객체
     */
    public <R> RestArgsBuilder queryParam(R param) {
      MultiValueMap<String, String> queryParam = new LinkedMultiValueMap<>();

      Field[] fileds = param.getClass().getDeclaredFields();
      for (Field field : fileds) {
        try {
          field.setAccessible(true);
          queryParam.add(
              field.getName(),
              URLEncoder.encode((String) field.get(param), StandardCharsets.UTF_8)
          );
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        }
      }
      this.queryParam = queryParam;
      return this;
    }

    /**
     * Rest API에 전달할 Request Body Contents
     *
     * @param body Request Body Contents (for POST Method)
     * @param <T>  Request Body Contents 클래스 타입
     * @return Builder 객체
     */
    public <T> RestArgsBuilder requestBody(T body) {
      this.requestBody = body;
      return this;
    }

    /**
     * Rest API Url Path Variables 값 지정
     *
     * @param args Path Variables 갑
     * @return Builder 객체
     */
    public RestArgsBuilder uriArgs(Map<String, ?> args) {
      this.uriArgs = args;
      return this;
    }

    /**
     * Rest API Call 시 전달될 Http Header 지정
     *
     * @param args HTTP Header 정보
     * @return Builder 객체
     */
    public RestArgsBuilder headers(Map<String, String> args) {
      this.headers.setAll(args);
      return this;
    }

    /**
     * Rest Arguments 인스턴스 생성
     *
     * @return 생성된 Rest Arguments 인스턴스
     */
    public RestArgs build() {
      return new RestArgs(this);
    }
  }

}
