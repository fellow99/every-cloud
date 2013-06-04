package com.fellow.every.blog;

public interface BlogAPI {
	
	/**
	 * 获取登录用户的博客信息列表
	 */
	BlogInfo[] listMe();

	/**
	 * 获取指定用户的博客信息列表
	 */
	BlogInfo[] listUser(String uid);

	/**
	 * 获取一条博客信息
	 */
	BlogInfo get(String blogId);

	/**
	 * 获取一条博客的评论信息列表
	 */
	BlogCommentInfo[] listComments(String blogId);

	/**
	 * 添加一条博客
	 */
	BlogInfo addBlog();

	/**
	 * 添加一条博客评论
	 */
	BlogInfo addComment(String blogId, String content);

	/**
	 * 添加一条博客评论
	 */
	BlogInfo addComment(String blogId, String content, CommentType type, String replyUid);
}
