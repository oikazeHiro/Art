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

package com.art.system.api.dict.dto;

import com.art.common.core.model.VueRouter;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Fxz
 * @version 0.0.1
 * @date 2021-11-28 15:24
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@Data
public class MenuDTO implements Serializable {

	private static final long serialVersionUID = -1L;

	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 一级菜单id
	 */
	public static final long LEVEL_ONE_MENU = 0L;

	/**
	 * 菜单
	 */
	public static final String TYPE_MENU = "0";

	/**
	 * 按钮
	 */
	public static final String TYPE_BUTTON = "1";

	/**
	 * 上级菜单ID
	 */
	private Long parentId;

	/**
	 * 菜单/按钮名称
	 */
	private String name;

	/**
	 * 菜单URL
	 */
	private String path;

	/**
	 * 重定向地址
	 */
	private String redirect;

	/**
	 * 对应 Vue组件
	 */
	private String component;

	/**
	 * 权限标识
	 */
	private String perms;

	/**
	 * 图标
	 */
	private String icon;

	/**
	 * 类型 0菜单 1按钮
	 */
	private String type;

	/**
	 * 是否缓存
	 */
	private Integer keepAlive;

	/**
	 * 排序
	 */
	private Integer orderNum;

	/**
	 * 是否隐藏(1 隐藏 0 不隐藏)
	 */
	private String hidden;

	/**
	 * title
	 */
	private String title;

	/**
	 * 应用id
	 */
	private Long application;

	/**
	 * 创建者
	 */
	private String createBy;

	/**
	 * 创建时间
	 */
	private LocalDateTime createTime;

	/**
	 * 更新者
	 */
	private String updateBy;

	/**
	 * 更新时间
	 */
	private LocalDateTime updateTime;

	public VueRouter<MenuDTO> toVueRouter() {
		VueRouter<MenuDTO> route = new VueRouter<>();
		route.setId(this.getId().toString());
		route.setParentId(this.getParentId().toString());
		route.setPath(this.getPath());
		route.setRedirect(this.getRedirect());
		route.setComponent(this.getComponent());
		route.setName(this.getName());
		route.setHidden(this.getHidden());
		route.setTitle(this.getTitle());
		route.setPerms(this.getPerms());
		route.setIcon(this.getIcon());
		route.setApplication(this.getApplication());
		route.setKeepAlive(this.getKeepAlive().toString());
		route.setType(this.getType());
		route.setOrderNum(Double.valueOf(this.getOrderNum()));
		return route;
	}

}
