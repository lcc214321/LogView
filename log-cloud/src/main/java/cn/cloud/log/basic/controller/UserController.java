package cn.cloud.log.basic.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Subquery;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.cloud.log.basic.bean.User;
import cn.cloud.log.basic.po.UserPo;
import cn.cloud.log.basic.po.UserType;
import cn.cloud.log.basic.service.UserService;
import cn.cloud.log.common.web.ControllerSupport;
import cn.cloud.log.exception.StatusException;
import cn.cloud.log.util.DateUtil;
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
	
	@ApiOperation(value = "查找所有用户分页", notes = "")
	@GetMapping("userPage/{curPage}/{pageSize}")
	@CrossOrigin(allowCredentials="true")
	public Page<UserPo> getuserPage(@PathVariable Integer curPage,
			@PathVariable Integer pageSize,@RequestParam(name="queryUserName",required = false)String username,@RequestParam(name="queryUserType",required = false) String usertype){
		Specification<UserPo> specification = (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (!StringUtils.isEmpty(username)) {
				predicates.add(cb.like(root.get("username"), toSqlSearchPattern(username)));
			}
			if (!StringUtils.isEmpty(usertype)) {
				predicates.add(cb.equal(root.get("usertype"), usertype));
			}
		

			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		PageRequest pageRequest = PageRequest.of(curPage, pageSize,
				new Sort(Direction.DESC, "updatetime"));

		Page<UserPo> page = userservice.findAll(specification, pageRequest);
		return page;
	}
	
	@ApiOperation(value = "新增用户", notes = "")
	@PostMapping("add")
	@CrossOrigin(allowCredentials="true")
	public void addUser(@RequestBody User userinfo){
		if(StringUtils.isEmpty(userinfo.getUsername())){
			throw new StatusException("225","用户名不能为空");
		}
		if(StringUtils.isEmpty(userinfo.getUserType())){
			throw new StatusException("225","用户类型不能为空");
		}
		UserPo userpo=new UserPo();
		userpo.setUsername(userinfo.getUsername());
		userpo.setUsertype(userinfo.getUserType().name());
		userpo.setPassword(userinfo.getPassword());
		userpo.setStatus(userinfo.getStatus());
		userpo.setUpdatetime(DateUtil.getNowDate());
		userservice.saveUser(userpo);
	}
	
	@ApiOperation(value = "修改用户", notes = "")
	@PutMapping("edit")
	@CrossOrigin(allowCredentials="true")
	public void editUser(@RequestBody User userinfo){
		if(StringUtils.isEmpty(userinfo.getUsername())){
			throw new StatusException("225","用户名不能为空");
		}
		if(StringUtils.isEmpty(userinfo.getUserType())){
			throw new StatusException("225","用户类型不能为空");
		}
		UserPo userpo=userservice.findUserById(userinfo.getUserid());
		userpo.setUsername(userinfo.getUsername());
		userpo.setUsertype(userinfo.getUserType().name());
		userpo.setStatus(userinfo.getStatus());
		userpo.setUpdatetime(DateUtil.getNowDate());
		userservice.saveUser(userpo);
	}
	
	@ApiOperation(value = "重置用户密码", notes = "")
	@PutMapping("resetpwd")
	@CrossOrigin(allowCredentials="true")
	public void resetuserPassword(@RequestBody User userinfo){
		UserPo userpo=userservice.findUserById(userinfo.getUserid());
		userpo.setPassword("123456");
		userservice.saveUser(userpo);
	}
	
	@ApiOperation(value = "启用禁用用户", notes = "")
	@PutMapping("{status}/{userid}")
	@CrossOrigin(allowCredentials="true")
	public void edituserstatus(@PathVariable String status,
			@PathVariable long userid){
		UserPo userpo=userservice.findUserById(userid);
		if(status.equals("enable")){
			userpo.setStatus(true);
		}else{
			userpo.setStatus(false);
		}	
		userpo.setUpdatetime(DateUtil.getNowDate());
		userservice.saveUser(userpo);
	}
	
	@ApiOperation(value = "删除用户", notes = "")
	@DeleteMapping("{userid}")
	@CrossOrigin(allowCredentials="true")
	public void deleteUser(@PathVariable long userid){
		UserPo userpo=userservice.findUserById(userid);
		userservice.deleteUser(userpo);
	}
	
	
}
