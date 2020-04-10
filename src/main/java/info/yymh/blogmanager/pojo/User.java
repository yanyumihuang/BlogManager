package info.yymh.blogmanager.pojo;

public class User {

  private String id;
  private String name;
  private String pwd;
  private String regeTime;
  private String roles;


  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }


  public String getRegeTime() {
    return regeTime;
  }

  public void setRegeTime(String regeTime) {
    this.regeTime = regeTime;
  }


  public String getRoles() {
    return roles;
  }

  public void setRoles(String roles) {
    this.roles = roles;
  }

}
