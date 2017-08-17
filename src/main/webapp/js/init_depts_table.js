$(function () {

    var t = $("#table_server").bootstrapTable({
        url: 'http://localhost:8080/dc/getDeptSplit',
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
                cp: parseInt(params.offset),//从数据库第几条记录开始
                ps: parseInt(params.limit)//找多少条
            };
        },
        idField: "deptno",//指定主键列
        columns: [
            {
                title: '#',//表的列名
                field: 'deptno',//json数据中rows数组中的属性名
                align: 'center',//水平居中
                formatter: function (value, row, index) {
                    return index + 1;
                }
            },
            {
                title: '部门名称',
                field: 'dname',
                align: 'center'
            },
            {
                title: '部门描述',
                field: 'deptDesc',
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.deptDesc == null || row.deptDesc == "") {
                        return "空";
                    }
                    return row.deptDesc;
                }
            },
            {
                title: '所属上级部门',
                field: 'parent.deptno',
                align: 'center',
                formatter: function (value, row, index) {
                    if (row.parentid == 0) {
                        return "空";
                    }
                    return row.parent.dname;
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
        $("#changedDeptNo").val(row.deptno);
        $("#changeDeptName").val(row.dname);
        $("#changeDeptDesc").val(row.depeDesc);
        getDepts(row.parentid);//调用getRoles()
    });

});

//模态框弹出时事件触发时进入此函数
function getDepts(parentid) {
    var item = sessionStorage.getItem("user");
    var user = JSON.parse(item);
    var current_uid;
    if (user == null || (current_uid = user.userId) == null) {
        window.location.href = "sign-in.html";
        $('#myModal').modal('hide');
    }
    if (parentid != 0) {
        $("#deptSelect").prop("disabled", "");
        initParentSelect(parentid, $("#deptSelect"));
    } else {
        $("#deptSelect").empty();
        $("#deptSelect").append($("<option value='0'>空</option>"));
        $("#deptSelect").prop("disabled", "disabled");
    }
}

//初始化下拉框
function initParentSelect(parentid, selector) {
    var setting = {
        url: "dc/preChangeDept",
        type: "post",
        data: "",
        success: function (data) {
            var deptno = $("#changedDeptNo").val();
            if (data.code == 0) {
                alert("服务器跑到火星啦！");
                return;
            }
            var deptList = data.result;
            var str = "<option value='0'>空</option>";

            for (var i = 0; i < deptList.length; i++) {
                var d = deptList[i];
                var selected = "";
                var icss = "";
                var tip = "";
                if (parseInt(d.deptno) == parseInt(deptno)) {
                    continue;
                }
                if (parentid != null && parseInt(d.deptno) === parseInt(parentid)) {
                    selected = "selected";
                    icss = " style='color: red' ";
                    tip = "(当前所属上级部门)"
                }
                str += "<option value='" + d.deptno + "' " + selected + " " + icss + ">" + d.dname + tip + "</option>";
            }

            $(selector).empty();
            $(selector).append($(str));
        },
        error: function (res) {
            alert("服务器跑到火星啦！");
        }
    };
    $.ajax(setting);
}


//修改部门信息模态框确定按钮事件触发函数
function changeDept() {

    var serialize = $("#changeDeptForm").serialize();
    var setting = {
        url: "dc/updateDept",
        type: "post",
        data: serialize,
        success: function (data) {
            if (data.code == 0) {
                $('#myModal').modal('hide');
                showTips(false, data.msg, $("#table_server"));
            } else {
                showTips(true, data.msg, $("#table_server"));
                $('#myModal').modal('hide');
                setTimeout(function () {
                    window.location.reload();
                }, 1000)
            }
        },
        error: function (res) {
            showTips(false, res.code, $("#table_server"));
            $('#myModal').modal('hide');
        }
    };

    $.ajax(setting);
}

//弹框提示逻辑
function showTips(status, msg, parent) {
    var $tip = null;
    if (status) {
        $tip = $("<div  class=\"alert alert-success alert-dismissable\" style='position: fixed;margin-left: 30%'> <a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>" + msg + "</div>").prependTo(parent);
    } else {
        $tip = $("<div  class=\"alert alert-danger alert-dismissable  fade in\" style='position: fixed;margin-left: 30%'> <a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>" + msg + "</div>").prependTo(parent);
    }
    setTimeout(function () {
        this.fadeOut();
    }.bind($tip), 3000)

}

//新增部门信息模态框弹出时出发的事件
$(function () {
    $("#addDeptModal").on('show.bs.modal', function () {
        initParentSelect(null, $("#addDeptSelect"));
    });
});


//新增部门信息的保存按钮的点击的事件
function addDept() {

    if ($("#addDeptName").val() == null || $("#addDeptName").val() == "") {
        showTips(false, "部门名称不能为空", $("#table_server"));
        return false;
    }

    var serialize = $("#addDeptForm").serialize();
    $.ajax({
        url: "dc/addDept",
        type: "post",
        data: serialize,
        success: function (data) {
            if (data.code == 0) {
                showTips(false, data.msg, $("#table_server"));
            } else {
                showTips(true, data.msg, $("#table_server"));
                $('#addDeptModal').modal('hide');
                setTimeout(function () {
                    window.location.reload();
                }, 1000)
            }
        },
        error: function (res) {
            $('#addDeptModal').modal('hide');
            showTips(false, res.code, $("#table_server"));
        }
    });
}

