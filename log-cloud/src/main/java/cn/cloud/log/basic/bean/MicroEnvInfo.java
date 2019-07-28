package cn.cloud.log.basic.bean;

public class MicroEnvInfo {
	private Long id;
	private Long envid;// 用户名称
	private String microservicename;// key
	private String ipaddr;// key
	private String ostype;// key
	private String loginuser;// key
	private String logpath;// key 日志存储路径
	private String lognamepattern;// key
	private String logpattern;// key
	private String lognamepostfix;// key
	private String splitstr;// key
	private String updatetime;// key

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getEnvid() {
		return envid;
	}

	public void setEnvid(Long envid) {
		this.envid = envid;
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
}
