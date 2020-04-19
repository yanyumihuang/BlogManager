/**
 * @author sikunliang
 * @date 2020/4/16
 */

var editor;
let articlesId="";
let num="";
//加载文章
function loadArticle() {
    articlesId=getParams("id");
    let url=getParams("address");
    $.ajax(url,
        {
            headers:{
                'token':token
            },
            async:false,
            success:function(res) {
                $("#content").html(res)
            }
        });

}
//加载评论
function loadCommetns(pageNumb) {
    $.ajax("/queryComments",{
        data:{"id":articlesId,"pageNum":pageNumb,"limit":"10"},
        async:false,
        headers:{
            'token':token
        },
        success:function (data) {
            if (data.code=="200"&&data.statue=="1"){
                result=data.resultLists;
                let comments="";
                for (let i=0;i<result.length;i++) {
                    let dd="";
                    if (result[i].replyId==undefined||result[i].replyId==null){
                        dd=""
                    }
                    else {
                         dd="<a href=\"#comment-"+result[i].replyId+"\"" +
                            " class=\"comment-quote\">"+"@"+ result[i].replyName + "</a>\n";
                    }


                    comments+= "<li>\n" +
                        "                        <div class=\"comment-body\" id=\"comment-"+result[i].userId+"\">\n" +
                        "                            <div class=\"cheader\">\n" +
                        "                                <strong>" + result[i].userName + "</strong>\n" +
                        "                            </a>\n" +
                        "                            <div class=\"timer\">\n" +
                        "                                <i class=\"fa fa-clock-o fa-fw\"></i>" + result[i].commentsDate + "\n" +
                        "                            </div>\n" +
                        "                        </div>\n" +
                        "                        <div class=\"content\">\n" +dd+
                        "                            " + result[i].comments + "\n" +
                        "                        </div>\n" +
                        "                        <div class=\"sign\">\n" +
                        "                            <i class=\"icons os-win2\"></i>" + result[i].devicesType + "<i class=\"sepa\"></i>\n" +
                        "                            <i class=\"icons browser-chrome\"></i>" + result[i].browserType + " <i class=\"sepa\"></i>\n" +
                        "                            <a href=\"#comment-1\" class=\"comment-reply\"" +
                        " onclick=\"$.comment.reply(result[i].userId, this)\"><i class=\"fa fa-reply" +
                        " fa-fw\"></i>回复</a>\n" +
                        "                        </div>\n" +
                        "                    </div>\n" +
                        "                    </li>"
                }
                $("#comments").html(comments);
                num=data.count;
                $("em").html(num);
            }
        }
    })
}
//初始化回复框
function  initReply() {
    var E = window.wangEditor;
     editor = new E('#editor');
    // 自定义菜单配置
    editor.customConfig.menus = [
        //  'code', 插入代码
        //			'head', // 标题
        //'bold', // 粗体
        //'italic', // 斜体
        //'underline', // 下划线
        //			'strikeThrough', // 删除线
        //			'foreColor', // 文字颜色
        //			'backColor', // 背景颜色
        'image', // 插入图片
        'link', // 插入链接
        //'list', // 列表
        //			'justify', // 对齐方式
        //'quote', // 引用
        'emoticon', // 表情
        //			'table', // 表格
        //			'video', // 插入视频
        //			'undo', // 撤销
        //			'redo' // 重复
    ];
    // debug模式下，有 JS 错误会以throw Error方式提示出来
    editor.customConfig.debug = true;

    // 关闭粘贴样式的过滤
    editor.customConfig.pasteFilterStyle = false;
    // 自定义处理粘贴的文本内容
    editor.customConfig.pasteTextHandle = function(content) {
        // content 即粘贴过来的内容（html 或 纯文本），可进行自定义处理然后返回
        return content
    };
    // 插入网络图片的回调
    editor.customConfig.linkImgCallback = function(url) {
        console.log(url) // url 即插入图片的地址
    };
    editor.customConfig.zIndex = 100;
    editor.create();
    E.fullscreen.init('#editor');
    //		editor.txt.clear(); //清空编辑器内容
    //		editor.txt.html('<p>用 JS 设置的内容</p><strong>hello</strong><script>alert(/xss/);<\/script>');
    //		editor.txt.append('<p>追加的内容</p>');
    // 读取 html
    console.log(editor.txt.html());
    // 读取 进行 xss 攻击过滤后的html
    console.log(filterXSS(editor.txt.html()));
    // 读取 text
    console.log(editor.txt.text());
    // 获取 JSON 格式的内容
    console.log(editor.txt.getJSON());
}
function getParams(key) {
    var reg = new RegExp("(^|&)" + key + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}
function verEmail(email) {
    let  patternEmail = /^[A-Za-z0-9]+([_\.][A-Za-z0-9]+)*@([A-Za-z0-9\-]+\.)+[A-Za-z]{2,6}$/;
    return patternEmail.test(email)
}
function verPassword(passWord) {
   let  parentPassword=/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/;
    return parentPassword.test(passWord)
}
function initPage(num) {
    slp = new SimplePagination(Math.ceil(parseInt(num)/5),1);
    slp.init({
        container: '.box',
        maxShowBtnCount: 3
    });
    slp.gotoPage(1);
}
