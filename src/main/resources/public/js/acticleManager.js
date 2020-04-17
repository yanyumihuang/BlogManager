/**
 * @author sikunliang
 * @date 2020/3/21
 * @Description
 */
let oldItroduction = '';
let switchFlag=false;
let  option="";
let flag=false;
let resultda;
let addArt="";
let articlesId="";
/*layui.use(['element'],function () {
    element.on('tab(demo)',function (data) {
        layer.msg("文章摘要列表")
    });
    element.on('tabDelete(demo)', function(){
        $('#articlesTable').next('.layui-table-view').remove()
    });
});*/
//查询文章分类类别
function categoryQuerys() {
    let result="";
    $.ajax("/queryCategory",{
        type:"get",
        async:false,
        headers:{
            'token':token
        },
        success:function (data) {
             result=data.resultLists;
             if (result.length!=0) {
                 for (let i = 0; i < result.length; i++) {
                     option += '<option value=' + result[i].id + '>' + result[i].category + '</option>'
                 }
             }
             else {
                 option='<option value="">请先增加分类再进行选择</option>'
             }
        },
        error:function (statue) {
            if (statue.status===700) {
                window.location.href = '/html/noAccess.html'
            }
        }
    });
    resultda=result;
    return result
}
//出现文章摘要页面
function articleQuery() {
    layui.use(['table','jquery','form','element'],function () {
        let table=layui.table;
        let form=layui.form;
        let element=layui.element;
        $=layui.jquery;
        //加载tab标签页
        if($("li[lay-id='1']").length==0) {
            element.tabAdd('demo', {
                title: '文章摘要列表'
                , content: '<table id=\"articlesTable\" lay-filter=\"articlesList\"></table>' //支持传入html
                , id: '1'
            });
            element.render('tab', 'demo');

            // 渲染表格
            table.render({
                elem: "#articlesTable",
                url: "/queryArticles",
                height: 500,
                headers: {
                    'token': token
                },
                request: {
                    pageName: 'pageNum'
                },
                response: {
                    statusCode: 200,
                    msgName: 'message',
                    dataName: 'resultLists'
                },
                page: true,
                cols: [
                    [
                        {field: 'ID', title: "id", hide: true},
                        {field: 'TITLE', title: "标题", width: 300, align: 'center'},
                        {field: 'AUTHOR', title: "作者", width: 150, align: 'center'},
                        {
                            field: 'CATEGORY',
                            title: "分类",
                            width: 120,
                            align: 'center',
                            event: "focu",
                            templet: '#switchCategory'
                        },
                        {
                            field: 'INTRODUCTION',
                            title: "介绍",
                            width: 400,
                            edit: 'text',
                            align: 'center',
                            event: 'signEdit'
                        },
                        {field: 'CREATEDATE', title: "上传时间", width: 120, sort: true, align: 'center'},
                        {field: 'MODIFYDATE', title: "最后修改时间", width: 150, sort: true, align: 'center'},
                        {
                            field: 'PRIVATE',
                            title: "私密性",
                            width: 80,
                            sort: true,
                            align: 'center',
                            templet: '#switchTpl',
                            event: "focu",
                            unresize: true
                        },
                    ]
                ],
                //表格渲染完成后给INTRODUCTION添加下拉框
                done(res, curr, count) {
                    tableData = res.resultLists;

                    if (option == "") {
                        resultda = categoryQuerys();
                    }
                    $("select[name='Category']").append(option);
                    layui.each($("select[name='Category']"), function (index, item) {
                        var elem = $(item);
                        let value='';
                        for (let i=0;i<resultda.length;i++){
                            if (resultda[i].category==tableData[index].CATEGORY){
                                value=resultda[i].id;
                                elem.val(value).parents('div.layui-table-cell').css('overflow', 'visible');
                                break;
                            }
                        }
                    });
                    form.render('select');
                },
            });
            //私密性改变的监控
            form.on('switch(switchPri)', function (obj) {
                let value=obj.value;
                if(value=="是"){
                    value='1'
                }
                else{
                    value='0'
                }
                let id = articlesId;
                $.ajax(
                    "/updateArticles",{
                        type:'post',
                        data:{'serret': value, "id": id},
                        headers:{
                            'token':token
                        },
                        success:function (data) {
                        if (data.code == '200') {
                            layer.msg('保存成功', {icon: 1});
                            if (value=="1") {
                                $("#ser").attr("value", "否")
                            }
                            else {
                                $("#ser").attr("value", "是")
                            }
                        } else {
                            layer.msg('出了点小问题，请稍后再试', {icon: 5});
                        }
                    },
                     error:function (statue) {
            if (statue.status===700) {
                window.location.href = '/html/noAccess.html'
            }
        }

                    })
            });
            //下拉框改变的监听
            form.on('select(Category)', function (obj) {
                let value=obj.value;
                let id = articlesId;
                $.ajax(
                    "/updateArticles",{
                        type:'post',
                        data:{'category': value, "id": id},
                        headers:{
                            'token':token
                        },
                        success:function (data) {
                            if (data.code =='200') {
                                layer.msg('保存成功', {icon: 1});
                            } else {
                                layer.msg('出了点小问题，请稍后再试', {icon: 5});
                            }
                        },
                         error:function (statue) {
            if (statue.status===700) {
                window.location.href = '/html/lognoAccess.html'
            }
        }})
            });
            //自定义事件的监听
            table.on('tool(articlesList)', function (obj) {
                let data = obj.data;
                switch (obj.event) {
                    case "signEdit":
                        oldItroduction = data.INTRODUCTION;
                        break;
                    case "focu":
                        articlesId=data.ID;
                        break
                }
            });
            //监听编辑事件
            table.on('edit(articlesList)', function (obj) {
                layui.use("layer", function () {
                    layer.confirm('确定保存吗？', {
                        btn: ['保存', '取消'] //按钮
                    }, function () {
                        let value = obj.value;
                        let id = obj.data.ID;
                        let fields = obj.field+"";
                        $.ajax(
                            "/updateArticles",{
                             type:'post',
                            data:{'introduction': value, "id": id},
                                headers:{
                                    'token':token
                                },
                            success:function (data) {
                                if (data.code =='200') {
                                    layer.msg('保存成功', {icon: 1});
                                    switchFlag = true
                                } else {
                                    layer.msg('出了点小问题，请稍后再试', {icon: 5});
                                    if (switchFlag) {
                                    } else {
                                        table.update("INTRODUCTION", oldItroduction)
                                        switchFlag = false
                                    }
                                }
                            },
                                 error:function (statue) {
            if (statue.status===700) {
                window.location.href = '/html/lonoAccess.html'
            }
        }
                            })

                    },
                        function () {
                        obj.update(
                            {"INTRODUCTION": oldItroduction}
                        );
                        layer.msg('已取消', {icon: 1}, {
                            time: 10000, //10s后自动关闭
                        });
                    });
                })
            });

            //移除tab的时候同时移除内容
            element.tabChange('demo', '1'); //切换到：用户管理
        }
        else {
            element.tabChange('demo', '1'); //切换到：用户管理
        }
    });
}


