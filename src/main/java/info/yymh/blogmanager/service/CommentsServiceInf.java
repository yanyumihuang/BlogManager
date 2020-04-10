package info.yymh.blogmanager.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import info.yymh.blogmanager.dao.CommentsDao;
import info.yymh.blogmanager.dao.UserDao;
import info.yymh.blogmanager.utils.Md5Utils;
import info.yymh.blogmanager.utils.ResultBean;
import info.yymh.blogmanager.utils.TokenUtils;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author sikunliang
 * @Package info.yymh.blogmanager.service
 * @ClassName:
 * @date 2020/3/25
 * @Description 文章评论功能
 */
@Service
public class CommentsServiceInf implements CommentsService {
    private UserDao userDao;
    private CommentsDao commentsDao;
    public CommentsServiceInf(UserDao userDao, CommentsDao commentsDao) {
        this.userDao = userDao;
        this.commentsDao = commentsDao;
    }

    @Override
    public ResultBean postCommetns(String userName, String passWord,String comments,String token) {
        ResultBean resultBean=new ResultBean();
        String result="";
        Map<String,Object> claim= TokenUtils.decodedJWT(token);
        if (claim.get("role")=="geneleRole"||claim.get("role")=="admin"){
            result = commentsDao.postComments(comments,(Integer) claim.get("id"));
            if (result!="0"){
                resultBean.setCode("200");
                resultBean.setStatue("1");
                resultBean.setMessage("评论成功");
                return resultBean;
            }
            else {
                resultBean.setCode("200");
                resultBean.setStatue("0");
                resultBean.setMessage("评论失败，请稍后再试");
                return resultBean;
            }
        }
        else {
            String hashPwd= null;
            try {
                hashPwd = Md5Utils.switchMd5(passWord);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            Integer id= userDao.insert(userName,hashPwd);
            String uuid=UUID.randomUUID().toString();
            //返回新的token
            token=TokenUtils.createToken(uuid,"geneleRole",id.toString(),userName);
            result = commentsDao.postComments(comments,id);
            resultBean.setCode("200");
            resultBean.setStatue("1");
            resultBean.setMessage(token);
            return resultBean;
        }
    }

    @Override
    public ResultBean deleteComments(String id) {
        ResultBean resultBean = new ResultBean();
        try {
            Integer result = commentsDao.deleteComments(id);
            if (result > 0) {
                resultBean.setCode("200");
                resultBean.setStatue("1");
                resultBean.setMessage("成功");
                return resultBean;
            } else {
                resultBean.setCode("200");
                resultBean.setStatue("0");
                resultBean.setMessage("失败");
                return resultBean;
            }
        }
        catch (Exception e){
            e.printStackTrace();
            resultBean.setCode("500");
            resultBean.setStatue("0");
            resultBean.setMessage("失败");
            return resultBean;
        }

    }

    @Override
    public ResultBean queryComments() {
        ResultBean result = new ResultBean();
        try {
            Page page= PageHelper.startPage(Integer.valueOf(1),5,true);
            List<HashMap<String,String>> commentsLists  =  commentsDao.queryComments();
            PageInfo pageInfo=new PageInfo(commentsLists);
            result.setCode("200");
            result.setStatue("1");
            result.setResultLists(commentsLists);
            result.setRows(commentsLists.size());
            result.setCount(String.valueOf(pageInfo.getTotal()));
            return result;
        }
        catch (Exception e){
            result.setCode("500");
            result.setStatue("0");
            result.setMessage("失败");
            return result;
        }
    }
}
