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

import com.art.common.quartz.core.constants.ScheduleConstants;
import com.art.common.quartz.core.scheduler.JobScheduler;
import com.art.scheduled.core.convert.JobConvert;
import com.art.scheduled.core.dto.JobDTO;
import com.art.scheduled.core.dto.JobPageDTO;
import com.art.scheduled.dao.dataobject.JobDO;
import com.art.scheduled.manager.JobManager;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 定时任务调度表
 *
 * @author fxz
 * @date 2022-04-03
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class JobService {

	private final JobManager jobManager;

	private final JobScheduler jobScheduler;

	/**
	 * 添加任务
	 */
	@SneakyThrows
	public JobDTO add(JobDTO dto) {
		// 保存数据库
		jobManager.add(dto);

		// 创建定时任务
		jobScheduler.add(dto.getJobId(), dto.getJobGroup(), dto.getParameters(), dto.getJobName(),
				dto.getCronExpression(), dto.getMisfirePolicy());

		// 更改job状态
		changeStatus(dto.getJobId(), dto.getJobGroup(), dto.getStatus());

		return dto;
	}

	/**
	 * 更新任务
	 */
	@SneakyThrows
	public JobDTO update(JobDTO dto) {
		jobManager.update(dto);

		jobScheduler.update(dto.getJobId(), dto.getJobGroup(), dto.getParameters(), dto.getJobName(),
				dto.getCronExpression(), dto.getMisfirePolicy());

		// 更改job状态
		changeStatus(dto.getJobId(), dto.getJobGroup(), dto.getStatus());

		return dto;
	}

	/**
	 * 根据id删除任务
	 */
	@SneakyThrows
	public Boolean deleteByJobId(Long id) {
		JobDO jobDO = jobManager.findById(id);
		int count = jobManager.deleteById(id);
		if (count > 0) {
			jobScheduler.delete(jobDO.getJobId(), jobDO.getJobGroup());
		}
		return Boolean.TRUE;
	}

	/**
	 * 定时任务状态修改
	 */
	@SneakyThrows
	public Boolean changeStatus(JobDTO dto) {
		// 更新数据库
		jobManager.update(dto);

		// 更改job状态
		changeStatus(dto.getJobId(), dto.getJobGroup(), dto.getStatus());

		return Boolean.TRUE;
	}

	private void changeStatus(Long jobId, String jobGroup, String status) {
		if (ScheduleConstants.Status.NORMAL.getValue().equals(status)) {
			resumeJob(jobId, jobGroup);
		}
		else if (ScheduleConstants.Status.PAUSE.getValue().equals(status)) {
			pauseJob(jobId, jobGroup);
		}
	}

	/**
	 * 暂停任务
	 * @param jobId jobId
	 * @param jobGroup job分组
	 */
	public void pauseJob(Long jobId, String jobGroup) {
		jobScheduler.pause(jobId, jobGroup);
	}

	/**
	 * 恢复任务
	 * @param jobId jobId
	 * @param jobGroup job分组
	 */
	public void resumeJob(Long jobId, String jobGroup) {
		jobScheduler.resumeJob(jobId, jobGroup);
	}

	/**
	 * 定时任务立即执行一次
	 */
	@SneakyThrows
	public void run(JobDTO dto) {
		jobScheduler.trigger(dto.getJobId(), dto.getJobGroup());
	}

	/**
	 * 分页
	 */
	public IPage<JobDTO> page(JobPageDTO page) {
		return JobConvert.INSTANCE.convertPage(jobManager.page(page));
	}

	/**
	 * 获取单条
	 */
	public JobDTO findById(Long id) {
		return JobConvert.INSTANCE.convert(jobManager.findById(id));
	}

}