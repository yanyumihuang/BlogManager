package info.yymh.blogmanager.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author sikunliang
 * @Package info.yymh.blogmanager.controller
 * @ClassName:
 * @date 2020/1/19
 * @Description
 */
@Controller
public class HelloWord {
@RequestMapping
    public void hello(HttpServletResponse response) throws IOException {
        response.getWriter().print("helloword");
    }
}
