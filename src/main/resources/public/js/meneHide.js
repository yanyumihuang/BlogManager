/**
 * @author sikunliang
 * @date 2020/3/17
 * @Description 监听滚动操作，从而展示不同的导航条
 */
//页面载入后隐藏导航栏
$(document).ready(function(){
});
$(window).scroll(function () {
    const scrollTop = $(document).scrollTop();
    const height = $('#navigation').outerHeight(true)+30;
    if (scrollTop>height){
        $("#menu-phone").fadeIn();
    }
    else {
        $("#menu-phone").fadeOut()
    }
});