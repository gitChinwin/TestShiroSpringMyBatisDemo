$(function () {

    var t = $("#table_server").bootstrapTable({
        url: 'http://localhost:8080/rc/getRoleSplit',
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
        singleSelect: false,//设置True 将禁止多选
        search: false, //显示搜索框
        data_local: "zh-US",//表格汉化
        sidePagination: "server", //服务端处理分页
        queryParams: function (params) {//自定义参数，这里的参数是传给后台的，我这是是分页用的
            return {//这里的params是table提供的
                cp: params.offset,//从数据库第几条记录开始
                ps: params.limit//找多少条
            };
        },
        idField: "roleid",//指定主键列
        columns: [
            {
                title: '#',//表的列名
                field: 'roleid',//json数据中rows数组中的属性名
                align: 'center',//水平居中
                formatter: function (value, row, index) {
                    return index + 1;
                }
            },
            {
                title: '岗位中文名称',
                field: 'roleCn',
                align: 'center'
            },
            {
                title: '岗位英文名称',
                field: 'roleEn',
                align: 'center'
            },
            {
                //EMAIL
                title: '岗位描述',
                field: 'roleDesc',
                align: 'center'
            },
            {
                title: '状态',
                field: 'roleStatus',
                align: 'center',
                formatter: function (value, row, index) {//自定义显示，这三个参数分别是：value该行的属性，row该行记录，index该行下标
                    return row.roleStatus == 0 ? "正常" : "异常";
                }

            },
            {
                title: '操作',
                field: '',
                align: 'center',
                formatter: function (value, row, index) {//自定义显示可以写标签哦~
                    var stringify = JSON.stringify(row);
                    return "<button type='button' class='btn-primary' data-toggle='modal' data-target='#myModal' " +
                        "data-row='" + stringify + "'>" +
                        "操作" +
                        "</button>";
                }
            }

        ]
    });


    t.on('load-success.bs.table', function (data) {//table加载成功后的监听函数
        console.log("load success");
        $(".pull-right").css("display", "block");
    });
    $('#myModal').on('show.bs.modal', function (e) {//模态框弹出时触发
        var button = $(e.relatedTarget); // 触发此事件的按钮
        var row = button.data('row'); // 解析出whatever内容
        $("#changeId").val(row.roleid);
        $("#changeRoleCn").val(row.roleCn);
        $("#changeRoleEn").val(row.roleEn);
        var $changeStatus = $("#changeRoleStatus option");
        for (var i = 0; i < $changeStatus.length; i++) {
            var $op = $changeStatus[i];
            var value = $op.value;
            if (value == row.roleStatus) {
                $op.setAttribute("selected", "selected");//此处用原生js设置属性= =，不知道为啥jQuery会报错
            }
        }
        getPris(row);//调用getRoles()
    });

});


function getPris(row) {
    //去sessionStorage查是否有用户，若无，跳转登录页
    var item = sessionStorage.getItem("user");
    var user = JSON.parse(item);
    var current_uid;
    if (user == null || (current_uid = user.userId) == null) {
        window.location.href = "sign-in.html";
    }

    var setting = {
        url: "rc/preChangePri",
        type: "post",
        data: "",
        async: false,
        success: function (dataInfo) {
            var checkableTree = $('#tree').treeview({
                data: dataInfo,
                // color:'#ee9a00',
                showIcon: false,
                showCheckbox: true,
                onNodeChecked: function (event, data) {
                    //选中父节点，则自动选择子节点
                    if (data.nodes != null) {
                        var arrayInfo = data.nodes;
                        for (var i = 0; i < arrayInfo.length; i++) {
                            // $('#treeview1').treeview('checkNode', [ arrayInfo[i].nodeId, { silent: true } ]);
                            $('#tree').treeview('toggleNodeChecked', [arrayInfo[i].nodeId, {silent: true}]);
                        }
                    }
                },
                onNodeUnchecked: function (event, data) {
                    /* $('#checkable-output').prepend('<p>' + node.text + ' was unchecked</p>');*/
                    //取消选中父节点，则自动取消选择子节点
                    if (data.nodes != null) {
                        var arrayInfo = data.nodes;
                        for (var i = 0; i < arrayInfo.length; i++) {
                            // $('#treeview1').treeview('checkNode', [ arrayInfo[i].nodeId, { silent: true } ]);
                            $('#treeview-checkable').treeview('toggleNodeChecked', [arrayInfo[i].nodeId, {silent: true}]);
                        }
                    }
                }

            });
            // Check/uncheck all
            $('#btn-check-all').on('click', function (e) {
                checkableTree.treeview('checkAll', {silent: $('#chk-check-silent').is(':checked')});
            });

            $('#btn-uncheck-all').on('click', function (e) {
                checkableTree.treeview('uncheckAll', {silent: $('#chk-check-silent').is(':checked')});
            });

            // /*对比两个树 如果菜单权限已经存在 选中checkbox*/
            // $('#modifyRoleButton').click(function () {
            //
            //     var li = 0;
            //     var m = 0
            //     var menuTreeList = new Array();
            //
            //     $('#treeMenu  li').each(function () {
            //         menuTreeList[m] = $(this).attr('data-nodeid');// 得到的是字符串数组
            //         m++;
            //     });
            //     for (var n = 0; n < menuTreeList.length; n++) {
            //         var aa = parseInt(menuTreeList[n]);// 这里必须转换成整型 ，aa必须是整型
            //         checkableTree.treeview('toggleNodeChecked', [aa, {silent: true}]);// 选中checkbox
            //     }
            //
            // });
        },
        error: function (res) {
            alert("树形结构加载失败！")
        }
    };
    $.ajax(setting);




        var pl = row.priList;
        var $lis = $("#tree li");
        for (var i = 0; i < $lis.length; i++) {
            var $li = $lis[i];
            var attribute = $li.getAttribute("data-nodeid");
            var text = $li.innerText;
            for (var j = 0; j < pl.length; j++) {
                var p = pl[j];
                if (p.priName == text) {
                    var node = $('#tree').treeview('getNode', attribute);
                    $('#tree').treeview('checkNode', [node, {silent: true}]);
                }
            }
        }


}

//模态框确定按钮事件触发函数
function changeRole() {

    /*更新完提交树*/
    $('#saveRole').click(function () {
        var menuList = new Array();
        var li = 0;
        $('#treeview-checkable  li').each(function () {
            if ($(this).hasClass("list-group-item node-treeview-checkable node-checked")) {
                menuList[li] = $(this).attr('data-nodeid') + 1;
                li++;
            }
        });

        var data = {roleId: roleId, menuList: menuList};
        $.ajax({
            type: "post",
            url: "/rc/updateRole",
            data: JSON.stringify(data),
            dataType: "json",
            contentType: 'application/json; charset=utf-8',
            traditional: true,
            success: function (data) {
                alert(data);
            }
        });
    });
}


