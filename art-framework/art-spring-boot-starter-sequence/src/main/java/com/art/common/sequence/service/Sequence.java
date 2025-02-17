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

package com.art.common.sequence.service;

import com.art.common.sequence.exception.SeqException;

/**
 * 序列号生成器接口
 *
 * @author Fxz
 * @version 0.0.1
 * @date 2022/5/23 09:43
 */
public interface Sequence {

	/**
	 * 生成下一个序列号
	 * @param name 号段名
	 * @return 序列号
	 * @throws SeqException 序列号异常
	 */
	Long next(String name) throws SeqException;

	/**
	 * 下一个生成序号（带格式）
	 * @param name 号段名
	 * @return 序列号
	 * @throws SeqException 序列号异常
	 */
	String nextValue(String name) throws SeqException;

}
