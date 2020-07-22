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

package kr.co.happl.framework.common.net;

import kr.co.happl.framework.common.exception.ThrowingConsumer;
import kr.co.happl.framework.common.logging.HapplLogger;
import kr.co.happl.framework.common.logging.enums.LoggerType;
import io.netty.channel.ChannelOption;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.LoggingCodecSupport;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.util.Optional;
import java.util.function.Function;

public class FluxClient {

	private final MediaType DEFAULT_MEDIA_TYPE = MediaType.APPLICATION_JSON;
	private final HapplLogger logger = HapplLogger.getInstance(LoggerType.INFO);
	private final WebClient webClient;

	@SuppressWarnings("unused")
	public static class Builder {
		private int memorySize = 1024 * 1024 * 50;
		private int timeout = 180;
		private String userAgent = "Happl DNG";
		private Function<ClientRequest, Mono<ClientRequest>> requestProcessor;
		private Function<ClientResponse, Mono<ClientResponse>> responseProcessor;
		private String baseUrl;

		public Builder memorySize(int memorySize) {
			this.memorySize = memorySize;
			return this;
		}

		public Builder timeout(int timeout) {
			this.timeout = timeout;
			return this;
		}

		public Builder userAgent(String userAgent) {
			this.userAgent = userAgent;
			return this;
		}

		public Builder requestProcessor(Function<ClientRequest, Mono<ClientRequest>> requestProcessor) {
			this.requestProcessor = requestProcessor;
			return this;
		}

		public Builder responseProcessor(Function<ClientResponse, Mono<ClientResponse>> responseProcessor) {
			this.responseProcessor = responseProcessor;
			return this;
		}

		public Builder baseUrl(String baseUrl) {
			this.baseUrl = baseUrl;
			return this;
		}

		public FluxClient build() {
			return new FluxClient(this);
		}
	}

	private FluxClient(Builder builder) {
		ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
				.codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(builder.memorySize))
				.build();
		exchangeStrategies
				.messageWriters().stream()
				.filter(LoggingCodecSupport.class::isInstance)
				.forEach(writer -> ((LoggingCodecSupport) writer).setEnableLoggingRequestDetails(true));

		this.webClient = WebClient.builder()
				.clientConnector(
						new ReactorClientHttpConnector(
								HttpClient
										.create()
										.secure(
												ThrowingConsumer.unchecked(
														sslContextSpec -> sslContextSpec.sslContext(
																SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build()
														)
												)
										)
										.tcpConfiguration(
												client -> client.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 120_000)
														.doOnConnected(conn ->
																conn
																		.addHandlerLast(new ReadTimeoutHandler(builder.timeout))
																		.addHandlerLast(new WriteTimeoutHandler(builder.timeout))
														)
										)
						)
				)
				.exchangeStrategies(exchangeStrategies)
				.filter(ExchangeFilterFunction.ofRequestProcessor(
						Optional.ofNullable(builder.requestProcessor).orElse(requestProcessor()))
				)
				.filter(ExchangeFilterFunction.ofResponseProcessor(
						Optional.ofNullable(builder.responseProcessor).orElse(responseProcessor()))
				)
				.defaultHeader("user-agent", builder.userAgent)
				.baseUrl(builder.baseUrl)
				.build();
	}

	private Function<ClientRequest, Mono<ClientRequest>> requestProcessor() {
		return clientRequest -> {
			logger.debug(String.format("Request: %s %s", clientRequest.method(), clientRequest.url()));
			clientRequest.headers().forEach((name, values) ->
					values.forEach(value -> logger.debug(String.format("%s : %s", name, value)))
			);
			return Mono.just(clientRequest);
		};
	}

	private Function<ClientResponse, Mono<ClientResponse>> responseProcessor() {
		return clientResponse -> {
			clientResponse.headers().asHttpHeaders().forEach((name, values) ->
					values.forEach(value -> logger.debug(String.format("Header '%s': %s", name, value)))
			);
			return Mono.just(clientResponse);
		};
	}

	public void get() {

	}

}
