/**
 * @author sikunliang
 * @date 2020/3/17
 * @Description 监听滚动操作，从而展示不同的导航条
 */
$(document).ready(function () {
    dataLoad()
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

function dataLoad() {
    let token = window.localStorage.getItem("token");
    let id = window.localStorage.getItem("id");
    if (token==null) {
        createToken();
    }
    let post_list = $(".post-list")
    $.ajax("/articlesquery", {
            type: "get",
            data: {'pageNum': 1},
            headers: {
                'token': token
            },
            success: function (data) {
                if (data.code == 400) {
                    createToken();
                    dataLoad();
                } else {
                    if (data.rows != 0) {
                        let result = data.resultLists;
                        let ininnerHtmlString = "";
                        console.log("标题" + result[0].TITLE)
                        for (var i = 0; i < data.rows; i++) {
                            ininnerHtmlString +=
                                "<div class='post-wrapper'>" +
                                "<article class=\"post white-box shadow reveal \">" +
                                "  <section class=\"meta\">\n" +
                                "    <div class=\"meta\" id=\"header-meta\">\n" +
                                "  <h2 class=\"title\">\n" +
                                "    <a href=\"/2020/03/15/146/\">\n" +

                                result[i].TITLE +
                                "    </a>\n" +
                                "  </h2>\n" +
                                "      <div class=\"new-meta-box\">\n" +
                                "<div class=\"new-meta-item author\">\n" +
                                "  <a href=\"https://www.bfdz.ink\" rel=\"nofollow\">\n" +
                                "    <img src=\"https://www.bfdz.ink/favicon.ico\">\n" +
                                "    <p>" +
                                result[i].AUTHOR + "</p>\n" +
                                "  </a>\n" +
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
                                "<p>coding:</p>\n" +
                                "<blockquote>\n" +
                                "<p>" + result[i].INTRODUCTION + "</p>\n" +
                                "</blockquote>\n" +
                                "          <div class=\"button readmore\">\n" +
                                "            <a href=\"/2020/03/15/146/\">\n" +
                                "              阅读全文 <i class=\"fas fa-chevron-right\"></i>\n" +
                                "            </a>\n" +
                                "          </div>\n" +
                                "    </div>\n" +
                                "  </section>\n" +
                                "</article>" +
                                "</div>"
                        }
                        post_list.html(ininnerHtmlString)
                    }

                }
            }
        }
    )
}
function  createToken() {
    $.get("/generictoken",
        {'id': id},
        function (data) {
            window.localStorage.setItem("token", data)
        })
}