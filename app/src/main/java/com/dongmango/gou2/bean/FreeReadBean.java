package com.dongmango.gou2.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */

public class FreeReadBean {

    private List<SlideBean> slide;
    private List<XinBean> xin;
    private List<HotBean> hot;

    public List<SlideBean> getSlide() {
        return slide;
    }

    public void setSlide(List<SlideBean> slide) {
        this.slide = slide;
    }

    public List<XinBean> getXin() {
        return xin;
    }

    public void setXin(List<XinBean> xin) {
        this.xin = xin;
    }

    public List<HotBean> getHot() {
        return hot;
    }

    public void setHot(List<HotBean> hot) {
        this.hot = hot;
    }

    public static class SlideBean {
        /**
         * id : 27
         * cid : 80
         * type : 0
         * bookname : NBA特刊2017年1月上
         * target_id : 1147
         * cover : http://www.dongmango.cn/Uploads/Picture/2017-02-10/589d22875a28c.jpg
         */

        private String id;
        private String cid;
        private String type;
        private String bookname;
        private String target_id;
        private String cover;

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

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBookname() {
            return bookname;
        }

        public void setBookname(String bookname) {
            this.bookname = bookname;
        }

        public String getTarget_id() {
            return target_id;
        }

        public void setTarget_id(String target_id) {
            this.target_id = target_id;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }
    }

    public static class XinBean {
        /**
         * id : 1185
         * bookname : NBA2017.2上
         * logo : http://www.dongmango.cn/Uploads/Picture/2017-03-17/58cb46f339618.jpg
         */

        private String id;
        private String bookname;
        private String logo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBookname() {
            return bookname;
        }

        public void setBookname(String bookname) {
            this.bookname = bookname;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }

    public static class HotBean {
        /**
         * id : 1185
         * bookname : NBA2017.2上
         * logo : http://www.dongmango.cn/Uploads/Picture/2017-03-17/58cb46f339618.jpg
         */

        private String id;
        private String bookname;
        private String logo;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBookname() {
            return bookname;
        }

        public void setBookname(String bookname) {
            this.bookname = bookname;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }
    }
}
