/* ------------------------------------------------------------------------- *
$Source: /work/cvs/ano-web/java/net/anotheria/webutils/stats/SystemInfoUtility.java,v $
$Author: lrosenberg $
$Date: 2007/02/25 23:13:18 $
$Revision: 1.4 $


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


import java.io.FileNotFoundException;

import net.anotheria.util.IOUtils;
import net.anotheria.util.StringUtils;
import net.java.dev.moskito.core.timing.timer.ITimerConsumer;
import net.java.dev.moskito.core.timing.timer.TimerServiceConstantsUtility;
import net.java.dev.moskito.core.timing.timer.TimerServiceFactory;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;


/**
 * TODO Please remain lrosenberg to comment SystemInfoUtility.java
 * @author lrosenberg
 * @created on Feb 7, 2005
 */
public class SystemInfoUtility implements ITimerConsumer{
    private static SystemInfo systemInfo;
    
    //zum testen einmal pro sekunde
    public static final int REREAD_INTERVAL = 1000*60/TimerServiceConstantsUtility.getSleepingUnit();
    
    private static Logger log;

    
    static{
        log = Logger.getLogger(SystemInfoUtility.class);
        log.debug("Created SystemInfo, registered for "+REREAD_INTERVAL+" ticks");
        TimerServiceFactory.createTimerService().addConsumer(new SystemInfoUtility(), REREAD_INTERVAL);
        systemInfo = readSystemInfo();
    }
    
    /* (non-Javadoc)
     * @see de.friendscout.vincent.services.timer.ITimerConsumer#receiveTimerEvent(int)
     */
    public void receiveTimerEvent(int arg0) {
        //create a new systeminfo object.
        SystemInfo info = readSystemInfo();
        systemInfo = info;
    }
    
    private static SystemInfo readSystemInfo(){
        SystemInfo info = new SystemInfo();
        fillLoadAverage(info);
        fillMemInfo(info);
        //System.out.println("read systeminfo: "+info);
        return info;
    }
 
    public static synchronized SystemInfo getSystemInfo(){
        return systemInfo;
    }
    
    private static void fillLoadAverage(SystemInfo info){
        try{
            String s = IOUtils.readFileBufferedAsString("/proc/loadavg");
            //System.out.println("S:"+s);
            String[] tokens = StringUtils.tokenize(s, ' ');
            info.setLoadAvgLastMinute(Float.parseFloat(tokens[0]));
            info.setLoadAvgLast5Minutes(Float.parseFloat(tokens[1]));
            info.setLoadAvgLast15Minutes(Float.parseFloat(tokens[2]));
        }catch(FileNotFoundException ignored){
            //
        }catch(Exception e){
            log.error("fillLoadAverage", e);
        }
    }
    
    private static long extractValue(String s){
        String t[] = StringUtils.tokenize(s, ':');
        String t1[] = StringUtils.tokenize(t[1].trim(), ' ');
        //DebugUtilities.printArray(t1);
        return Long.parseLong(t1[0])*getMultiplikator(t1[1]);
    }
    
    private static int getMultiplikator(String s){
        if (s.equalsIgnoreCase("kB"))
            return 1024;
       
        return 1;
    }
    
    private static void fillMemInfo(SystemInfo info){
        try{
            String s = IOUtils.readFileBufferedAsString("/proc/meminfo");
            //System.out.println("S:"+s);
            String[] tokens = StringUtils.tokenize(s, '\n');
            info.setTotalMemory(extractValue(tokens[0]));
            info.setFreeMemory(extractValue(tokens[1]));
            info.setBuffers(extractValue(tokens[2]));
            info.setCached(extractValue(tokens[3]));
            
            
        }catch(FileNotFoundException ignored){
            //
        }catch(Exception e){
            log.error("fillLoadAverage", e);
        }
    }

    public static void main(String a[]){
        BasicConfigurator.configure();
        try{
            Thread.sleep(10000);
        }catch(Exception e){}
    }

}

/* ------------------------------------------------------------------------- *
 * $Log: SystemInfoUtility.java,v $
 * Revision 1.4  2007/02/25 23:13:18  lrosenberg
 * *** empty log message ***
 *
 * Revision 1.3  2006/10/29 21:13:30  lrosenberg
 * *** empty log message ***
 *
 * Revision 1.2  2006/01/15 18:49:41  lrosenberg
 * *** empty log message ***
 *
 * Revision 1.1  2005/05/04 16:17:18  lro
 * *** empty log message ***
 *
 * Revision 1.3  2005/02/08 16:40:24  lrosenberg
 * *** empty log message ***
 *
 * Revision 1.2  2005/02/07 16:52:23  lrosenberg
 * *** empty log message ***
 *
 * Revision 1.1  2005/02/07 16:43:33  lrosenberg
 * action stats now shows load average and mem usage
 *
 */