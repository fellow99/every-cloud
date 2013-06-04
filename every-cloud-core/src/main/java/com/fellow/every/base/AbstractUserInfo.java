package com.fellow.every.base;

import com.fellow.every.user.OnlineType;
import com.fellow.every.user.SexType;
import com.fellow.every.user.UserInfo;

public class AbstractUserInfo extends AbstractAccountInfo implements UserInfo, java.io.Serializable{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	private String nickname;
	private String description;
	private String phone;
	private String email;
	private String birthday;
	private SexType sex;
	private String marriage;
	private String education;
	private String trade;
	private String job;
	private String country;
	private String province;
	private String city;
	private String location;
	private String urlHomepage;
	private String urlHead;
	private String urlHeadBig;
	private OnlineType online;
	
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public SexType getSex() {
		return sex;
	}
	public void setSex(SexType sex) {
		this.sex = sex;
	}
	public String getMarriage() {
		return marriage;
	}
	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getTrade() {
		return trade;
	}
	public void setTrade(String trade) {
		this.trade = trade;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getUrlHomepage() {
		return urlHomepage;
	}
	public void setUrlHomepage(String urlHomepage) {
		this.urlHomepage = urlHomepage;
	}
	public String getUrlHead() {
		return urlHead;
	}
	public void setUrlHead(String urlHead) {
		this.urlHead = urlHead;
	}
	public String getUrlHeadBig() {
		return urlHeadBig;
	}
	public void setUrlHeadBig(String urlHeadBig) {
		this.urlHeadBig = urlHeadBig;
	}
	public OnlineType getOnline() {
		return online;
	}
	public void setOnline(OnlineType online) {
		this.online = online;
	}
}
