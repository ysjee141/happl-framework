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

package kr.co.happl.framework.common.security;

import kr.co.happl.framework.common.logging.HapplLogger;
import kr.co.happl.framework.common.logging.enums.LoggerType;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Sha512Test {

	HapplLogger logger = HapplLogger.getInstance(LoggerType.DATA);

	static Stream<Arguments> textStreams() {
		return Stream.of(
				Arguments.of("OriginText", "cb436902faf189a57af6b6444774c4cecabd0f52fc81ecc3024d6fbd26049abcb3b7a5f109cb14d4007dc8c8ea34901f20ed99f43d4891c0364feece619d72cc"),
				Arguments.of("홍길동", "720c97cd0032f5e9280312592a1fc2a3538fc3d1f3b0e193e44e70c86180ef9e52f8619744287024d17aedce0dcc4471f8ae023fdd35faed09fd0cd16e49a6ff"),
				Arguments.of("1234567890", "12b03226a6d8be9c6e8cd5e55dc6c7920caaa39df14aab92d5e3ea9340d1c8a4d3d0b8e4314f1f6ef131ba4bf1ceb9186ab87c801af0d5c95b1befb8cedae2b9"),
				Arguments.of("!@#$%^&*()", "138fad927473f694c3a02cca61008e52572bd19ce442f20e139b6f09157b97157fd71946fedfec2381b7e33618afe5f7c24a873ed1efe416978acfc434503614")
		);
	}

	@ParameterizedTest(name = "{0} 을 암호화")
	@MethodSource("textStreams")
	void encrypt(String originText, String encryptedString) {
		assertEquals(
				encryptedString,
				Sha512.encrypt(originText)
		);
	}
}