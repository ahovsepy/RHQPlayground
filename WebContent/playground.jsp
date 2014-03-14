<%@ page language="java" contentType="text/html; charset=US-ASCII"
    pageEncoding="US-ASCII"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map.Entry"%>
<%@page import="java.util.HashMap"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
<title>RHQ Playground</title>
</head>
<script src="http://code.jquery.com/jquery-latest.js">   
</script>
<script src="js/bootstrap.min.js"></script>
<link href="css/bootstrap.min.css" rel="stylesheet">
<script>

function getSampleCode(name) {
	$.get('sample', { name : name }, function(responseText) {
		<!-- $('#sample').html(responseText);	-->
		document.getElementById("sample").value = responseText;

	});
}

function runSample() {
	
	var sample =  document.getElementById("sample").value ;
	sample = sample.trim();
	if(sample == "" || sample == null){
	alert("No sample to run");	
	}
	else {
		$("#Run").attr("disabled", true);
		$.post('sample', { sample : sample }, function(responseText) {
		    document.getElementById("sample").value = responseText;
		    $("#Run").removeAttr("disabled");
		});
		
	}
}

</script>
<body>
<%
//allow access only if session exists
String user = null;
if(session.getAttribute("user") == null){
    response.sendRedirect("index.jsp");
}else user = (String) session.getAttribute("user");
String userName = null;
String sessionID = null;
Cookie[] cookies = request.getCookies();
if(cookies !=null){
for(Cookie cookie : cookies){
    if(cookie.getName().equals("user")) userName = cookie.getValue();
    if(cookie.getName().equals("JSESSIONID")) sessionID = cookie.getValue();
}
}
%>
<form action="logout" class="" method="post">
<table  width="580" border="1">
<tr  border="1">
<td width="127" height="84"  border="1" ><img src="jboss_rhq_logo.jpg" width="79" height="80" alt="RHQ logo"/></td> 
<td class="pull-right" ><input type="submit"  class="btn btn-primary" name="logout" id="logout" value="logout"> </td>
<tr>


	<tr  border="1"><td height="256" border="1">
<ul class="nav">	
<%
HashMap<String,String> cliSamples = (HashMap<String,String>) session.getAttribute("cliSamples");
System.out.println(cliSamples);
if(cliSamples != null){
for (Entry<String, String> map : cliSamples.entrySet()) {
	String name = map.getKey();
	%>
	<li style="cursor: pointer; color:blue" class="nav" onclick="getSampleCode('<%=name%>',this)" ><%=name%></li> <br>
	<%		}
}
%>
</ul>
</td>
<td height="256" border="1">

<textarea class="form-control" name="sample"  id="sample" value="">
<%
if(session.getAttribute("sample") != null && !session.getAttribute("sample").equals("")) { %>
<% String sample = (String)  session.getAttribute("sample"); %>
<%=sample%>
<%} %>
</textarea>

</td></tr>
<tr>
<td></td>
<td class="pull-right" >
<input type="button"  class="btn btn-primary" name="Run" id="Run" value="Run" onclick="runSample(); return false;"  >
<input type="reset" class="btn btn-primary" name="reset" id="reset" value="Clean" onclick="javascript:eraseText();" >
</td>
</tr>
</table>
</form>
<script>
function eraseText() {
    document.getElementById("sample").value = "";
}
</script>
</body>
</html>