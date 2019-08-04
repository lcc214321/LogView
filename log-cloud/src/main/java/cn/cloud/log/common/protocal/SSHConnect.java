package cn.cloud.log.common.protocal;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Vector;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

/**
 * 连接linux
 * 
 * @author Administrator
 *
 */
public class SSHConnect {
	private JSch jsch = new JSch();
	private Session session;
	private int port = 22;
	private Channel channel=null;
   
	public SSHConnect(String ipaddr, String username, String password) throws JSchException {
		this.session = jsch.getSession(username, ipaddr, 22);
		this.session.setPassword(password);
		session.setConfig("StrictHostKeyChecking", "no");
		this.session.connect();
	}
	
	public ChannelSftp getiFileSftp() throws JSchException {
		channel = (Channel) session.openChannel("sftp");
		channel.connect(1000);
		ChannelSftp sftp = (ChannelSftp) channel;
		return sftp;
	}
	public void close() {
		if (session != null) {
			session.disconnect();
		}
		if (channel != null) {
			channel.disconnect();
		}
	}

	/**
	 * mtime为modify时间，ctime为状态变更时间，如chmod等，atime为访问时间如cat，more等
	 * 
	 * @param path
	 * @throws JSchException
	 * @throws SftpException
	 */
	public void FileWalker(String path) throws JSchException, SftpException {
		Channel channel = (Channel) session.openChannel("sftp");
		channel.connect(1000);
		ChannelSftp sftp = (ChannelSftp) channel;
		walker(sftp,path);
		
	}

	public void walker(ChannelSftp sftp, String path) throws SftpException {
		System.out.println("主目录:"+path);
		Vector<LsEntry> ve = sftp.ls(path);
		for (LsEntry v : ve) {
			if (!v.getFilename().equals(".") && !v.getFilename().equals("..")) {
				Vector<LsEntry> vs = sftp.ls(path + "/" + v.getFilename());
				if (vs.size() != 1) {
                    walker(sftp,path + "/" + v.getFilename());
				}else{
					System.out.println(v.getFilename()+"-"+v.getAttrs().getMTime());
				}
			}

			
		}
	}

	public void downloadFile(String path, String savepath) throws JSchException, SftpException, IOException {
		Channel channel = (Channel) session.openChannel("sftp");
		channel.connect(1000);
		ChannelSftp sftp = (ChannelSftp) channel;
		FileOutputStream out = new FileOutputStream(new File(savepath));
		sftp.get(path, out);
		out.close();
		if (session != null) {
			session.disconnect();
		}
		if (channel != null) {
			channel.disconnect();
		}
	}

	public static void main(String[] args) {
		try {
			SSHConnect conn = new SSHConnect("192.168.10.39", "qmth", "qmth");
			conn.FileWalker("/home/qmth/project/examcloud/examcloud-core-basic/logs");

		} catch (JSchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SftpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

}
