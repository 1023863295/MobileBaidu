package cn.pear.mobilebaidu.bean;

import java.util.List;

/**
 * Created by liuliang on 2017/7/13.
 */

public class HistoryBean {
    private String groupTitle;
    private List<UrlBean> itemList;

    public HistoryBean(String groupTitle, List<UrlBean> itemList) {
        this.groupTitle = groupTitle;
        this.itemList = itemList;
    }

    public HistoryBean() {
    }

    public void setGroupTitle(String groupTitle) {
        this.groupTitle = groupTitle;
    }

    public void setItemList(List<UrlBean> itemList) {
        this.itemList = itemList;
    }

    public String getGroupTitle() {
        return groupTitle;
    }

    public List<UrlBean> getItemList() {
        return itemList;
    }

    @Override
    public String toString() {
        return "HistoryBean{" +
                "groupTitle='" + groupTitle + '\'' +
                ", itemList=" + itemList +
                '}';
    }
}
