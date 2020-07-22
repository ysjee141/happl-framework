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

package kr.co.happl.framework.common.vo;

import kr.co.happl.framework.common.annotations.ExcludeReflectField;
import kr.co.happl.framework.common.base.BaseVo;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@Getter
@Setter
public class BaseVoInstance extends BaseVo {

  @ExcludeReflectField
  private static final long serialVersionUID = 622609884776540789L;

  private String name;
  private Integer a;
  private Integer[] b;
  private BaseVoInstance2 vo2;
  private BaseVoInstance2[] vo3;
  private Map<String, Object> map;
  private Collection<BaseVoInstance2> collect;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof BaseVoInstance)) {
      return false;
    }
    BaseVoInstance that = (BaseVoInstance) o;
    return Objects.equals(name, that.name)
        && Objects.equals(a, that.a);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, a);
  }
}
