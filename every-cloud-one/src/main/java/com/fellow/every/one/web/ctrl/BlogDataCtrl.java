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
import com.fellow.every.status.StatusAPI;
import com.fellow.every.status.StatusCommentInfo;
import com.fellow.every.status.StatusInfo;
import com.fellow.every.user.UserAPI;
import com.fellow.every.user.UserInfo;

@Controller  
@RequestMapping("/api/blog-data")
public class BlogDataCtrl {
	
	@Resource(name="everyCloudBusiness")
	private EveryCloudBusiness everyCloudBusiness;
	
	@Resource(name="profileBusiness")
	private ProfileBusiness profileBusiness;

	@RequestMapping(params = "method=public")
	public ModelAndView publics(
			HttpServletRequest request, HttpServletResponse response,
			String provider, int page, int size) throws Exception{

		String loginProvider = LoginUserContextHolder.getProvider();
	    UserInfo loginUser = LoginUserContextHolder.getUserInfo();
	    if(loginUser == null){
	      throw new RuntimeException("Session User is null");
	    }

	    String userId = loginUser.getId();
	    
	    List<StatusInfo> items = null;
	    AccessToken accessToken = profileBusiness.getSubToken(loginProvider, userId, provider);
	    if(accessToken != null){
	    	StatusAPI statusAPI = everyCloudBusiness.getStatusAPI(provider);
	    	StatusInfo[] status = statusAPI.listPublic(accessToken, page, size);
	    	items = Arrays.asList(status);
	    }

		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("items", items);
		
		return modelAndView;
	}

	@RequestMapping(params = "method=home")
	public ModelAndView home(
			HttpServletRequest request, HttpServletResponse response,
			String provider, int page, int size) throws Exception{

		String loginProvider = LoginUserContextHolder.getProvider();
	    UserInfo loginUser = LoginUserContextHolder.getUserInfo();
	    if(loginUser == null){
	      throw new RuntimeException("Session User is null");
	    }

	    String userId = loginUser.getId();
	    
	    List<StatusInfo> items = null;
	    AccessToken accessToken = profileBusiness.getSubToken(loginProvider, userId, provider);
	    if(accessToken != null){
	    	StatusAPI statusAPI = everyCloudBusiness.getStatusAPI(provider);
	    	StatusInfo[] status = statusAPI.listHome(accessToken, page, size);
	    	items = Arrays.asList(status);
	    }

		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("items", items);
		
		return modelAndView;
	}

	@RequestMapping(params = "method=me")
	public ModelAndView me(
			HttpServletRequest request, HttpServletResponse response,
			String provider, int page, int size) throws Exception{

		String loginProvider = LoginUserContextHolder.getProvider();
	    UserInfo loginUser = LoginUserContextHolder.getUserInfo();
	    if(loginUser == null){
	      throw new RuntimeException("Session User is null");
	    }

	    String userId = loginUser.getId();
	    
	    List<StatusInfo> items = null;
	    AccessToken accessToken = profileBusiness.getSubToken(loginProvider, userId, provider);
	    if(accessToken != null){
	    	StatusAPI statusAPI = everyCloudBusiness.getStatusAPI(provider);
	    	StatusInfo[] status = statusAPI.listMe(accessToken, page, size);
	    	items = Arrays.asList(status);
	    }

		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("items", items);
		
		return modelAndView;
	}

	@RequestMapping(params = "method=friends")
	public ModelAndView friends(
			HttpServletRequest request, HttpServletResponse response,
			String provider, int page, int size) throws Exception{

		String loginProvider = LoginUserContextHolder.getProvider();
	    UserInfo loginUser = LoginUserContextHolder.getUserInfo();
	    if(loginUser == null){
	      throw new RuntimeException("Session User is null");
	    }

	    String userId = loginUser.getId();
	    
	    List<StatusInfo> items = null;
	    AccessToken accessToken = profileBusiness.getSubToken(loginProvider, userId, provider);
	    if(accessToken != null){
	    	StatusAPI statusAPI = everyCloudBusiness.getStatusAPI(provider);
	    	StatusInfo[] status = statusAPI.listFriends(accessToken, page, size);
	    	items = Arrays.asList(status);
	    }

		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("items", items);
		
		return modelAndView;
	}

	@RequestMapping(params = "method=mentions")
	public ModelAndView mentions(
			HttpServletRequest request, HttpServletResponse response,
			String provider, int page, int size) throws Exception{

		String loginProvider = LoginUserContextHolder.getProvider();
	    UserInfo loginUser = LoginUserContextHolder.getUserInfo();
	    if(loginUser == null){
	      throw new RuntimeException("Session User is null");
	    }

	    String userId = loginUser.getId();
	    
	    List<StatusInfo> items = null;
	    AccessToken accessToken = profileBusiness.getSubToken(loginProvider, userId, provider);
	    if(accessToken != null){
	    	StatusAPI statusAPI = everyCloudBusiness.getStatusAPI(provider);
	    	StatusInfo[] status = statusAPI.listMentions(accessToken, page, size);
	    	items = Arrays.asList(status);
	    }

		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("items", items);
		
		return modelAndView;
	}

	@RequestMapping(params = "method=user")
	public ModelAndView user(
			HttpServletRequest request, HttpServletResponse response,
			String provider, String uid, int page, int size) throws Exception{

		String loginProvider = LoginUserContextHolder.getProvider();
	    UserInfo loginUser = LoginUserContextHolder.getUserInfo();
	    if(loginUser == null){
	      throw new RuntimeException("Session User is null");
	    }

	    String userId = loginUser.getId();
	    
	    List<StatusInfo> items = null;
	    AccessToken accessToken = profileBusiness.getSubToken(loginProvider, userId, provider);
	    if(accessToken != null){
	    	StatusAPI statusAPI = everyCloudBusiness.getStatusAPI(provider);
	    	StatusInfo[] status = statusAPI.listUser(accessToken, uid, page, size);
	    	items = Arrays.asList(status);
	    }

		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("items", items);
		
		return modelAndView;
	}

	@RequestMapping(params = "method=comments")
	public ModelAndView comments(
			HttpServletRequest request, HttpServletResponse response,
			String provider, String statusId, int page, int size) throws Exception{

		String loginProvider = LoginUserContextHolder.getProvider();
	    UserInfo loginUser = LoginUserContextHolder.getUserInfo();
	    if(loginUser == null){
	      throw new RuntimeException("Session User is null");
	    }

	    String userId = loginUser.getId();
	    
	    List<StatusCommentInfo> items = null;
	    AccessToken accessToken = profileBusiness.getSubToken(loginProvider, userId, provider);
	    if(accessToken != null){
	    	StatusAPI statusAPI = everyCloudBusiness.getStatusAPI(provider);
	    	StatusCommentInfo[] status = statusAPI.listComments(accessToken, statusId, page, size);
	    	items = Arrays.asList(status);
	    }

		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("items", items);
		
		return modelAndView;
	}
}
