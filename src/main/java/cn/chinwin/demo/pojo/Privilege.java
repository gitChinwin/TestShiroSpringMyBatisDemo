package cn.chinwin.demo.pojo;

import java.io.Serializable;

public class Privilege implements Serializable {

	private Integer priid;
	private String priUrl;
	private String priName;
	private String priImage;
	private Integer parentid;
	private String priStatus;
	private String priDesc;
	private String priHtml;

	public Integer getPriid() {
		return priid;
	}

	public void setPriid(Integer priid) {
		this.priid = priid;
	}

	public String getPriUrl() {
		return priUrl;
	}

	public void setPriUrl(String priUrl) {
		this.priUrl = priUrl;
	}

	public String getPriName() {
		return priName;
	}

	public void setPriName(String priName) {
		this.priName = priName;
	}

	public String getPriImage() {
		return priImage;
	}

	public void setPriImage(String priImage) {
		this.priImage = priImage;
	}

	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	public String getPriStatus() {
		return priStatus;
	}

	public void setPriStatus(String priStatus) {
		this.priStatus = priStatus;
	}

	public String getPriDesc() {
		return priDesc;
	}

	public void setPriDesc(String priDesc) {
		this.priDesc = priDesc;
	}

	public String getPriHtml() {
		return priHtml;
	}

	public void setPriHtml(String priHtml) {
		this.priHtml = priHtml;
	}
}
