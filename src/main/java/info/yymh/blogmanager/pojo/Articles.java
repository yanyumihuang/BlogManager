package info.yymh.blogmanager.pojo;

/**
 * @author sikunliang
 * @Package info.yymh.blogmanager
 * @ClassName:
 * @date 2020/3/23
 * @Description
 */
public class Articles {
    private long id;
    private String title;
    private String author;
    private String category;
    private String introduction;
    private String address;
    private String createDate;
    private String modifyDate;
    private boolean serret;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }


    public String getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(String modifyDate) {
        this.modifyDate = modifyDate;
    }

    public boolean isSerret() {
        return serret;
    }

    public void setSerret(boolean serret) {
        this.serret = serret;
    }
}

