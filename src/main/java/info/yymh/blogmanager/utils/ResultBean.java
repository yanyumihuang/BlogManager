package info.yymh.blogmanager.utils;

import java.util.HashMap;
import java.util.List;

/**
 *  规定返回结果的值
 * @author sikunliang
 * @date 2020/3/20
 */
public class ResultBean {
    /**
     * 服务器状态
     */
    private  String code;
    /**
     * 返回信息
     */
    private String message;
    /**
     * 返回结果集
     */
    private List<HashMap<String,String>> resultLists;
    /**
     * 本次查询数量
     */
    private Integer rows;
    /**
     *此功能总数量
     */
    private String count;

    /**
     * 查询成功失败状态码
     */
    private String statue;

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



    public void setResultLists(List<HashMap<String, String>> resultLists) {
        this.resultLists = resultLists;
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public List<HashMap<String, String>> getResultLists() {
        return resultLists;
    }
}
