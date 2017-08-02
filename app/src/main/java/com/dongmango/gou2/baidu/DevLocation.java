package com.dongmango.gou2.baidu;

public class DevLocation {
	// 维度
	private double latitude;
	// 经度
	private double longitude;

	/**
	 * 设置纬度
	 * */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	/**
	 * 读取纬度
	 * */
	public double getLatitude() {
		return this.latitude;
	}

	/**
	 * 设置经度
	 * */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	/**
	 * 读取经度
	 * */
	public double getLongitude() {
		return this.longitude;
	}

}
