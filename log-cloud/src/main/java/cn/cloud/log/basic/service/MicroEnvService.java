package cn.cloud.log.basic.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import cn.cloud.log.basic.po.MicroServicePo;


public interface MicroEnvService {
	public MicroServicePo findMicroEnvByid(Long id);
	public List<MicroServicePo> findMicroEnvByEnvId(Long id);
	public void SaveEnv(MicroServicePo env);
	public Page<MicroServicePo> findAllEnv(Specification<MicroServicePo> specification, PageRequest pageRequest);
	public void deleteEnv(MicroServicePo envpo);
	public List<MicroServicePo> findAllEnv();
	public MicroServicePo findMicroEnvByUniqueIndex(String ipaddr,Long envid,String microservicename);
}
