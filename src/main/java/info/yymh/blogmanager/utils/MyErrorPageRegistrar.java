package info.yymh.blogmanager.utils;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

/**
 * @author sikunliang
 * @date 2020/4/17
 */

@Component
public class MyErrorPageRegistrar implements ErrorPageRegistrar {
        @Override
        public void registerErrorPages(ErrorPageRegistry errorPageRegistry) {
            ErrorPage page404 = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
            ErrorPage page500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");
            errorPageRegistry.addErrorPages(page404, page500);
        }
    }

