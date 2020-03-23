package info.yymh.blogmanager.service;

import info.yymh.blogmanager.dao.TokenDao;
import info.yymh.blogmanager.utils.ConfigBean;
import info.yymh.blogmanager.utils.TokenUtils;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

/**
 * @author sikunliang
 * @Package info.yymh.blogmanager.service
 * @ClassName:
 * @date 2020/3/23
 * @Description
 */
@Service
public class TokenServiceInf implements TokenService {
    private ConfigBean configBean;
    private TokenUtils tokenUtils;
    private TokenDao tokenDao;

    public TokenServiceInf(ConfigBean configBean, TokenUtils tokenUtils, TokenDao tokenDao) {
        this.configBean = configBean;
        this.tokenUtils = tokenUtils;
        this.tokenDao = tokenDao;
    }

    @Override
    public String genericToken(String id) {
        UUID uuid=UUID.randomUUID();
        String uid=uuid.toString();
        long ttlMillis=configBean.getTtlMillis();
        String stringKey=configBean.getKey();
        String token="";
        if (id!=null&&id!="") {
             Map<String,String> userInf = tokenDao.queryRole(id);
            token= tokenUtils.createToken(uid, ttlMillis, stringKey, userInf.get("role"),userInf.get("id"),userInf.get("name"));
        }
        else {
            token = tokenUtils.createToken(uid, ttlMillis, stringKey, "","","");
        }
        return token;
    }
}
