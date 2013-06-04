package com.fellow.every.friend;

public interface FriendAPI {
	/**
	 * 获取互为好友的关注人
	 */
	FriendInfo[] listCloseFriends();

	/**
	 * 获取互为好友的关注人ID
	 */
	String[] listCloseFriendIds();
	

	/**
	 * 获取关注人
	 */
	FriendInfo[] listFriends();

	/**
	 * 获取关注人ID
	 */
	String[] listFriendIds();
	

	/**
	 * 获取粉丝
	 */
	FriendInfo[] listFollowers();

	/**
	 * 获取粉丝ID
	 */
	String[] listFollowerIds();


	/**
	 * 获取黑名单
	 */
	FriendInfo[] listBlacks();

	/**
	 * 获取黑名单ID
	 */
	String[] listBlackIds();


	/**
	 * 获取本人与某用户的共同好友
	 */
	FriendInfo[] listSameFriends(String uid);

	/**
	 * 获取本人与某用户的共同好友ID
	 */
	String[] listSameFriendIds(String uid);
	
	
	
	/**
	 * 添加关注
	 */
	FriendInfo addFriend(String uid);

	/**
	 * 移除消关注
	 */
	FriendInfo removeFriend(String uid);

	/**
	 * 移除粉丝
	 */
	FriendInfo removeFollower(String uid);

	/**
	 * 添加黑名单
	 */
	FriendInfo addBlack(String uid);

	/**
	 * 移除黑名单
	 */
	FriendInfo removeBlack(String uid);
}
