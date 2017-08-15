$(function () {

    var t = $("#table_server").bootstrapTable({
        url: 'http://localhost:8080/uc/getUserSplit',
        method: 'get',
        dataType: "json",
        striped: true,//设置为 true 会有隔行变色效果
        undefinedText: "空",//当数据为 undefined 时显示的字符
        pagination: true, //分页
        // paginationLoop:true,//设置为 true 启用分页条无限循环的功能。
        showToggle: "true",//是否显示 切换试图（table/card）按钮
        showColumns: "true",//是否显示 内容列下拉框
        pageNumber: 1,//如果设置了分页，首页页码
        // showPaginationSwitch:true,//是否显示 数据条数选择框
        pageSize: 5,//如果设置了分页，页面数据条数
        pageList: [5, 10, 20, 40],	//如果设置了分页，设置可供选择的页面数据条数。设置为All 则显示所有记录。
        paginationPreText: '&lsaquo;',//指定分页条中上一页按钮的图标或文字,这里是<
        paginationNextText: '&rsaquo;',//指定分页条中下一页按钮的图标或文字,这里是>
        // singleSelect: false,//设置True 将禁止多选
        search: false, //显示搜索框
        data_local: "zh-CN",//表格汉化
        sidePagination: "server", //服务端处理分页
        queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的
            return {//这里的params是table提供的
                cp: params.offset,//从数据库第几条记录开始
                ps: params.limit//找多少条
            };
        },
        idField: "userId",//指定主键列
        columns: [
            {
                title: '#',//表的列名
                field: 'userId',//json数据中rows数组中的属性名
                align: 'center',//水平居中
                formatter: function (value, row, index) {
                    return index + 1;
                }
            },
            {
                title: '账号',
                field: 'loginName',
                align: 'center'
            },
            {
                title: '真实姓名',
                field: 'realName',
                align: 'center'
            },
            {
                //EMAIL
                title: '邮箱',
                field: 'email',
                align: 'center'
            },
            {
                //部门名字
                title: '部门',
                field: 'dept.dname',//可以直接取到属性里面的属性，赞
                align: 'center'
            },
            {
                title: '状态',
                field: 'userStatus',
                align: 'center',
                formatter: function (value, row, index) {//自定义显示，这三个参数分别是：value该行的属性，row该行记录，index该行下标
                    return row.userStatus == 0 ? "正常" : row.userStatus == 1 ? "请假" : "离职";
                }

            },
            {
                title: '操作',
                field: 'userId',
                align: 'center',
                formatter: function (value, row, index) {//自定义显示可以写标签哦~
                    var stringify = JSON.stringify(row);
                    return "<button type='button' class='btn-primary' data-toggle='modal' data-target='#myModal' " +
                        "data-row='" + stringify + " '>" +
                        "操作" +
                        "</button>";
                }
            }

        ]
    });


    t.on('load-success.bs.table', function (data) {//table加载成功后的监听函数
        // console.log("load success");
        $(".pull-right").css("display", "block");
    });
    $('#myModal').on('show.bs.modal', function (e) {
        var button = $(e.relatedTarget); // 触发事件的按钮
        var paaaa = button.data("row"); // 解析出whatever内容
        var row = JSON.parse(paaaa);
        //为模态框的input赋值
        $("#changedUserId").val(row.userId);
        $("#changeLoginName").val(row.loginName);
        $("#changeRealName").val(row.realName);
        $("#changeEmail").val(row.email);
        // 设置职工状态下拉框，并初始化选中值
        var $changeStatus = $("#changeStatus option");
        for (var i = 0; i < $changeStatus.length; i++) {
            var $op = $changeStatus[i];
            var value = $op.value;
            if (value == row.userStatus) {
                $op.setAttribute("selected", "selected");//此处用原生js设置属性= =，不知道为啥jQuery会报错
            }
        }
        if (row.dept != null && row.dept.dname != null) {
            $("#changeDeptno").val(row.dept.dname);
        }else{
            $("#changeDeptno").val("空");
        }

        getRoles(row.roleId);//调用getRoles()去后台查岗位
    });

});

//模态框弹出时事件触发时进入此函数
function getRoles(rid) {
    var item = sessionStorage.getItem("user");
    var user = JSON.parse(item);
    var current_uid;
    if (user == null || (current_uid = user.userId) == null) {
        window.location.href = "sign-in.html";
    }
    var setting = {
        url: "uc/preChangeRole",
        type: "post",
        data: "",
        success: function (data) {

            if (data.code == 0) {
                alert("服务器跑到火星啦！");
                return;
            }
            var roleList = data.result;
            var str = "";
            for (var i = 0; i < roleList.length; i++) {
                var role = roleList[i];
                var selected = "";
                if (parseInt(role.roleid) === parseInt(rid)) {
                    selected = "selected"
                }
                str += "<option value='" + role.roleid + "'  " + selected + ">" + role.roleCn + "</option>";
            }
            $("#roleSelect").empty();
            $("#roleSelect").append($(str));
        },
        error: function (res) {
            alert("服务器跑到火星啦！");
        }
    };
    $.ajax(setting);
}

//模态框确定按钮事件触发函数
function changeUser() {

    var serialize = $("#changeRoleForm").serialize();
    var uid = $("#changedUserId").val();
    var rid = $("#roleSelect option[selected]").val();
    var setting = {
        url: "uc/changeRole",
        type: "post",
        data: serialize,
        success: function (data) {
            if (data.code == 0) {
                alert(data.msg);
                $('#myModal').modal('hide');
                return;
            }
            alert(data.msg);
            $('#myModal').modal('hide');
            window.setInterval(1);
            window.location.reload();
        },
        error: function () {
            alert("服务器跑到火星啦！");
            $('#myModal').modal('hide');
        }
    }

    $.ajax(setting);
}