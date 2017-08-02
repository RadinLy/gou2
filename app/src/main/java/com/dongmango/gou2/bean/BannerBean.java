package com.dongmango.gou2.bean;

/**
 * Created by Administrator on 2017/7/31.
 */

public class BannerBean {

    /**
     * cid : 1
     * path : http://www.dongmango.cn/Uploads/Picture/2017-02-06/5897e986aa694.jpg
     * target_type : 1
     * target_id : 19
     */

    private String cid;
    private String path;
    private String target_type;
    private String target_id;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTarget_type() {
        return target_type;
    }

    public void setTarget_type(String target_type) {
        this.target_type = target_type;
    }

    public String getTarget_id() {
        return target_id;
    }

    public void setTarget_id(String target_id) {
        this.target_id = target_id;
    }
}
