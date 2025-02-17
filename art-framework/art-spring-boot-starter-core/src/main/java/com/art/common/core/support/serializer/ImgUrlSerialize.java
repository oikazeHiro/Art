/*
 * COPYRIGHT (C) 2023 Art AUTHORS(fxzcloud@gmail.com). ALL RIGHTS RESERVED.
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

package com.art.common.core.support.serializer;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.art.common.core.annotation.ImgUrl;
import com.art.common.core.constant.RegexpConstant;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author Fxz
 * @version 0.0.1
 * @date 2022/5/29 20:44
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class ImgUrlSerialize extends JsonSerializer<Object> implements ContextualSerializer {

	@Value("${biz.oss.resources-url}")
	private String imgDomain;

	@Override
	public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if (Objects.isNull(value)) {
			gen.writeString(StrUtil.EMPTY);
		}
		else if (value instanceof List) {
			writeList((List) value, gen);
		}
		else {
			writeString(value, gen);
		}
	}

	private void writeList(List value, JsonGenerator gen) throws IOException {
		if (CollectionUtils.isEmpty(value)) {
			gen.writeArray(new java.lang.String[] {}, 0, 0);
		}

		List<String> collect = (List<String>) value.stream().map(this::buildString).collect(Collectors.toList());
		gen.writeArray(collect.toArray(new String[0]), 0, collect.size());
	}

	private void writeString(Object value, JsonGenerator gen) throws IOException {
		gen.writeString(buildString(value));
	}

	public String buildString(Object value) {
		if (!(value instanceof String)) {
			return StrUtil.EMPTY;
		}

		String str = (String) value;
		if (StrUtil.isBlank(str)) {
			return StrUtil.EMPTY;
		}

		StringBuilder sb = new StringBuilder();

		Pattern pattern = Pattern.compile(RegexpConstant.HTTP_PROTOCOL_REGEXP);
		boolean res = pattern.matcher(str).find();

		// 图片为http协议开头，直接返回
		if (res) {
			sb.append(str);
		}
		else {
			sb.append(imgDomain).append(str);
		}

		return sb.toString();
	}

	@Override
	public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty beanProperty)
			throws JsonMappingException {
		if (Objects.nonNull(beanProperty)) {
			if (Objects.equals(beanProperty.getType().getRawClass(), String.class)
					|| Objects.equals(beanProperty.getType().getRawClass(), List.class)) {
				ImgUrl imgUrl = beanProperty.getAnnotation(ImgUrl.class);
				if (Objects.isNull(imgUrl)) {
					imgUrl = beanProperty.getContextAnnotation(ImgUrl.class);
				}
				if (Objects.nonNull(imgUrl)) {
					return SpringUtil.getBean(ImgUrlSerialize.class);
				}
			}
			return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
		}
		return serializerProvider.findNullValueSerializer(null);
	}

}
