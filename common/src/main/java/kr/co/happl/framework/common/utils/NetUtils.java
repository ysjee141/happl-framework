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

import org.json.simple.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@SuppressWarnings("unused")
public class NetUtils {

  /**
   * POST Data를 JSON 객체로 변환
   *
   * @param req 원본 HTTP Request
   * @return 변환된 JSON 객체
   */
  public static JSONObject serializeForm(HttpServletRequest req) {

    Map<String, Object> data = new HashMap<>();

    Enumeration<String> paramNames = req.getParameterNames();

    while (paramNames.hasMoreElements()) {
      String key = paramNames.nextElement();
      String[] value = req.getParameterValues(key);
      data.put(key, value[0]);
    }

    return new JSONObject(data);
  }

  /**
   * URI에 Query String 추가하기
   *
   * @param uri        추가 될 URI
   * @param parameters 추가할 Query String
   * @param <T>        parameter Type
   * @return Query String가 추가된 URI
   * @throws URISyntaxException 예외
   */
  @SuppressWarnings("unchecked")
  public static <T> URI addQuerysToUri(URI uri, T parameters) throws URISyntaxException {
    Map<String, ?> map;
    if (parameters instanceof Map) {
      map = (Map<String, ?>) parameters;
    } else {
      map = CommonUtils.convertPojoToMap(parameters);
    }

    String queryString = Optional.ofNullable(uri.getQuery()).orElse("");
    StringBuilder sb = new StringBuilder();

    if (queryString.length() > 0) {
      sb.append(queryString);
      sb.append("&");
    }

    sb.append(mapToQueryString((Map<String, Object>) map));

    return new URI(
        uri.getScheme(),
        uri.getAuthority(),
        uri.getPath(),
        sb.toString(),
        uri.getFragment()
    );
  }

  /**
   * Map 객체를 Query String으로 변환
   *
   * @param parameters 변환 할 Map 객체
   * @return 변환된 Query String
   */
  public static String mapToQueryString(Map<String, Object> parameters) {

    StringBuilder sb = new StringBuilder();

    for (Map.Entry<String, Object> stringObjectEntry : parameters.entrySet()) {
      sb.append(stringObjectEntry.getKey());
      sb.append("=");
      sb.append(stringObjectEntry.getValue());
      sb.append("&");
    }

    return sb.deleteCharAt(sb.length() - 1).toString();
  }

  /**
   * 클라이언트(Client)의 IP주소를 조회하는 기능
   *
   * @param request 원본 HTTP Request 객체
   * @return 클라이언트 IP 주소
   */
  public static String getClntIP(HttpServletRequest request) {
    return request.getRemoteAddr();
  }

  /**
   * 클라이언트의 OS 정보를 조회하는 기능
   *
   * @param request 원본 HTTP Request 객체
   * @return 클라이언트 OS 정보
   */
  public static String getClientOsInfo(HttpServletRequest request) {

    String userAgent = request.getHeader("user-agent");
    String osInfo;

    if (userAgent.contains("NT 6.0")) {
      osInfo = "Windows Vista/Server 2008";
    } else if (userAgent.contains("NT 5.2")) {
      osInfo = "Windows Server 2003";
    } else if (userAgent.contains("NT 5.1")) {
      osInfo = "Windows XP";
    } else if (userAgent.contains("NT 5.0")) {
      osInfo = "Windows 2000";
    } else if (userAgent.contains("NT 6.3")) {
      osInfo = "Windows 8.1​";
    } else if (userAgent.contains("NT 6.2")) {
      osInfo = "Windows 8";
    } else if (userAgent.contains("NT 6.1")) {
      osInfo = "Windows 7";
    } else if (userAgent.contains("NT 10.0")) {
      osInfo = "Windows 10";
    } else if (userAgent.contains("NT 4.0")) {
      osInfo = "Windows NT 4.0";
    } else if (userAgent.contains("Windows ME")) {
      osInfo = "Windows ME";
    } else if (userAgent.contains("Windows CE")) {
      osInfo = "Windows CE";
    } else if (userAgent.contains("OpenBSD")) {
      osInfo = "OpenBSD";
    } else if (userAgent.contains("SunOS")) {
      osInfo = "SunOS";
    } else if (userAgent.contains("9x 4.90")) {
      osInfo = "Windows Me";
    } else if (userAgent.contains("98")) {
      osInfo = "Windows 98";
    } else if (userAgent.contains("95")) {
      osInfo = "Windows 95";
    } else if (userAgent.contains("Win16")) {
      osInfo = "Windows 3.x";
    } else if (userAgent.contains("Linux")) {
      osInfo = "Linux";
    } else if (userAgent.contains("Macintosh")) {
      osInfo = "Macintosh";
    } else if (userAgent.contains("Mac_PowerPC")) {
      osInfo = "Mac OS";
    } else if (userAgent.contains("QNX")) {
      osInfo = "QNX";
    } else if (userAgent.contains("BeOS")) {
      osInfo = "BeOS";
    } else if (userAgent.contains("OS/2")) {
      osInfo = "OS/2";
    } else if (userAgent.contains("iPhone")) {
      osInfo = "iPhone";
    } else if (userAgent.contains("iPad")) {
      osInfo = "iPad";
    } else if (userAgent.contains("Android")) {
      osInfo = "Android";
    } else if (userAgent.contains("Windows")) {
      osInfo = "Windows";
    } else if (userAgent.contains("NT")) {
      osInfo = "Windows NT";
    } else {
      osInfo = "";
    }

    return osInfo;
  }

