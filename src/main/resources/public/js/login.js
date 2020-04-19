/**
 * @author sikunliang
 * @date 2020/4/4
 * @Description
 */
layui.use(['form'], function() {
    let form=layui.form
    form.verify({
        password: [
            /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/
            , '密码至少八个字符，至少一个字母和一个数字'
        ]
    });

    form.on('submit(submit)', function(res){
        $.ajax("/login", {
            type: "post",
            data: res.field,
            headers: {
                'token': token
            },
            success: function (data) {
                if (data.code=="200"&&data.statue=="1"){
                    window.localStorage.setItem("token", data.message);
                    token=data.message;
                    window.location.href='/html/manager.html'
                }
                else {
                    layer.msg("账户或密码错误")
                }
            }
        });
        return false;
    });
});