package net.anotheria.webutils.bean;

/**
 * TODO Please remain lrosenberg to comment SystemInfoBean.java
 * @author lrosenberg
 * @created on Feb 7, 2005
 */
public class SystemInfoBean {
    private float loadAvgLastMinute;
    private float loadAvgLast5Minutes;
    private float loadAvgLast15Minutes;
    
    private long totalMemory;
    private long freeMemory;
    private long cached;
    private long buffers;
    private long availableMemory;

    private String totalMemoryFormatted;
    private String freeMemoryFormatted;
    private String cachedFormatted;
    private String buffersFormatted;
    private String availableMemoryFormatted;

    /**
     * @return Returns the availableMemory.
     */
    public long getAvailableMemory() {
        return availableMemory;
    }
    /**
     * @param availableMemory The availableMemory to set.
     */
    public void setAvailableMemory(long availableMemory) {
        this.availableMemory = availableMemory;
    }
    /**
     * @return Returns the availableMemoryFormatted.
     */
    public String getAvailableMemoryFormatted() {
        return availableMemoryFormatted;
    }
    /**
     * @param availableMemoryFormatted The availableMemoryFormatted to set.
     */
    public void setAvailableMemoryFormatted(String availableMemoryFormatted) {
        this.availableMemoryFormatted = availableMemoryFormatted;
    }
    /**
     * @return Returns the buffers.
     */
    public long getBuffers() {
        return buffers;
    }
    /**
     * @param buffers The buffers to set.
     */
    public void setBuffers(long buffers) {
        this.buffers = buffers;
    }
    /**
     * @return Returns the buffersFormatted.
     */
    public String getBuffersFormatted() {
        return buffersFormatted;
    }
    /**
     * @param buffersFormatted The buffersFormatted to set.
     */
    public void setBuffersFormatted(String buffersFormatted) {
        this.buffersFormatted = buffersFormatted;
    }
    /**
     * @return Returns the cached.
     */
    public long getCached() {
        return cached;
    }
    /**
     * @param cached The cached to set.
     */
    public void setCached(long cached) {
        this.cached = cached;
    }
    /**
     * @return Returns the cachedFormatted.
     */
    public String getCachedFormatted() {
        return cachedFormatted;
    }
    /**
     * @param cachedFormatted The cachedFormatted to set.
     */
    public void setCachedFormatted(String cachedFormatted) {
        this.cachedFormatted = cachedFormatted;
    }
    /**
     * @return Returns the freeMemory.
     */
    public long getFreeMemory() {
        return freeMemory;
    }
    /**
     * @param freeMemory The freeMemory to set.
     */
    public void setFreeMemory(long freeMemory) {
        this.freeMemory = freeMemory;
    }
    /**
     * @return Returns the freeMemoryFormatted.
     */
    public String getFreeMemoryFormatted() {
        return freeMemoryFormatted;
    }
    /**
     * @param freeMemoryFormatted The freeMemoryFormatted to set.
     */
    public void setFreeMemoryFormatted(String freeMemoryFormatted) {
        this.freeMemoryFormatted = freeMemoryFormatted;
    }
    /**
     * @return Returns the loadAvgLast15Minutes.
     */
    public float getLoadAvgLast15Minutes() {
        return loadAvgLast15Minutes;
    }
    /**
     * @param loadAvgLast15Minutes The loadAvgLast15Minutes to set.
     */
    public void setLoadAvgLast15Minutes(float loadAvgLast15Minutes) {
        this.loadAvgLast15Minutes = loadAvgLast15Minutes;
    }
    /**
     * @return Returns the loadAvgLast5Minutes.
     */
    public float getLoadAvgLast5Minutes() {
        return loadAvgLast5Minutes;
    }
    /**
     * @param loadAvgLast5Minutes The loadAvgLast5Minutes to set.
     */
    public void setLoadAvgLast5Minutes(float loadAvgLast5Minutes) {
        this.loadAvgLast5Minutes = loadAvgLast5Minutes;
    }
    /**
     * @return Returns the loadAvgLastMinute.
     */
    public float getLoadAvgLastMinute() {
        return loadAvgLastMinute;
    }
    /**
     * @param loadAvgLastMinute The loadAvgLastMinute to set.
     */
    public void setLoadAvgLastMinute(float loadAvgLastMinute) {
        this.loadAvgLastMinute = loadAvgLastMinute;
    }
    /**
     * @return Returns the totalMemory.
     */
    public long getTotalMemory() {
        return totalMemory;
    }
    /**
     * @param totalMemory The totalMemory to set.
     */
    public void setTotalMemory(long totalMemory) {
        this.totalMemory = totalMemory;
    }
    /**
     * @return Returns the totalMemoryFormatted.
     */
    public String getTotalMemoryFormatted() {
        return totalMemoryFormatted;
    }
    /**
     * @param totalMemoryFormatted The totalMemoryFormatted to set.
     */
    public void setTotalMemoryFormatted(String totalMemoryFormatted) {
        this.totalMemoryFormatted = totalMemoryFormatted;
    }
}

/* ------------------------------------------------------------------------- *
 * $Log: SystemInfoBean.java,v $
 * Revision 1.2  2006/10/29 21:13:30  lrosenberg
 * *** empty log message ***
 *
 * Revision 1.1  2005/05/04 16:17:18  lro
 * *** empty log message ***
 *
 * Revision 1.1  2005/02/07 16:43:18  lrosenberg
 * *** empty log message ***
 *
 */