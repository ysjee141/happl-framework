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

package kr.co.happl.framework.data.enums;

import kr.co.happl.framework.data.annotations.MapperFive;
import kr.co.happl.framework.data.annotations.MapperFour;
import kr.co.happl.framework.data.annotations.MapperOne;
import kr.co.happl.framework.data.annotations.MapperThree;
import kr.co.happl.framework.data.annotations.MapperTwo;

import java.lang.annotation.Annotation;

/**
 * 다중 Data Source 환경에서 사용할 Data Source를 선택하기 위한 Annotation Enum<br/>
 * Configuration(YAML)에서 사용하기 위한 Enum 타입
 *
 * @author JI YOONSEONG
 **/
@SuppressWarnings("unused")
public enum MapperOrders {

  MAPPER_ONE(1), MAPPER_TWO(2),
  MAPPER_THREE(3), MAPPER_FOUR(4),
  MAPPER_FIVE(5);

  private final int order;

  MapperOrders(int order) {
    this.order = order;
  }

  /**
   * 선택된 Mapper Order에 맞는 Mapper Annotation 가져오기
   *
   * @return Mapper Annotation
   */
  public Class<? extends Annotation> get() {
    Class<? extends Annotation> result;
    switch (order) {
      case 1:
        result = MapperOne.class;
        break;
      case 2:
        result = MapperTwo.class;
        break;
      case 3:
        result = MapperThree.class;
        break;
      case 4:
        result = MapperFour.class;
        break;
      case 5:
        result = MapperFive.class;
        break;
      default:
        result = null;
    }

    return result;
  }

}