//评论页面
function commentsQuery() {
    layui.use(['table','jquery','form','element','util'],function () {
        let table=layui.table;
        let form=layui.form;
        let element=layui.element;
        let util=layui.util;
        $=layui.jquery;
        //加载tab标签页
        if($("li[lay-id='2']").length==0) {
            element.tabAdd('demo', {
                title: '评论管理'
                , content: '<table id=\"commentsTable\" lay-filter=\"commentsLists\"></table>' //支持传入html
                , id: '2'
            });
            element.render('tab', 'demo');
            // 渲染表格
            table.render({
                elem: "#commentsTable",
                url: "/queryComments",
                height: 500,
                headers: {
                    'token': token
                },
                request: {
                    pageName: 'pageNum'
                },
                response: {
                    statusCode: 200,
                    msgName: 'message',
                    dataName: 'resultLists'
                },
                page: true,
                cols: [
                    [
                        {field: 'id', title: "id", hide: true},
                        {field: 'article', title: "评论文章", width: 300, align: 'center'},
                        {field: 'userName', title: "用户", width: 100, align: 'center'},
                        {field: 'comments', title: "评论内容", width: 300, align: 'center',templet: function(d){
                                return util.escape(d.comments);
                            }},
                        {field: 'replyName', title: "回复用户", width: 100, align: 'center'},
                        {field: 'browserType', title: "浏览器类型", width: 150, align: 'center'},
                        {field: 'devicesType', title: "设备类型", width: 150, align: 'center'},
                        {field: 'ip', title: "访问ip", width: 150, align: 'center'},
                        {field: 'commentsDate', title: "评论时间", width: 120, align: 'center'},
                        {fixed: 'right', title: '操作', toolbar: '#barTool', width: 150}
                    ]
                ],
                id:'commentsList'
            });
            //自定义事件的监听
            table.on('tool(commentsLists)', function (obj) {
                if (obj.event == "del") {
                    layer.confirm('真的删除行么', function (index) {
                        obj.del();
                        let id = obj.data.id;
                        $.ajax(
                            "/deleteComments",{
                            type:'post',
                            data:{"id": id},
                                headers:{
                                    'token':token
                                },
                            success:function (data) {
                                if (data.code == '200') {
                                    layer.msg('保存成功', {icon: 1});
                                } else {
                                    layer.msg('出了点小问题，请稍后再试', {icon: 5});
                                }
                            },
                                 error:function (statue) {
            if (statue.status===700) {
                window.location.href = '/html/noAccess.html'
            }
        }
                            });
                        layer.close(index);
                    });
                }
            });
            element.tabChange('demo', '2')
        }
        else {
            element.tabChange('demo', '2')
        }
    });
}
//分类查询
function categoryQuery() {
    layui.use(['table','jquery','form','element','layer'],function () {
        let table = layui.table;
        let form = layui.form;
        let element = layui.element;
        let layer = layui.layer;
        $ = layui.jquery;
        if($("li[lay-id='3']").length==0) {
        //加载tab标签页
        element.tabAdd('demo', {
            title: '分类管理'
            , content: '<table id=\"categoryTable\" lay-filter=\"categoryList\"></table>' //支持传入html
            , id: '3'
        });
        element.render('tab', 'demo');
        // 渲染表格
        table.render({
            elem: "#categoryTable",
            url: "/queryCategory",
            height: 500,
            toolbar: '#toolbar',
            headers: {
                'token': token
            },
            request: {
                pageName: 'pageNum'
            },
            response: {
                statusCode: 200,
                msgName: 'message',
                dataName: 'resultLists'
            },
            page: true,
            cols: [
                [
                    {field: 'id', title: "id", hide: true},
                    {field: 'category', title: "分类",edit: 'text', width: 300, align: 'center',event:'signEdit'},
                    {field: 'num', title: "分类下文章数量", width: 300, align: 'center'},
                    {fixed: 'right', title: '操作', toolbar: '#barTool', width: 150}
                ]
            ],
            id:'categoryLists'
        });
        //自定义事件的监听
            table.on('tool(categoryList)', function (obj) {
                let data = obj.data;
                switch (obj.event) {
                    case "signEdit":
                        oldItroduction = data.category;
                        break;
                    case "del":
                        layer.confirm('真的删除行么', function (index) {
                            obj.del();
                            let id = obj.data.id;
                            if (obj.data.num>0){
                                layer.msg("该分类下存在文章不能删除")
                            }
                            else {
                                $.ajax(
                                    "/deleteCategory", {
                                        type: 'post',
                                        data: {"id": id},
                                        headers: {
                                            'token': token
                                        },
                                        success: function (data) {
                                            if (data.code == '200') {
                                                layer.msg('删除成功', {icon: 1});
                                                option="";
                                            } else {
                                                layer.msg('出了点小问题，请稍后再试', {icon: 5});
                                            }
                                        },
                                         error:function (statue) {
            if (statue.status===700) {
                window.location.href = '/html/noAccess.html'
            }
        }
                                    });
                            }
                            layer.close(index);
                        });
                        break
                }
            });
            table.on('edit(categoryList)', function (obj) {
                layui.use("layer", function () {
                    layer.confirm('确定保存吗？', {
                            btn: ['保存', '取消'] //按钮
                        }, function () {
                            let value = obj.value;
                            let id = obj.data.id;
                            $.ajax(
                                "/updateCategory",{
                                    type:'post',
                                    data:{'category': value, "id": id},
                                    headers:{
                                        'token':token
                                    },
                                    success:function (data) {
                                        if (data.code =='200') {
                                            layer.msg('保存成功', {icon: 1});
                                             option="";
                                            switchFlag = true
                                        } else {
                                            layer.msg('出了点小问题，请稍后再试', {icon: 5});
                                            if (switchFlag) {
                                            } else {
                                                table.update("category", oldItroduction)
                                                switchFlag = false;
                                            }
                                        }
                                    },
                                     error:function (statue) {
            if (statue.status===700) {
                window.location.href = '/html/noAccess.html'
            }
        }})

                        },
                        function () {
                            obj.update(
                                {"category": oldItroduction}
                            );
                            layer.msg('已取消', {icon: 1}, {
                                time: 10000, //10s后自动关闭
                            });
                        });
                })
            });
        table.on('toolbar(categoryList)', function (obj) {
            if (obj.event == "addCategory") {
                layui.use('layer', function () {
                    layer.open({
                        type: 1,
                        title: '新增分类',
                        closeBtn: 1,
                        shade: 0,
                        area: ['50%', '50%'],
                        skin: 'layui-layer-demo',
                        btn: ['新增', '取消'],
                        content: $('#addCategoryForm'),
                        yes: function (index, layero) {
                            let categoryName=$("#categoryName").val();
                            if (categoryName == '') {
                                layer.alert('输入值不能为空')
                            } else {
                                $.ajax(
                                    "/insertCategory",{
                                        type:'post',
                                        data:{'category': categoryName},
                                        headers:{
                                            'token':token
                                        },
                                        success:function (data) {
                                            if (data.code == '200') {
                                                layer.msg('保存成功', {icon: 1});
                                            } else {
                                                layer.msg('出了点小问题，请稍后再试', {icon: 5});
                                            }
                                        },
                                         error:function (statue) {
            if (statue.status===700) {
                window.location.href = '/html/noAccess.html'
            }
        }});
                                layer.close(index);
                                table.reload('categoryLists', {
                                    page: {
                                        curr: 1
                                    }
                                });
                            }
                        }
                        , btn2: function (index, layero) {
                        }
                    })
                });
            }
        });
        //移除tab的时候同时移除内容
        element.tabChange('demo', '3'); //切换到：用户管理
    }
        else {
            element.tabChange('demo', '3');
        }
    });
}
function logout() {
    window.localStorage.setItem("token", "");
    window.location.href='/html/login.html'
}
function logQuery() {
    layui.use(['table','element'],function () {
        let table = layui.table;
        let element = layui.element;
        if($("li[lay-id='5']").length==0) {
            //加载tab标签页
            element.tabAdd('demo', {
                title: '日志管理'
                , content: '<table id=\"logTable\" lay-filter=\"logList\"></table>' //支持传入html
                , id: '5'
            });
            element.render('tab', 'demo');
            // 渲染表格
            table.render({
                elem: "#logTable",
                url: "/queryLog",
                height: 500,
                headers: {
                    'token': token
                },
                request: {
                    pageName: 'pageNum'
                },
                response: {
                    statusCode: 200,
                    msgName: 'message',
                    dataName: 'resultLists'
                },
                page: true,
                cols: [
                    [
                        {field: 'id', title: "id", hide: true},
                        {field: 'userName', title: "操作用户",edit: 'text', width: 300, align: 'center',event:'signEdit'},
                        {field: 'params', title: "参数", width: 300, align: 'center'},
                        {field: 'action', title: "操作名称", width: 300, align: 'center'},
                        {field: 'methodName', title: "访问方法名", width: 300, align: 'center'},
                        {field: 'ip', title: "访问ip", width: 300, align: 'center'},
                        {field: 'time', title: "操作时间", width: 300, align: 'center'},
                    ]
                ],
                id:'logLists'
            });
            element.tabChange('demo', '5');
        }
        else {
            element.tabChange('demo', '5');
        }
    });
}
