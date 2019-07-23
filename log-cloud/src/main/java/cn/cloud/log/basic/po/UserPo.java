package cn.cloud.log.basic.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/*
 * 用户数据库存储对象
 */
@Entity
@Table(name = "tbl_basic_user")
public class UserPo implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true, name = "username")
	private String username;// 用户名称
	@Column(nullable = false, unique = false, name = "password")
	private String password;// key
	@Column(nullable = false, unique = false, name = "usertype")
	@Enumerated(EnumType.STRING)
	private UserType usertype;// key
	@Column(nullable = true, unique = false, name = "updatetime")
	private String updatetime;
	@Column(nullable = false, unique = false, name = "enable")
	private boolean status;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserType getUsertype() {
		return usertype;
	}

	public void setUsertype(UserType usertype) {
		this.usertype = usertype;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	

}
