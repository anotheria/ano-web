/* ------------------------------------------------------------------------- *
$Source: /work/cvs/ano-web/java/net/anotheria/webutils/stats/SystemInfo.java,v $
$Author: lrosenberg $
$Date: 2006/10/29 21:13:30 $
$Revision: 1.2 $


Copyright 2004-2005 by FriendScout24 GmbH, Munich, Germany.
All rights reserved.

This software is the confidential and proprietary information
of FriendScout24 GmbH. ("Confidential Information").  You
shall not disclose such Confidential Information and shall use
it only in accordance with the terms of the license agreement
you entered into with FriendScout24 GmbH.
See www.friendscout24.de for details.
** ------------------------------------------------------------------------- */
package net.anotheria.webutils.stats;

import net.anotheria.util.NumberUtils;

/**
 * TODO Please remain lrosenberg to comment SystemInfo.java
 * @author lrosenberg
 * @created on Feb 7, 2005
 */
public class SystemInfo {
    private float loadAvgLastMinute;
    private float loadAvgLast5Minutes;
    private float loadAvgLast15Minutes;
    
    private long totalMemory;
    private long freeMemory;
    private long cached;
    private long buffers;
    
    
    
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
    
    public long getAvailableMemory(){
        return cached+freeMemory;
    }
    
    public String toString(){
        String ret = "Load: "+loadAvgLastMinute+" "+loadAvgLast5Minutes+" "+loadAvgLast15Minutes;
        ret += "\nMem Total:"+NumberUtils.makeSizeString((int)totalMemory)+", Buffers:"+NumberUtils.makeSizeString((int)buffers)+" Cached:"+NumberUtils.makeSizeString((int)cached)+", Free:"+NumberUtils.makeSizeString((int)freeMemory)+" Available:"+NumberUtils.makeSizeString((int)getAvailableMemory());
        return ret;
    }
}

/* ------------------------------------------------------------------------- *
 * $Log: SystemInfo.java,v $
 * Revision 1.2  2006/10/29 21:13:30  lrosenberg
 * *** empty log message ***
 *
 * Revision 1.1  2005/05/04 16:17:18  lro
 * *** empty log message ***
 *
 * Revision 1.1  2005/02/07 16:43:33  lrosenberg
 * action stats now shows load average and mem usage
 *
 */