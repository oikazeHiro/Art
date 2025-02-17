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

package com.art.scheduled.service;

import com.art.common.quartz.core.service.ArtJobLogService;
import com.art.scheduled.core.convert.JobLogConvert;
import com.art.scheduled.core.dto.JobLogDTO;
import com.art.scheduled.core.dto.JobLogPageDTO;
import com.art.scheduled.manager.JobLogManager;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 定时任务调度日志表
 *
 * @author fxz
 * @date 2022-04-03
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JobLogService implements ArtJobLogService {

	private final JobLogManager jobLogManager;

	/**
	 * 保存job执行日志
	 * @param jobBeanName jobBeanName
	 * @param jobMessage job日志信息
	 * @param ex 异常信息
	 */
	@Override
	public void addJobLog(String jobBeanName, String jobMessage, String ex) {
		JobLogDTO dto = new JobLogDTO().setJobName(jobBeanName)
			.setJobMessage(jobMessage)
			.setExceptionInfo(ex)
			.setStatus(StringUtils.isBlank(ex) ? "0" : "1");
		jobLogManager.addJobLog(dto);
	}

	/**
	 * 分页
	 */
	public IPage<JobLogDTO> page(JobLogPageDTO pageDTO) {
		return JobLogConvert.INSTANCE.convertPage(jobLogManager.page(pageDTO));
	}

	/**
	 * 获取单条
	 */
	public JobLogDTO findById(Long id) {
		return JobLogConvert.INSTANCE.convert(jobLogManager.findById(id));
	}

}