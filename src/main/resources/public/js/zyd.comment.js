/**
 * bootstrap版评论框
 * 
 * @date 2018-01-05 10:57
 * @author zhyd(yadong.zhang0415#gmail.com)
 * @link https://github.com/zhangyd-c
 */
let replyId="";
    $.extend({
	comment: {
		submit: function(target) {
			var $this = $(target);
			$this.button('loading');
			$('#detail-modal').modal('show');
			$(".close").click(function() {
				setTimeout(function() {
					$this.html("<i class='fa fa-close'></i>取消操作...");
					setTimeout(function() {
						$this.button('reset');
					}, 1000);
				}, 500);
			});
			// 模态框抖动
			//		$('#detail-modal .modal-content').addClass("shake");
			$("#detail-form-btn").click(function() {
			    let browser=getBrowser();
			    let os=getOperationSys();
			    let comments=filterXSS(editor.txt.html());
				$.ajax({
					type: "get",
					url: "/postComments?articlesId="+articlesId+"&browserType="+browser+"&devicesType="+os+"&comments="+comments+"&replyId="+replyId,
					async: true,
                    headers:{
                        'token':token
                    },
					success: function(data) {
						if(data.code == 200&&data.statue==1) {
							console.log(data.message);
						} else  {
							console.log(data.message);
						}
						$('#detail-modal').modal('hide');

						setTimeout(function() {
							$this.html("<i class='fa fa-check'></i>" + data.message);
							setTimeout(function() {
								$this.button('reset');
							}, 1000);
						}, 1000);
					},
					error: function(data) {
					    if (data.status==700){
					      $.post("/regeist?&"+$("#detail-form").serialize(),
					            function(data){
					                if (data.code==200&&data.statue==1){
					                    token=data.message;
                                        window.localStorage.setItem("token", data.message);
                                        $("#detail-form-btn").click();
                                   }
					                else {
                                        $("#messagerPassword").html(data.message);
                                    }
                            })
                        }

					}
				});
			});
		},
		reply: function(pid, c) {
			replyId=pid;
			$('#comment-pid').val(pid);
			$('#cancel-reply').show();
			$('.comment-reply').show();
			$(c).hide();
			$(c).parents('.comment-body').append($('#comment-post'));
			//			$(c).parent().parent().parent().append($('#comment-post'));
		},
		cancelReply: function(c) {
			$('#comment-pid').val("");
			$('#cancel-reply').hide();
			$(c).parents(".comment-body").find('.comment-reply').show();
			//			$(c).parent().parent().parent().find('.comment-reply').show();
			$("#comment-place").append($('#comment-post'));
		}
	}
});

$(function() {
	$('[data-toggle="tooltip"]').tooltip();
	$("#comment-form-btn").click(function() {
		$.comment.submit($(this));
	});
});
function getBrowser() {
    var UserAgent = navigator.userAgent.toLowerCase();
    var browser = null;
    var browserArray = {
        IE: window.ActiveXObject || "ActiveXObject" in window, // IE
        Chrome: UserAgent.indexOf('chrome') > -1 && UserAgent.indexOf('safari') > -1, // Chrome浏览器
        Firefox: UserAgent.indexOf('firefox') > -1, // 火狐浏览器
        Opera: UserAgent.indexOf('opera') > -1, // Opera浏览器
        Safari: UserAgent.indexOf('safari') > -1 && UserAgent.indexOf('chrome') == -1, // safari浏览器
        Edge: UserAgent.indexOf('edge') > -1, // Edge浏览器
        QQBrowser: /qqbrowser/.test(UserAgent), // qq浏览器
        WeixinBrowser: /MicroMessenger/i.test(UserAgent) // 微信浏览器
    };
    for (var i in browserArray) {
        if (browserArray[i]) {
            browser = i;
        }
    }
    return browser;
}
function getOperationSys() {
    var OS = '';
    var OSArray = {};
    var UserAgent = navigator.userAgent.toLowerCase();
    OSArray.Windows = (navigator.platform == 'Win32') || (navigator.platform == 'Windows');
    OSArray.Mac = (navigator.platform == 'Mac68K') || (navigator.platform == 'MacPPC')
        || (navigator.platform == 'Macintosh') || (navigator.platform == 'MacIntel');
    OSArray.iphone = UserAgent.indexOf('iPhone') > -1;
    OSArray.ipod = UserAgent.indexOf('iPod') > -1;
    OSArray.ipad = UserAgent.indexOf('iPad') > -1;
    OSArray.Android = UserAgent.indexOf('Android') > -1;
    for (var i in OSArray) {
        if (OSArray[i]) {
            OS = i;
        }
    }
    return OS;
}