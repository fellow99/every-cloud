package com.fellow.every.one.business.every;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletContext;

import org.scribe.builder.api.Api;
import org.scribe.model.OAuthConfig;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.fellow.every.one.business.EveryCloudBusiness;
import com.fellow.every.auth.AccessToken;
import com.fellow.every.blog.BlogAPI;
import com.fellow.every.disk.DiskAPI;
import com.fellow.every.friend.FriendAPI;
import com.fellow.every.status.StatusAPI;
import com.fellow.every.user.UserAPI;

@Service
public class EveryCloudBusinessImpl implements EveryCloudBusiness, ApplicationContextAware{

	private ApplicationContext parentContext;
	private ServletContext servletContext;

	private List<String> providers;
	
	private Map<String, ApplicationContext> providerContexts;

	private Map<String, Collection<Capability>> capabilitiesMap;
	
	public EveryCloudBusinessImpl(List<String> providers){
		this.providers = providers;
		providerContexts = new ConcurrentHashMap<String, ApplicationContext>();
		capabilitiesMap = new ConcurrentHashMap<String, Collection<Capability>>();
		
		for(String provider : this.providers){
			ApplicationContext ctx = createProviderContext(provider);
			providerContexts.put(provider, ctx);
		}
	}

	@Override
	public Collection<String> getProviders() {
		return Collections.unmodifiableList(providers);
	}

	@Override
	public String getProviderName(String provider) {
		return provider;
	}

	@Override
	public Collection<Capability> getCapabilities(String provider) {
		
		Collection<Capability> capabilities = capabilitiesMap.get(provider);
		if(capabilities == null){
			capabilities = new ArrayList<Capability>();

			ApplicationContext ctx = providerContext(provider);
			
			if(ctx.containsBean("userAPI"))capabilities.add(Capability.USER);
			if(ctx.containsBean("friendAPI"))capabilities.add(Capability.FRIEND);
			if(ctx.containsBean("statusAPI"))capabilities.add(Capability.STATUS);
			if(ctx.containsBean("blogAPI"))capabilities.add(Capability.BLOG);
			if(ctx.containsBean("diskAPI"))capabilities.add(Capability.DISK);
			
			capabilitiesMap.put(provider, capabilities);
		}
		return capabilities;
	}

	@Override
	public boolean isCapable(String provider, Capability capability) {
		Collection<Capability> capabilities = getCapabilities(provider);
		return capabilities.contains(capability);
	}
	
	@Override
	public void setApplicationContext(ApplicationContext parentContext)
			throws BeansException {
		this.parentContext = parentContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	public ApplicationContext createProviderContext(String provider) {
		XmlWebApplicationContext context = new XmlWebApplicationContext();

		context.setParent(parentContext);
		context.setServletContext(servletContext);
		context.setConfigLocation("classpath:conf/api-service-" + provider + ".xml");
		
		context.refresh();
		
		
		return context;
	}
	
	public ApplicationContext providerContext(String provider) {
		ApplicationContext ctx = providerContexts.get(provider);
		if(ctx == null){
			throw new RuntimeException("Provider not found： " + provider);
		}
		return ctx;
	}

	@Override
	public OAuthConfig getOAuthConfig(String provider) {
		ApplicationContext ctx = providerContext(provider);
	    if(ctx == null){
	    	throw new RuntimeException("Provider is null");
		}
	    return ctx.getBean("oauthConfig", OAuthConfig.class);
	}

	@Override
	public Api getOAuthAPI(String provider) {
		ApplicationContext ctx = providerContext(provider);
	    if(ctx == null){
	    	throw new RuntimeException("Provider is null");
		}
	    return ctx.getBean("oauthAPI", Api.class);
	}

	@Override
	public AccessToken createAccessToken(String provider) {
		ApplicationContext ctx = providerContext(provider);
	    if(ctx == null){
	    	throw new RuntimeException("Provider is null");
		}
	    
	    AccessTokenFactory accessTokenFactory = ctx.getBean("accessTokenFactory", AccessTokenFactory.class);
	    if(accessTokenFactory == null){
	    	throw new RuntimeException("AccessTokenFactory object is null");
		}
	    
	    return accessTokenFactory.createAccessToken();
	}

	@Override
	public UserAPI getUserAPI(String provider) {
		ApplicationContext ctx = providerContext(provider);
	    if(ctx == null){
	    	throw new RuntimeException("Provider is null");
		}
	    return ctx.getBean("userAPI", UserAPI.class);
	}

	@Override
	public FriendAPI getFriendAPI(String provider) {
		ApplicationContext ctx = providerContext(provider);
	    if(ctx == null){
	    	throw new RuntimeException("Provider is null");
		}
	    return ctx.getBean("friendAPI", FriendAPI.class);
	}

	@Override
	public StatusAPI getStatusAPI(String provider) {
		ApplicationContext ctx = providerContext(provider);
	    if(ctx == null){
	    	throw new RuntimeException("Provider is null");
		}
	    return ctx.getBean("statusAPI", StatusAPI.class);
	}

	@Override
	public BlogAPI getBlogAPI(String provider) {
		ApplicationContext ctx = providerContext(provider);
	    if(ctx == null){
	    	throw new RuntimeException("Provider is null");
		}
	    return ctx.getBean("blogAPI", BlogAPI.class);
	}

	@Override
	public DiskAPI getDiskAPI(String provider) {
		ApplicationContext ctx = providerContext(provider);
	    if(ctx == null){
	    	throw new RuntimeException("Provider is null");
		}
	    return ctx.getBean("diskAPI", DiskAPI.class);
	}
}
