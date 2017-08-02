package com.dongmango.gou2.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/31.
 */

public class RecomGoodsBean {

    private List<NewwBean> neww;
    private List<BoutiqueBean> boutique;

    public List<NewwBean> getNeww() {
        return neww;
    }

    public void setNeww(List<NewwBean> neww) {
        this.neww = neww;
    }

    public List<BoutiqueBean> getBoutique() {
        return boutique;
    }

    public void setBoutique(List<BoutiqueBean> boutique) {
        this.boutique = boutique;
    }

    public static class NewwBean {
        /**
         * id : 25
         * cid : 137
         * title : 漫画周刊
         * image : http://www.dongmango.cn/Uploads/Picture/2016-11-17/582d3a1325ecd.jpg
         * type : magazine
         */

        private String id;
        private String cid;
        private String title;
        private String image;
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }

    public static class BoutiqueBean {
        /**
         * id : 49
         * cid : 138
         * title : 百家讲坛 红色版
         * image : http://www.dongmango.cn/Uploads/Picture/2016-12-28/586356f0e0dbc.jpg
         * type : magazine
         */

        private String id;
        private String cid;
        private String title;
        private String image;
        private String type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
