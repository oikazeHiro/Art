/*
 * COPYRIGHT (C) 2022 Art AUTHORS(fxzcloud@gmail.com). ALL RIGHTS RESERVED.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.art.gateway.handler;

import com.anji.captcha.model.common.ResponseModel;
import com.anji.captcha.model.vo.CaptchaVO;
import com.anji.captcha.service.CaptchaService;
import com.art.common.core.model.Result;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author fxz
 */
@Component
@RequiredArgsConstructor
public class AjImageCodeCreateHandler implements HandlerFunction<ServerResponse> {

	private final CaptchaService captchaService;

	@Override
	@SneakyThrows
	public Mono<ServerResponse> handle(ServerRequest serverRequest) {
		CaptchaVO vo = new CaptchaVO();
		vo.setCaptchaType("blockPuzzle");

		ResponseModel responseModel = captchaService.get(vo);

		return ServerResponse.status(HttpStatus.OK)
			.contentType(MediaType.APPLICATION_JSON)
			.body(BodyInserters.fromValue(Result.success(responseModel)));
	}

}
