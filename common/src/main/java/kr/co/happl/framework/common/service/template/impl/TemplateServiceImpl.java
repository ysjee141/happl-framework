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

package kr.co.happl.framework.common.service.template.impl;

import kr.co.happl.framework.common.base.BaseService;
import kr.co.happl.framework.common.service.template.TemplateService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Free Marker를 통한 Template 처리를 위한 서비스
 *
 * @author JI YOONSEONG
 */
@Service
@SuppressWarnings("unused")
public class TemplateServiceImpl extends BaseService implements TemplateService {

  private final Configuration configuration = new Configuration(Configuration.VERSION_2_3_19);
  @Value("${template.path}")
  String templatePath;
  private Template template;
  private Object model;

  @PostConstruct
  private void init() {
    configuration.setDefaultEncoding("UTF-8");
    configuration.setClassForTemplateLoading(this.getClass(), templatePath);
  }

  @Override
  public void setTemplate(String template) {
    try {
      this.template = configuration.getTemplate(template);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public <T> void setModel(T model) {
    this.model = model;
  }

  @Override
  public String getContent() {
    String result = "";
    try {
      StringWriter stringWriter = new StringWriter();
      template.process(this.model, stringWriter);
      result = stringWriter.toString();
      stringWriter.close();
    } catch (TemplateException | IOException e) {
      e.printStackTrace();
    }
    return result;
  }

  @Override
  public String getContent(String templateFile) {
    this.setTemplate(templateFile);
    return this.getContent();
  }

  @Override
  public String getContent(String templateFile, Object model) {
    this.setTemplate(templateFile);
    this.setModel(model);
    return this.getContent();
  }
}
