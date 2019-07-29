package cn.cloud.log.basic.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import cn.cloud.log.basic.po.EnvPo;

public interface EnvDao
		extends JpaRepository<EnvPo, Long>, QueryByExampleExecutor<EnvPo>, JpaSpecificationExecutor<EnvPo> {
	public EnvPo findByid(Long Id);
}
