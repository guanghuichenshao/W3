package com.zyc.po;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/1/25 0025.
 */
public class Message implements Serializable {
    private String zid;//发主贴的用户id
    private String title;//当前帖子主题
    private String rid;//发回帖的用户ID
    private String rtitle;//发回帖的标题内容

    public String getRtitle() {
        return rtitle;
    }

    public void setRtitle(String rtitle) {
        this.rtitle = rtitle;
    }

    public String getZid() {
        return zid;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public void setZid(String zid) {
        this.zid = zid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}