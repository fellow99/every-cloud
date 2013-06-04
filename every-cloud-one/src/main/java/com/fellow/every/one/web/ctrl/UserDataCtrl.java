package com.fellow.every.one.web.ctrl;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.one.business.EveryCloudBusiness;
import com.fellow.every.one.business.ProfileBusiness;
import com.fellow.every.one.web.filter.LoginUserContextHolder;
import com.fellow.every.user.UserAPI;
import com.fellow.every.user.UserInfo;

@Controller  
@RequestMapping("/api/user-data")
public class UserDataCtrl {
	
	@Resource(name="everyCloudBusiness")
	private EveryCloudBusiness everyCloudBusiness;
	
	@Resource(name="profileBusiness")
	private ProfileBusiness profileBusiness;

	@RequestMapping(params = "method=me")
	public ModelAndView me(
			HttpServletRequest request, HttpServletResponse response,
			String provider) throws Exception{

		String loingProvider = LoginUserContextHolder.getProvider();
	    UserInfo loginUser = LoginUserContextHolder.getUserInfo();
	    if(loginUser == null){
	      throw new RuntimeException("Session User is null");
	    }

	    String userId = loginUser.getId();
	    
	    UserInfo user = null;
	    AccessToken accessToken = profileBusiness.getSubToken(loingProvider, userId, provider);
	    if(accessToken != null){
	    	UserAPI userAPI = everyCloudBusiness.getUserAPI(provider);
	    	user = userAPI.myInfo(accessToken);
	    }

		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("user", user);
		
		return modelAndView;
	}

	@RequestMapping(params = "method=user")
	public ModelAndView user(
			HttpServletRequest request, HttpServletResponse response,
			String provider, String uid) throws Exception{

		String loingProvider = LoginUserContextHolder.getProvider();
	    UserInfo loginUser = LoginUserContextHolder.getUserInfo();
	    if(loginUser == null){
	      throw new RuntimeException("Session User is null");
	    }

	    String userId = loginUser.getId();
	    
	    UserInfo user = null;
	    AccessToken accessToken = profileBusiness.getSubToken(loingProvider, userId, provider);
	    if(accessToken != null){
	    	UserAPI userAPI = everyCloudBusiness.getUserAPI(provider);
	    	user = userAPI.getInfo(accessToken, uid);
	    }

		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("user", user);
		
		return modelAndView;
	}
}
