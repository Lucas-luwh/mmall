package com.mmall.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @package: com.mmall.service
 * @author: luweihong
 * @description:
 * @create: 2020/11/30:23:05
 * @version: 1.0
 */
public interface IFileService {

	/**
	 * 上传文件
	 * @param file 文件
	 * @param path 路径
	 * @return 文件名
	 */
	String upload(MultipartFile file, String path);
}
