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
    <title>分页</title>
    <style type="text/css">
        .current {
            text-decoration: none;
            font-weight: bold;
            font-size: 20px;
            color: red;
            width: 30px;
            margin-left: 5px;
        }

        .other {
            text-decoration: none;
            width: 30px;
            margin-left: 5px;
        }

        .showfont {
            font-size: 13px;
            font-style: italic;
        }

    </style>
    <script type="text/javascript" src="js/jquery-1.8.3.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#firstpage").click(function () {
                $("#cp").val("1");
                splitform.submit();
            });

            $("#prepage").click(function () {
                if ($("#cp").val() > 1) {
                    $("#cp").val($("#cp").val() - 1);
                    splitform.submit();
                }
            });

            $("#nextpage").click(function () {
                if (parseInt($("#cp").val()) < parseInt($("#allpage").val())) {
                    $("#cp").val(parseInt($("#cp").val()) + 1);
                    splitform.submit();
                }
            });

            $("#lastpage").click(function () {
                if (parseInt($("#cp").val()) < parseInt($("#allpage").val())) {
                    $("#cp").val($("#allpage").val());
                    splitform.submit();
                }
            });

        });

        function gopage(mypage) {
            if (parseInt(mypage) >= 1 && parseInt(mypage) <= parseInt($("#allpage").val())) {
                $("#cp").val(mypage);
                splitform.submit();
            }
        }


    </script>
</head>
<body>
<form id="splitform" name="splitform" action="${param.myurl }" method="get">
    <input type="hidden" value="${cp }" name="cp" id="cp"/>
    <input type="hidden" value="${count }" name="count" id="count"/>
    <input type="hidden" value="${allpage }" name="allpage" id="allpage"/>


    <input type="button" id="firstpage" ${cp==1?"disabled":"" } value="首页"/>
    <input type="button" id="prepage" ${cp==1?"disabled":"" } value="上一页"/>
    <c:if test="${cp>3 }">
        <a href="javascript:void(0);" onclick="gopage('${cp-3}');" class="other">...</a>
    </c:if>
    <c:if test="${cp>2 }">
        <a href="javascript:void(0);" onclick="gopage('${cp-2}');" class="other">${cp-2 }</a>
    </c:if>
    <c:if test="${cp>1 }">
        <a href="javascript:void(0);" onclick="gopage('${cp-1}');" class="other">${cp-1 }</a>
    </c:if>
    <a href="javascript:void(0);" class="current">${cp }</a>
    <c:if test="${cp<allpage }">
        <a href="javascript:void(0);" onclick="gopage('${cp+1}');" class="other">${cp+1 }</a>
    </c:if>
    <c:if test="${cp+1<allpage }">
        <a href="javascript:void(0);" onclick="gopage('${cp+2}');" class="other">${cp+2 }</a>
    </c:if>
    <c:if test="${cp+2<allpage }">
        <a href="javascript:void(0);" onclick="gopage('${cp+3}');" class="other">...</a>
    </c:if>

    <input type="button" id="nextpage" ${cp==allpage?"disabled":"" } value="下一页"/>
    <input type="button" id="lastpage"  ${cp==allpage?"disabled":"" } value="尾页"/>

    <span class="showfont">
			当前是第<b>${cp }</b>页，共有<b>${count }</b>条数据，分为<b>${allpage }</b>页，每页显示
			<select name="ps" id="ps" onchange="gopage('1')">
				<option value="1" ${ps==1?"selected":"" } >1</option>
				<option value="3" ${ps==3?"selected":"" } >3</option>
				<option value="5" ${ps==5?"selected":"" } >5</option>
				<option value="10" ${ps==10?"selected":"" } >10</option>
			</select>
			行
		</span>
</form>
</body>
</html>