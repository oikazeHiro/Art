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

package com.art.common.core.constant;

/**
 * @author fxz
 */
public interface RegexpConstant {

	/**
	 * 简单手机号正则（这里只是简单校验是否为 11位，实际规则更复杂）
	 */
	String MOBILE_REG = "[1]\\d{10}";

	/**
	 * http协议正则
	 */
	String HTTP_PROTOCOL_REGEXP = "^((http[s]{0,1})://)";

}