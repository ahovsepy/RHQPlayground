<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>AJAX calls using Jquery in Servlet</title>
        <script src="http://code.jquery.com/jquery-latest.js">   
        </script>
<script type="text/javascript">
	function test(name) {
		$.get('main', {name : name }, function(responseText) {
			$('#sample').html(responseText);

		});

	}
</script>

</head>
<body>
<form id="form1">
<h1>AJAX Demo using Jquery in JSP and Servlet</h1>
Enter your Name:
<!--  <input type="text" id="user"/> -->
<!-- <input type="button" id="submit" value="Ajax Submit"/> -->
<li style="cursor: pointer;" class="submit" onclick="test('usingResProxiesExamples.js',this)" >usingResProxiesExamples.js</li>
<li style="cursor: pointer;" class="submit" onclick="test('ackAlertsForPlatform.js', this)" >ackAlertsForPlatform.js</li> 
<br/>
<textarea id="sample">
</textarea>
</form>
</body>
</html>