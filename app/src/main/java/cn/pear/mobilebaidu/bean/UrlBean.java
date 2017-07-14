package cn.pear.mobilebaidu.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by liuliang on 2017/7/13.
 */
@Entity
public class UrlBean {
    @Id(autoincrement = true)
    protected Long id;

    @Property(nameInDb = "title")
    private String title;

    @Property(nameInDb = "url")
    protected String url;

    @Property(nameInDb = "update")
    protected long update;

    @Property(nameInDb = "ur_icon")
    protected String urlIcon;

    public String getUrlIcon() {
        return this.urlIcon;
    }

    public void setUrlIcon(String urlIcon) {
        this.urlIcon = urlIcon;
    }

    public long getUpdate() {
        return this.update;
    }

    public void setUpdate(long update) {
        this.update = update;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Generated(hash = 1707693005)
    public UrlBean(Long id, String title, String url, long update, String urlIcon) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.update = update;
        this.urlIcon = urlIcon;
    }

    @Generated(hash = 241588977)
    public UrlBean() {
    }

}
