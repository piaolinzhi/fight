package wusc.edu.service.user.dao;

import com.ucmed.common.core.dao.BaseDao;

import wusc.edu.facade.user.entity.PmsUser;


/**
 * 
 * @描述: 用户表数据访问层接口.
 * @版本: 1.0 .
 */
public interface PmsUserDao extends BaseDao<PmsUser> {

	/**
	 * 根据用户登录名获取用户信息.
	 * 
	 * @param loginName
	 *            .
	 * @return user .
	 */
	PmsUser findByUserNo(String userNo);

}
