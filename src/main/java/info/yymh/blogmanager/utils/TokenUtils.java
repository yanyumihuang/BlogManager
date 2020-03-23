package info.yymh.blogmanager.utils;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author sikunliang
 * @Package info.yymh.blogmanager.utils
 * @ClassName:
 * @date 2020/3/23
 * @Description 创建token，并对token进行校验
 */
@Component
public class TokenUtils {
    public String createToken(String id,long ttlMillis,String stringkey,String rolePemission,String userId,String userName){
        SignatureAlgorithm signatureAlgorithm=SignatureAlgorithm.HS256;
        long nowMillis=System.currentTimeMillis();
        Date now=new Date(nowMillis);
        Map<String ,Object> claim=new HashMap<>(3);
        claim.put("uuid",userId);
        claim.put("name",userName);
        claim.put("role",rolePemission);
        SecretKey key=generalKey(stringkey);
        JwtBuilder builder= Jwts.builder()
                .setClaims(claim)
                .setId(id)
                .setIssuedAt(now)
                .signWith(signatureAlgorithm,key);
        if (ttlMillis>0){
            long extMillis=nowMillis+ttlMillis;
            Date exp=new Date(extMillis);
            builder.setExpiration(exp);
        }
        return  builder.compact();
    }
    public SecretKey generalKey(String stringKey){
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }
}
