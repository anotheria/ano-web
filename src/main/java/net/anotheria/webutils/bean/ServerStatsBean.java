package net.anotheria.webutils.bean;

/**
 * This beans holds the information about the server status stats.
 * @author lrosenberg
 * Created on 18.08.2004
 */
public class ServerStatsBean {
	private String maxMemory;
	private String freeMemory;
	private String totalMemory;
	private long sessions;
	private long maxSessions;
	private String date;
	private long uptime;
	private String serverName;
	private String serverStartTime;
	
	/**
	 * @return
	 */
	public String getFreeMemory() {
		return freeMemory;
	}

	/**
	 * @return
	 */
	public String getMaxMemory() {
		return maxMemory;
	}

	/**
	 * @return
	 */
	public long getMaxSessions() {
		return maxSessions;
	}

	/**
	 * @return
	 */
	public long getSessions() {
		return sessions;
	}

	/**
	 * @return
	 */
	public String getTotalMemory() {
		return totalMemory;
	}

	/**
	 * @param string
	 */
	public void setFreeMemory(String string) {
		freeMemory = string;
	}

	/**
	 * @param string
	 */
	public void setMaxMemory(String string) {
		maxMemory = string;
	}

	/**
	 * @param i
	 */
	public void setMaxSessions(long i) {
		maxSessions = i;
	}

	/**
	 * @param i
	 */
	public void setSessions(long i) {
		sessions = i;
	}

	/**
	 * @param string
	 */
	public void setTotalMemory(String string) {
		totalMemory = string;
	}

	/**
	 * @return
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param string
	 */
	public void setDate(String string) {
		date = string;
	}

	/**
	 * @return
	 */
	public long getUptime() {
		return uptime;
	}

	/**
	 * @param l
	 */
	public void setUptime(long l) {
		uptime = l;
	}

	/**
	 * @return
	 */
	public String getServerName() {
		return serverName;
	}

	/**
	 * @param string
	 */
	public void setServerName(String string) {
		serverName = string;
	}

	/**
	 * @return
	 */
	public String getServerStartTime() {
		return serverStartTime;
	}

	/**
	 * @param string
	 */
	public void setServerStartTime(String string) {
		serverStartTime = string;
	}

}
