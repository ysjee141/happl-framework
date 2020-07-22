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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.happl.framework.common.annotations.ExcludeReflectField;
import kr.co.happl.framework.common.base.HapplMap;
import kr.co.happl.framework.common.logging.HapplLogger;
import kr.co.happl.framework.common.logging.enums.LoggerType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.lang.NonNull;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 공통 유틸리티 클래스
 *
 * @author JI YOONSEONG
 */
@SuppressWarnings("unused")
public class CommonUtils {

	private static final HapplLogger logger = HapplLogger.getInstance(LoggerType.INFO);

	/**
	 * String 인스턴스가 Null 일 경우 공백 반환
	 *
	 * @param s 검증할 문자열
	 * @return 검증 결과 (Null - 공백 / Not Null - 원문)
	 */
	public static String nullToBlank(String s) {
		return Optional.ofNullable(s).orElse("");
	}

	/**
	 * 객체 인스턴스가 Null 일 경우 지정한 기본 값 반환
	 *
	 * @param t            검증할 인스턴스
	 * @param defaultValue 기본 인스턴스
	 * @param <T>          검증할 인스턴스 클래스 타입
	 * @return 검증 결과 (Null - 기본 값 / Not Null - 원본 인스턴스)
	 */
	public static <T> T nullToDefault(T t, T defaultValue) {
		return Optional.ofNullable(t).orElse(defaultValue);
	}

	/**
	 * condition 값이 참이면 trueValue를 거짓이면 falseValue를 반환 한다.
	 *
	 * @param condition  검증할 조건
	 * @param trueValue  검증 결과 참일 경우 반환 값
	 * @param falseValue 검증 결과 거짓일 경우 반환 값
	 * @return 검증 결과
	 */
	public static String ternaryString(boolean condition, String trueValue, String falseValue) {
		return condition ? trueValue : falseValue;
	}

	/**
	 * eMail 체크 정규식.
	 *
	 * @param email 전자메일 주소
	 * @return 검증 결과
	 */
	public static boolean isEmail(String email) {
		String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
		return email.matches(regex);
	}

	/**
	 * 유선전화 체크 정규식.(하이픈은 있어도 없어도 저장 됨.)
	 *
	 * @param homePhoneNo 유선전화번호
	 * @return 검증 결과
	 */
	public static boolean isHomePhoneNo(String homePhoneNo) {
		String regex = "^(02|0[3-9][0-9])-?(\\d{4}|\\d{3})-?\\d{4}$";
		return homePhoneNo.matches(regex);
	}

	/**
	 * 휴대전화 체크 정규식.(하이픈은 있어도 없어도 저장 됨.)
	 *
	 * @param mobilePhoneNo 휴대전화번호
	 * @return 검증 결과
	 */
	public static boolean isMobilePhoneNo(String mobilePhoneNo) {
		String regex = "^01(?:0|1|[6-9])-?(?:\\d{3}|\\d{4})-?\\d{4}$";
		return mobilePhoneNo.matches(regex);
	}

