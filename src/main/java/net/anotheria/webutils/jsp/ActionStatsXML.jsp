<%@ page 
	contentType="text/xml;charset=iso-8859-1" session="true" 
%><%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"
%><%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"
%><?xml version="1.0" encoding="iso-8859-1"?>
<stats timestamp="<bean:write name="serverStats" property="date"/>">
  <server>
    <%--<name><bean:write name="servername"/></name>--%>
    <currentSessionCount><bean:write name="serverStats" property="sessions"/></currentSessionCount>
    <maxSessionCount><bean:write name="serverStats" property="maxSessions"/></maxSessionCount>
    <memory>
    	<free><bean:write name="serverStats" property="freeMemory"/></free>
    	<allocated><bean:write name="serverStats" property="totalMemory"/></allocated>
    	<total><bean:write name="serverStats" property="maxMemory"/></total>
    </memory>
    <upsince><bean:write name="serverStartTime" ignore="true" scope="application"/></upsince>
    <uptime><bean:write name="serverStats" ignore="true" property="uptime"/></uptime>
  </server>
	<machine>
	  <load>
	  	<last1min><bean:write name="machineStats" property="loadAvgLastMinute"/></last1min>
	  	<last5min><bean:write name="machineStats" property="loadAvgLast5Minutes"/></last5min>
	  	<last15min><bean:write name="machineStats" property="loadAvgLast15Minutes"/></last15min>
	  </load>
	  <memory>
	  	<total><bean:write name="machineStats" property="totalMemory"/></total>
	  	<free><bean:write name="machineStats" property="freeMemory"/></free>
	  	<cached><bean:write name="machineStats" property="cached"/></cached>
	  	<buffers><bean:write name="machineStats" property="buffers"/></buffers>
	  	<available><bean:write name="machineStats" property="availableMemory"/></available>
	  	<totalF><bean:write name="machineStats" property="totalMemoryFormatted"/></totalF>
	  	<freeF><bean:write name="machineStats" property="freeMemoryFormatted"/></freeF>
	  	<cachedF><bean:write name="machineStats" property="cachedFormatted"/></cachedF>
	  	<buffersF><bean:write name="machineStats" property="buffersFormatted"/></buffersF>
	  	<availableF><bean:write name="machineStats" property="availableMemoryFormatted"/></availableF>
	  </memory>
	</machine>
  <actions>
	<logic:iterate name="stats" type="net.anotheria.webutils.bean.ActionStatsBean" id="stat">
	  <action>
	   <name><bean:write name="stat" property="name"/></name>
	   <requests><bean:write name="stat" property="requests"/></requests>
	   <successful><bean:write name="stat" property="successful"/></successful>
	   <errors><bean:write name="stat" property="errors"/></errors>
	   <successPercentage><bean:write name="stat" property="successPercentage"/></successPercentage>
	   <minTime><bean:write name="stat" property="minTime"/></minTime>
	   <maxTime><bean:write name="stat" property="maxTime"/></maxTime>
	   <midTime><bean:write name="stat" property="midTime"/></midTime>
	   <lastTime><bean:write name="stat" property="lastTime"/></lastTime>
	   <totalTime><bean:write name="stat" property="totalTime"/></totalTime>
	   <measures><bean:write name="stat" property="measures"/></measures>
	   <class><bean:write name="stat" property="className"/></class>
	  </action>
	</logic:iterate>
  </actions>
</stats>  
