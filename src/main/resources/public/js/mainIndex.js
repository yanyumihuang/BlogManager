/**
 * @author sikunliang
 * @date 2020/3/17
 * @Description 监听滚动操作，从而展示不同的导航条
 */
let num="";
$(document).ready(function () {
    dataLoad(1);
    initPage(num)
});
$(window).scroll(function () {
    let scrollTop = $(document).scrollTop();
    let height = $('#navigation').outerHeight(true) + 30;
    if (scrollTop > height) {
        $("#menu-phone").fadeIn();
    } else {
        $("#menu-phone").fadeOut()
    }
});

function dataLoad(pageNumb) {
    let post_list = $(".post-list");
    $.ajax("/queryArticles", {
            type: "get",
            data: {'pageNum': pageNumb},
            async:false,
            headers: {
                'token': token
            },
            success: function (data) {
                if (data.code === "200"&&data.statue==="1") {
                    if (data.rows !== 0) {
                        let result = data.resultLists;
                        let ininnerHtmlString = "";
                        for (var i = 0; i < data.rows; i++) {
                            ininnerHtmlString +=
                                "<div class='post-wrapper'>" +
                                "<article class=\"post white-box shadow reveal \">" +
                                "  <section class=\"meta\">\n" +
                                "    <div class=\"meta\" id=\"header-meta\">\n" +
                                "  <h2 class=\"title\">\n" +
                                "    <a href=\"/html/detail.html?id="+ result[i].ID+"&address="+result[i].ADDRESS+"\">\n" +
                                result[i].TITLE +
                                "    </a>\n" +
                                "  </h2>\n" +
                                "      <div class=\"new-meta-box\">\n" +
                                "<div class=\"new-meta-item author\">\n" +
                                "    <p STYLE='margin-bottom: 0px'>" + result[i].AUTHOR + "</p>\n" +
                                "</div>\n" +
                                "  <div class=\"new-meta-item category\">\n" +
                                "    <a href=\"/categories/%E5%AE%9E%E7%94%A8%E7%BD%91%E7%AB%99/\" rel=\"nofollow\">\n" +
                                "      <i class=\"fas fa-folder-open\" aria-hidden=\"true\"></i>\n" +
                                "      <p>" +
                                result[i].CATEGORY +
                                "</p>\n" +
                                "    </a>\n" +
                                "  </div>\n" +
                                "            <div class=\"new-meta-item date\">\n" +
                                "  <a class=\"notlink\">\n" +
                                "    <i class=\"fas fa-edit\" aria-hidden=\"true\"></i>\n" +
                                "    <p>发布于：" + result[i].CREATEDATE + "</p>\n" +
                                "  </a>\n" +
                                "</div>\n" +
                                "      </div>\n" +
                                "        <hr>\n" +
                                "    </div>\n" +
                                "  </section>\n" +
                                "  <section class=\"article typo\">\n" +
                                "    <div class=\"article-entry\" itemprop=\"articleBody\">\n" +
                                "      <p>" + result[i].INTRODUCTION + "</p>\n" +
                                "          <div class=\"button readmore\">\n" +
                                "          <a href=\"/html/detail.html?id="+ result[i].ID+"&address="+result[i].ADDRESS+"\">\n"+
                                "         阅读全文 <i class=\"fas fa-chevron-right\"></i>\n" +
                                "            </a>\n" +
                                "          </div>\n" +
                                "    </div>\n" +
                                "  </section>\n" +
                                "</article>" +
                                "</div>"
                        }
                        post_list.html(ininnerHtmlString);
                        num=data.count;
                    }
                } else {
                    alert("加载出错请稍后再试")
                }
            }
        }
    )

}
function initPage(num) {
    slp = new SimplePagination(Math.ceil(parseInt(num)/5),0);
    slp.init({
        container: '.box',
        maxShowBtnCount: 3
    });
    slp.gotoPage(1);
}
