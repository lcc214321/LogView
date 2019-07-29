package cn.cloud.log.basic.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cn.cloud.log.basic.dao.EnvDao;
import cn.cloud.log.basic.po.EnvPo;
import cn.cloud.log.basic.service.EnvService;

@Service
public class EnvServiceImpl implements EnvService {
    @Autowired
    private EnvDao envdao;
	
	@Override
	public EnvPo findEnvByid(Long id) {
		// TODO Auto-generated method stub
		return envdao.findByid(id);
	}

	@Override
	public EnvPo SaveEnv(EnvPo env) {
		// TODO Auto-generated method stub
		return envdao.save(env);
	}

	@Override
	public Page<EnvPo> findAllEnv(Specification<EnvPo> specification, PageRequest pageRequest) {
		// TODO Auto-generated method stub
		return envdao.findAll(specification, pageRequest);
	}

	@Override
	public void deleteEnv(EnvPo envpo) {
		// TODO Auto-generated method stub
		envdao.delete(envpo);
	}

	@Override
	public List<EnvPo> findAllEnv() {
		// TODO Auto-generated method stub
		return envdao.findAll();
	}

}
