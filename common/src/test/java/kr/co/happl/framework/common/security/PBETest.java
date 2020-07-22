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

import java.security.NoSuchAlgorithmException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PBETest {

	private final String password = "HapplgPbePassword";
	HapplLogger logger = HapplLogger.getInstance(LoggerType.DATA);

	static Stream<Arguments> textStreams() {
		return Stream.of(
				Arguments.of("OriginText", "3iI25V+EucOQsV4mduGiin3qu4bGJ5lX"),
				Arguments.of("홍길동", "UZVo8Qrnvg+jFfrla2D34q/mdEOm71wH"),
				Arguments.of("1234567890", "/ACSsPSNSDq0lLw7KY1iywpPc92AQql6"),
				Arguments.of("!@#$%^&*()", "mIM7/1z28JEOwLc3vc9k8k08csrw81ry")
		);
	}

	@ParameterizedTest(name = "{0} 을 암호화")
	@MethodSource("textStreams")
	void encode(String originText) throws NoSuchAlgorithmException {
		String encryptedText = PBE.encode(originText, password);
		encryptedText = PBE.decode(encryptedText, password);
		assertEquals( originText, encryptedText);
	}

	@ParameterizedTest(name = "{1} 을 복호화")
	@MethodSource("textStreams")
	void decode(String originText, String encryptedText) {
		assertEquals(
				originText,
				PBE.decode(encryptedText, password)
		);
	}
}