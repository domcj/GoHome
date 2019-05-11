package com.huarun.gohome.request;

import org.springframework.web.multipart.MultipartFile;

public class ClueInfoVo {

	private String loseName;

	private String loseIphone;

	private String loseSex;

	private String loseAddress;

	private String description;

	private MultipartFile file;

	public String getLoseName() {
		return loseName;
	}

	public void setLoseName(String loseName) {
		this.loseName = loseName;
	}

	public String getLoseIphone() {
		return loseIphone;
	}

	public void setLoseIphone(String loseIphone) {
		this.loseIphone = loseIphone;
	}

	public String getLoseSex() {
		return loseSex;
	}

	public void setLoseSex(String loseSex) {
		this.loseSex = loseSex;
	}

	public String getLoseAddress() {
		return loseAddress;
	}

	public void setLoseAddress(String loseAddress) {
		this.loseAddress = loseAddress;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
}
