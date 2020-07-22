/*
 * Copyright(c) 2020, HAPPL Developer Group. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is a framework for developing JAVA based solutions through Spring Framework
 * (or Spring Boot). This code may be used for any projects, but may not be suitable and makes
 * no warranty.
 *
 * This code is publicly available and can be used with any solution.
 * However, HAPPL Developer Group is not responsible for any problems arising from duplication,
 * modification and redistribution of this code.
 *
 * If you have any questions regarding the reproduction, modification, redistribution or use of
 * this code, please contact us via the contact details below.
 *
 * @url https://velog.io/@ysjee141
 * @author JI YOONSEONG
 * @email ysjee141@gmail.com
 */

/*
 */

package kr.co.happl.framework.data.config;

import kr.co.happl.framework.data.annotations.MapperOne;
import kr.co.happl.framework.data.enums.MapperOrders;

import javax.validation.constraints.NotNull;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Map;

/**
 * Data Mapper 설정을 위한 Data Source Configuration 클래스<br/>
 * 다중 Data Source 사용을 위해 {@link ArrayList} 클래스 상속 받음<br/>
 * Source Properties는 {@link com.zaxxer.hikari.HikariDataSource} 규칙을 따름.
 * <pre>
 *   [YAML 구조]
 *   datasource:
 *     - name: Data Source Name {{@link String}}
 *       basePackage: Base Package for Scanning Mapper {{@link String}}
 *       order: {@link MapperOrders}
 *       source:
 *         jdbcUrl: JDBC URL {{@link String}}
 *         username: JDBC USERNAME {{@link String}}
 *         password: JDBC PASSWORD {{@link String}}
 *         driverClassName: JDBC DRIVER CLASS NAME
 *         maximumPoolSize: max pool size {{@link Integer}}
 * </pre>
 *
 * @author JI YOONSEONG
 **/
@SuppressWarnings("unused")
public class DataRepositoryConfig extends ArrayList<DataRepositoryConfig.DrcDataSource> {

  private static final long serialVersionUID = -2912230143786700292L;

  public static class DrcDataSource {
    @NotNull
    private String name;
    private String basePackage = "kr.co.happl";
    private Class<? extends Annotation> order = MapperOne.class;
    private Map<String, String> source;

    /**
     * Mapper 스캔을 위한 Base Package 이름 가져오기
     *
     * @return Package 이름
     */
    public String getBasePackage() {
      return basePackage;
    }

    /**
     * Mapper 스캔을 위한 Base Package 이름 설정
     *
     * @param basePackage Mapper 스캔을 위한 Base Package 이름
     */
    public void setBasePackage(String basePackage) {
      this.basePackage = basePackage;
    }

    /**
     * Mapper 스캔을 위한 Mapper Annotation 가져오기
     *
     * @return Mapper 스캔을 위한 Annotation
     */
    public Class<? extends Annotation> getOrder() {
      return order;
    }

    /**
     * Mapper 스캔을 위한 Mapper Annotation 설정
     *
     * @param order Mapper Annotation Enum
     */

    public void setOrder(MapperOrders order) {
      this.order = order.get();
    }

    /**
     * Repository 구분을 위한 Repository 이름
     *
     * @return Repository 이름
     */
    public String getName() {
      return name;
    }

    /**
     * Repository 구분을 위한 Repository 이름
     *
     * @param name Repository 이름
     */
    public void setName(String name) {
      this.name = name;
    }

    /**
     * Hikaria Data Source 설정 가져오기
     *
     * @return Hikaria Data Source 설정
     */
    public Map<String, String> getSource() {
      return source;
    }

    /**
     * Hikaria Data Source 설정 하기
     *
     * @param source Hikaria Data Source 설정
     */
    public void setSource(Map<String, String> source) {
      this.source = source;
    }
  }

}
