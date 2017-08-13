<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
	String basepath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basepath %>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>中部页面</title>
<style type="text/css">
	.left{
		float: left;
		border: solid 1px gray;
		width: 15%;
		height: 100%;
	}
	
	.right{
		border: solid 1px gray;
		width: 100%;
		height: 100%;
	}
</style>
</head>
<body>
	<div>
		<div class="left">
			<iframe src="pages/center/left.jsp" width="100%" height="500" frameborder="0"  ></iframe>
		</div>
		<div class="right">
			<iframe name="rightframe" src="pages/center/right.jsp" width="83%" height="500" frameborder="0" ></iframe>
		</div>
	</div>
</body>
</html>