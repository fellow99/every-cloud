package com.fellow.every.user;

public interface UserInfo extends AccountInfo {
	String getNickname();
	String getDescription();
	String getPhone();
	String getEmail();
	String getBirthday();
	SexType getSex();
	String getMarriage();
	String getEducation();
	String getTrade();
	String getJob();
	String getCountry();
	String getProvince();
	String getCity();
	String getLocation();
	String getUrlHomepage();
	String getUrlHead();
	String getUrlHeadBig();
	OnlineType getOnline();
}
