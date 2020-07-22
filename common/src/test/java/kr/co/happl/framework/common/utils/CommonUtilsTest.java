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

package kr.co.happl.framework.common.utils;

import kr.co.happl.framework.common.logging.HapplLogger;
import kr.co.happl.framework.common.logging.enums.LoggerType;
import kr.co.happl.framework.common.vo.BaseVoInstance;
import kr.co.happl.framework.common.vo.BaseVoInstance2;
import kr.co.happl.framework.common.vo.TestPojo;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Common Utility 테스트")
class CommonUtilsTest {

	private final HapplLogger logger = HapplLogger.getInstance(LoggerType.DATA);

	private TestPojo testPojo;
	private Map<String, Object> testMap;
	private MultiValueMap<String, String> testMultiValueMap;
	private byte[] testBytes;

	static Stream<String> stringDatas() {
		return Stream.of( null, "A", " ", "" );
	}

	static Stream<Arguments> emailDatas() {
		return Stream.of(
				Arguments.of("ysjee141@gmail.com", true),
				Arguments.of("gmail.com", false)
		);
	}

	static Stream<Arguments> phoneDatas() {
		return Stream.of(
				Arguments.of("01041983092", "Mobile"),
				Arguments.of("010-4198-3092", "Mobile"),
				Arguments.of("0316670392", "Home"),
				Arguments.of("031-667-0392", "Home")
		);
	}

	@BeforeEach
	void init() {
		testPojo = new TestPojo("홍길동", 30);
		testMap = new HashMap<>();
		testMap.put("name", "홍길동");
		testMap.put("age", 30);
		testMultiValueMap = new LinkedMultiValueMap<>();
		testMultiValueMap.add("name", "홍길동");
		testMultiValueMap.add("age", "30");

		testBytes = CommonUtils.convertPojoToByteArray(testPojo);
	}

	@ParameterizedTest
	@DisplayName("null String 객체를 \"\"로 반환")
	@MethodSource("stringDatas")
	void nullToBlank(String str) {
		assertEquals( (str == null) ? "" : str, CommonUtils.nullToBlank(str), "Null 객체 Blank 반환 실패" );
	}

	@ParameterizedTest
	@MethodSource("stringDatas")
	<T> void nullToDefault(T str) {
		assertEquals( (str == null) ? "" : str, CommonUtils.nullToDefault(str, "") );
	}

	@ParameterizedTest
	@MethodSource("emailDatas")
	void isEmail(String arg, boolean a) {
		assertEquals(CommonUtils.isEmail(arg), a);
	}

	@ParameterizedTest
	@MethodSource("phoneDatas")
	void isHomePhoneNo(String arg, String type) {
		assertEquals( CommonUtils.isHomePhoneNo(arg), type.equals("Home") );
	}

	@ParameterizedTest
	@MethodSource("phoneDatas")
	void isMobilePhoneNo(String arg, String type) {
		assertEquals( CommonUtils.isMobilePhoneNo(arg), type.equals("Mobile") );
	}

	@Test
	void convertPojoToByteArray() {
		byte[] b = CommonUtils.convertPojoToByteArray(testPojo);
		assertArrayEquals(b, testBytes);
	}

	@Test
	@DisplayName("Byte 배열을 POJO로 변환")
	void convertByteArrayToPojo() {
		TestPojo o = new TestPojo("홍길동", 23);
		byte[] b = CommonUtils.convertPojoToByteArray(o);
		TestPojo t = CommonUtils.convertByteArrayToPojo(b, TestPojo.class);
		assertEquals(o, t);
	}

	@Test
	@DisplayName("POJO 데이터를 Map으로 변환")
	void convertPojoToMap() {
		Map<String, Object> m = CommonUtils.convertPojoToMap(testPojo);
		m.remove("this$0");	// 테스트 내부 클래스 참조 제거
		assertEquals(m, testMap);
	}

	@Test
	void convertMapToMultivalue() {
		assertEquals(CommonUtils.convertMapToMultivalue(testMap), testMultiValueMap);
	}

	@Test
	@DisplayName("POJO 데이터를 Json으로 변환")
	void convertPojoToJson() {

		BaseVoInstance2 vo2 = new BaseVoInstance2();
		vo2.setCity("Seoul");
		vo2.setCountry("Korea");

		Map<String, Object> map = new HashMap<>();
		map.put("text", "활빈당");
		map.put("intance", vo2);

		Collection<BaseVoInstance2> collection = new ArrayList<>();
		collection.add(vo2);
		collection.add(vo2);
		collection.add(vo2);
		collection.add(vo2);

		BaseVoInstance vo1 = new BaseVoInstance();
		vo1.setA(0);
		vo1.setB(new Integer[]{0, 2, 3});
		vo1.setName("홍길동");
		vo1.setVo2(vo2);
		vo1.setVo3(new BaseVoInstance2[]{ vo2, vo2 } );
		vo1.setMap(map);
		vo1.setCollect(collection);

 		JSONObject o = CommonUtils.convertPojoToJson(vo1);

 		logger.info(new String[]{
				o.toJSONString(),
				vo1.toJson().toJSONString()
		});

		assertEquals(o.toJSONString(), vo1.toJson().toJSONString());
	}

}