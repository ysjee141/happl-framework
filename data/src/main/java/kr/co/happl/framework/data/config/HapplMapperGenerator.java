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

package kr.co.happl.framework.data.config;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.type.DateTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.SqlTimestampTypeHandler;
import org.apache.ibatis.type.StringTypeHandler;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

/**
 * Application Context 생성 후 MyBatis
 *
 * @author JI YOONSEONG
 **/
@Component
public class HapplMapperGenerator {

  /**
   * 다중 Data Source 사용을 위한 MyBatis Mapper 생성 후 Bean 등록
   *
   * @param context Application Context
   */
  public static void createMappers(ApplicationContext context) {
    LazyHolder.INSTANCE.create(context);
  }

  /**
   * 다중 Data Source 사용을 위한 MyBatis Mapper Generator
   * Application 구동시 YAML(또는 Properties)에 선언된 Data Source 기반으로
   * MyBatis Mapper를 생성 후 Bean 등록
   *
   * @param context Application Context {@link ApplicationContext}
   */
  public void create(ApplicationContext context) {
    BeanDefinitionRegistry beanFactory =
        (BeanDefinitionRegistry) ((GenericApplicationContext) context).getBeanFactory();

    beanFactory.removeBeanDefinition("sqlSessionFactory");
    beanFactory.removeBeanDefinition("sqlSessionTemplate");

    // MyBatis Configuration
    /*
       MyBatis Config XML
       <configuration>
         <settings>
           <setting name="lazyLoadingEnabled" value="true" />
           <setting name="multipleResultSetsEnabled" value="true" />
           <setting name="useColumnLabel" value="true" />
           <setting name="defaultExecutorType" value="SIMPLE" />
           <setting name="mapUnderscoreToCamelCase" value="true" />
           <setting name="callSettersOnNulls" value="true"/>
           <setting name="cacheEnabled" value="false" />
           <setting name="jdbcTypeForNull" value="NULL" />
         </settings>

         <typeHandlers>
           <typeHandler handler="org.apache.ibatis.type.StringTypeHandler"
                 javaType="java.lang.String" />
           <typeHandler handler="org.apache.ibatis.type.SqlTimestampTypeHandler"
                 javaType="java.sql.Timestamp" />
           <typeHandler handler="org.apache.ibatis.type.DateTypeHandler"
                 javaType="java.sql.Time" />
           <typeHandler handler="org.apache.ibatis.type.DateTypeHandler"
                 javaType="java.sql.Date" />
         </typeHandlers>
       </configuration>
     */

    Configuration myBatisConfiguration = new Configuration();
    myBatisConfiguration.setMapUnderscoreToCamelCase(true);
    myBatisConfiguration.setLazyLoadingEnabled(true);
    myBatisConfiguration.setMultipleResultSetsEnabled(true);
    myBatisConfiguration.setUseColumnLabel(true);
    myBatisConfiguration.setDefaultExecutorType(ExecutorType.SIMPLE);
    myBatisConfiguration.setCallSettersOnNulls(true);
    myBatisConfiguration.setCacheEnabled(false);
    myBatisConfiguration.setJdbcTypeForNull(JdbcType.NULL);
    myBatisConfiguration.getTypeHandlerRegistry().register(String.class, StringTypeHandler.class);
    myBatisConfiguration.getTypeHandlerRegistry()
        .register(Timestamp.class, SqlTimestampTypeHandler.class);
    myBatisConfiguration.getTypeHandlerRegistry().register(Time.class, DateTypeHandler.class);
    myBatisConfiguration.getTypeHandlerRegistry().register(Date.class, DateTypeHandler.class);

    DataRepositoryConfig config = context.getBean(DataRepositoryConfig.class);
    config.forEach(repository -> {
      // DataSource 등록(Hikara Data Source Type)
      String dataSourceName = repository.getName() + "DataSource";
      GenericBeanDefinition dataSourceBeanDefinition = new GenericBeanDefinition();
      dataSourceBeanDefinition.setBeanClass(HikariDataSource.class);
      dataSourceBeanDefinition.setPropertyValues(
          new MutablePropertyValues(repository.getSource())
      );

      beanFactory.registerBeanDefinition(dataSourceName, dataSourceBeanDefinition);

      // SqlSessionFactory
      String sqlSessionFactoryName = repository.getName() + "SqlSessionFactory";
      AbstractBeanDefinition sqlSessionFactory = BeanDefinitionBuilder
          .genericBeanDefinition(SqlSessionFactoryBean.class)
          .addPropertyReference("dataSource", dataSourceName)
          .addPropertyValue("configuration", myBatisConfiguration)
          .addPropertyValue(
              "mapperLocations",
              "classpath*:/mapper/" + repository.getName() + "/*.xml"
          )
          .getBeanDefinition();

      beanFactory.registerBeanDefinition(sqlSessionFactoryName, sqlSessionFactory);

      // SqlSessionTemplate
      String sqlSessionTemplateName = repository.getName() + "SqlSessionTemplate";
      AbstractBeanDefinition sqlSessionTemplate = BeanDefinitionBuilder
          .genericBeanDefinition(SqlSessionTemplate.class)
          .addConstructorArgReference(sqlSessionFactoryName)
          .getBeanDefinition();

      beanFactory.registerBeanDefinition(sqlSessionTemplateName, sqlSessionTemplate);

      // TransactionManager
      String transactionManagerName = repository.getName() + "TransactionManager";
      AbstractBeanDefinition transactionManager = BeanDefinitionBuilder
          .genericBeanDefinition(DataSourceTransactionManager.class)
          .addPropertyReference("dataSource", dataSourceName)
          .getBeanDefinition();

      beanFactory.registerBeanDefinition(transactionManagerName, transactionManager);

      // MapperScannerConfigurer
      String mapperScannerName = repository.getName() + "MapperScanner";
      BeanDefinitionBuilder builder = BeanDefinitionBuilder
          .genericBeanDefinition(MapperScannerConfigurer.class)
          .addPropertyReference("sqlSessionFactoryBeanName", sqlSessionFactoryName)
          .addPropertyValue("basePackage", repository.getBasePackage() + "*");

      if (repository.getOrder() != null) {
        builder.addPropertyValue("annotationClass", repository.getOrder());
      }

      beanFactory.registerBeanDefinition(mapperScannerName, builder.getBeanDefinition());

    });
  }

  private static class LazyHolder {
    public static final HapplMapperGenerator INSTANCE = new HapplMapperGenerator();
  }

}
