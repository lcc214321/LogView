package cn.cloud.log.basic.controller;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;

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

import cn.cloud.log.basic.service.EnvService;
import cn.cloud.log.basic.service.MicroEnvService;
import cn.cloud.log.basic.bean.MicroEnvInfo;
import cn.cloud.log.basic.po.EnvPo;
import cn.cloud.log.basic.po.MicroServicePo;
import cn.cloud.log.common.web.ControllerSupport;
import cn.cloud.log.exception.StatusException;
import cn.cloud.log.util.DateUtil;
import io.swagger.annotations.ApiOperation;
@Transactional
@RestController
@RequestMapping("${$rmp.ctr.basic}/microenv")
public class MicroEnvController extends ControllerSupport {
    
	@Autowired
	MicroEnvService microenvservice;
	@Autowired
	EnvService envservice;
	
	@ApiOperation(value = "查找所有微服务环境分页", notes = "")
	@GetMapping("microenvPage/{curPage}/{pageSize}")
	@CrossOrigin(allowCredentials="true")
	public Page<MicroServicePo> getuserPage(@PathVariable Integer curPage,
			@PathVariable Integer pageSize,@RequestParam(name="queryEnvId",required = false)String envid,@RequestParam(name="queryMicroEnvId",required = false)String micorenvid){
		Specification<MicroServicePo> specification = (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (!StringUtils.isEmpty(envid)) {
				predicates.add(cb.equal(root.get("envid"), envid));
			}
			if (!StringUtils.isEmpty(micorenvid)) {
				predicates.add(cb.equal(root.get("id"), micorenvid));
			}
			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		PageRequest pageRequest = PageRequest.of(curPage, pageSize,
				new Sort(Direction.DESC, "updatetime"));

		Page<MicroServicePo> page = microenvservice.findAllEnv(specification, pageRequest);
		return page;
	}
	
	@ApiOperation(value = "新增微服务", notes = "")
	@PostMapping("add")
	@CrossOrigin(allowCredentials="true")
	public void addEnv(@RequestBody MicroEnvInfo envinfo){
		MicroServicePo envpo=new MicroServicePo();
		EnvPo poenv=envservice.findEnvByid(envinfo.getEnvid());
		envpo.setEnvname(poenv.getEnvname());
		envpo.setIpaddr(envinfo.getIpaddr());
		envpo.setLoginpassword(envinfo.getLoginpassword());
		envpo.setLoginuser(envinfo.getLoginuser());
		envpo.setLognamepattern(envinfo.getLognamepattern());
		envpo.setLognamepostfix(envinfo.getLognamepostfix());
		envpo.setLogpath(envinfo.getLogpath());
		envpo.setLogpattern(envinfo.getLogpattern());
		envpo.setMicroservicename(envinfo.getMicroservicename());
		envpo.setOstype(envinfo.getOstype());
		envpo.setSplitstr(envinfo.getSplitstr());
		envpo.setUpdatetime(DateUtil.getNowDate());
		microenvservice.SaveEnv(envpo);
	}
	
	@ApiOperation(value = "修改微服务配置", notes = "")
	@PutMapping("edit")
	@CrossOrigin(allowCredentials="true")
	public void editUser(@RequestBody MicroEnvInfo envinfo){
		if(StringUtils.isEmpty(envinfo.getMicroservicename())){
			throw new StatusException("225","微服务名称不能为空");
		}
		if(StringUtils.isEmpty(envinfo.getEnvid())){
			throw new StatusException("225","环境id不能为空");
		}
		MicroServicePo envpo=microenvservice.findMicroEnvByid(envinfo.getId());
		EnvPo poenv=envservice.findEnvByid(envinfo.getEnvid());
		envpo.setEnvid(poenv.getId());
		envpo.setEnvname(poenv.getEnvname());
		envpo.setIpaddr(envinfo.getIpaddr());
		envpo.setLognamepattern(envinfo.getLognamepattern());
		envpo.setLognamepostfix(envinfo.getLognamepostfix());
		envpo.setLogpath(envinfo.getLogpath());
		envpo.setLogpattern(envinfo.getLogpattern());
		envpo.setMicroservicename(envinfo.getMicroservicename());
		envpo.setOstype(envinfo.getOstype());
		envpo.setSplitstr(envinfo.getSplitstr());
		envpo.setUpdatetime(DateUtil.getNowDate());
		microenvservice.SaveEnv(envpo);
	}
		
	@ApiOperation(value = "删除微服务", notes = "")
	@DeleteMapping("{envid}")
	@CrossOrigin(allowCredentials="true")
	public void deleteMicroEnv(@PathVariable long envid){
//		EnvPo envpo=envservice.findEnvByid(envid);
//		envservice.deleteEnv(envpo);
		MicroServicePo envpo=microenvservice.findMicroEnvByid(envid);
		microenvservice.deleteEnv(envpo);
	}
	
	@ApiOperation(value = "获取环境下的所有微服务", notes = "")
	@GetMapping("/queryMicroByEnvId")
	@CrossOrigin(allowCredentials="true")
	public List<MicroServicePo> queryMicroEnvByEnvid(@RequestParam(name="queryEnvId",required = false)String envid){
		List<MicroServicePo> list=microenvservice.findMicroEnvByEnvId(Long.valueOf(envid));
		return list;
//		EnvPo envpo=envservice.findEnvByid(envid);
//		envservice.deleteEnv(envpo);
	}
}
