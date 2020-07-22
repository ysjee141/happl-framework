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

import java.io.Serializable;
import java.util.Objects;

public class TestPojo implements Serializable {

	private static final long serialVersionUID = -982260988141090383L;

	String name;
	int age;

	public TestPojo(String name, int age) {
		this.name = name;
		this.age = age;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TestPojo)) return false;
		TestPojo testPojo = (TestPojo) o;
		return age == testPojo.age &&
				Objects.equals(name, testPojo.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, age);
	}
}
