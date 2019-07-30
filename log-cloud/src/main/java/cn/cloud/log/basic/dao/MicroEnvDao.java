package cn.cloud.log.basic.dao;
/**
 * 微服务存储dao
 * @author win10
 *
 */

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import cn.cloud.log.basic.po.MicroServicePo;

public interface MicroEnvDao extends JpaRepository<MicroServicePo, Long>, QueryByExampleExecutor<MicroServicePo>, JpaSpecificationExecutor<MicroServicePo>{
    public MicroServicePo findByid(Long id);//通过id找寻微服务
    public List<MicroServicePo> findByenvid(Long envid);//通过envid找到所有微服务
    @Modifying
    @Query("update MicroServicePo sc set sc.envname = :envname where sc.id =:envid")
    public void updateEnvNamebyEnvId(@Param(value = "envid")Long envid,@Param(value = "envname")String envname);
    @Query("select sc from MicroServicePo sc where envid=:envid and sc.microservicename=:microservicename and sc.ipaddr=:ipaddr")
    public MicroServicePo findByenvidAndmicroservicenameAndipaddr(@Param(value = "envid")Long envid,@Param(value = "microservicename")String microservicename,@Param(value = "ipaddr")String ipaddr);
}
