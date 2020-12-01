package com.mmall.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @package: com.mmall.util
 * @author: luweihong
 * @description:
 * @create: 2020/12/1:19:41
 * @version: 1.0
 */
@Slf4j
@Data
public class FTPUtil {

	private String ip;
	private int port;
	private String user;
	private String pwd;
	private FTPClient ftpClient;

	private static String ftpIp = PropertiesUtil.getProperty("ftp.server.ip");
	private static String ftpUser = PropertiesUtil.getProperty("ftp.user");
	private static String ftpPass = PropertiesUtil.getProperty("ftp.pass");

	public FTPUtil(String ip,int port,String user,String pwd){
		this.ip = ip;
		this.port = port;
		this.user = user;
		this.pwd = pwd;
	}

	public static boolean uploadFile(List<File> fileList) throws IOException {
		FTPUtil ftpUtil = new FTPUtil(ftpIp, 21, ftpUser, ftpPass);
		log.info("开始连接ftp服务器");
		boolean result = ftpUtil.uploadFile("img", fileList);
		log.info("开始连接ftp服务器,结束上传,上传结果:{}",result);
		return result;
	}

	private boolean uploadFile(String remotePath,List<File> fileList) throws IOException {
		boolean uploadFlag = true;
		FileInputStream fis = null;
		try {
			ftpClient.changeWorkingDirectory(remotePath);
			ftpClient.setBufferSize(1024);
			ftpClient.setControlEncoding(StandardCharsets.UTF_8.toString());
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();

			for (File fileItem : fileList) {
				fis = new FileInputStream(fileItem);
				ftpClient.storeFile(fileItem.getName(), fis);
			}
		} catch (FileNotFoundException e) {
			uploadFlag = false;
			log.error("找不到上传文件", e);
			e.printStackTrace();
		} catch (IOException e) {
			uploadFlag = false;
			log.error("上传文件异常", e);
			e.printStackTrace();
		} finally {
			if (fis != null) {
				fis.close();
				ftpClient.disconnect();
			}
		}
		return uploadFlag;
	}
}
