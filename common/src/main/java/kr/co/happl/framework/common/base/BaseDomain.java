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

package kr.co.happl.framework.common.base;

import org.apache.commons.lang3.builder.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.lang.NonNull;

import java.io.Serializable;

/**
 * Value Object 생성을 위한 상위 추상 클래스<br/>
 * 시스템 내 모든 Value Object는 이 클래스를 상속 받아야 한다.
 */
public abstract class BaseDomain implements Serializable, Comparable<Object> {

	private static final long serialVersionUID = -1896068767546910821L;

	/**
	 * 객체의 Hash Code 가져오기
	 *
	 * @return 현재 객체의 Hash Code
	 */
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	/**
	 * 현재 객체와 대상 객체 동일성 체크
	 *
	 * @param obj 비교할 대상 객체
	 * @return 비교 결과
	 */
	@Override
	public boolean equals(Object obj) {
		return getClass() == obj.getClass() && EqualsBuilder.reflectionEquals(this, obj);
	}

	/**
	 * 객체 정보를 문자열로 변환
	 *
	 * @return 변환된 문자열
	 */
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	/**
	 * 인스턴스 정보를 JSON 객체로 변환
	 *
	 * @return 변환된 JSON 객체
	 * @throws ParseException JSON Parsing 오류
	 */
	public JSONObject toJson() throws ParseException {
		JSONParser parser = new JSONParser();
		return (JSONObject) parser.parse(this.toJsonString());
	}

	/**
	 * 인스턴스 정보를 JSON 문자열로 변환
	 *
	 * @return 변환된 JSON 문자열
	 */
	public String toJsonString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
	}

	/**
	 * 현재 객체와 대상 객체를 비교(정렬)
	 *
	 * @param obj 비교 대상 객체
	 * @return 비교 결과
	 */
	@Override
	public int compareTo(@NonNull Object obj) {
		return CompareToBuilder.reflectionCompare(this, obj);
	}
}
