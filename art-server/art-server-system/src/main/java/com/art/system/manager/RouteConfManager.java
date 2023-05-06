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

package com.art.system.manager;

import com.art.system.api.route.dto.RouteConfDTO;
import com.art.system.core.convert.RouteConfConvert;
import com.art.system.dao.dataobject.RouteConfDO;
import com.art.system.dao.mysql.RouteConfMapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Fxz
 * @version 0.0.1
 * @date 2022/11/26 13:49
 */
@RequiredArgsConstructor
@Component
public class RouteConfManager {

	private final RouteConfMapper routeConfMapper;

	public void addRouteConf(RouteConfDTO routeConfDTO) {
		routeConfMapper.insert(RouteConfConvert.INSTANCE.convert(routeConfDTO));
	}

	public List<RouteConfDO> listRouteConf() {
		return routeConfMapper.selectList(Wrappers.<RouteConfDO>lambdaQuery().orderByAsc(RouteConfDO::getSortOrder));
	}

	public void deleteRouteConf() {
		routeConfMapper.delete(Wrappers.emptyWrapper());
	}

	public void deleteRouteConfById(Long id) {
		routeConfMapper.deleteById(id);
	}

	public RouteConfDO getRouteCongById(Long id) {
		return routeConfMapper.selectById(id);
	}

}
