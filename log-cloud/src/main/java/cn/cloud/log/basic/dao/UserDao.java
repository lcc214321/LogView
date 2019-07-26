package cn.cloud.log.basic.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import cn.cloud.log.basic.po.UserPo;
import cn.cloud.log.basic.po.UserType;

public interface UserDao
		extends JpaRepository<UserPo, Long>, QueryByExampleExecutor<UserPo>, JpaSpecificationExecutor<UserPo> {
	public UserPo findByid(Long Id);

	public List<UserPo> findByusertype(UserType type);

	@Query(value = "select t from UserPo t where t.username like '%:username%' and t.usertype= :usertype")
	public List<UserPo> findUserPo(@Param("username") String username, @Param("usertype") String usertype);

	public UserPo findByusername(String username);
}
