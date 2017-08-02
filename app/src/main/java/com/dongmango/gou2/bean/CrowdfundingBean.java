package com.dongmango.gou2.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */

public class CrowdfundingBean {

    /**
     * totalPage : 1
     * listData : [{"id":"1","title":"众筹测试","cover":"http://www.dongmango.cn/Uploads/Picture/2016-11-17/582d1941e0cd0.jpg","status":"1","now_amount":"266.00","people_num":"3","create_time":"1482988000","end_time":"1504195200"}]
     * total : 1
     */

    private int totalPage;
    private String total;
    private List<ListDataBean> listData;

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

    public static class ListDataBean {
        /**
         * id : 1
         * title : 众筹测试
         * cover : http://www.dongmango.cn/Uploads/Picture/2016-11-17/582d1941e0cd0.jpg
         * status : 1
         * now_amount : 266.00
         * people_num : 3
         * create_time : 1482988000
         * end_time : 1504195200
         */

        private String id;
        private String title;
        private String cover;
        private String status;
        private String now_amount;
        private String people_num;
        private String create_time;
        private String end_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getNow_amount() {
            return now_amount;
        }

        public void setNow_amount(String now_amount) {
            this.now_amount = now_amount;
        }

        public String getPeople_num() {
            return people_num;
        }

        public void setPeople_num(String people_num) {
            this.people_num = people_num;
        }

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }
    }
}
