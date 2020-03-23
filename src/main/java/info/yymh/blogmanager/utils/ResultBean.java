package info.yymh.blogmanager.utils;

import java.util.HashMap;
import java.util.List;

/**
 * @author sikunliang
 * @Package info.yymh.blogmanager.utils
 * @ClassName:
 * @date 2020/3/20
 * @Description 规定返回结果的值
 */
public class ResultBean {
    private  String code;
    private String message;
    private List<HashMap<String,String>> resultLists;
    private Integer rows;
    private String count;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<HashMap<String, String>> getResultLists() {
        return resultLists;
    }

    public void setResultLists(List<HashMap<String, String>> resultLists) {
        this.resultLists = resultLists;
    }
}
