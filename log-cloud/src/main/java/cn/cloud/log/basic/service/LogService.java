package cn.cloud.log.basic.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import cn.cloud.log.basic.po.LogPo;



public interface LogService {
	   public LogPo findLogByid(Long id);
	   public LogPo SaveLog(LogPo env);
	   public Page<LogPo> findAllLog(Specification<LogPo> specification,PageRequest pageRequest);
	   public List<LogPo> findAllLog(Specification<LogPo> specification);//不分页查询
	   public LogPo findlogbyoriginfilenameandmtime(String filename,String mtime,Long microid);
	   
}
