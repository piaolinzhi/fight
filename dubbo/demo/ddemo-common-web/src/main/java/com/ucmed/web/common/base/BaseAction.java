package com.ucmed.web.common.base;

import com.ucmed.web.common.constant.SessionConstant;
import com.ucmed.web.common.struts.Struts2ActionSupport;
import wusc.edu.facade.user.entity.PmsUser;


/**
 * 
 * @描述: Web系统权限模块基础支撑Action.
 * @版本: V1.0
 * 
 */
@SuppressWarnings("serial")
public class BaseAction extends Struts2ActionSupport implements UserLoginedAware {

	/**
	 * 取出当前登录用户对象
	 */
	@Override
	public PmsUser getLoginedUser() {
		PmsUser user = (PmsUser) this.getSessionMap().get(SessionConstant.USER_SESSION_KEY);
		return user;
	}

}
