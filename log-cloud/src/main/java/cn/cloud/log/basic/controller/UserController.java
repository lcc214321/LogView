package cn.cloud.log.basic.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.cloud.log.basic.po.UserPo;
import cn.cloud.log.basic.service.UserService;
import cn.cloud.log.common.web.ControllerSupport;
import cn.cloud.log.exception.StatusException;
import io.swagger.annotations.ApiOperation;

/**
 * usercontroller
 * @author Administrator
 *
 */
@Transactional
@RestController
@RequestMapping("${$rmp.ctr.basic}/user")
public class UserController extends ControllerSupport {
	@Autowired
    UserService userservice;
	
	@ApiOperation(value = "修改密码", notes = "")
	@PutMapping("password")
	@CrossOrigin(allowCredentials="true")
	public void modifypassword(@RequestParam("oldpassword")String oldpwd,@RequestParam("newpassword")String newpwd){
		HttpSession session=getRequest().getSession(false);
		String userid=((String) session.getAttribute("key")).replace("U_S_","");
		UserPo userpo=userservice.findUserById(Long.valueOf(userid));
		if(userpo.getPassword().equals(oldpwd)){
			userpo.setPassword(newpwd);
			userservice.saveUser(userpo);
		}else{
			throw new StatusException("225","旧密码不对");
		}
	}
}
