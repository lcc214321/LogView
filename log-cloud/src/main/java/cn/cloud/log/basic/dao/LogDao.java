package cn.cloud.log.basic.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import cn.cloud.log.basic.po.LogPo;



public interface LogDao extends JpaRepository<LogPo, Long>, QueryByExampleExecutor<LogPo>, JpaSpecificationExecutor<LogPo> {
     public LogPo findByid(Long id);//通过id找对应的采集日志
     public List<LogPo> findByenvid(Long envid);
     public List<LogPo> findBylogtype(String logtype);
}
