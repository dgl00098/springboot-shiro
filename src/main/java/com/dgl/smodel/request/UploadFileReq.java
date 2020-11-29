package com.dgl.smodel.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件请求体
 */
@Data
public class UploadFileReq {

	//文件
	private MultipartFile multipartFile;

	//文件类型
	private int fileType;

	public UploadFileReq() {

	}
	public UploadFileReq(MultipartFile multipartFile,int fileType) {
		this.multipartFile = multipartFile;
		this.fileType = fileType;
	}
}
