$(function () {
    var obj = sessionStorage.getItem("user");
    var user = JSON.parse(obj);
    if (user === null) {
        window.location.href = "/sign-in.html";
    }
    $("#myName").html("<span class='glyphicon glyphicon-user padding-right-small'" +
        "style = 'position:relative;top: 3px;' ></span >" + user.loginName + "<i class='fa fa-caret-down'></i>");
    var priList = user.role.priList;
    if (priList == null || priList.length === 0) {
        return;
    }
    var str = "";
    for (var i = 0; i < priList.length; i++) {
        var pri = priList[i];
        if (pri.parentid === 0) {
            var flag = "parent" + i;
            str += "<li><a href='#' data-target='." + flag + "' class='nav-header' data-toggle='collapse'>" +
                "<i class='fa fa-fw fa-dashboard'></i> " + pri.priName + "<i class='fa fa-collapse'></i></a></li>";
            str += "<li><ul class='" + flag + " nav nav-list collapse in'>";
            for (var j = 0; j < priList.length; j++) {
                var p = priList[j];
                if (p.parentid === pri.priid) {
                    str += "<li><a href='";
                    if (p.priUrl == null) {
                        str += "#" + "'><span class='fa fa-caret-right'></span>" + p.priName + "</a></li>"
                    } else {
                        str += p.priHtml + "'><span class='fa fa-caret-right'></span>" + p.priName + "</a></li>"
                    }

                }
            }
            str += "</ul></li>";
        }
    }
    $("#leftMenu").append($(str));

});