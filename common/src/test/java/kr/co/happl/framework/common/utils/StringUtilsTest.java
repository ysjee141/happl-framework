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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StringUtilsTest {

	List<String> actualList = new ArrayList<>();
	String[] actualArray = {};

	static Stream<Arguments> stringDatas() {
		return Stream.of(
				Arguments.of("ABCD", false),
				Arguments.of(" ABCD", true),
				Arguments.of("ABCD ", true),
				Arguments.of("A BCD", true),
				Arguments.of("A B C D", true)
		);
	}

	static Stream<Arguments> stringDatas2() {
		return Stream.of(
				Arguments.of(" ", true),
				Arguments.of("", true),
				Arguments.of(" A", false),
				Arguments.of("A B C", false),
				Arguments.of("ABC ", false)
		);
	}

	static Stream<Arguments> koreanDatas() {
		return Stream.of(
				Arguments.of("홍길동", "ㅎ"),
				Arguments.of("박문수", "ㅂ"),
				Arguments.of("성춘향", "ㅅ")
		);
	}

	static Stream<String> stringDatas3() {
		return Stream.of(null, "A", " ", "");
	}

	@BeforeEach
	void init() {
		actualList.add("A");
		actualList.add("B");
		actualList.add("C");
		actualList.add("D");
		actualList.add("E");

		actualArray = actualList.toArray(String[]::new);
	}

	@ParameterizedTest(name = "{0} 초성 테스트")
	@DisplayName("한글 초성 추출 테스트")
	@MethodSource("koreanDatas")
	void getConsonant(String text, String consonant) {
		assertEquals(
				StringUtils.getIndexChar(text),
				consonant
		);
	}

	@ParameterizedTest(name = "{0}은 공백은 포함한다: {1}")
	@DisplayName("문자열 공백 포함 여부 체크")
	@MethodSource("stringDatas")
	void isAnyBlank(String s, boolean b) {
		assertEquals(StringUtils.isAnyBlank(s), b);
	}

	@ParameterizedTest(name = "{0}은 공백은 포함하지 않는다: {1}")
	@DisplayName("문자열 공백 불포함 여부 체크")
	@MethodSource("stringDatas")
	void isNoneBlank(String s, boolean b) {
		assertEquals(StringUtils.isNoneBlank(s), !b);
	}

	@ParameterizedTest(name = "{0}은 공백이다: {1}")
	@DisplayName("문자열 공백 여부 체크")
	@MethodSource("stringDatas2")
	void isBlank(String s, boolean b) {
		assertEquals(StringUtils.isBlank(s), b);
	}

	@ParameterizedTest(name = "{0}은 공백이 아니다: {1}")
	@DisplayName("문자열 공백 아님 여부 체크")
	@MethodSource("stringDatas2")
	void isNotBlank(String s, boolean b) {
		assertEquals(StringUtils.isNotBlank(s), !b);
	}

	@ParameterizedTest
	@DisplayName("String 객체가 비어있는지 체크")
	@MethodSource("stringDatas3")
	void isEmpty(String s) {
		assertEquals(StringUtils.isEmpty(s), s == null || s.trim().equals(""));
	}

	@ParameterizedTest
	@DisplayName("문자열 구분자 분리(List)")
	@ValueSource(strings = {"A,B,C,D,E"})
	void splitToList(String str) {
		List<String> result = StringUtils.splitToList(str, ",");
		assertEquals(result, actualList);
	}

	@ParameterizedTest
	@DisplayName("문자열 구분자 분리(배열)")
	@ValueSource(strings = {"A,B,C,D,E"})
	void split(String str) {
		String[] result = StringUtils.split(str, ",");
		assertArrayEquals(result, actualArray);
	}

	@ParameterizedTest
	@DisplayName("숫자 콤마(,) 추가")
	@ValueSource(ints = {1000000})
	void addComma(int str) {
		assertEquals("1,000,000", StringUtils.addComma(str));
	}

}