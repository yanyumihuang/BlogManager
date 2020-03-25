/**
 * @author sikunliang
 * @date 2020/3/21
 * @Description
 */
let oldItroduction = '';
let switchFlag=false;
let  option="";
let flag=false;
function categoryQuery() {
    $.ajax("/category",{
        type:"get",
        header:{
            'token':token
        },
        success:function (data) {
            let result=data.resultLists;
            for (let i=0;i<result.size();i++){
                option+=result.category
            }
        }
    });

}
function articleQuery() {
    let token = window.localStorage.getItem("token");
    let id = window.localStorage.getItem("id");
    layui.use(['table','jquery'],function () {
        let table=layui.table;
        $=layui.jquery;

        table.render({
            elem:"#articlesTable",
            url:"/articlesquery",
            headers:{
               'token':token
            },
            request:{
                pageName:'pageNum'
            },
            response:{
                statusCode:200,
                msgName:'message',
                dataName:'resultLists'
            },
            page:true,
            cols:[
                [
                    {field: 'ID',title: "id",hide:true},
                    {field: 'TITLE',title: "标题",width:300,align:'center'},
                    {field: 'AUTHOR',title: "作者",width:150,align:'center'},
                    {field: 'CATEGORY',title: "分类",width:120,align:'center',event:"focu",emplet:function(){
                        if (flag){return '<select lay-filter="select",lay-verify="">\n + option+</select>'}
                        }},
                    {field: 'INTRODUCTION',title: "介绍",width:400,edit:'text',align:'center',event:'signEdit'},
                    {field: 'CREATEDATE',title: "上传时间",width:120, sort: true,align:'center' },
                    {field: 'MODIFYDATE',title: "最后修改时间",width:150, sort: true,align:'center'},
                    {field: 'PRIVATE',title: "私密性",width:80, sort: true,align:'center',templet: '#switchTpl', unresize: true},
                ]
            ],
        });
        form.on('switch(sexDemo)', function(obj){
            layer.tips(this.value + ' ' + this.name + '：'+ obj.elem.checked, obj.othis);
        });
        table.on('tool(articlesList)',function (obj) {
            let data=obj.data;
            oldItroduction=data.INTRODUCTION
        });
        table.on('edit(articlesList)',function (obj){
            categoryQuery();
            flag=true;
        });
        table.on('edit(articlesList)',function (obj) {
            layui.use("layer",function () {
                layer.confirm('确定保存吗？', {
                    btn: ['保存','取消'] //按钮
                }, function(){
                    let value=obj.value;
                    let id=obj.data.ID;
                    let field=obj.field;
                    $.get(
                        "/articlesupdate",
                        {field:value,"id":id},
                        function (data) {
                            if (data.code='200'){
                                layer.msg('保存成功', {icon: 1});
                                switchFlag=true
                            }
                            else {
                                layer.msg('出了点小问题，请稍后再试', {icon: 5});
                                if (switchFlag){
                                }
                                else {
                                    table.update("INTRODUCTION",oldItroduction)
                                }
                            }
                        })

                }, function(){
                    obj.update(
                        { "INTRODUCTION":oldItroduction}
                    )
                    layer.msg('已取消',{icon: 1}, {
                        time: 10000, //10s后自动关闭
                    });
                });
            })
        })
    });
}