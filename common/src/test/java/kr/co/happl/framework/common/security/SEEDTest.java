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

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SEEDTest {

	private final int[] seedKeyString = SEED.getSeedRoundKey("HapplgSeedKey!@#");
	HapplLogger logger = HapplLogger.getInstance(LoggerType.DATA);

	static Stream<Arguments> keyStreams() {
		return Stream.of(
				Arguments.of("HapplgSeedKey!@#", new int[]{-1854568593, 920276422, -33745969, 1617891153, 585960509, 1797709154, -1652882273, 1288365160, -1515450557, -1754506614, 115338203, -568797884, -593314141, -902801376, 1266522271, 1862411119, 2105527260, 3085542, 1471373956, 1832941137, 755192350, 879923439, 1891415627, 1559977765, -987361741, -2009826092, -1496405934, 1859412601, -1397277549, -759868717, 1117076097, -2110049958}),
				Arguments.of("BackOfficeKey!@#", new int[]{-1269662446, -1780270977, -710896519, -390269054, -1083285979, 398335840, -228528054, -1247868948, 1775942072, -1704109504, -390764056, 1815305325, 1730317020, -1903516310, -1868218309, 1555604670, 1133601580, -1214004986, -2119270115, 941646377, -866100258, -309077677, -1479947943, 1369681552, -149671375, 129837570, -489035067, -2670071, 266403779, -886095895, -1232053590, -1786239823})
		);
	}

	static Stream<Arguments> textStreams() {
		return Stream.of(
				Arguments.of("OriginText", "eawz7z7fvIp673HCO42NAQ=="),
				Arguments.of("홍길동", "ThrCohNZL/k5v4zjbBm4Lg=="),
				Arguments.of("1234567890", "yv+J5TZehXt4Pccu68g0rQ=="),
				Arguments.of("!@#$%^&*()", "nwGKz/HplUyKtK5mjb2sFg==")
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