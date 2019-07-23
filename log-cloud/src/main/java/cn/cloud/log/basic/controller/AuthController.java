package cn.cloud.log.basic.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.cloud.log.basic.bean.LoginInfo;
import cn.cloud.log.basic.bean.User;
import cn.cloud.log.basic.po.UserPo;
import cn.cloud.log.basic.service.UserService;
import cn.cloud.log.exception.StatusException;
import io.swagger.annotations.ApiOperation;

@Transactional
@RestController

@RequestMapping("${$rmp.ctr.basic}/auth")
public class AuthController {
    @Autowired
    UserService userservice;
	
	@ApiOperation(value = "登入", notes = "")
	@PostMapping("login")
	@CrossOrigin
	public Object login(@RequestBody LoginInfo loginInfo, HttpServletRequest request) {
		if(StringUtils.isEmpty(loginInfo.getUsername())){
			throw new StatusException("402","用户名不能为空");
		}
		if(StringUtils.isEmpty(loginInfo.getPassword())){
			throw new StatusException("402","密码不能为空");
		}
		UserPo userpo=userservice.findUser(loginInfo.getUsername());
		if(userpo==null){
			throw new StatusException("402","用户不存在");
		}
		if(userpo.getPassword().equals(loginInfo.getPassword())){
			//如果账户禁用则直接抛出错误
			if(userpo.getStatus()==false){
				throw new StatusException("402","用户禁用");
			}
			//将user信息记入session中
			HttpSession session=request.getSession();
			String key="U_S_"+userpo.getId();
			String token=UUID.randomUUID().toString();
			session.setAttribute("key", key);
			session.setAttribute("token", token);
			User user=new User();
			user.setKey(key);
			user.setToken(token);
			user.setUsername(userpo.getUsername());
			user.setUserid(userpo.getId());
			user.setUserType(userpo.getUsertype());
			return user;
		}else{
			throw new StatusException("402","用户名或密码不正确");
		}
		
		
		
		
		
	}
}
