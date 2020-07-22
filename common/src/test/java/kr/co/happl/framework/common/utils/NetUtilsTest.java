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

import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.mock.web.MockHttpServletRequest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Net Utility 테스트")
class NetUtilsTest {

	MockHttpServletRequest request = new MockHttpServletRequest();
	URI uriOrigin;
	URI uriResult;
	String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/83.0.4103.116 Safari/537.36";

	@BeforeEach
	void init() throws URISyntaxException {
		// 테스트를 위한 데이터 생성
		request.addParameter("name", "hong");
		request.addParameter("age", "30");

		request.setRemoteAddr("1.2.3.4");
		request.addHeader("user-agent", userAgent);


		uriOrigin = new URI("http://www.example.com?test=true");
		uriResult = new URI("http://www.example.com?test=true&name=hong&age=30");
	}

	static Stream<Arguments> queryData() {
		Map<String, Object> param = new HashMap<>();
		param.put("name", "hong");
		param.put("age", "30");

		return Stream.of(
				Arguments.of(param)
		);
	}

	@Test
	void serializeForm() {
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("name", "hong");
		resultMap.put("age", "30");
		assertEquals(new JSONObject(resultMap), NetUtils.serializeForm(request));
	}

	@ParameterizedTest
	@MethodSource("queryData")
	void addQuerysToUri(Object param) {
		if (param instanceof Map) {
			assertDoesNotThrow(() -> {
				URI result = NetUtils.addQuerysToUri(uriOrigin, param);
				assertEquals(result, uriResult);
			}, "URI QueryString 추가 실패");
		}
	}

	@ParameterizedTest
	@MethodSource("queryData")
	void mapToQueryString(Object param) {
		String result = NetUtils.mapToQueryString((Map<String, Object>) param);
		assertEquals(
				"name=hong&age=30",
				result
		);
	}

	@Test
	void getClntIP() {
		String ip = NetUtils.getClntIP(request);
		assertEquals("1.2.3.4", ip);
	}

	@Test
	void getClientOsInfo() {
		String os = NetUtils.getClientOsInfo(request);
		assertEquals("Windows 10", os);
	}

	@Test
	void getClientAgentInfo() {
		String result = NetUtils.getClientAgentInfo(request);
		assertEquals(userAgent, result);
	}

	@Test
	void getClientWebKind() {
		String browserName = NetUtils.getClientWebKind(request);
		assertEquals("Google Chrome", browserName);
	}

	@Test
	void getClientWebVer() {
		String version = NetUtils.getClientWebVer(request);
		assertEquals("83.0", version);
	}

	@Test
	void isMobile() {
		boolean result = NetUtils.isMobile(request);
		assertFalse(result);
	}
}