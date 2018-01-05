package com.my.sampleGw.common.config;


import java.net.InetSocketAddress;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:properties/server-config.${demonEnv:org}${serverNo:}.properties")
public class SpringConfig {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(SpringConfig.class);

	//-DdemonType=gift
	@Value("#{systemProperties['demonType'] == null ? 'org' : systemProperties['demonType']}")
	private String dmType;
	
	//-DdemonEnv=loc
	@Value("#{systemProperties['demonEnv'] == null ? 'loc' : systemProperties['demonEnv']}")
	private String dmEnv;

	@Value("${boss.thread.count}")
	private int bossCount;

	@Value("${worker.thread.count}")
	private int workerCount;

	@Value("${so.keepalive}")
	private boolean keepAlive;

	@Value("${so.backlog}")
	private int backlog;

	@Value("${so.linger}")
	private int linger;

	@Value("${tcp.nodelay}")
	private boolean nodelay;

	@Value("${so.rcvBuf}")
	private int rcvBuf;

	@Value("${so.sndBuf}")
	private int sndBuf;

	@Value("${org.tcp.ip}")
	private String orgServerIp;
	
	@Value("${org.tcp.port}")
	private int orgServerPort;
	
	@Value("${org.tcp.timeout}")
	private int orgServerTimeout;

	@Value("${demonType}")
	private String demonType;
	
	public String getDmType() {
		return dmType;
	}
	
	public void setDmType(String dmType) {
		this.dmType = dmType;
	}
	
	public String getDmEnv() {
		return dmEnv;
	}
	
	public void setDmEnv(String dmEnv) {
		this.dmEnv = dmEnv;
	}
	
	public int getBossCount() {
		return bossCount;
	}

	public void setBossCount(int bossCount) {
		this.bossCount = bossCount;
	}

	public int getWorkerCount() {
		return workerCount;
	}

	public void setWorkerCount(int workerCount) {
		this.workerCount = workerCount;
	}

	public boolean isKeepAlive() {
		return keepAlive;
	}

	public void setKeepAlive(boolean keepAlive) {
		this.keepAlive = keepAlive;
	}

	public int getBacklog() {
		return backlog;
	}

	public void setBacklog(int backlog) {
		this.backlog = backlog;
	}

	public int getLinger() {
		return linger;
	}

	public void setLinger(int linger) {
		this.linger = linger;
	}

	public boolean isNodelay() {
		return nodelay;
	}

	public void setNodelay(boolean nodelay) {
		this.nodelay = nodelay;
	}

	public int getRcvBuf() {
		return rcvBuf;
	}

	public void setRcvBuf(int rcvBuf) {
		this.rcvBuf = rcvBuf;
	}

	public int getSndBuf() {
		return sndBuf;
	}

	public void setSndBuf(int sndBuf) {
		this.sndBuf = sndBuf;
	}

	public String getOrgServerIp() {
		return orgServerIp;
	}

	public void setOrgServerIp(String orgServerIp) {
		this.orgServerIp = orgServerIp;
	}

	public int getOrgServerPort() {
		return orgServerPort;
	}

	public void setOrgServerPort(int orgServerPort) {
		this.orgServerPort = orgServerPort;
	}

	public int getOrgServerTimeout() {
		return orgServerTimeout;
	}

	public void setOrgServerTimeout(int orgServerTimeout) {
		this.orgServerTimeout = orgServerTimeout;
	}

	public String getDemonType() {
		return demonType;
	}

	public void setDemonType(String demonType) {
		this.demonType = demonType;
	}
	
	@Bean(name = "inetSocketAddress")
    public InetSocketAddress serverTcpSocketAddress() {
		if ("org".equals(demonType)) {
			log.info("org port : " + orgServerPort);
			log.info("demonType[" + demonType);
			return new InetSocketAddress(orgServerPort);
		} else if ("a".equals(demonType)) {
			log.info("a port : " + orgServerPort);
			return new InetSocketAddress(orgServerPort);
		} else if ("b".equals(demonType)) {
			log.info("b port : " + orgServerPort);
			return new InetSocketAddress(orgServerPort);
		} else {
			return null;
		}
	}

}
