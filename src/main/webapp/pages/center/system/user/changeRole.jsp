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
    <title>Title</title>
    <script type="text/javascript" src="js/jquery-1.8.3.js"></script>
    <script type="text/javascript" src="js/lhgdialog/lhgdialog.js"></script>
    <script type="text/javascript">
        var DG = frameElement.lhgDG;
        DG.addBtn("makesure","确定",makesure);
        function makesure(){
            $.post("uc/changerole",{"userid":$("#userid").val(),"roleid":$("#roleid").val()},function(data){
                if(data=="true"){
                    alert("修改成功!!!");
                    //DG.curWin.location.reload();
                    DG.cancel();
                }else{
                    alert("修改失败,请重新操作或者联系管理员");
                    DG.cancel();
                }
            },"text");
            //DG.curWin.location.reload();
            //DG.cancel();
        }
    </script>
</head>
<body>

    <form action="" method="">
        <input type="hidden" name="userid" value="">
        账号：<input type="text" value="" disabled/>
        岗位：<select id="mySelect">
        <option value=""></option>
    </select>
    </form>

</body>
</html>
