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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class TripleDesTest {

	HapplLogger logger = HapplLogger.getInstance(LoggerType.DATA);

	static Stream<Arguments> textStreams() {
		return Stream.of(
				Arguments.of("OriginText", "ED3792508F878195328106F333CC466"),
				Arguments.of("홍길동", "D2B17C3CFBB03D8BF71220055411290"),
				Arguments.of("1234567890", "E87F1FCF82D132F9BB018CA6738A19F"),
				Arguments.of("!@#$%^&*()", "5B28D17A7B6E724B6E5D8CC43A8BF7")
		);
	}

	@ParameterizedTest(name = "{1} 을 암호화")
	@MethodSource("textStreams")
	void getKey() {

	}

	@Test
	void encrypt() {
	}

	@Test
	void decrypt() {
	}

	@Test
	void decryptForMobile() {
	}
}