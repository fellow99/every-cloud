package com.fellow.every.one.web.ctrl;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.friend.FriendAPI;
import com.fellow.every.friend.FriendInfo;
import com.fellow.every.one.business.EveryCloudBusiness;
import com.fellow.every.one.business.ProfileBusiness;
import com.fellow.every.one.web.filter.LoginUserContextHolder;
import com.fellow.every.user.UserInfo;

@Controller  
@RequestMapping("/api/friend-data")
public class FriendDataCtrl {
	
	@Resource(name="everyCloudBusiness")
	private EveryCloudBusiness everyCloudBusiness;
	
	@Resource(name="profileBusiness")
	private ProfileBusiness profileBusiness;

	@RequestMapping(params = "method=listFriends")
	public ModelAndView listFriends(
			HttpServletRequest request, HttpServletResponse response,
			String provider) throws Exception{

		String loingProvider = LoginUserContextHolder.getProvider();
	    UserInfo loginUser = LoginUserContextHolder.getUserInfo();
	    if(loginUser == null){
	      throw new RuntimeException("Session User is null");
	    }

	    String userId = loginUser.getId();
	    
	    FriendInfo[] items = null;
	    AccessToken accessToken = profileBusiness.getSubToken(loingProvider, userId, provider);
	    if(accessToken != null){
	    	FriendAPI friendAPI = everyCloudBusiness.getFriendAPI(provider);
	    	items = friendAPI.listFriends();
	    }

		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("items", items);
		
		return modelAndView;
	}

	@RequestMapping(params = "method=listFollowers")
	public ModelAndView listFollowers(
			HttpServletRequest request, HttpServletResponse response,
			String provider) throws Exception{

		String loingProvider = LoginUserContextHolder.getProvider();
	    UserInfo loginUser = LoginUserContextHolder.getUserInfo();
	    if(loginUser == null){
	      throw new RuntimeException("Session User is null");
	    }

	    String userId = loginUser.getId();
	    
	    FriendInfo[] items = null;
	    AccessToken accessToken = profileBusiness.getSubToken(loingProvider, userId, provider);
	    if(accessToken != null){
	    	FriendAPI friendAPI = everyCloudBusiness.getFriendAPI(provider);
	    	items = friendAPI.listFollowers();
	    }

		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("items", items);
		
		return modelAndView;
	}

	@RequestMapping(params = "method=listCloseFriends")
	public ModelAndView listCloseFriends(
			HttpServletRequest request, HttpServletResponse response,
			String provider) throws Exception{

		String loingProvider = LoginUserContextHolder.getProvider();
	    UserInfo loginUser = LoginUserContextHolder.getUserInfo();
	    if(loginUser == null){
	      throw new RuntimeException("Session User is null");
	    }

	    String userId = loginUser.getId();
	    
	    FriendInfo[] items = null;
	    AccessToken accessToken = profileBusiness.getSubToken(loingProvider, userId, provider);
	    if(accessToken != null){
	    	FriendAPI friendAPI = everyCloudBusiness.getFriendAPI(provider);
	    	items = friendAPI.listCloseFriends();
	    }

		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("items", items);
		
		return modelAndView;
	}

	@RequestMapping(params = "method=listBlacks")
	public ModelAndView listBlacks(
			HttpServletRequest request, HttpServletResponse response,
			String provider) throws Exception{

		String loingProvider = LoginUserContextHolder.getProvider();
	    UserInfo loginUser = LoginUserContextHolder.getUserInfo();
	    if(loginUser == null){
	      throw new RuntimeException("Session User is null");
	    }

	    String userId = loginUser.getId();
	    
	    FriendInfo[] items = null;
	    AccessToken accessToken = profileBusiness.getSubToken(loingProvider, userId, provider);
	    if(accessToken != null){
	    	FriendAPI friendAPI = everyCloudBusiness.getFriendAPI(provider);
	    	items = friendAPI.listBlacks();
	    }

		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("items", items);
		
		return modelAndView;
	}
}
