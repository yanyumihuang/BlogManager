package info.yymh.blogmanager.pojo;


public class Comments {

  private String id;
  private String comments;
  private String commentsDate;
  private String  userId;
  private String  articlesId;
  private String  replyId;
  private String devicesType;
  private String browserType;
  private String ip;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }


  public String getCommentsDate() {
    return commentsDate;
  }

  public void setCommentsDate(String commentsDate) {
    this.commentsDate = commentsDate;
  }


  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }


  public String getArticlesId() {
    return articlesId;
  }

  public void setArticlesId(String articlesId) {
    this.articlesId = articlesId;
  }


  public String getReplyId() {
    return replyId;
  }

  public void setReplyId(String replyId) {
    this.replyId = replyId;
  }


  public String getDevicesType() {
    return devicesType;
  }

  public void setDevicesType(String devicesType) {
    this.devicesType = devicesType;
  }


  public String getBrowserType() {
    return browserType;
  }

  public void setBrowserType(String browserType) {
    this.browserType = browserType;
  }


  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

}
