package info.yymh.blogmanager.pojo;

import javax.annotation.Resource;

/**
 * Created by sikunliang on 2019/7/18 10:56
 */
@Resource
public class MailModel {
    private String fromaddress;
    private String toAddress;
    private String content;
    private String subject;

    public String getFromaddress() {
        return fromaddress;
    }

    public void setFromaddress(String fromaddress) {
        this.fromaddress = fromaddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "MailModel{" +
                "fromaddress='" + fromaddress + '\'' +
                ", toAddress='" + toAddress + '\'' +
                ", content='" + content + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
