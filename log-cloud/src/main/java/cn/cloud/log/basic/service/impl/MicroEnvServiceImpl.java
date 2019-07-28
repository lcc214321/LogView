package cn.cloud.log.basic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.cloud.log.basic.dao.EnvDao;
import cn.cloud.log.basic.dao.MicroEnvDao;
import cn.cloud.log.basic.po.MicroServicePo;
import cn.cloud.log.basic.service.MicroEnvService;

@Service
public class MicroEnvServiceImpl implements MicroEnvService {
    @Autowired
    private MicroEnvDao microenvdao;
	
    @Autowired
    private EnvDao envdao;

	@Override
	public MicroServicePo findMicroEnvByid(Long id) {
		// TODO Auto-generated method stub
		return microenvdao.findbyid(id);
	}

	@Override
	public void SaveEnv(MicroServicePo env) {
		// TODO Auto-generated method stub
		microenvdao.save(env);
	}

	@Override
	public Page<MicroServicePo> findAllEnv(Specification<MicroServicePo> specification, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return microenvdao.findAll(specification,pageRequest);
	}

	@Override
	public void deleteEnv(MicroServicePo envpo) {
		// TODO Auto-generated method stub
		microenvdao.delete(envpo);
	}

	@Override
	public List<MicroServicePo> findAllEnv() {
		// TODO Auto-generated method stub
		return microenvdao.findAll();
	}

	@Override
	public List<MicroServicePo> findMicroEnvByEnvId(Long id) {
		// TODO Auto-generated method stub
		return microenvdao.findbyenvid(id);
	}
    
	

}
