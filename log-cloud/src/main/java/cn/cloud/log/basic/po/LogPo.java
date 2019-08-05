package cn.cloud.log.basic.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 存储logpo的
 * @author Administrator
 *
 */
@Entity
@Table(name = "tbl_basic_log")
public class LogPo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -9129776880313050651L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = false, name = "envid")
	private Long envid;
	@Column(nullable = false, unique = false, name = "envname")
	private String envname;// 环境名称
	@Column(nullable = false, unique = false, name = "microenvid")
	private Long microenvid; //采集的微服务id
	@Column(nullable = false, unique = false, name = "microenvname")
	private String microenvname;//采集的微服务名称
	@Column(nullable = false, unique = false, name = "ipaddr")
	private String ipaddr;//采集的微服务ip地址
//	@Column(nullable = false, unique = false, name = "logtype")
//	private String logtype;//日志类型error,info,warn,debug,all
	@Column(nullable = false, unique = false, name = "collectdate")
	private String collectdate;//采集时间
	@Column(nullable = false, unique = false, name = "updatetime")
	private String updatetime;//更新时间
	@Column(nullable = false, unique = false, name = "orginfilename")
	private String orginfilename;//来源文件名称和路径
	@Column(nullable = false, unique = false, name = "savepath")
	private String savepath;//文件存储路径
	@Transient
	private boolean isinclude;

	@Transient
	private boolean canView;
	@Column(nullable = false, unique = false, name = "originfilemtime")
	private String originfileMtime;//源文件修改时间，若修改时间不变则不作处理
	
	public String getOriginfileMtime() {
		return originfileMtime;
	}
	public void setOriginfileMtime(String originfileMtime) {
		this.originfileMtime = originfileMtime;
	}
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
	public String getEnvname() {
		return envname;
	}
	public void setEnvname(String envname) {
		this.envname = envname;
	}
	public Long getMicroenvid() {
		return microenvid;
	}
	public void setMicroenvid(Long microenvid) {
		this.microenvid = microenvid;
	}
	public String getMicroenvname() {
		return microenvname;
	}
	public void setMicroenvname(String microenvname) {
		this.microenvname = microenvname;
	}
	public String getIpaddr() {
		return ipaddr;
	}
	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}
	public String getCollectdate() {
		return collectdate;
	}
	public void setCollectdate(String collectdate) {
		this.collectdate = collectdate;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
	public String getOrginfilename() {
		return orginfilename;
	}
	public void setOrginfilename(String orginfilename) {
		this.orginfilename = orginfilename;
	}
	public String getSavepath() {
		return savepath;
	}
	public void setSavepath(String savepath) {
		this.savepath = savepath;
	}
	public boolean isIsinclude() {
		return isinclude;
	}
	public void setIsinclude(boolean isinclude) {
		this.isinclude = isinclude;
	}
	public boolean isCanView() {
		return canView;
	}
	public void setCanView(boolean canView) {
		this.canView = canView;
	}
}
