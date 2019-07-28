package cn.cloud.log.basic.po;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *       环境po
 * @author win10
 *
 */
@Entity
@Table(name = "tbl_basic_env")
public class EnvPo implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3772235443736565293L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, unique = true, name = "envname")
	private String envname;// 用户名称
	@Column(nullable = true, unique = false, name = "remark")
	private String remark;// key
	@Column(nullable = false, unique = false, name = "updatetime")
	private String updatetime;// key
	@Column(nullable = false, unique = false, name = "enable")
	private boolean status;
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public String getEnvname() {
		return envname;
	}
	public void setEnvname(String envname) {
		this.envname = envname;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}
}
