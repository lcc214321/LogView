package cn.cloud.log.collect.thread;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Value;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.jcraft.jsch.ChannelSftp.LsEntry;

import cn.cloud.log.basic.po.LogPo;
import cn.cloud.log.basic.po.MicroServicePo;
import cn.cloud.log.basic.service.LogService;
import cn.cloud.log.collect.LinuxCollect;
import cn.cloud.log.common.constants.ConfigConstants;
import cn.cloud.log.common.protocal.SSHConnect;
import cn.cloud.log.exception.StatusException;
import cn.cloud.log.util.DateUtil;
import cn.cloud.log.util.FileUtil;
import io.netty.util.internal.StringUtil;

/**
 * 采集线程
 * 
 * @author win10
 *
 */
public class CollectThread{

	private LogService logservice;
	private MicroServicePo micropo;
	private String collectdate;

	public void run() {
		// TODO Auto-generated method stub
		if (micropo.getOstype().equals("linux")) {
			collectLinux();
		} else if (micropo.getOstype().equals("windows")) {

		} else {
			throw new StatusException("330", "不支持的操作系统");
		}
	}

	private void collectLinux() {
		try {
			SSHConnect conn = new SSHConnect(micropo.getIpaddr(), micropo.getLoginuser(), micropo.getLoginpassword());
			ChannelSftp sftp = conn.getiFileSftp();
			LinuxCollect collect = new LinuxCollect() {

				@Override
				public void walker(String filepath) {
					// TODO Auto-generated method stub
					Vector<LsEntry> ve;
					try {
						ve = sftp.ls(filepath);
						for (LsEntry v : ve) {
							if (!v.getFilename().equals(".") && !v.getFilename().equals("..")) {
								Vector<LsEntry> vs = sftp.ls(filepath + "/" + v.getFilename());
								if (vs.size() != 1) {
									walker(filepath + "/" + v.getFilename());
								} else {
									if(!this.isfilter(v)){
										String serverpath=micropo.getMicroservicename()+"_"+micropo.getIpaddr();
										String savepath=ConfigConstants.logroot+"/"+serverpath+"/"+collectdate;
										String originpath=filepath + "/" + v.getFilename();
										FileUtil.mkdir(savepath);
										savepath=savepath+"/"+v.getFilename();
										conn.downloadFile(sftp,originpath, savepath);
										LogPo logPo=logservice.findlogbyoriginfilenameandmtime(v.getFilename(), v.getAttrs().getMtimeString(),micropo.getId());
										if(logPo!=null){
											return;
										}									
										LogPo logpo=new LogPo();
										if(new File(savepath).length()>10240*1024){
											logpo.setCanView(false);
										}else{
											logpo.setCanView(true);
										}
										logpo.setCollectdate(collectdate);
										logpo.setEnvid(micropo.getEnvid());
										logpo.setEnvname(micropo.getEnvname());
										logpo.setIpaddr(micropo.getIpaddr());
										logpo.setMicroenvid(micropo.getId());
										logpo.setMicroenvname(micropo.getMicroservicename());
										logpo.setOrginfilename(v.getFilename());
										logpo.setOriginfileMtime(v.getAttrs().getMtimeString());
										logpo.setSavepath(savepath);
										logpo.setUpdatetime(DateUtil.getNowDate());
										logservice.SaveLog(logpo);
									}
								}
							}

						}
					} catch (SftpException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSchException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				/**
				 * 为true过滤
				 * @throws ParseException 
				 */

				public boolean isfilter(LsEntry file) throws ParseException {
					// TODO Auto-generated method stub
					 boolean result=true;
					 String splitstr=micropo.getSplitstr();
					 if(StringUtil.isNullOrEmpty(splitstr)) {
						 splitstr=";";
					 }
					 //和采集日期比较若不符合,过滤
					 int mtime=file.getAttrs().getMTime();
					 Long mtimevalue=Long.valueOf(mtime);
					 String date1=DateUtil.formateDate(mtimevalue, "yyyy-MM-dd");
					 if(!date1.equals(collectdate)) {
						 return true;
					 }
					 String lognamepattern=micropo.getLognamepattern();
					 String lognamepostfix=micropo.getLognamepostfix();
					 String[] lognamepatterns=lognamepattern.split(splitstr);
					 String[] lognamepostfixs=lognamepostfix.split(splitstr);
					 if(filenamefilter(file.getFilename(),lognamepatterns)) {
						 return true;
					 }
					 if(filepostfixfilter(file.getFilename(),lognamepostfixs)) {
						 return true;
					 }
                     return false;
				}

				@Override
				public void collect() {
					// TODO Auto-generated method stub
                   String logpath=micropo.getLogpath();
                   walker(logpath);
				}
				@Override
				public boolean filenamefilter(String filename, String[] filenamepatterns) {
					// TODO Auto-generated method stub
					boolean result=true;
					for(int i=0;i<filenamepatterns.length;i++) {
						if(filename.matches(filenamepatterns[i])) {
							result=false;
							break;
						}
					}
					return result;
				}
				@Override
				public boolean filepostfixfilter(String filename, String[] filepostfix) {
					// TODO Auto-generated method stub
					boolean result=true;
					for(int i=0;i<filepostfix.length;i++) {
						if(filename.endsWith(filepostfix[i])) {
							result=false;
							break;
						}
					}
					return result;
				}

			};
			collect.collect();
			conn.close();

		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void collectWindow() {

	}

	public CollectThread(LogService logservice, MicroServicePo micropo,String collectdate) {
		this.logservice = logservice;
		this.micropo = micropo;
		this.collectdate=collectdate;
	}

}
