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

package kr.co.happl.framework.common.loader;

import kr.co.happl.framework.common.exception.BaseException;
import kr.co.happl.framework.common.utils.FileUtils;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * JAR 파일을 통해 동적으로 Class를 호출하기 위한 Class Loader
 *
 * @author JI YOONSEONG
 */
@SuppressWarnings("unused")
public class HapplClassLoader {

  private final List<URL> urls = new LinkedList<>();
  private String modulePath;
  private URLClassLoader classLoader;

  /**
   * 특정 경로의 JAR 파일을 로드하는 Class Loader 클래스
   */
  public HapplClassLoader() {
    this.createLoader(null);
  }

  /**
   * 특정 경로의 JAR 파일을 로드하는 Class Loader 클래스
   *
   * @param modulePath JAR 파일을 로드할 경로
   */
  public HapplClassLoader(String modulePath) {

    this.modulePath = modulePath;

    try {
      if (!FileUtils.existsAndMakeDir(this.modulePath)) {
        throw new BaseException("모듈 Path 생성 실패");
      }

      this.createLoader(new File(this.modulePath));

    } catch (BaseException e) {
      e.printStackTrace();
    }

  }

  /**
   * Class Loader 가져오기
   *
   * @return Happl Class Loader
   */
  public ClassLoader getClassLoader() {
    return this.classLoader;
  }

  /**
   * Class Loader 생성
   *
   * @param modulePath JAR 모듈이 포함된 디렉토리 경로
   */
  private void createLoader(@Nullable File modulePath) {
    try {
      for (File file : FileUtils.getFiles(modulePath)) {
        urls.add(file.toURI().toURL());
      }

      this.classLoader = new URLClassLoader(
          urls.toArray(new URL[0]),
          ClassLoader.getPlatformClassLoader()
      );
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Class Loader에 JAR 모듈 추가
   *
   * @param modulePath 추가할 Module 절대 경로
   * @return 모듈 추가 결과
   */
  public boolean addModule(@NonNull String modulePath) {
    boolean result;
    try {
      this.urls.add((new File(modulePath)).toURI().toURL());
      result = this.refreshLoader();
    } catch (MalformedURLException e) {
      e.printStackTrace();
      result = false;
    }
    return result;
  }

  /**
   * Happl Class Loader로부터 클래스 가져오기
   *
   * @param className 가져올 클래스 이름(ex. kr.co.happl.framework.common.utils.StringUtils)
   * @return 지정된 클래스
   */
  public Class<?> getClass(@NonNull String className) {
    return this.getClass(className, this.classLoader);
  }

  /**
   * 지정된 Class Loader로부터 클래스 가져오기
   *
   * @param className   가져올 클래스 이름(ex. kr.co.happl.framework.common.utils.StringUtils)
   * @param classLoader 가져올 대상 Class Loader
   * @return 지정된 클래스
   */
  public Class<?> getClass(@NonNull String className, @NonNull ClassLoader classLoader) {
    Class<?> clazz = null;

    try {
      clazz = Class.forName(className, true, classLoader);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    return clazz;
  }

  /**
   * Happl Class Loader로부터 지정된 클래스 인스턴스 생성하기
   * - 생성자에 Arguments가 있는 클래스의 인스턴스는 생성할 수 없습니다.
   *
   * @param className 인스턴스를 생성할 클래스 이름(ex. kr.co.happl.framework.common.utils.StringUtils)
   * @param <R>       인스턴스가 생성될 클래스 타입
   * @return 생성된 클래스 인스턴스
   */
  public <R> R getClassInstance(@NonNull String className) {
    return this.getClassInstance(className, new Class<?>[]{}, new Object[]{});
  }

  /**
   * Happl Class Loader로부터 지정된 클래스 인스턴스 생성하기
   *
   * @param className 인스턴스를 생성할 클래스 이름(ex. kr.co.happl.framework.common.utils.StringUtils)
   * @param <R>       인스턴스가 생성될 클래스 타입
   * @return 생성된 클래스 인스턴스
   */
  public <R> R getClassInstance(
      @NonNull String className,
      @NonNull Class<?>[] parameterType,
      @NonNull Object[] arguments
  ) {
    R instance = null;

    if (parameterType.length != arguments.length) {
      try {
        throw new BaseException("Argument의 Class Type과 Object의 쌍이 맞지 않습니다.");
      } catch (BaseException e) {
        e.printStackTrace();
      }
    } else {
      try {
        Class<?> clazz = this.getClass(className);
        //noinspection unchecked
        instance = (R) clazz.getDeclaredConstructor(parameterType).newInstance(arguments);
      } catch (InstantiationException | IllegalAccessException
          | InvocationTargetException | NoSuchMethodException e
      ) {
        e.printStackTrace();
      }
    }

    return instance;
  }

  /**
   * Class Loader 갱신
   *
   * @return 갱신 결과
   */
  public boolean refreshLoader() {
    boolean result = true;
    try {
      this.createLoader(new File(this.modulePath));
    } catch (Exception e) {
      result = false;
      e.printStackTrace();
    }
    return result;
  }

  /**
   * 특정 Annotation이 선언된 클래스 가져오기
   * - 검색 대상 패키지: kr.co.happl
   * - 검색 대상 Class Loader: System Class Loader
   *
   * @param clazz 검색할 Annotation Class
   * @return 검색된 클래스
   */
  public Set<Class<?>> getClassByAnnotation(@NonNull Class<? extends Annotation> clazz) {
    return this.getClassByAnnotation(clazz, "kr.co.happl");
  }

  /**
   * 특정 Annotation이 선언된 클래스 가져오기
   * - 대상 클래스 Loader: System Class Loader
   *
   * @param clazz       검색할 Annotation Class
   * @param packageName 검색할 패키지 이름
   * @return 검색된 클래스
   */
  public Set<Class<?>> getClassByAnnotation(
      @NonNull Class<? extends Annotation> clazz,
      @NonNull String packageName
  ) {
    return this.getClassByAnnotation(clazz, packageName, ClassLoader.getSystemClassLoader());
  }

  /**
   * 특정 Annotation이 선언된 클래스 가져오기
   *
   * @param clazz       검색할 Annotation Class
   * @param packageName 검색할 패키지 이름
   * @param classLoader 검색할 Class Loader
   * @return 검색된 클래스
   */
  public Set<Class<?>> getClassByAnnotation(
      @NonNull Class<? extends Annotation> clazz,
      @NonNull String packageName,
      @NonNull ClassLoader classLoader
  ) {
    FilterBuilder filterBuilder = new FilterBuilder().includePackage(packageName);

    Reflections reflections = new Reflections(
        new ConfigurationBuilder()
            .filterInputsBy(filterBuilder)
            .setScanners(
                new TypeAnnotationsScanner().filterResultsBy(
                    new FilterBuilder().includePackage(clazz.getPackageName())
                ),
                new ResourcesScanner().filterResultsBy(filterBuilder),
                new SubTypesScanner().filterResultsBy(filterBuilder)
            )
            .setUrls(ClasspathHelper.forClassLoader(classLoader))
    );

    return reflections.getTypesAnnotatedWith(clazz, true);
  }

}
