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

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JSON Utility 테스트")
class JsonUtilsTest {

	JSONArray testArray = new JSONArray();
	JSONArray actualArray = new JSONArray();

	@BeforeEach
	void init() {

		JSONObject a = new JSONObject();
		a.put("key", 0);
		a.put("name", "홍길동");

		JSONObject b = new JSONObject();
		b.put("key", 1);
		b.put("name", "홍길동");

		testArray.add(b);
		testArray.add(a);

		actualArray.add(a);
		actualArray.add(b);

	}

	@Test
	@DisplayName("JSON Key를 통한 정렬")
	void sortJsonByKey() {
		JSONArray result = JsonUtils.sortJsonByKey(testArray, "key");

		assertEquals(result, actualArray);
	}
}