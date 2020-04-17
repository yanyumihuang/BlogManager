package info.yymh.blogmanager.controller;

import info.yymh.blogmanager.annotation.ControllerLog;
import info.yymh.blogmanager.pojo.Comments;
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
    @ControllerLog("查询文章留言")
    public ResultBean queryComments(String id,String pageNum,String limit){
        ResultBean resultBean=commentsService.queryComments(id,pageNum,limit);
        return  resultBean;
    }

    @RequestMapping("/postComments")
    @ResponseBody
    @ControllerLog("新增文章留言")
    public ResultBean postComments(Comments comments, HttpServletRequest request){
        String token=request.getHeader("token");
        String ip=request.getRemoteAddr();
        comments.setIp(ip);
        ResultBean resultBean=commentsService.postCommetns(comments,token);
        return resultBean;
    }

    @RequestMapping("/deleteComments")
    @ResponseBody
    @ControllerLog("删除文章留言")
    public ResultBean deleteComments(String id){
        ResultBean resultBean=commentsService.deleteComments(id);
        return resultBean;
    }
}
