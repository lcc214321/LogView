package cn.cloud.log.basic.dao;
/**
 * 微服务存储dao
 * @author win10
 *
 */

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import cn.cloud.log.basic.po.MicroServicePo;

public interface MicroEnvDao extends JpaRepository<MicroServicePo, Long>, QueryByExampleExecutor<MicroServicePo>, JpaSpecificationExecutor<MicroServicePo>{
    public MicroServicePo findbyid(Long id);//通过id找寻微服务
    public List<MicroServicePo> findbyenvid(Long envid);//通过envid找到所有微服务
}
