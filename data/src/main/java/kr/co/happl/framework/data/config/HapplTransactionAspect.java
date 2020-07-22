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

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Data Transaction Advisor Aspect Generator
 *
 * @author JI YOONSEONG
 **/
public class HapplTransactionAspect {

  private static final int TX_METHOD_TIMEOUT = 3;
  private static final String AOP_POINTCUT_EXPRESSION =
      "execution(* kr.co.happl..*.*service.*(..))";

  /**
   * Transaction Manager를 생성 후 Bean으로 등록
   *
   * @param context                         Application Context
   * @param dataSourceTransactionManagerMap Bean으로 등록된 Data
   *
   * @see ApplicationContext
   * @see Map
   * @see DataSourceTransactionManager
   */
  public static void createTransactionAspect(
      ApplicationContext context,
      Map<String, DataSourceTransactionManager> dataSourceTransactionManagerMap
  ) {
    dataSourceTransactionManagerMap.forEach((key, instance) ->
        ((StaticApplicationContext) context).getBeanFactory()
            .registerSingleton(key.concat("TxAdvisor"), LazyHolder.INSTANCE.create(instance))
    );
  }

  /**
   * Transation Manager를 통한 Transaction Advisor 생성
   *
   * @param dataSourceTransactionManager Advisor를 생성할 Transaction Manager
   * @return 생성된 Transaction Advisor
   *
   * @see TransactionInterceptor
   * @see DataSourceTransactionManager
   * @see Advisor
   */
  public Advisor create(
      DataSourceTransactionManager dataSourceTransactionManager
  ) {

    List<RollbackRuleAttribute> rollbackRules = new ArrayList<>();
    rollbackRules.add(new RollbackRuleAttribute(Exception.class));

    /* If need to add additionall exception, add here */
    DefaultTransactionAttribute readOnlyAttribute =
        new DefaultTransactionAttribute(TransactionDefinition.PROPAGATION_REQUIRED);
    readOnlyAttribute.setReadOnly(true);
    readOnlyAttribute.setTimeout(TX_METHOD_TIMEOUT);

    RuleBasedTransactionAttribute writeAttribute =
        new RuleBasedTransactionAttribute(
            TransactionDefinition.PROPAGATION_REQUIRED, rollbackRules
        );
    writeAttribute.setTimeout(TX_METHOD_TIMEOUT);

    String readOnlyTransactionAttributesDefinition = readOnlyAttribute.toString();
    String writeTransactionAttributesDefinition = writeAttribute.toString();

    // Read-Only
    Properties txAttributes = new Properties();
    txAttributes.setProperty("retrieve*", readOnlyTransactionAttributesDefinition);
    txAttributes.setProperty("select*", readOnlyTransactionAttributesDefinition);
    txAttributes.setProperty("get*", readOnlyTransactionAttributesDefinition);
    txAttributes.setProperty("list*", readOnlyTransactionAttributesDefinition);
    txAttributes.setProperty("search*", readOnlyTransactionAttributesDefinition);
    txAttributes.setProperty("find*", readOnlyTransactionAttributesDefinition);
    txAttributes.setProperty("count*", readOnlyTransactionAttributesDefinition);

    // write rollback-rule
    txAttributes.setProperty("*", writeTransactionAttributesDefinition);

    TransactionInterceptor txAdvice = new TransactionInterceptor();
    txAdvice.setTransactionAttributes(txAttributes);
    txAdvice.setTransactionManager(dataSourceTransactionManager);

    AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
    pointcut.setExpression(AOP_POINTCUT_EXPRESSION);

    return new DefaultPointcutAdvisor(pointcut, txAdvice);
  }

  private static class LazyHolder {
    public static HapplTransactionAspect INSTANCE = new HapplTransactionAspect();
  }

}
