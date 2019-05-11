package com.huarun.gohome.request;

import org.springframework.web.multipart.MultipartFile;

public class PublishVo {

	private String title;
	private String category;
	private String detail;
	private MultipartFile file;

	public PublishVo(String title, String category, String detail, MultipartFile file) {
		this.title = title;
		this.category = category;
		this.detail = detail;
		this.file = file;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
