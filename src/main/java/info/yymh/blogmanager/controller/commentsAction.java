package info.yymh.blogmanager.controller;

import info.yymh.blogmanager.service.CommentsService;
import info.yymh.blogmanager.utils.ResultBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @author sikunliang
 * @Package info.yymh.blogmanager.controller
 * @ClassName:
 * @date 2020/3/25
 * @Description 进行文章评论
 */
@Controller
public class commentsAction {
    private CommentsService commentsService;

    public commentsAction(CommentsService commentsService) {
        this.commentsService = commentsService;
    }
    @RequestMapping("/queryComments")
    @ResponseBody
    public ResultBean queryComments(){
        ResultBean resultBean=commentsService.queryComments();
        return  resultBean;
    }

    @RequestMapping("/postComments")
    @ResponseBody
    public ResultBean postComments(String userName, String passWord, String comments, HttpServletRequest request){
        String token=request.getHeader("token");
        ResultBean resultBean=commentsService.postCommetns(userName,passWord,comments,token);
        return resultBean;
    }

    @RequestMapping("/deleteComments")
    @ResponseBody
    public ResultBean deleteComments(String id){
        ResultBean resultBean=commentsService.deleteComments(id);
        return resultBean;
    }
}
