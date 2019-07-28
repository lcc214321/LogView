package cn.cloud.log.basic.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.Predicate;
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

import cn.cloud.log.basic.bean.EnvInfo;
import cn.cloud.log.basic.bean.User;
import cn.cloud.log.basic.po.EnvPo;
import cn.cloud.log.basic.service.EnvService;
import cn.cloud.log.common.web.ControllerSupport;
import cn.cloud.log.exception.StatusException;
import cn.cloud.log.util.DateUtil;
import io.swagger.annotations.ApiOperation;


@Transactional
@RestController
@RequestMapping("${$rmp.ctr.basic}/env")
public class EnvController extends ControllerSupport {
	@Autowired
    EnvService envservice;
	
	@ApiOperation(value = "查找所有环境分页", notes = "")
	@GetMapping("envPage/{curPage}/{pageSize}")
	@CrossOrigin(allowCredentials="true")
	public Page<EnvPo> getuserPage(@PathVariable Integer curPage,
			@PathVariable Integer pageSize,@RequestParam(name="queryEnvName",required = false)String EnvName){
		Specification<EnvPo> specification = (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (!StringUtils.isEmpty(EnvName)) {
				predicates.add(cb.like(root.get("envname"), toSqlSearchPattern(EnvName)));
			}

			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		PageRequest pageRequest = PageRequest.of(curPage, pageSize,
				new Sort(Direction.DESC, "updatetime"));

		Page<EnvPo> page = envservice.findAllEnv(specification, pageRequest);
		return page;
	}
	
	@ApiOperation(value = "新增环境", notes = "")
	@PostMapping("add")
	@CrossOrigin(allowCredentials="true")
	public void addEnv(@RequestBody EnvInfo envinfo){

		EnvPo envpo=new EnvPo();
        envpo.setRemark(envinfo.getRemark());
        envpo.setEnvname(envinfo.getEnvname());
        envpo.setUpdatetime(DateUtil.getNowDate());
        envpo.setStatus(true);
        envservice.SaveEnv(envpo);
	}
	
	@ApiOperation(value = "修改环境", notes = "")
	@PutMapping("edit")
	@CrossOrigin(allowCredentials="true")
	public void editUser(@RequestBody EnvInfo envinfo){
		if(StringUtils.isEmpty(envinfo.getEnvname())){
			throw new StatusException("225","环境名称不能为空");
		}
		EnvPo envpo=envservice.findEnvByid(envinfo.getId());
		envpo.setEnvname(envinfo.getEnvname());
		envpo.setRemark(envinfo.getRemark());
		envpo.setUpdatetime(DateUtil.getNowDate());
		envservice.SaveEnv(envpo);
	}
	
	
	
	@ApiOperation(value = "启用禁用环境", notes = "")
	@PutMapping("{status}/{EnvId}")
	@CrossOrigin(allowCredentials="true")
	public void resetuserPassword(@PathVariable String status,
			@PathVariable long EnvId){
		EnvPo envpo=envservice.findEnvByid(EnvId);
		if(status.equals("enable")){
			envpo.setStatus(true);
		}else{
			envpo.setStatus(false);
		}	
		envpo.setUpdatetime(DateUtil.getNowDate());
		envservice.SaveEnv(envpo);
	}
	
	@ApiOperation(value = "删除环境", notes = "")
	@DeleteMapping("{envid}")
	@CrossOrigin(allowCredentials="true")
	public void deleteUser(@PathVariable long envid){
		EnvPo envpo=envservice.findEnvByid(envid);
		envservice.deleteEnv(envpo);
	}
}
