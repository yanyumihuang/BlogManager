package info.yymh.blogmanager.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

/**
 * @author sikunliang
 * @date 2020/3/19
 */

@Service
public class ArticlesServiceInf implements ArticlesService{
    @Override
    public String query() {
        Page page=PageHelper.startPage(1,5,true);
        return null;
    }
}
