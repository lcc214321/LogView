package cn.cloud.log.basic.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;

import cn.cloud.log.basic.po.EnvPo;


public interface EnvService {
   public EnvPo findEnvByid(Long id);
   public EnvPo SaveEnv(EnvPo env);
   public Page<EnvPo> findAllEnv(Specification<EnvPo> specification,PageRequest pageRequest);
   public void deleteEnv(EnvPo envpo);
   public List<EnvPo> findAllEnv();
}
