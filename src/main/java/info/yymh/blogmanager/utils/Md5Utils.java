package info.yymh.blogmanager.utils;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author sikunliang
 * @Package info.yymh.blogmanager.utils
 * @ClassName:
 * @date 2020/3/25
 * @Description 对密码进行加密
 */
public class Md5Utils {
    public static String switchMd5(String key) throws NoSuchAlgorithmException {
        MessageDigest messageDigest=MessageDigest.getInstance("MD5");
        messageDigest.update(key.getBytes());
        byte[] digest=messageDigest.digest();
        String myhash= DatatypeConverter.printHexBinary(digest).toUpperCase();
        return myhash;
    }
}
