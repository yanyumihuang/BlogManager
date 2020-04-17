package info.yymh.blogmanager.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import info.yymh.blogmanager.dao.LogDao;
import info.yymh.blogmanager.utils.ResultBean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author sikunliang
 * @date 2020/4/17
 */
@Service
public class LogServiceImpl implements LogService {
    private LogDao logDao;

    public LogServiceImpl(LogDao logDao) {
        this.logDao = logDao;
    }

    @Override
    public ResultBean queryLog(String pageNum,String limit) {
        ResultBean resultBean=new ResultBean();
        Page page= PageHelper.startPage(Integer.valueOf(pageNum),Integer.valueOf(limit),true);
        List<HashMap<String,String>> result=logDao.queryLog();
        PageInfo pageInfo=new PageInfo(result);
        resultBean.setCode("200");
        resultBean.setStatue("1");
        resultBean.setResultLists(result);
        resultBean.setRows(result.size());
        resultBean.setCount(String.valueOf(pageInfo.getTotal()));
        return resultBean;
    }
}
