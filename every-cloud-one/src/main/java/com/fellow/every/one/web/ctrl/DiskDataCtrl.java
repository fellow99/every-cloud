package com.fellow.every.one.web.ctrl;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fellow.every.auth.AccessToken;
import com.fellow.every.disk.DiskAPI;
import com.fellow.every.disk.FileInfo;
import com.fellow.every.disk.QuotaInfo;
import com.fellow.every.http.HTTPStreamUtil;
import com.fellow.every.one.business.EveryCloudBusiness;
import com.fellow.every.one.business.ProfileBusiness;
import com.fellow.every.one.web.filter.LoginUserContextHolder;
import com.fellow.every.user.UserInfo;

@Controller  
@RequestMapping("/api/disk-data")
public class DiskDataCtrl {
	
	@Resource(name="everyCloudBusiness")
	private EveryCloudBusiness everyCloudBusiness;
	
	@Resource(name="profileBusiness")
	private ProfileBusiness profileBusiness;

	@RequestMapping(params = "method=quota")
	public ModelAndView quota(
			HttpServletRequest request, HttpServletResponse response,
			String provider) throws Exception{

		String loginProvider = LoginUserContextHolder.getProvider();
	    UserInfo loginUser = LoginUserContextHolder.getUserInfo();
	    if(loginUser == null){
	      throw new RuntimeException("Session User is null");
	    }

	    String userId = loginUser.getId();
	    
	    QuotaInfo quota = null;
	    AccessToken accessToken = profileBusiness.getSubToken(loginProvider, userId, provider);
	    if(accessToken != null){
	    	DiskAPI diskAPI = everyCloudBusiness.getDiskAPI(provider);
	    	quota = diskAPI.quota(accessToken);
	    }

		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("quota", quota);
		
		return modelAndView;
	}

	@RequestMapping(params = "method=metadata")
	public ModelAndView metadata(
			HttpServletRequest request, HttpServletResponse response,
			String provider, String path) throws Exception{

		String loginProvider = LoginUserContextHolder.getProvider();
	    UserInfo loginUser = LoginUserContextHolder.getUserInfo();
	    if(loginUser == null){
	      throw new RuntimeException("Session User is null");
	    }

	    String userId = loginUser.getId();
	    
	    FileInfo metadata = null;
	    AccessToken accessToken = profileBusiness.getSubToken(loginProvider, userId, provider);
	    if(accessToken != null){
	    	DiskAPI diskAPI = everyCloudBusiness.getDiskAPI(provider);
	    	metadata = diskAPI.metadata(accessToken, path);
	    }

		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("metadata", metadata);
		
		return modelAndView;
	}

	@RequestMapping(params = "method=list")
	public ModelAndView list(
			HttpServletRequest request, HttpServletResponse response,
			String provider, String path) throws Exception{

		String loginProvider = LoginUserContextHolder.getProvider();
	    UserInfo loginUser = LoginUserContextHolder.getUserInfo();
	    if(loginUser == null){
	      throw new RuntimeException("Session User is null");
	    }

	    String userId = loginUser.getId();
	    
	    List<FileInfo> items = null;
	    AccessToken accessToken = profileBusiness.getSubToken(loginProvider, userId, provider);
	    if(accessToken != null){
	    	DiskAPI diskAPI = everyCloudBusiness.getDiskAPI(provider);
	    	FileInfo[] files = diskAPI.list(accessToken, path);
	    	items = Arrays.asList(files);
	    }

		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("items", items);
		
		return modelAndView;
	}

	@RequestMapping(params = "method=thumbnail")
	public ModelAndView thumbnail(
			HttpServletRequest request, HttpServletResponse response,
			String provider, String path) throws Exception{

		String loginProvider = LoginUserContextHolder.getProvider();
	    UserInfo loginUser = LoginUserContextHolder.getUserInfo();
	    if(loginUser == null){
	      throw new RuntimeException("Session User is null");
	    }

	    String userId = loginUser.getId();
	    
	    AccessToken accessToken = profileBusiness.getSubToken(loginProvider, userId, provider);
	    if(accessToken != null){
	    	DiskAPI diskAPI = everyCloudBusiness.getDiskAPI(provider);
	    	InputStream is = diskAPI.thumbnail(accessToken, path, 64, 64);
	    	
	    	OutputStream os = response.getOutputStream();
	    	HTTPStreamUtil.bufferedWriting(os, is);
	    	response.flushBuffer();
	    }
	    return null;
	}
}
