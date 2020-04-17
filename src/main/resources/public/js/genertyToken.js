/**
 * @author sikunliang
 * @date 2020/4/12
 */
let token="";
    token= window.localStorage.getItem("token");
    id = window.localStorage.getItem("id");
    if (token==""||token==null){
        createToken();
    }
function  createToken() {
    $.ajax("/generictoken",{
        async :false,
        success:function (data) {
            window.localStorage.setItem("token", data);
            token=data;
        }})
}
