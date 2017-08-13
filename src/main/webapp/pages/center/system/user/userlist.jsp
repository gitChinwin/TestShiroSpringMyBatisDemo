<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
    String path = request.getContextPath();
    String basepath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <base href="<%=basepath %>"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>用户列表页面</title>
    <script type="text/javascript" src="js/jquery-1.8.3.js"></script>
    <script type="text/javascript">
        function openDialog(uid) {

            window.parent.parent.parent.changeRoleeee(uid);
        }
    </script>
</head>
<body>
<h4>用户列表</h4>
<table border="1" cellspacing="0" width="100%">
    <tr>
        <th>选择</th>
        <th>账号</th>
        <th>真实姓名</th>
        <th>邮箱</th>
        <th>部门</th>
        <th>状态</th>
        <th>操作</th>
    </tr>
    <c:forEach items="${userlist }" var="ul">
        <tr>
            <td><input type="checkbox" value="${ul.userId }" name="ids"/></td>
            <td>${ul.loginName }</td>
            <td>${ul.realName }</td>
            <td>${ul.email }</td>
            <td>${ul.dept.dname }</td>
            <td>${ul.userStatus=='0'?"正常":(ul.userStatus=='1'?"请假":"离职") }</td>
            <td>
                <a href="javascript:void(0);" onclick="openDialog(${ul.userId })">角色变更</a>
            </td>
        </tr>
    </c:forEach>
</table>

<jsp:include page="/pages/split.jsp">
    <jsp:param value="uc/getUserSplit" name="myurl"/>
</jsp:include>


</body>
</html>