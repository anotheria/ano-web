<%@ page
	contentType="text/html;charset=iso-8859-1" session="true"
%><%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean"
%><%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html"
%><%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic"
%>
<HTML>
<HEAD>
<jsp:include page="/net/anotheria/cesaria/bsag/jsp/edit/Pragmas.jsp" flush="true"/>
<bean:message key="styles.cms.link"/>
<title>@Upload file</title>
</head>
<body topmargin="0" marginwidth="0" marginheight="0" onLoad="parent.stopState();">
<%--<logic:messagesPresent>
<table width="100%" border="0" cellspacing="0" cellpadding="0" bgcolor="#e2e2e2">
	<tr><td colspan="3" height="16">&nbsp;</td></tr>
	<tr>
		<td colspan="3" bgcolor="#FF0000"><img src="<bean:message key="emptyimage"/>" width="1" height="1"></td>
	</tr>
	<tr>
		<td bgcolor="#FF0000" width="1"><img src="<bean:message key="emptyimage"/>" width="1" height="1"></td>
		<td>
			<table width="100%" border="0" cellspacing="0" cellpadding="6">
				<html:messages id="msg" bundle="ds">
					<tr>
				  	<td><font color="#FF0000">### <%=msg%> ###</font></td>
				   </tr>
				 </html:messages>
			</table>
		</td>
		<td bgcolor="#FF0000" width="1"><img src="<bean:message key="emptyimage"/>" width="1" height="1"></td>
	</tr>
	<tr>
		<td colspan="3" bgcolor="#FF0000"><img src="<bean:message key="emptyimage"/>" width="1" height="1"></td>	
	</tr>
</table>
</logic:messagesPresent>
<logic:messagesNotPresent>--%>
<logic:equal name="ano-web.file.fileBean" property="valid" value="true">
<table  cellspacing="0" cellpadding="0" width=100% >
	<tr class="hellerblau">
		<td>
		Filename:&nbsp;
		<logic:equal name="ano-web.file.fileBean" property="filePresent" value="false">
		<bean:write name="ano-web.file.fileBean" property="name"/>
		</logic:equal>
		<logic:equal name="ano-web.file.fileBean" property="filePresent" value="true">
		<a target="_blank" href="/bsag/cms/showTmpFile"><bean:write name="ano-web.file.fileBean" property="name"/></a>
		</logic:equal>
		&nbsp;|&nbsp;
		Filesize:&nbsp;<bean:write name="ano-web.file.fileBean" property="size"/>
		&nbsp;|&nbsp;
		<bean:write name="ano-web.file.fileBean" property="message"/>
		</td>
    </tr>
	<tr class="hellerblau">
		<td colspan=5>&nbsp;</td>
    </tr>
</table>
</logic:equal>
<%--</logic:messagesNotPresent>--%>
</body>
</html>
