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

package kr.co.happl.framework.common.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Application Context 통한 처리를 위한 Provider 클래스
 */
@Component
@SuppressWarnings("unused")
public class ApplicationCtxProvider implements ApplicationContextAware {

  private static ApplicationContext context;

  public static ApplicationContext getContext() {
    return context;
  }

  /**
   * 인스턴스를 Application Bean 등록
   *
   * @param instance 등록할 인스턴스
   * @param name     등록할 인스턴스 이름
   * @param <T>      등록할 인스턴스 클래스 타입 (Generic)
   */
  public static <T> void autowireBean(T instance, String name) {
    AutowireCapableBeanFactory factory = context.getAutowireCapableBeanFactory();
    factory.autowireBean(instance);
    factory.initializeBean(instance, name);
  }

  public static Object autowireBean(Class<?> instance) {
    AutowireCapableBeanFactory factory = context.getAutowireCapableBeanFactory();
    return factory.autowire(instance, AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE, true);
  }

  /**
   * Application Context 내 Autowire BeanFactory 가져오기
   *
   * @return 실행 중인 Application Context Autowire BeanFactory
   */
  public static AutowireCapableBeanFactory getAutowireBeanFactory() {
    return context.getAutowireCapableBeanFactory();
  }

  /**
   * Singleton Bean 등록을 위한 Bean Factory 가져오기
   *
   * @return Singleton Bean Factory
   * @see ConfigurableListableBeanFactory
   */
  public static ConfigurableListableBeanFactory getSingletonBeanFactory() {
    return ((StaticApplicationContext) context).getBeanFactory();
  }

  /**
   * 다중 Bean 등록을 위한 Bean Registry 가져오기
   *
   * @return Bean Definition Registry
   * @see BeanDefinitionRegistry
   * @see GenericApplicationContext
   */
  public static BeanDefinitionRegistry getBeanDefinitionRegistry() {
    return (BeanDefinitionRegistry) ((GenericApplicationContext) context).getBeanFactory();
  }

  /**
   * Bean 이름으로 Bean 인스턴스 검색
   *
   * @param beanName 검색할 Bean 이름
   * @return 검색된 Bean 인스턴스
   */
  public static Object getBean(String beanName) {
    return context.getBean(beanName);
  }

  /**
   * Bean 이름과 Bean Class Type을 통해 Bean 인스턴스 검색
   *
   * @param beanName     검색할 Bean 이름
   * @param requiredType 검색할 Bean Class Type; 반드시 일치해야함(인터페이스 또는 부모 클래스 가능)
   * @return 검색된 Bean 인스턴스
   */
  public static <T> T getBean(String beanName, Class<T> requiredType) {
    return context.getBean(beanName, requiredType);
  }

  /**
   * Bean Class Type 을 통해 Bean 검색
   *
   * @param clazz 검색할 Bean Class Type
   * @param <T>   Bean Class Teyp; 반드시 일치해야함(인터페이스 또는 부모 클래스 가능)
   * @return 검색된 Bean 인스턴스 Map
   */
  public static <T> Map<String, T> getBeanOfType(Class<T> clazz) throws BeansException {
    return context.getBeansOfType(clazz);
  }

  @Override
  public void setApplicationContext(
      @NonNull ApplicationContext applicationContext
  ) throws BeansException {
    context = applicationContext;
  }
}
