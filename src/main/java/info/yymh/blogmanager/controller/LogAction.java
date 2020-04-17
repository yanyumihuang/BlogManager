package info.yymh.blogmanager.controller;

import info.yymh.blogmanager.annotation.ControllerLog;
import info.yymh.blogmanager.service.LogService;
import info.yymh.blogmanager.utils.ResultBean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author sikunliang
 * @date 2020/4/17
 */
@Controller
public class LogAction {
    private LogService logService;

    public LogAction(LogService logService) {
        this.logService = logService;
    }

    @RequestMapping("/queryLog")
    @ResponseBody
    @ControllerLog("查询日志")
    public ResultBean queryLog(String pageNum,String limit){
        ResultBean resultBean=logService.queryLog(pageNum,limit);
        return resultBean;
    }
}
