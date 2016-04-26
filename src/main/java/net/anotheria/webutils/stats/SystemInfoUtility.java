package net.anotheria.webutils.stats;


import net.anotheria.util.IOUtils;
import net.anotheria.util.StringUtils;
//import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.Timer;
import java.util.TimerTask;


/**
 * TODO Please remain lrosenberg to comment SystemInfoUtility.java
 * @author lrosenberg
 * @created on Feb 7, 2005
 */
public class SystemInfoUtility{
    private static SystemInfo systemInfo;
    
    //zum testen einmal pro sekunde
    public static final int REREAD_INTERVAL = 1000*60;
    
    private static Logger log;

    private static final Timer timer = new Timer("AnoWebSysInfoUtilityReader", true);
    
    static{
        log = LoggerFactory.getLogger(SystemInfoUtility.class);
        log.debug("Created SystemInfo, registered for "+REREAD_INTERVAL+" ticks");
        timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
		        systemInfo = readSystemInfo();
			}
		}, 0, REREAD_INTERVAL);
    }
    
    private static SystemInfo readSystemInfo(){
        SystemInfo info = new SystemInfo();
        fillLoadAverage(info);
        fillMemInfo(info);
        System.out.println("read systeminfo: "+info);
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
//        BasicConfigurator.configure();
        try{
            Thread.sleep(10000);
        }catch(Exception e){}
    }

}
