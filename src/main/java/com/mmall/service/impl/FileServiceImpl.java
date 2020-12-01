package com.mmall.service.impl;

import com.google.common.collect.Lists;
import com.mmall.service.IFileService;
import com.mmall.util.FTPUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

/**
 * @package: com.mmall.service.impl
 * @author: luweihong
 * @description:
 * @create: 2020/11/30:23:06
 * @version: 1.0
 */
@Service("iFileService")
@Slf4j
public class FileServiceImpl implements IFileService {

	@Override
	public String upload(MultipartFile file, String path){
		String fileName = file.getOriginalFilename();
		String fileExtensionName = fileName.substring(fileName.lastIndexOf(".") + 1);
		String uploadFileName = UUID.randomUUID().toString() + "." + fileExtensionName;
		log.info("开始上传文件,上传文件的文件名:{},上传的路径:{},新文件名:{}",fileName,path,uploadFileName);

		File fileDir = new File(path);
		boolean isMkdir = false;
		if (!fileDir.exists()){
			boolean isWrite = fileDir.setWritable(true);
			if (isWrite){
				log.info("目录权限可写");
			}else{
				log.info("目录权限不可写");
			}
			isMkdir = fileDir.mkdirs();
		}
		File targetFile = null;
		if (isMkdir){
			try {
				targetFile = new File(path, uploadFileName);
				file.transferTo(targetFile);
				FTPUtil.uploadFile(Lists.newArrayList(targetFile));
				Files.delete(targetFile.toPath());
			} catch (IOException e) {
				log.error("上传文件异常",e);
			}
		}
		Assert.notNull(targetFile,"文件存在");
		return targetFile.getName();
	}
}
