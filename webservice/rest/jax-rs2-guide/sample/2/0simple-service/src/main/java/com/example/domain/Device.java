package com.example.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
// JAXB标准定义的两个注解，用来将Device类和XML格式的设备数据相互转换并在服务器和客户端之间传输.
// 1.@XmlRootElement
@XmlRootElement(name = "device")
public class Device {
	private String deviceIp;
	private int deviceStatus;

	public Device() {
	}

	public Device(String deviceIp) {
		super();
		this.deviceIp = deviceIp;
	}
	// 2.@XmlAttribute
	@XmlAttribute
	public String getIp() {
		return deviceIp;
	}

	public void setIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}

	@XmlAttribute
	public int getStatus() {
		return deviceStatus;
	}

	public void setStatus(int deviceStatus) {
		this.deviceStatus = deviceStatus;
	}
}
