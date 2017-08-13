$(function () {
    var oTable = $('#table_server').dataTable(
        {
            "sPaginationType": "full_numbers", //分页风格，full_number会把所有页码显示出来（大概是，自己尝试）
            "sDom": "<'row-fluid inboxHeader'<'span6'<'dt_actions'>l><'span6'f>r>t<'row-fluid inboxFooter'<'span6'i><'span6'p>>", //待补充
            "iDisplayLength": 10,//每页显示10条数据
            "bAutoWidth": false,//宽度是否自动，感觉不好使的时候关掉试试
            "bLengthChange": false,
            "bFilter": false,

            "oLanguage": {//下面是一些汉语翻译
                "sSearch": "搜索",
                "sLengthMenu": "每页显示 _MENU_ 条记录",
                "sZeroRecords": "没有检索到数据",
                "sInfo": "显示 _START_ 至 _END_ 条 &nbsp;&nbsp;共 _TOTAL_ 条",
                "sInfoFiltered": "(筛选自 _MAX_ 条数据)",
                "sInfoEmtpy": "没有数据",
                "sProcessing": "正在加载数据...",
                // "sProcessing": "<img src='{{rootUrl}}global/img/ajaxLoader/loader01.gif' />", //这里是给服务器发请求后到等待时间显示的 加载gif
                "oPaginate":
                    {
                        "sFirst": "首页",
                        "sPrevious": "前一页",
                        "sNext": "后一页",
                        "sLast": "末页"
                    }
            },
            "bProcessing": true, //开启读取服务器数据时显示正在加载中……特别是大数据量的时候，开启此功能比较好
            "bServerSide": true, //开启服务器模式，使用服务器端处理配置datatable。注意：sAjaxSource参数也必须被给予为了给datatable源代码来获取所需的数据对于每个画。 这个翻译有点别扭。开启此模式后，你对datatables的每个操作 每页显示多少条记录、下一页、上一页、排序（表头）、搜索，这些都会传给服务器相应的值。
            "sAjaxSource": "uc/getUserSplit", //给服务器发请求的url
            "aoColumns": [//对应上面thead里面的序列字段名字和返回的json序列的key对应
                {data: "userId",},
                {data: "loginName",},
                {data: "realName",},
                {
                    //EMAIL
                    data: function (e) {
                        if (e.email) {
                            return e.email;
                        }
                        return "空";
                    }
                },
                {
                    //部门名字
                    data: function (e) {
                        if (e.dept.dname) {
                            return e.dept.dname;
                        }
                        return "空";
                    }
                },
                {
                    data: function (e) {
                        if(e.userStatus==0){
                            return "正常";
                        }else if(e.userStatus==1){
                            return "请假";
                        }else{
                            return "离职";
                        }
                    }
                },
                {
                    data: function (e) {//这里给最后一列返回一个操作列表
                        //e是得到的json数组中的一个item ，可以用于控制标签的属性。
                        return '<a class="btn btn-default btn-xs show-detail-json"><i class="icon-edit"></i>显示详细</a>';
                    }
                }
            ],
            // "aoColumnDefs": [//和aoColums类似，但他可以给指定列附近爱属性
            //     {"bSortable": false, "aTargets": [1, 3, 6, 7, 8, 9]},  //这句话意思是第1,3,6,7,8,9列（从0开始算） 不能排序
            //     {"bSearchable": false, "aTargets": [1, 2, 3, 4, 5, 6, 7, 8, 9]}, //bSearchable 这个属性表示是否可以全局搜索，其实在服务器端分页中是没用的
            // ],
            "aaSorting": [[2, "desc"]], //默认排序
            "fnRowCallback": function(nRow, aData, iDisplayIndex) {// 当创建了行，但还未绘制到屏幕上的时候调用，通常用于改变行的class风格
                if (aData.status == 1) {
                    $('td:eq(8)', nRow).html("<span class='text-error'>审核中</span>");
                } else if (aData.status == 4) {
                    $('td:eq(8)', nRow).html("<span class='text-error'>审核失败</span>");
                } else if (aData.active == 0) {
                    $('td:eq(8)', nRow).html("<span>隐藏</span>");
                } else {
                    $('td:eq(8)', nRow).html("<span class='text-success'>显示</span>");
                }
                $('td:eq(9)', nRow).html("<a href='' user_id='" + aData.user_id + "' class='ace_detail'>详情</a>");
                if (aData.status != 1 && aData.status != 4 && aData.active == 0) {
                    $("<a class='change_ace_status'>显示</a>").appendTo($('td:eq(9)', nRow));
                } else if (aData.status != 1 && aData.status != 4 && aData.active == 1) {
                    $("<a class='change_ace_status'>隐藏</a>").appendTo($('td:eq(9)', nRow));
                }
                return nRow;
            },
            "fnInitComplete": function(oSettings, json) { //表格初始化完成后调用 在这里和服务器分页没关系可以忽略
                $("input[aria-controls='DataTables_Table_0']").attr("placeHolder", "请输入高手用户名");
            }

        }
    );
});
