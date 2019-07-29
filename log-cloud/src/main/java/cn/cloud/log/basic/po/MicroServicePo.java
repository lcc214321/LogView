package cn.cloud.log.basic.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 微服务存储
 * 
 * @author win10
 *
 */
@Entity
@Table(name = "tbl_basic_microservice")
public class MicroServicePo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7927602596439096809L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = false, name = "envid")
	private Long envid;
	@Column(nullable = false, unique = false, name = "envname")
    private String envname;// 环境名称
	@Column(nullable = false, unique = false, name = "microservicename")
	private String microservicename;// key
	@Column(nullable = false, unique = false, name = "ipaddr")
	private String ipaddr;// key
	@Column(nullable = false, unique = false, name = "ostype")
	private String ostype;// key
	@Column(nullable = false, unique = false, name = "loginuser")
	private String loginuser;// key
	@Column(nullable = false, unique = false, name = "loginpassword")
	private String loginpassword;// key
	@Column(nullable = false, unique = false, name = "logpath")
	private String logpath;// key 日志存储路径
	@Column(nullable = false, unique = false, name = "lognamepattern")
	private String lognamepattern;// key
	@Column(nullable = false, unique = false, name = "logpattern")
	private String logpattern;// key
	@Column(nullable = false, unique = false, name = "lognamepostfix")
	private String lognamepostfix;// key
	@Column(nullable = false, unique = false, name = "splitstr")
	private String splitstr;// key
	@Column(nullable = false, unique = false, name = "updatetime")
	private String updatetime;// key
	
	public Long getEnvid() {
		return envid;
	}

	public void setEnvid(Long envid) {
		this.envid = envid;
	}

	public String getEnvname() {
		return envname;
	}
    
	public void setEnvname(String envname) {
		this.envname = envname;
	}
	

	public String getMicroservicename() {
		return microservicename;
	}

	public void setMicroservicename(String microservicename) {
		this.microservicename = microservicename;
	}

	public String getIpaddr() {
		return ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	public String getOstype() {
		return ostype;
	}

	public void setOstype(String ostype) {
		this.ostype = ostype;
	}

	public String getLoginuser() {
		return loginuser;
	}

	public void setLoginuser(String loginuser) {
		this.loginuser = loginuser;
	}
	@JsonIgnore  
	public String getLoginpassword() {
		return loginpassword;
	}

	public void setLoginpassword(String loginpassword) {
		this.loginpassword = loginpassword;
	}

	public String getLogpath() {
		return logpath;
	}

	public void setLogpath(String logpath) {
		this.logpath = logpath;
	}

	public String getLognamepattern() {
		return lognamepattern;
	}

	public void setLognamepattern(String lognamepattern) {
		this.lognamepattern = lognamepattern;
	}

	public String getLogpattern() {
		return logpattern;
	}

	public void setLogpattern(String logpattern) {
		this.logpattern = logpattern;
	}

	public String getLognamepostfix() {
		return lognamepostfix;
	}

	public void setLognamepostfix(String lognamepostfix) {
		this.lognamepostfix = lognamepostfix;
	}

	public String getSplitstr() {
		return splitstr;
	}

	public void setSplitstr(String splitstr) {
		this.splitstr = splitstr;
	}

	public String getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public Long getId() {
		return id;
	}
}
