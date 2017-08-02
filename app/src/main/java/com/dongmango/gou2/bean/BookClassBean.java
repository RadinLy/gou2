package com.dongmango.gou2.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/11.
 */

public class BookClassBean {

    /**
     * totalPage : 1
     * listData : [{"id":"1455","bookname":"NBA2017.4下","logo":"http://www.dongmango.cn/Uploads/Picture/2017-05-17/591bbdc04001f.png"},{"id":"1452","bookname":"NBA2017.4上","logo":"http://www.dongmango.cn/Uploads/Picture/2017-05-17/591bae3235c36.png"},{"id":"1442","bookname":"NBA2017.3下","logo":"http://www.dongmango.cn/Uploads/Picture/2017-05-16/591a54d00a78e.png"},{"id":"1441","bookname":"NBA2017.3上","logo":"http://www.dongmango.cn/Uploads/Picture/2017-05-16/591a4bcf801a8.png"},{"id":"1186","bookname":"NBA2017.2下","logo":"http://www.dongmango.cn/Uploads/Picture/2017-03-17/58cb48d12147c.jpg"},{"id":"1185","bookname":"NBA2017.2上","logo":"http://www.dongmango.cn/Uploads/Picture/2017-03-17/58cb46f339618.jpg"},{"id":"1184","bookname":"NBA2017.1下","logo":"http://www.dongmango.cn/Uploads/Picture/2017-02-13/58a160878f2dc.jpg"},{"id":"1148","bookname":"NBA2017.1上","logo":"http://www.dongmango.cn/Uploads/Picture/2017-02-10/589d2d4f33ebc.jpg"}]
     * total : 8
     * cate : [{"cid":"84","cname":"漫画类","pid":"80","sort":"1","image":null},{"cid":"85","cname":"小说类","pid":"80","sort":"2","image":null},{"cid":"86","cname":"体育类","pid":"80","sort":"3","image":null},{"cid":"87","cname":"其他","pid":"80","sort":"4","image":null}]
     */

    private int totalPage;
    private String total;
    private List<ListDataBean> listData;
    private List<CateBean> cate;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<ListDataBean> getListData() {
        return listData;
    }

    public void setListData(List<ListDataBean> listData) {
        this.listData = listData;
    }

    public List<CateBean> getCate() {
        return cate;
    }

    public void setCate(List<CateBean> cate) {
        this.cate = cate;
    }

    public static class ListDataBean {
        /**
         * id : 1455
         * bookname : NBA2017.4下
         * logo : http://www.dongmango.cn/Uploads/Picture/2017-05-17/591bbdc04001f.png
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

    public static class CateBean {
        /**
         * cid : 84
         * cname : 漫画类
         * pid : 80
         * sort : 1
         * image : null
         */

        private String cid;
        private String cname;
        private String pid;
        private String sort;
        private String image;

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getCname() {
            return cname;
        }

        public void setCname(String cname) {
            this.cname = cname;
        }

        public String getPid() {
            return pid;
        }

        public void setPid(String pid) {
            this.pid = pid;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
