package com.fellow.every.one.web.ctrl;

import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fellow.every.one.business.EveryCloudBusiness;
import com.fellow.every.one.business.ProfileBusiness;
import com.fellow.every.one.web.filter.LoginUserContextHolder;
import com.fellow.every.user.UserInfo;

@Controller  
@RequestMapping("/sub-provider/data")
public class SubProviderDataCtrl {
	
	@Resource(name="everyCloudBusiness")
	private EveryCloudBusiness everyCloudBusiness;
	
	@Resource(name="profileBusiness")
	private ProfileBusiness profileBusiness;

	@RequestMapping(params = "method=list")
	public ModelAndView subProviders(
			HttpServletRequest request, HttpServletResponse response) throws Exception{

		String loginProvider = LoginUserContextHolder.getProvider();
	    UserInfo loginUser = LoginUserContextHolder.getUserInfo();
	    if(loginUser == null){
	      throw new RuntimeException("Session User is null");
	    }

	    String userId = loginUser.getId();
	    
	    Collection<String> subProviders = everyCloudBusiness.getProviders();
	    List<Map<String, Object>> items = new ArrayList<Map<String, Object>>();
	    for(String p : subProviders){
	    	String name = everyCloudBusiness.getProviderName(p);
			boolean login = (profileBusiness.getSubToken(loginProvider, userId, p) != null);

			Map<String, Object> info = new HashMap<String, Object>();
			info.put("provider", p);
			info.put("name", name);
			info.put("login", login);

			Map<String, Object> capability = new HashMap<String, Object>();
			
			for(EveryCloudBusiness.Capability c: EveryCloudBusiness.Capability.values()){
				boolean capable = everyCloudBusiness.isCapable(p, c);
				capability.put(c.toString(), capable);
			}
			info.put("capability", capability);
			items.add(info);
	    }
	    
		ModelAndView modelAndView = new ModelAndView("jsonView");
		modelAndView.addObject("items", items);
		
		return modelAndView;
	}
}
