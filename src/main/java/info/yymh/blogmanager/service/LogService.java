package info.yymh.blogmanager.service;

import info.yymh.blogmanager.utils.ResultBean;

public interface LogService {
    ResultBean queryLog(String pageNum,String limit);
}