  /**
   * 클라이언트(Client)의 Agent 정보를 조회하는 기능
   *
   * @param request 원본 HTTP Request 객체
   * @return 클라이언트의 User-Agent 정보
   */
  public static String getClientAgentInfo(HttpServletRequest request) {
    return request.getHeader("user-agent");
  }

  /**
   * 클라이언트(Client)의 웹브라우저 종류를 조회하는 기능
   *
   * @param request 원본 HTTP Request 객체
   * @return 클라이언트 웹 브라우저 종류
   */
  public static String getClientWebKind(HttpServletRequest request) {

    String userAgent = request.getHeader("user-agent");

    // 웹브라우저 종류 조회
    String webKind;
    if (userAgent.contains("Edge")) {
      webKind = "Internet Explorer Edge" + userAgent.substring(userAgent.indexOf("Edge") + 4);
    } else if (userAgent.contains("rv") && userAgent.contains("Trident")) {
      webKind = "Internet Explorer "
          + userAgent.substring(userAgent.indexOf("rv") + 3, userAgent.indexOf(")"));
    } else if (userAgent.toUpperCase().contains("SAFARI")) {
      if (userAgent.toUpperCase().contains("CHROME")) {
        webKind = "Google Chrome";
      } else {
        webKind = "Safari";
      }
    } else if (userAgent.toUpperCase().contains("GECKO")) {
      if (userAgent.toUpperCase().contains("NESCAPE")) {
        webKind = "Netscape (Gecko/Netscape)";
      } else if (userAgent.toUpperCase().contains("FIREFOX")) {
        webKind = "Mozilla Firefox (Gecko/Firefox)";
      } else {
        webKind = "Mozilla (Gecko/Mozilla)";
      }
    } else if (userAgent.toUpperCase().contains("MSIE")) {
      if (userAgent.toUpperCase().contains("OPERA")) {
        webKind = "Opera (MSIE/Opera/Compatible)";
      } else {
        webKind = "Internet Explorer (MSIE/Compatible)";
      }
    } else if (userAgent.toUpperCase().contains("THUNDERBIRD")) {
      webKind = "Thunderbird";
    } else {
      webKind = "Other Web Browsers";
    }
    return webKind;
  }

  /**
   * 클라이언트(Client)의 웹브라우저 버전을 조회하는 기능
   *
   * @param request 원본 HTTP Request 객체
   * @return 클라이언트 웹 브라우저 버전
   */
  public static String getClientWebVer(HttpServletRequest request) {

    String userAgent = request.getHeader("user-agent");

    // 웹브라우저 버전 조회
    String webVer = "";
    String[] arr = {"MSIE", "OPERA", "NETSCAPE", "FIREFOX", "SAFARI", "CHROME"};
    for (String anArr : arr) {
      int startLoc = userAgent.toUpperCase().indexOf(anArr);
      if (startLoc != -1) {
        int findLoc = startLoc + anArr.length();
        webVer = userAgent.toUpperCase().substring(findLoc, findLoc + 5);
        webVer = webVer.replaceAll("/", "")
            .replaceAll(";", "")
            .replaceAll("\\^", "")
            .replaceAll(",", "")
            .replaceAll("//.", "");
      }
    }
    return webVer;
  }

  /**
   * 클라이언트 모바일 유무 검사
   *
   * @param request 원본 HTTP Request 객체
   * @return 모바일 유무 (true: 모바일)
   */
  public static boolean isMobile(HttpServletRequest request) {
    String userAgent = request.getHeader("user-agent");

    boolean mobile1 = userAgent.matches(
        ".*(iPhone|iPad|iPod|Android|Windows CE|"
            + "BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|"
            + "Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson).*"
    );
    boolean mobile2 = userAgent.matches(".*(LG|SAMSUNG|Samsung).*");
    int agentIndex = userAgent.indexOf("com.covision.moapp");

    return mobile1 || mobile2 || agentIndex > 0;

  }

}
