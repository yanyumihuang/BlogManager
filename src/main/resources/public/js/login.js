/**
 * @author sikunliang
 * @date 2020/4/4
 * @Description
 */
let token="";
let id="";
$(document).ready(function () {
 token= window.localStorage.getItem("token");
 id = window.localStorage.getItem("id");
if (token==""||token==null){
    createToken();
    token = window.localStorage.getItem("token");
}
});
function login() {
    let userName=$("#userName").val();
    let password=$("#passWord").val();
    $.ajax("/login", {
        type: "post",
        data: {'userName': userName,'passWord':password},
        headers: {
            'token': token
        },
        success: function (data) {
            if (data.code=="200"&&data.statue=="1"){
                window.localStorage.setItem("token", data.message);
                window.location.href='/html/manager.html'
            }
            else {
                layer.msg("账户或密码错误")
            }
        }
    });
}
function  createToken() {
    $.get("/generictoken",
        {'id': id},
        function (data) {
            window.localStorage.setItem("token", data)
        })
}