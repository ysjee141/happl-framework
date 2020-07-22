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

package kr.co.happl.framework.common.security;

import kr.co.happl.framework.common.logging.HapplLogger;
import kr.co.happl.framework.common.logging.enums.LoggerType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SEEDTest {

	private final int[] seedKeyString = SEED.getSeedRoundKey("HapplgSeedKey!@#");
	HapplLogger logger = HapplLogger.getInstance(LoggerType.DATA);

	static Stream<Arguments> keyStreams() {
		return Stream.of(
				Arguments.of("HapplgSeedKey!@#", new int[]{994276781, 2006861955, -1732372851, -554327225, -32898850, -1741471817, -1892990775, 644725094, 1942663643, -307889541, -425752199, 1814942322, -985446822, 1992907222, -121265388, 13616408, -286532517, -1969692862, 971644305, 308808130, 1615142164, 1755199015, -4759550, -1601775984, -2100338660, -763400243, 1712439182, 2144168568, 707659565, -1328082223, 269685378, 1925647766}),
				Arguments.of("BackOfficeKey!@#", new int[]{-1269662446, -1780270977, -710896519, -390269054, -1083285979, 398335840, -228528054, -1247868948, 1775942072, -1704109504, -390764056, 1815305325, 1730317020, -1903516310, -1868218309, 1555604670, 1133601580, -1214004986, -2119270115, 941646377, -866100258, -309077677, -1479947943, 1369681552, -149671375, 129837570, -489035067, -2670071, 266403779, -886095895, -1232053590, -1786239823})
		);
	}

	static Stream<Arguments> textStreams() {
		return Stream.of(
				Arguments.of("OriginText", "TY4K6YjIVhCEPCccRyWfxg=="),
				Arguments.of("홍길동", "ZDaqEDT5kFte9INHvOt3lw=="),
				Arguments.of("1234567890", "XGOSL2vYuRpwoSjlV/q/jg=="),
				Arguments.of("!@#$%^&*()", "lVHh+RAYcFKBDmYjdJAqGg==")
		);
	}

	@ParameterizedTest(name = "{1} 을 복호화")
	@MethodSource("textStreams")
	void getSeedDecrypt(String originText, String encryptedString) {
		try {
			assertEquals(
					originText,
					SEED.getSeedDecrypt(encryptedString, seedKeyString)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@ParameterizedTest(name = "{0} 을 암호화")
	@MethodSource("textStreams")
	void getSeedEncrypt(String originText, String encryptedString) {
		try {
			assertEquals(
					encryptedString,
					SEED.getSeedEncrypt(originText, seedKeyString)
			);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@ParameterizedTest
	@MethodSource("keyStreams")
	void getSeedRoundKey(String originKey, int[] seedKey) {
		assertArrayEquals(SEED.getSeedRoundKey(originKey), seedKey);
	}

}