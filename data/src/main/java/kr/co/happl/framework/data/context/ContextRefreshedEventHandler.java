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

package kr.co.happl.framework.data.context;

import kr.co.happl.framework.data.config.HapplMapperGenerator;
import kr.co.happl.framework.data.config.HapplTransactionAspect;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;

/**
 * context
 *
 * @author JI YOONSEONG
 **/
@Component
public class ContextRefreshedEventHandler {

  /**
   * Application Context 갱신 Event Handler
   * <pre>
   *   - 다중 Data Source 사용을 위한 MyBatis Mapper Generate
   *   - 다중 Data Source 사용을 위한 Transaction Aspect 설정
   * </pre>
   *
   * @param event Application Context Refresed Event
   */
  @EventListener
  public void onApplicationEvent(ContextRefreshedEvent event) {
    ApplicationContext context = event.getApplicationContext();
    HapplMapperGenerator.createMappers(context);
    HapplTransactionAspect.createTransactionAspect(
        context,
        context.getBeansOfType(DataSourceTransactionManager.class)
    );
  }

}
