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

package com.art.common.sequence.segment.mysql;

import com.art.common.sequence.exception.SeqException;
import com.art.common.sequence.segment.SeqSegment;
import com.art.common.sequence.segment.SeqSegmentConfig;
import com.art.common.sequence.segment.SeqSegmentManager;
import com.art.common.sequence.segment.mysql.service.JdbcSegmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * mysql号段管理器
 *
 * @author Fxz
 * @version 0.0.1
 * @date 2022/5/23 13:14
 */
@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "fxz.common.sequence", value = "type", havingValue = "mysql", matchIfMissing = true)
public class JdbcSeqSegmentManager implements SeqSegmentManager {

	private final JdbcSegmentService jdbcSegmentService;

	private static Map<String, SeqSegment> seqRangeMap = new ConcurrentHashMap<>(8);

	/**
	 * 获取指定号段名的下一个号段
	 * @param name 号段名
	 * @param seqSegmentConfig 序列号号段配置
	 * @return 返回号段
	 * @throws SeqException 序列号异常
	 */
	@Override
	public SeqSegment nextSegment(String name, SeqSegmentConfig seqSegmentConfig) throws SeqException {
		SeqSegment seqSegment = seqRangeMap.get(name);
		if (Objects.isNull(seqSegment)) {
			// 不存在就set，存在就忽略
			jdbcSegmentService.setIfAbsentRange(name, seqSegmentConfig.getRangeStart());
		}

		Integer rangeStep = seqSegmentConfig.getRangeStep();
		Long max = jdbcSegmentService.incrementRange(name, rangeStep);
		Long min = max - rangeStep;

		seqSegment = new SeqSegment(min, max, seqSegmentConfig.getStep());

		seqRangeMap.put(name, seqSegment);

		return seqSegment;
	}

}
