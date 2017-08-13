<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basepath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basepath %>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>顶部页面</title>
</head>
<body >
	<div>
		<ul>
			<c:forEach items="${sessionScope.users.role.priList }" var="p" >
				<c:if test="${p.parentid==0 }">
					<li>
						<a href="${p.priUrl }" target="rightframe">${p.priName }</a>
						<ul>
							<c:forEach items="${sessionScope.users.role.priList }" var="child" >
							<c:if test="${child.parentid==p.priid }">
								<li><a href="${child.priUrl }" target="rightframe">${child.priName }</a></li>
							</c:if>
							</c:forEach>
						</ul>
					</li>
					
				</c:if>
			</c:forEach>
			
			
		</ul>
	
	</div>
</body>
</html>