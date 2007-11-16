package net.anotheria.webutils.util;

import org.apache.log4j.Logger;

public class DownloadMeterOutput extends Thread{
	
	private long interval;
	
	private Logger log = Logger.getLogger(DownloadMeterOutput.class);
	
	public DownloadMeterOutput(long anInterval){
		super("DownloadMeterOutput");
		setDaemon(true);
		interval = anInterval;
		this.start();
	}
	
	public void run(){
		while(true){
			try{
				sleep(interval);
			}catch(InterruptedException e){}
			long amount = DownloadMeter.getDownloadedBytesInPeriod(interval); 
			long kbit = amount / (interval/1000) * 8;
			log.info("Download amount in last "+interval+" milliseconds is : "+amount+" bytes, kbit: "+kbit);
		}
		
	}
}
