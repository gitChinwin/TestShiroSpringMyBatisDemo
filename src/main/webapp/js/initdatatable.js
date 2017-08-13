$(function () {
    // var tablePrefix = "#table_server_";
    $("#table_server").dataTable({
        serverSide: true,//分页，取数据等等的都放到服务端去
        processing: true,//载入数据的时候是否显示“载入中”
        pageLength: 5,//首次加载的数据条数
        ordering: false,//排序操作在服务端进行，所以可以关了。
        // paging: true,
        searching:false,
        ajax: {//类似jquery的ajax参数，基本都可以用。
            type: "post",//后台指定了方式，默认get，外加datatable默认构造的参数很长，有可能超过get的最大长度。
            url: "uc/getUserSplit",
            dataSrc: "data",//默认data，也可以写其他的，格式化table的时候取里面的数据
            data: function (d) {//d 是原始的发送给服务器的数据，默认很长。
                var param = {};//因为服务端排序，可以新建一个参数对象
                param.cp = d.start ;//开始的序号
                param.ps = d.length;//要取的数据的
                // var formData = $("#filter_form").serializeArray();//把form里面的数据序列化成数组
                // formData.forEach(function (e) {
                //     param[e.name] = e.value;
                // });
                return param;//自定义需要传递的参数。
            }
        },
        columns: [//对应上面thead里面的序列字段名字和返回的json序列的key对应
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
            // {
            //     //Student 没有hireDate
            //     data: function (e) {
            //         if (e.hireDate) {//默认是/Date(794851200000)/格式，需要显示成年月日方式
            //             return new Date(Number(e.hireDate.replace(/\D/g, ''))).toLocaleDateString();
            //         }
            //         return "空";
            //     }
            // },
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
        // initComplete: function (setting, json) {
        //     //初始化完成之后替换原先的搜索框。
        //     //本来想把form标签放到hidden_filter 里面，因为事件绑定的缘故，还是拿出来。
        //     $(tablePrefix + "filter").html("<label>Search:<input type='search' class='form-control input-sm' placeholder='' aria-controls='table_server'></label>");
        // },
        language: {
            lengthMenu: '<select class="form-control input-xsmall">' + '<option value="5">5</option>' + '<option value="10">10</option>' + '<option value="20">20</option>' + '<option value="30">30</option>' + '<option value="40">40</option>' + '<option value="50">50</option>' + '</select>条记录',//左上角的分页大小显示。
            processing: "载入中",//处理页面数据的时候的显示
            paginate: {//分页的样式文本内容。
                previous: "上一页",
                next: "下一页",
                first: "第一页",
                last: "最后一页"
            },

            zeroRecords: "没有内容",//table tbody内容为空时，tbody的内容。
            //下面三者构成了总体的左下角的内容。
            info: "总共_PAGES_ 页，显示第_START_ 到第 _END_ ，筛选之后得到 _TOTAL_ 条，初始_MAX_ 条 ",//左下角的信息显示，大写的词为关键字。
            infoEmpty: "0条记录",//筛选为空时左下角的显示。
            infoFiltered: ""//筛选之后的左下角筛选提示(另一个是分页信息显示，在上面的info中已经设置，所以可以不显示)，
        }
    });
    // $("#table_server_filter input[type=search]").css({ width: "auto" });//右上角的默认搜索文本框，不写这个就超出去了。
});
// $(document).on("submit", "#filter_form", function () {
//     return false;
// });
$(document).on("click", "#go_search", function () {
    $("#table_server").DataTable().draw();//点搜索重新绘制table。
});
$(document).on("click", ".show-detail-json", function () {//取出当前行的数据
    alert(JSON.stringify($("#table_server").DataTable().row($(this).parents("tr")).data()));
});
// function indexFormatter(value, row, index) {
//     return index+1;
// }
// $(document).on('order.dt search.dt',
//     function() {
//         t.column(0, {
//             "search": 'applied',
//             "order": 'applied'
//         }).nodes().each(function(cell, i) {
//             cell.innerHTML = i + 1;
//         });
//     }).draw();