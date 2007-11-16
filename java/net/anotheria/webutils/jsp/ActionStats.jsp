<%@ page 
	contentType="text/html;charset=iso-8859-1" session="true" 
%><%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"
%><%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"
%><%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"
%><%@ taglib uri="/WEB-INF/tlds/struts-tiles.tld" prefix="tiles"
%>
<html><body>
	<table border="0" cellpadding="0" cellspacing="0" height="100%" width="100%">
		<tr>
			<td><p class="boxhead1">ActionStats</p></td>
		</tr>
    </table>
    <br>
Subsystems:&nbsp;<a href="admShowActionStats?pFilter=">all</a>&nbsp;
<logic:iterate name="subSystems" type="java.lang.String" id="subSystem">
<a href="admShowActionStats?pFilter=<bean:write name="subSystem"/>"><bean:write name="subSystem"/></a>&nbsp;
</logic:iterate>
<br><br>
<table cellpadding="4" cellspacing="0" border="1" width="100%">
 <tr>
   <td>Action name &nbsp;<a href="admShowActionStats?pSortBy=1&pSortOrder=asc&pFilter=<bean:write name="actionFilter" ignore="true"/>">A</a>&nbsp;<a href="admShowActionStats?pSortBy=1&pSortOrder=desc&pFilter=<bean:write name="actionFilter" ignore="true"/>">Z</a></td>
   <td>Req &nbsp;<a href="admShowActionStats?pSortBy=3&pSortOrder=asc&pFilter=<bean:write name="actionFilter" ignore="true"/>">A</a>&nbsp;<a href="admShowActionStats?pSortBy=3&pSortOrder=desc&pFilter=<bean:write name="actionFilter" ignore="true"/>">Z</a></td>
   <td>Suc &nbsp;<a href="admShowActionStats?pSortBy=5&pSortOrder=asc&pFilter=<bean:write name="actionFilter" ignore="true"/>">A</a>&nbsp;<a href="admShowActionStats?pSortBy=5&pSortOrder=desc&pFilter=<bean:write name="actionFilter" ignore="true"/>">Z</a></td>
   <td>Err &nbsp;<a href="admShowActionStats?pSortBy=4&pSortOrder=asc&pFilter=<bean:write name="actionFilter" ignore="true"/>">A</a>&nbsp;<a href="admShowActionStats?pSortBy=4&pSortOrder=desc&pFilter=<bean:write name="actionFilter" ignore="true"/>">Z</a></td>
   <td>Suc&nbsp;% &nbsp;<a href="admShowActionStats?pSortBy=6&pSortOrder=asc&pFilter=<bean:write name="actionFilter" ignore="true"/>">A</a>&nbsp;<a href="admShowActionStats?pSortBy=6&pSortOrder=desc&pFilter=<bean:write name="actionFilter" ignore="true"/>">Z</a></td>
   <td>min &nbsp;<a href="admShowActionStats?pSortBy=7&pSortOrder=asc&pFilter=<bean:write name="actionFilter" ignore="true"/>">A</a>&nbsp;<a href="admShowActionStats?pSortBy=7&pSortOrder=desc&pFilter=<bean:write name="actionFilter" ignore="true"/>">Z</a></td>
   <td>max &nbsp;<a href="admShowActionStats?pSortBy=8&pSortOrder=asc&pFilter=<bean:write name="actionFilter" ignore="true"/>">A</a>&nbsp;<a href="admShowActionStats?pSortBy=8&pSortOrder=desc&pFilter=<bean:write name="actionFilter" ignore="true"/>">Z</a></td>
   <td>mid &nbsp;<a href="admShowActionStats?pSortBy=9&pSortOrder=asc&pFilter=<bean:write name="actionFilter" ignore="true"/>">A</a>&nbsp;<a href="admShowActionStats?pSortBy=9&pSortOrder=desc&pFilter=<bean:write name="actionFilter" ignore="true"/>">Z</a></td>
   <td>last &nbsp;<a href="admShowActionStats?pSortBy=10&pSortOrder=asc&pFilter=<bean:write name="actionFilter" ignore="true"/>">A</a>&nbsp;<a href="admShowActionStats?pSortBy=10&pSortOrder=desc&pFilter=<bean:write name="actionFilter" ignore="true"/>">Z</a></td>
   <td>total &nbsp;<a href="admShowActionStats?pSortBy=11&pSortOrder=asc&pFilter=<bean:write name="actionFilter" ignore="true"/>">A</a>&nbsp;<a href="admShowActionStats?pSortBy=11&pSortOrder=desc&pFilter=<bean:write name="actionFilter" ignore="true"/>">Z</a></td>
   <td>count &nbsp;<a href="admShowActionStats?pSortBy=12&pSortOrder=asc&pFilter=<bean:write name="actionFilter" ignore="true"/>">A</a>&nbsp;<a href="admShowActionStats?pSortBy=12&pSortOrder=desc&pFilter=<bean:write name="actionFilter" ignore="true"/>">Z</a></td>
   <td>Action class &nbsp;<a href="admShowActionStats?pSortBy=2&pSortOrder=asc&pFilter=<bean:write name="actionFilter" ignore="true"/>">A</a>&nbsp;<a href="admShowActionStats?pSortBy=2&pSortOrder=desc&pFilter=<bean:write name="actionFilter" ignore="true"/>">Z</a></td>
