package com.dongmango.gou2.utils;

/**
 * 保存用户的状态 登录状态 用户信息
 * 
 * @author Leader
 * 
 */
public class AppGlobal {
	// 1、TRUE为登录 2、FALSE为登出
	public static boolean isLogin = false;

	private AppGlobal() {
	}

	private static AppGlobal instance;

	public static AppGlobal getInstance() {
		if (instance == null) {
			instance = new AppGlobal();
		}
		return instance;
	}
}
