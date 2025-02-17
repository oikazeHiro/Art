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

package com.art.common.mp.core.encrypt.config;

import com.art.common.mp.core.encrypt.core.interceptor.DecryptInterceptor;
import com.art.common.mp.core.encrypt.core.interceptor.EncryptInterceptor;
import com.art.common.mp.core.encrypt.core.propertie.EncryptProperties;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * @author Fxz
 * @version 0.0.1
 * @date 2022/9/15 14:09
 */
@AutoConfiguration
@EnableConfigurationProperties(EncryptProperties.class)
public class EncryptAutoConfig {

	@Bean
	public DecryptInterceptor decryptInterceptor(EncryptProperties encryptProperties) {
		return new DecryptInterceptor(encryptProperties);
	}

	@Bean
	public EncryptInterceptor encryptInterceptor(EncryptProperties encryptProperties) {
		return new EncryptInterceptor(encryptProperties);
	}

}
