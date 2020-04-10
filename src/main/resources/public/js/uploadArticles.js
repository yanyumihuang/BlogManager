/**
 * @author sikunliang
 * @date 2020/3/28
 * @Description
 */

layui.use(['upload','form','jquery','element','layer','layedit','laydate'],function () {
        let element = layui.element;
        let form = layui.form;
        let layedit=layui.layedit;
        let laydate=layui.laydate;
        let upload=layui.upload;
        $=layui.jquery;
    $(document).ready(function () {
        token = window.localStorage.getItem("token");
        id = window.localStorage.getItem("id");
        if (token == "" || token == null) {
            createToken();
            token = window.localStorage.getItem("token");
        }
                categoryQuerys();
                $("select[name='category']").append(option);
        form.render("select");
        addArt=$("#addArticles").html();
        $("#fileUp").show();
        $("#fileEdit").hide();
        $.ajaxSetup({
            header:{'token':token}
        });
        });
    laydate.render({
        elem: '#createDate'
        ,value: new Date()
        ,max:'nowDate'
        ,isInitValue: true
    });
 /*   var uploadInst=upload.render({
       elem:'#uploadPic',
        url:"/uploadImg",
        headers:    {
           'token':token
        },
        choose:function(obj){
           let files=obj.pushFile();
            obj.preview(function (index,file,result) {
                $("#headerCover").attr('src',result);
            });
        },
        before:function(){
            layer.load();
        },
        done:function (res) {
           layer.closeAll('loading');
           if (res.success===0){
               return layer.msg("上传失败")
           }
        },
        error:function () {
            layer.closeAll('loading');
            var headerCoverText = $('#headerCoverText');
            headerCoverText.html('<span style="color: #FF5722;">上传失败</span> <a class="layui-btn layui-btn-xs demo-reload">重试</a>');
            headerCoverText.find('.demo-reload').on('click', function(){
                uploadInst.upload();
            })
        }
    });*/
    var uploadIns=upload.render({
       elem:'#choiceFile',
        url:'/uploadArt',
        headers:{'token':token},
        accept:'file',
        exts:'md',
        choose:function(obj){
            obj.preview(function (index,file,result) {
                $("#fileName").html(file.name);
                if ($("#title").val()==""){
                    $("#title").val(file.name)
                }
            })

        },
        before:function (obj) {

        },
        done:function (res) {
            if (res.success===0){
                return layer.msg("上传失败")
            }
            else {
                $("#address").val(res.url);
            }
        },
        error:function () {
           layer.msg("上传出错请重试")
        }
    });
    form.on('switch(articlesPri)',function(data){
        let value=data.value;
        if ( value==0) {
            $("#articlesPri").attr("value", '1');
        }
        else {
            $("#articlesPri").attr("value", '0');
        }
    });
    form.on('submit(demo2)', function(data){
        $.ajax(
            "/insertArticles",{
                type:'post',
                data:data.field,
                headers:{
                    'token':token
                },
                success:function (data) {
                    if (data.code== '200'&&data.statue=="1") {
                        layer.msg('保存成功', {icon: 1});
                    } else {
                        layer.msg('出了点小问题，请稍后再试', {icon: 5});
                    }
                }});
        return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
    });
    form.on('radio(choic)',function(data){
        if (data.value=="0"){
            $("#fileUp").show();
            $("#fileEdit").hide();
        }
        else {
            $("#fileUp").hide();
            $("#fileEdit").show();
            layer.open({
                type: 1
                ,content: $('#myeditor')
                ,btn: '关闭全部'
                ,btnAlign: 'c' //按钮居中
                ,shade: 0 //不显示遮罩
                ,area: ['80%', '80%']
                ,yes: function(){
                    let content = testEditor.getHTML();
                    $.ajax(
                        "/uploadMd",{
                            type:'post',
                            data:{"content": content},
                            headers:{
                                'token':token
                            },
                            success:function (data) {
                                if (data.success== '1') {
                                    layer.msg('保存成功', {icon: 1});
                                    $("#address").val(data.url);
                                } else {
                                    layer.msg('出了点小问题，请稍后再试', {icon: 5});
                                }
                            }});
                    layer.closeAll();
                },
                success:function () {
                    testEditor = editormd("test-editormd", {
                        width: "90%",
                        height: 740,
                        path : '/editormd/lib/',
                        theme : "default",
                        previewTheme : "default",
                        editorTheme : "default",
                        //markdown : md,             // 初始化编辑区的内容
                        codeFold : true,
                        //syncScrolling : false,
                        saveHTMLToTextarea : true,    // 保存 HTML 到 Textarea
                        searchReplace : true,
                        //watch : false,                // 关闭实时预览
                        htmlDecode : "style,script,iframe|on*",            // 开启 HTML 标签解析，为了安全性，默认不开启
                        //toolbar  : false,             //关闭工具栏
                        //previewCodeHighlight : false, // 关闭预览 HTML 的代码块高亮，默认开启
                        emoji : true,
                        taskList : true,
                        tocm            : true,         // Using [TOCM]
                        tex : true,                   // 开启科学公式TeX语言支持，默认关闭
                        flowChart : true,             // 开启流程图支持，默认关闭
                        sequenceDiagram : true,       // 开启时序/序列图支持，默认关闭,
                        //dialogLockScreen : false,   // 设置弹出层对话框不锁屏，全局通用，默认为true
                        //dialogShowMask : false,     // 设置弹出层对话框显示透明遮罩层，全局通用，默认为true
                        //dialogDraggable : false,    // 设置弹出层对话框不可拖动，全局通用，默认为true
                        //dialogMaskOpacity : 0.4,    // 设置透明遮罩层的透明度，全局通用，默认值为0.1
                        //dialogMaskBgColor : "#000", // 设置透明遮罩层的背景颜色，全局通用，默认为#fff
                        imageUpload : true,
                        imageFormats : ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
                        imageUploadURL : "/uploadImg", // 文件上传路径，返回值为图片加载的路径
                        onload : function() {
                            // 加载后富文本编辑器成功后的回调
                            console.log('onload', this);
                            //this.fullscreen();
                            //this.unwatch();
                            //this.watch().fullscreen();

                            //this.setMarkdown("#PHP");
                            //this.width("100%");
                            //this.height(480);
                            //this.resize("100%", 640);
                        }
                    });
                }
            });
        }
    });
});
function addArticles() {
    layui.use(['table','jquery','form','element'],function () {
        let table=layui.table;
        let form=layui.form;
        let element=layui.element;
        $=layui.jquery;
        //加载tab标签页
        if($("li[lay-id='4']").length==0) {
            element.tabAdd('demo', {
                title: '新增文章'
                , content: addArt //支持传入html
                , id: '4'
            });
            element.render('tab', 'demo');
            form.render();
            element.tabChange('demo', '4');
        }
        else {
            element.tabChange('demo', '4');
        }
    });
}