	/**
	 * POJO 객체를 Byte 배열로 변환
	 * - POJO는 {@link java.io.Serializable}를 구현하고,
	 * {@link Object#equals(Object)} 함수가 Override 되어 있어야 한다
	 *
	 * @param originData 변환할 POJO 객체
	 * @return 변환된 Byte 배열
	 */
	public static <T> byte[] convertPojoToByteArray(T originData) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] bytes = null;

		try {
			ObjectOutput out = new ObjectOutputStream(bos);
			out.writeObject(originData);
			out.flush();
			bytes = bos.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return bytes;
	}

	/**
	 * Byte 배열을 POJO 객체로 변환
	 * - POJO는 {@link java.io.Serializable}를 구현하고,
	 * {@link Object#equals(Object)} 함수가 Override 되어 있어야 한다
	 *
	 * @param bytes      변환할 Byte 배열
	 * @param originType 변환될 POJO Class 타입
	 * @return 변환된 POJO 객체
	 */
	@SuppressWarnings("unchecked")
	public static <R> R convertByteArrayToPojo(byte[] bytes, Class<?> originType) {

		HapplMap<String, Object> a = new HapplMap<>();

		R convertedObject = null;

		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			ObjectInputStream in = new ObjectInputStream(bis);
			Object o = in.readObject();

			convertedObject = (R) originType.cast(o);
			in.close();
		} catch (IOException | ClassNotFoundException | ClassCastException e) {
			e.printStackTrace();
		}

		return convertedObject;
	}

	/**
	 * Pojo 객체를 Map으로 변환
	 *
	 * @param pojo 원본 Pojo 객체
	 * @param <T>  원본 Pojo 객체 클래스 타입
	 * @param <R>  반환될 Map Value 클래스 타입
	 * @return 변환된 Map 객체
	 */
	@SuppressWarnings("unchecked")
	public static <T, R> Map<String, R> convertPojoToMap(T pojo) {

		Map<String, R> result = new HashMap<>();

		for (Field field : pojo.getClass().getDeclaredFields()) {
			try {
				if (!field.getName().equals("serialVersionUID")) {
					field.setAccessible(true);
					result.put(field.getName(), (R) field.get(pojo));
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * Map 객체를 MultiValueMap으로 변환
	 *
	 * @param map 원본 Map 객체
	 * @return 변환된 MultiValueMap
	 */
	public static MultiValueMap<String, String> convertMapToMultivalue(Map<String, ?> map) {
		MultiValueMap<String, String> result = new LinkedMultiValueMap<>();
		result.setAll(map.entrySet().stream().collect(
				Collectors.toMap(Map.Entry::getKey, e -> e.getValue().toString())
		));
		return result;
	}

	/**
	 * POJO 객체를 JSON 객체로 변환
	 *
	 * @param obj 변환할 객체
	 * @param <T> 변환할 객체 클래스 타입
	 * @return 변환된 JSON 객체
	 */
	public static <T> JSONObject convertPojoToJson(@NonNull T obj) {
		Map<String, Object> result = new HashMap<>();

		for (Field field : obj.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			if (!field.isAnnotationPresent(ExcludeReflectField.class)) {
				try {
					Object value = field.get(obj);

					boolean isCollection = Collection.class.isAssignableFrom(field.getType());
					if (field.getType().isArray() || isCollection) {
						Object[] values;
						if (isCollection) {
							values = ((Collection<?>) field.get(obj)).toArray();
						} else {
							values = (Object[]) field.get(obj);
						}

						if (ReflectUtils.isDefaultType(values[0].getClass())) {
							value = Arrays.asList(values).toString();
						} else {
							JSONArray valueArray = new JSONArray();
							for (Object o : values) {
								//noinspection unchecked
								valueArray.add(convertPojoToJson(o));
							}
							value = valueArray.clone();
						}
					} else if (Map.class.isAssignableFrom(field.getType())) {
						Map<Object, Object> valueMap = new HashMap<>();

						//noinspection unchecked
						((Map<Object, Object>) field.get(obj)).forEach((k, v) -> {
							if (ReflectUtils.isDefaultType(v.getClass())) {
								valueMap.put(k, v);
							} else {
								valueMap.put(k, convertPojoToJson(v));
							}
						});

						value = new JSONObject(valueMap);

					} else if (!ReflectUtils.isDefaultType(field.getType())) {
						value = convertPojoToJson(field.get(obj));
					}

					result.put(field.getName(), value);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

		return new JSONObject(result);
	}

	/**
	 * Map 객체를 Json Object로 변환
	 *
	 * @param map 변환할 Map 객체
	 * @return 변환된 Json Object
	 */
	public static JSONObject convertMapToJson(@NonNull Map<String, ?> map) {
		Map<String, Object> result = new HashMap<>();
		map.keySet().forEach(key -> {
			Object value = map.get(key);

			boolean isCollection = Collection.class.isAssignableFrom(value.getClass());
			if (value.getClass().isArray() || isCollection) {
				Object[] values;
				if (isCollection) {
					values = ((Collection<?>) value).toArray();
				} else {
					values = (Object[]) value;
				}

				//noinspection DuplicatedCode
				if (ReflectUtils.isDefaultType(values[0].getClass())) {
					value = Arrays.asList(values).toString();
				} else {
					JSONArray valueArray = new JSONArray();
					for (Object o : values) {
						//noinspection unchecked
						valueArray.add(convertPojoToJson(o));
					}
					value = valueArray.clone();
				}
			} else if (Map.class.isAssignableFrom(value.getClass())) {
				Map<Object, Object> valueMap = new HashMap<>();

				//noinspection unchecked
				((Map<Object, Object>) value).forEach((k, v) -> {
					if (ReflectUtils.isDefaultType(v.getClass())) {
						valueMap.put(k, v);
					} else {
						valueMap.put(k, convertPojoToJson(v));
					}
				});

				value = new JSONObject(valueMap);

			} else if (!ReflectUtils.isDefaultType(value.getClass())) {
				value = convertPojoToJson(value.getClass());
			}

			result.put(key, value);

		});

		return new JSONObject(result);
	}

	/**
	 * Object 객체 정보를 문자열로 변환
	 *
	 * @param obj 원본 Object 객체
	 * @return 변환된 문자열
	 */
	public static String convertObjectToString(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			logger.error(e, "JsonProcessingException");
		}
		return null;
	}

}
