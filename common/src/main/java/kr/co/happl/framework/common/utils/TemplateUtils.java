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

package kr.co.happl.framework.common.utils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;

@SuppressWarnings("unused")
public class TemplateUtils {

  /**
   * Template 파일에 데이터가 적용된 문자열 반환
   *
   * @param templateFile Template 파일
   * @param model        적용할 데이터 모델
   * @return 데이터가 적용된 문자열
   */
  public static String getContentByTemplate(String templateFile, Object model) {
    String result = "";

    Configuration config = new Configuration(Configuration.VERSION_2_3_19);
    config.setDefaultEncoding("UTF-8");

    try {
      Template template = config.getTemplate(templateFile);
      StringWriter stringWriter = new StringWriter();
      template.process(model, stringWriter);
      result = stringWriter.toString();
    } catch (IOException | TemplateException e) {
      e.printStackTrace();
    }

    return result;
  }

}
