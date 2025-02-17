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

package com.art.common.canal.config;

import com.art.common.canal.CanalBase;
import com.art.common.canal.DefaultCanalBase;
import com.art.common.canal.support.parser.*;
import com.art.common.canal.support.parser.converter.CanalFieldConverterFactory;
import com.art.common.canal.support.parser.converter.InMemoryCanalFieldConverterFactory;
import com.art.common.canal.support.processor.BaseCanalBinlogEventProcessor;
import com.art.common.canal.support.processor.CanalBinlogEventProcessorFactory;
import com.art.common.canal.support.processor.InMemoryCanalBinlogEventProcessorFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import java.util.Map;

/**
 * @author fxz
 */
@AutoConfiguration
public class CanalAutoConfiguration implements SmartInitializingSingleton, BeanFactoryAware {

	private ConfigurableListableBeanFactory configurableListableBeanFactory;

	@Bean
	@ConditionalOnMissingBean
	public CanalBinlogEventProcessorFactory canalBinlogEventProcessorFactory() {
		return InMemoryCanalBinlogEventProcessorFactory.of();
	}

	@Bean
	@ConditionalOnMissingBean
	public ModelTableMetadataManager modelTableMetadataManager(CanalFieldConverterFactory canalFieldConverterFactory) {
		return InMemoryModelTableMetadataManager.of(canalFieldConverterFactory);
	}

	@Bean
	@ConditionalOnMissingBean
	public CanalFieldConverterFactory canalFieldConverterFactory() {
		return InMemoryCanalFieldConverterFactory.of();
	}

	@Bean
	@ConditionalOnMissingBean
	public CanalBinLogEventParser canalBinLogEventParser() {
		return DefaultCanalBinLogEventParser.of();
	}

	@Bean
	@ConditionalOnMissingBean
	public ParseResultInterceptorManager parseResultInterceptorManager(
			ModelTableMetadataManager modelTableMetadataManager) {
		return InMemoryParseResultInterceptorManager.of(modelTableMetadataManager);
	}

	@Bean
	@Primary
	public CanalBase canalGlue(CanalBinlogEventProcessorFactory canalBinlogEventProcessorFactory) {
		return DefaultCanalBase.of(canalBinlogEventProcessorFactory);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.configurableListableBeanFactory = (ConfigurableListableBeanFactory) beanFactory;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void afterSingletonsInstantiated() {
		ParseResultInterceptorManager parseResultInterceptorManager = configurableListableBeanFactory
			.getBean(ParseResultInterceptorManager.class);
		ModelTableMetadataManager modelTableMetadataManager = configurableListableBeanFactory
			.getBean(ModelTableMetadataManager.class);
		CanalBinlogEventProcessorFactory canalBinlogEventProcessorFactory = configurableListableBeanFactory
			.getBean(CanalBinlogEventProcessorFactory.class);
		CanalBinLogEventParser canalBinLogEventParser = configurableListableBeanFactory
			.getBean(CanalBinLogEventParser.class);
		Map<String, BaseParseResultInterceptor> interceptors = configurableListableBeanFactory
			.getBeansOfType(BaseParseResultInterceptor.class);
		interceptors
			.forEach((k, interceptor) -> parseResultInterceptorManager.registerParseResultInterceptor(interceptor));
		Map<String, BaseCanalBinlogEventProcessor> processors = configurableListableBeanFactory
			.getBeansOfType(BaseCanalBinlogEventProcessor.class);
		processors.forEach((k, processor) -> processor.init(canalBinLogEventParser, modelTableMetadataManager,
				canalBinlogEventProcessorFactory, parseResultInterceptorManager));
	}

}
