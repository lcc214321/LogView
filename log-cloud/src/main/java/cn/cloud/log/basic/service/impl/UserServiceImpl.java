package cn.cloud.log.basic.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.cloud.log.basic.dao.UserDao;
import cn.cloud.log.basic.po.UserPo;
import cn.cloud.log.basic.po.UserType;
import cn.cloud.log.basic.service.UserService;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserDao userdao;


	@Override
	public UserPo saveUser(UserPo user) {
		// TODO Auto-generated method stub
		user=userdao.save(user);
		return user;
	}

	@Override
	public List<UserPo> findUserPo(String username, UserType type) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(username)){
			return userdao.findByusertype(type);
		}else{
			return userdao.findUserPo(username, type.name());
		}
		
	}

	@Override
	public UserPo findUser(String username) {
		// TODO Auto-generated method stub
		UserPo user=userdao.findByusername(username);
		return user;
	}

	@Override
	public UserPo findUserById(long id) {
		// TODO Auto-generated method stub
		return userdao.findByid(id);
	}

	@Override
	public Page<UserPo> findAll(Specification<UserPo> specification, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return userdao.findAll(specification, pageRequest);
	}

	@Override
	public void deleteUser(UserPo userpo) {
		// TODO Auto-generated method stub
		userdao.delete(userpo);
	}
}