</tr>
   
<logic:iterate name="stats" type="net.anotheria.webutils.bean.ActionStatsBean" id="stat">
  <tr>
   <td><a href="admShowActionStat?pName=<bean:write name="stat" property="className"/>"><bean:write name="stat" property="name"/></a></td>
   <td><bean:write name="stat" property="requests"/></td>
   <td><bean:write name="stat" property="successful"/></td>
   <td><bean:write name="stat" property="errors"/></td>
   <td><bean:write name="stat" property="successPercentage"/>%</td>
   <td><bean:write name="stat" property="minTime"/></td>
   <td><bean:write name="stat" property="maxTime"/></td>
   <td><bean:write name="stat" property="midTime"/></td>
   <td><bean:write name="stat" property="lastTime"/></td>
   <td><bean:write name="stat" property="totalTime"/></td>
   <td><bean:write name="stat" property="measures"/></td>
   <td><bean:write name="stat" property="className"/></td>
  </tr>
</logic:iterate>

</table>
<br>
Generated @ <i><bean:write name="serverStats" property="serverName"/>@<bean:write name="serverStats" property="date"/></i><br>
Active sessions:&nbsp;<b><bean:write name="serverStats" property="sessions"/></b>&nbsp;(<bean:write name="serverStats" property="maxSessions"/>)<br>
Memory free: <b><bean:write name="serverStats" property="freeMemory"/></b>, total: <bean:write name="serverStats" property="totalMemory"/>, max: <bean:write name="serverStats" property="maxMemory"/>.<br>
Server up since: <bean:write name="serverStats" property="serverStartTime" ignore="true" scope="application"/>&nbsp(<bean:write name="serverStats" ignore="true" property="uptime"/> seconds)<br>
<br><br>
Load: 1Min: <bean:write name="machineStats" property="loadAvgLastMinute"/>, 5Min: <bean:write name="machineStats" property="loadAvgLast5Minutes"/>, 15Min <bean:write name="machineStats" property="loadAvgLast15Minutes"/><br>
Memory: <b>AV: <bean:write name="machineStats" property="availableMemory"/></b>, TO: <bean:write name="machineStats" property="totalMemory"/>, FR: <bean:write name="machineStats" property="freeMemory"/>, CA: <bean:write name="machineStats" property="cached"/>, BU: <bean:write name="machineStats" property="buffers"/><br>
Memory: <b>AV: <bean:write name="machineStats" property="availableMemoryFormatted"/></b>, TO: <bean:write name="machineStats" property="totalMemoryFormatted"/>, FR: <bean:write name="machineStats" property="freeMemoryFormatted"/>, CA: <bean:write name="machineStats" property="cachedFormatted"/>, BU: <bean:write name="machineStats" property="buffersFormatted"/><br>
