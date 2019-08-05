package cn.cloud.log.basic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.cloud.log.basic.dao.LogDao;
import cn.cloud.log.basic.po.LogPo;
import cn.cloud.log.basic.service.LogService;

@Service
public class LogServiceImpl implements LogService{
    @Autowired
    LogDao logdao;

	@Override
	public LogPo findLogByid(Long id) {
		// TODO Auto-generated method stub
		return logdao.findByid(id);
	}

	@Override
	public LogPo SaveLog(LogPo env) {
		// TODO Auto-generated method stub
		return logdao.save(env);
	}

	@Override
	public Page<LogPo> findAllLog(Specification<LogPo> specification, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return logdao.findAll(specification, pageRequest);
	}

	@Override
	public List<LogPo> findAllLog(Specification<LogPo> specification) {
		// TODO Auto-generated method stub
		return logdao.findAll(specification);
	}

	@Override
	public LogPo findlogbyoriginfilenameandmtime(String filename, String mtime,Long microid) {
		// TODO Auto-generated method stub
		return logdao.findlog(filename, mtime,microid);
	}
	
	
	

}
