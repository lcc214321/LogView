package cn.cloud.log.basic.service;



import java.util.List;

import cn.cloud.log.basic.po.UserPo;
import cn.cloud.log.basic.po.UserType;

public interface UserService{
	public UserPo saveUser(UserPo user);
	public List<UserPo> findUserPo(String username,UserType type);
	public UserPo findUser(String username);
	public UserPo findUserById(long id);
}
