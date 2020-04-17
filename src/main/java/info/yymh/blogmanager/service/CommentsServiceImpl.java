package info.yymh.blogmanager.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import info.yymh.blogmanager.annotation.ServiceLog;
import info.yymh.blogmanager.dao.CommentsDao;
import info.yymh.blogmanager.dao.UserDao;
import info.yymh.blogmanager.pojo.Comments;
import info.yymh.blogmanager.utils.ResultBean;
import info.yymh.blogmanager.utils.TokenUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * 文章评论管理
 * @author sikunliang
 * @date 2020/3/25
 */
@Service
public class CommentsServiceImpl implements CommentsService {
    private UserDao userDao;
    private CommentsDao commentsDao;
    private TokenUtils tokenUtils;
    public CommentsServiceImpl(UserDao userDao, CommentsDao commentsDao, TokenUtils tokenUtils) {
        this.userDao = userDao;
        this.commentsDao = commentsDao;
        this.tokenUtils = tokenUtils;
    }

    @Override
    @ServiceLog("新增文章留言")
    public ResultBean postCommetns(Comments comments,String token) {
        String id= (String) tokenUtils.decodedJWT(token).get("id");
        comments.setUserId(id);
        ResultBean resultBean=new ResultBean();
            Integer result = commentsDao.postComments(comments);
            resultBean.setCode("200");
            if (0 != result){
                resultBean.setStatue("1");
                resultBean.setMessage("评论成功");
            }
            else {
                resultBean.setStatue("0");
                resultBean.setMessage("评论失败，请稍后再试");
            }
        return resultBean;
    }

    @Override
    @ServiceLog("删除文章留言")
    public ResultBean deleteComments(String id) {
        ResultBean resultBean = new ResultBean();
            Integer result = commentsDao.deleteComments(id);
            resultBean.setCode("200");
            if (result > 0) {
                resultBean.setStatue("1");
                resultBean.setMessage("成功");
            } else {
                resultBean.setStatue("0");
                resultBean.setMessage("失败");
            }
            return resultBean;
    }

    @Override
    @ServiceLog("查询文章留言")
    public ResultBean queryComments(String id,String pageNum,String limit) {
        ResultBean result = new ResultBean();
            Page page= PageHelper.startPage(Integer.parseInt(pageNum),Integer.parseInt(limit),true);
            List<HashMap<String,String>> commentsLists  =  commentsDao.queryComments();
            PageInfo pageInfo=new PageInfo(commentsLists);
            result.setCode("200");
            result.setStatue("1");
            result.setResultLists(commentsLists);
            result.setRows(commentsLists.size());
            result.setCount(String.valueOf(pageInfo.getTotal()));
            return result;
    }
}
