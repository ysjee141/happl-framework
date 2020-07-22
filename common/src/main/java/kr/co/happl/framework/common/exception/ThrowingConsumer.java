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

package kr.co.happl.framework.common.exception;

import java.util.function.Consumer;

@FunctionalInterface
public interface ThrowingConsumer<T, E extends Throwable> {
	void accept(T t) throws E;

	static <T, E extends Throwable> Consumer<T> unchecked(ThrowingConsumer<T, E> f) {
		return t -> {
			try {
				f.accept(t);
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		};
	}

	static <T, E extends Throwable> Consumer<T> unchecked(ThrowingConsumer<T, E> f, Consumer<Throwable> c) {
		return t -> {
			try {
				f.accept(t);
			} catch (Throwable e) {
				c.accept(e);
			}
		};
	}
}
