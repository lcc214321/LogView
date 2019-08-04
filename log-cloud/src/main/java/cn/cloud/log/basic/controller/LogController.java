package cn.cloud.log.basic.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import cn.cloud.log.basic.po.LogPo;
import cn.cloud.log.basic.service.LogService;
import cn.cloud.log.common.web.ControllerSupport;
import io.swagger.annotations.ApiOperation;

@Transactional
@RestController
@RequestMapping("${$rmp.ctr.log}/log")
public class LogController extends ControllerSupport {

	@Autowired
	LogService logservice;

	@ApiOperation(value = "采集日志", notes = "")
	@PostMapping("collect")
	@CrossOrigin(allowCredentials="true")
	public void collect(){
		
	}

	@ApiOperation(value = "查询日志", notes = "")
	@GetMapping("logPage/{curPage}/{curPageSize}")
	@CrossOrigin(allowCredentials = "true")
	public Page<LogPo> getLogPage(@PathVariable Integer curPage, @PathVariable Integer curPageSize,
			@RequestParam(name = "queryEnvId", required = false) String envid,
			@RequestParam(name = "queryMicroEnvId", required = false) String microenvid,
			@RequestParam(name = "queryServiceIP", required = false) String ipaddr,
			@RequestParam(name = "querydate", required = false) String collectdate,
			@RequestParam(name = "querycontent", required = false) String querycontent) {

		Specification<LogPo> specification = (root, query, cb) -> {
			List<Predicate> predicates = new ArrayList<>();
			if (!StringUtils.isEmpty(envid)) {
				predicates.add(cb.equal(root.get("envid"), envid));
			}
			if (!StringUtils.isEmpty(microenvid)) {
				predicates.add(cb.equal(root.get("microenvid"), microenvid));
			}
//			if (!StringUtils.isEmpty(logtype)) {
//				predicates.add(cb.equal(root.get("logtype"), logtype));
//			}
			if (!StringUtils.isEmpty(ipaddr)) {
				predicates.add(cb.equal(root.get("ipaddr"), ipaddr));
			}
			if (!StringUtils.isEmpty(collectdate)) {
				predicates.add(cb.equal(root.get("collectdate"), collectdate));
			}

			return cb.and(predicates.toArray(new Predicate[predicates.size()]));
		};

		PageRequest pageRequest = PageRequest.of(curPage, curPageSize,
				new Sort(Direction.DESC, "updatetime"));

		Page<LogPo> page = logservice.findAllLog(specification, pageRequest);
		if(!StringUtils.isEmpty(querycontent)){
			List<LogPo> listpo=page.getContent();
			for(LogPo po:listpo){
				
			}
		}
		
		return page;
	}
	/**
	 * 通过pos每次返回1000行，下拉滚动条继续调用返回
	 * @param curid
	 * @param curPos
	 * @return
	 * @throws IOException 
	 */
	
	@ApiOperation(value = "查看日志", notes = "")
	@GetMapping("viewlog/{curid}/{curPos}")
	@CrossOrigin(allowCredentials = "true")
	public String getLogContent(@PathVariable Long curid, @PathVariable Integer curPos) throws IOException{
		LogPo logpo=logservice.findLogByid(curid);
		String filepath=logpo.getSavepath();
		File file=new File(filepath);
		InputStream in =new FileInputStream(file);
		byte[] bytes=new byte[in.available()];
		in.read(bytes);
		return new String(bytes,"utf-8");
	}
	
	@ApiOperation(value = "查看日志", notes = "")
	@GetMapping("downloadviewlog/{curid}")
	@CrossOrigin(allowCredentials = "true")
	public void exportlog(@PathVariable Long curid) throws IOException{
		LogPo logpo=logservice.findLogByid(curid);
		String filepath=logpo.getSavepath();
		File file=new File(filepath);
		InputStream in =new FileInputStream(file);
		exportFile(file.getName(), file);
	}
	
	
	
}
