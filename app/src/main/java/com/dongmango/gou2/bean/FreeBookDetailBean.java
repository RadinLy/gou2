package com.dongmango.gou2.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */

public class FreeBookDetailBean {

    /**
     * totalPage : 8
     * listData : [{"id":"530","infoid":"1185","image":"http://www.dongmango.cn/Uploads/Picture/2017-03-17/58cb454ca0234.jpg","sort":"100000"},{"id":"529","infoid":"1185","image":"http://www.dongmango.cn/Uploads/Picture/2017-03-17/58cb454a4eaab.jpg","sort":"100000"},{"id":"528","infoid":"1185","image":"http://www.dongmango.cn/Uploads/Picture/2017-03-17/58cb45476dfd9.jpg","sort":"100000"},{"id":"527","infoid":"1185","image":"http://www.dongmango.cn/Uploads/Picture/2017-03-17/58cb45453de08.jpg","sort":"100000"},{"id":"526","infoid":"1185","image":"http://www.dongmango.cn/Uploads/Picture/2017-03-17/58cb4542c3e1f.jpg","sort":"100000"},{"id":"525","infoid":"1185","image":"http://www.dongmango.cn/Uploads/Picture/2017-03-17/58cb4540dd9e6.jpg","sort":"100000"},{"id":"524","infoid":"1185","image":"http://www.dongmango.cn/Uploads/Picture/2017-03-17/58cb453d74245.jpg","sort":"100000"},{"id":"523","infoid":"1185","image":"http://www.dongmango.cn/Uploads/Picture/2017-03-17/58cb453a2cd93.jpg","sort":"100000"},{"id":"522","infoid":"1185","image":"http://www.dongmango.cn/Uploads/Picture/2017-03-17/58cb4537084e6.jpg","sort":"100000"},{"id":"521","infoid":"1185","image":"http://www.dongmango.cn/Uploads/Picture/2017-03-17/58cb45347420d.jpg","sort":"100000"},{"id":"520","infoid":"1185","image":"http://www.dongmango.cn/Uploads/Picture/2017-03-17/58cb4531593bb.jpg","sort":"100000"},{"id":"519","infoid":"1185","image":"http://www.dongmango.cn/Uploads/Picture/2017-03-17/58cb452f0e679.jpg","sort":"100000"},{"id":"518","infoid":"1185","image":"http://www.dongmango.cn/Uploads/Picture/2017-03-17/58cb452c127d5.jpg","sort":"100000"},{"id":"517","infoid":"1185","image":"http://www.dongmango.cn/Uploads/Picture/2017-03-17/58cb452969fa4.jpg","sort":"100000"},{"id":"516","infoid":"1185","image":"http://www.dongmango.cn/Uploads/Picture/2017-03-17/58cb4526a6453.jpg","sort":"100000"}]
     * total : 114
     */

    private int totalPage;
    private int total;
    private List<ListDataBean> listData;

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ListDataBean> getListData() {
        return listData;
    }

    public void setListData(List<ListDataBean> listData) {
        this.listData = listData;
    }

    public static class ListDataBean {
        /**
         * id : 530
         * infoid : 1185
         * image : http://www.dongmango.cn/Uploads/Picture/2017-03-17/58cb454ca0234.jpg
         * sort : 100000
         */

        private String id;
        private String infoid;
        private String image;
        private String sort;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getInfoid() {
            return infoid;
        }

        public void setInfoid(String infoid) {
            this.infoid = infoid;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getSort() {
            return sort;
        }

        public void setSort(String sort) {
            this.sort = sort;
        }
    }
}
