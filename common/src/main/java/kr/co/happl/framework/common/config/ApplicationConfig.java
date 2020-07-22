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

package kr.co.happl.framework.common.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@SuppressWarnings("unused")
public class ApplicationConfig {

  private final Properties properties = new Properties();

  private ApplicationConfig() {
    try (InputStream is = new FileInputStream("")) {
      this.properties.load(is);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static ApplicationConfig getInstance() {
    return LazyHolder.INSTANCE;
  }

  public Object get(String key) {
    return properties.get(key);
  }

  private static class LazyHolder {
    private static final ApplicationConfig INSTANCE = new ApplicationConfig();
  }

}
