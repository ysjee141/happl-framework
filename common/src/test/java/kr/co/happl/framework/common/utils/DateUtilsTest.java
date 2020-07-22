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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("Date Utility 테스트")
class DateUtilsTest {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	void getAvailableTimeZones() {
		AtomicReference<JSONArray> timeZones = new AtomicReference<>(new JSONArray());
		assertDoesNotThrow(
				() -> timeZones.set(DateUtils.getAvailableTimeZones()),
				"사용 가능한 TimeZone 추출 실패"
		);
	}

	@ParameterizedTest
	@ValueSource(strings = {"", "yyyy-MM-dd HH:mm:ss"})
	void getUTCString(String format) {
		AtomicReference<String> utcString = new AtomicReference<>("");
		assertDoesNotThrow(() -> {
					if (format.equals("")) {
						utcString.set(DateUtils.getUtcString());
					} else {
						utcString.set(DateUtils.getUtcString(format));
					}
				}, "UTC 표준시 가져오기 실패"
		);
	}

	@Test
	void getUTCTime() {
	}

	@Test
	void getLocalString() {
	}

	@Test
	void testGetLocalString() {
	}

	@Test
	void getLocalTime() {
	}

	@Test
	void testGetLocalTime() {
	}

	@Test
	void testGetLocalString1() {
	}

	@Test
	void testGetLocalString2() {
	}

	@Test
	void testGetLocalTime1() {
	}

	@Test
	void testGetLocalString3() {
	}

	@Test
	void testGetLocalString4() {
	}

	@Test
	void testGetLocalTime2() {
	}

	@Test
	void testGetLocalTime3() {
	}

	@Test
	void getCurrentDay() {
	}

	@Test
	void getAddDate() {
	}

	@Test
	void testGetAddDate() {
	}

	@Test
	void getMonthAgoDate() {
	}

	@Test
	void getDayOfWeek() {
	}

	@Test
	void getDateByDateOfWeek() {
	}

	@Test
	void getLastDayOfWeek() {
	}

	@Test
	void getFirstDayOfWeek() {
	}

	@Test
	void getPeriodDay() {
	}

	@Test
	void formatUTCToLocalAndBackTime() {
	}

	@Test
	void strToDate() {
	}

	@Test
	void testStrToDate() {
	}

	@Test
	void diffDate() {
	}

	@Test
	void testDiffDate() {
	}

	@Test
	void diffHour() {
	}

	@Test
	void testDiffHour() {
	}

	@Test
	void diffMinute() {
	}

	@Test
	void testDiffMinute() {
	}

	@Test
	void convertDateFormat() {
	}
}