package com.dongmango.gou2.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */

public class CartoonBean {

    /**
     * id : 222
     * ptitle : 2017.01
     * period : 1
     * sn : 170207111211
     * price : 15.00
     * retail_price : 15.00
     * subsidy : 0
     * sell_time : 1486396800
     * cover : http://www.dongmango.cn/Uploads/Picture/2017-02-07/58993b0977fb8.jpg
     * magazine_id : 1050
     * create_time : 1486437131
     * update_time : 1498186060
     * get_time : 1486828800
     * extended_time : 0
     * is_recommend : 0
     * rcomd_sort : 0
     * merchant_subsidy : 0
     * user_subsidy : 100
     * book : {"bookname":"童趣 小公主","author":" ","publishers":"人民邮电出版社","information":"\u200d<\/span>\u200d<\/span>在公主故事里，总是有着数不清的盛大舞会，我们能不能去参加这样的舞会呢?让自己置身于公主舞会的氛围中，换上漂亮的衣裳。变身为闪亮的公主之星\u2026\u2026这一切并不是梦幻，快打开这本特辑，参加精彩的公主舞会，尽情地欢乐吧!\u200d<\/span>\u200d<\/span><\/span><\/p>","cid":"1"}
     * preview : []
     * periods : [{"id":"529","magazine_id":"1050","period":"6","cover":"http://www.dongmango.cn/Uploads/Picture/2017-06-23/594c81b5ef79a.png","ptitle":"2017.06"},{"id":"528","magazine_id":"1050","period":"5","cover":"http://www.dongmango.cn/Uploads/Picture/2017-06-23/594c819cebd5d.png","ptitle":"2017.05"},{"id":"527","magazine_id":"1050","period":"4","cover":"http://www.dongmango.cn/Uploads/Picture/2017-06-23/594c818e79469.png","ptitle":"2017.04"},{"id":"526","magazine_id":"1050","period":"3","cover":"http://www.dongmango.cn/Uploads/Picture/2017-06-23/594c817e2bea6.png","ptitle":"2017.03"},{"id":"525","magazine_id":"1050","period":"2","cover":"http://www.dongmango.cn/Uploads/Picture/2017-06-23/594c816d27e00.jpg","ptitle":"2017.02"},{"id":"222","magazine_id":"1050","period":"1","cover":"http://www.dongmango.cn/Uploads/Picture/2017-02-07/58993b0977fb8.jpg","ptitle":"2017.01"}]
     * peripherys : [{"id":"41","title":"公主与萌宠特辑4","period":"","magazine_id":"0","cover":"http://www.dongmango.cn/Uploads/Picture/2017-02-07/58993d21434fd.jpg","price":"18.00","retail_price":"20.00","get_time":"1486828800","extended_time":"0","target_type":"2","Stringro":"公主与萌宠特辑4","status":"在售中"}]
     * is_collection : 0
     * rebate : 1
     */

    private String id;
    private String ptitle;
    private String period;
    private String sn;
    private String price;
    private String retail_price;
    private String subsidy;
    private String sell_time;
    private String cover;
    private String magazine_id;
    private String create_time;
    private String update_time;
    private String get_time;
    private String extended_time;
    private String is_recommend;
    private String rcomd_sort;
    private String merchant_subsidy;
    private String user_subsidy;
    private BookBean book;
    private String is_collection;
    private String rebate;
    private List<?> preview;
    private List<PeriodsBean> periods;
    private List<PeripherysBean> peripherys;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPtitle() {
        return ptitle;
    }

    public void setPtitle(String ptitle) {
        this.ptitle = ptitle;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRetail_price() {
        return retail_price;
    }

    public void setRetail_price(String retail_price) {
        this.retail_price = retail_price;
    }

    public String getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(String subsidy) {
        this.subsidy = subsidy;
    }

    public String getSell_time() {
        return sell_time;
    }

    public void setSell_time(String sell_time) {
        this.sell_time = sell_time;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getMagazine_id() {
        return magazine_id;
    }

    public void setMagazine_id(String magazine_id) {
        this.magazine_id = magazine_id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    public String getGet_time() {
        return get_time;
    }

    public void setGet_time(String get_time) {
        this.get_time = get_time;
    }

    public String getExtended_time() {
        return extended_time;
    }

    public void setExtended_time(String extended_time) {
        this.extended_time = extended_time;
    }

    public String getIs_recommend() {
        return is_recommend;
    }

    public void setIs_recommend(String is_recommend) {
        this.is_recommend = is_recommend;
    }

    public String getRcomd_sort() {
        return rcomd_sort;
    }

    public void setRcomd_sort(String rcomd_sort) {
        this.rcomd_sort = rcomd_sort;
    }

    public String getMerchant_subsidy() {
        return merchant_subsidy;
    }

    public void setMerchant_subsidy(String merchant_subsidy) {
        this.merchant_subsidy = merchant_subsidy;
    }

    public String getUser_subsidy() {
        return user_subsidy;
    }

    public void setUser_subsidy(String user_subsidy) {
        this.user_subsidy = user_subsidy;
    }

    public BookBean getBook() {
        return book;
    }

    public void setBook(BookBean book) {
        this.book = book;
    }

    public String getIs_collection() {
        return is_collection;
    }

    public void setIs_collection(String is_collection) {
        this.is_collection = is_collection;
    }

    public String getRebate() {
        return rebate;
    }

    public void setRebate(String rebate) {
        this.rebate = rebate;
    }

    public List<?> getPreview() {
        return preview;
    }

    public void setPreview(List<?> preview) {
        this.preview = preview;
    }

    public List<PeriodsBean> getPeriods() {
        return periods;
    }

    public void setPeriods(List<PeriodsBean> periods) {
        this.periods = periods;
    }

    public List<PeripherysBean> getPeripherys() {
        return peripherys;
    }

    public void setPeripherys(List<PeripherysBean> peripherys) {
        this.peripherys = peripherys;
    }

    public static class BookBean {
        /**
         * bookname : 童趣 小公主
         * author :
         * publishers : 人民邮电出版社
         * information : ‍</span>‍</span>在公主故事里，总是有着数不清的盛大舞会，我们能不能去参加这样的舞会呢?让自己置身于公主舞会的氛围中，换上漂亮的衣裳。变身为闪亮的公主之星……这一切并不是梦幻，快打开这本特辑，参加精彩的公主舞会，尽情地欢乐吧!‍</span>‍</span></span></p>
         * cid : 1
         */

        private String bookname;
        private String author;
        private String publishers;
        private String information;
        private String cid;

        public String getBookname() {
            return bookname;
        }

        public void setBookname(String bookname) {
            this.bookname = bookname;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getPublishers() {
            return publishers;
        }

        public void setPublishers(String publishers) {
            this.publishers = publishers;
        }

        public String getInformation() {
            return information;
        }

        public void setInformation(String information) {
            this.information = information;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }
    }

    public static class PeriodsBean {
        /**
         * id : 529
         * magazine_id : 1050
         * period : 6
         * cover : http://www.dongmango.cn/Uploads/Picture/2017-06-23/594c81b5ef79a.png
         * ptitle : 2017.06
         */

        private String id;
        private String magazine_id;
        private String period;
        private String cover;
        private String ptitle;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMagazine_id() {
            return magazine_id;
        }

        public void setMagazine_id(String magazine_id) {
            this.magazine_id = magazine_id;
        }

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getPtitle() {
            return ptitle;
        }

        public void setPtitle(String ptitle) {
            this.ptitle = ptitle;
        }
    }

    public static class PeripherysBean {
        /**
         * id : 41
         * title : 公主与萌宠特辑4
         * period :
         * magazine_id : 0
         * cover : http://www.dongmango.cn/Uploads/Picture/2017-02-07/58993d21434fd.jpg
         * price : 18.00
         * retail_price : 20.00
         * get_time : 1486828800
         * extended_time : 0
         * target_type : 2
         * Stringro : 公主与萌宠特辑4
         * status : 在售中
         */

        private String id;
        private String title;
        private String period;
        private String magazine_id;
        private String cover;
        private String price;
        private String retail_price;
        private String get_time;
        private String extended_time;
        private String target_type;
        private String Stringro;
        private String status;

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

        public String getPeriod() {
            return period;
        }

        public void setPeriod(String period) {
            this.period = period;
        }

        public String getMagazine_id() {
            return magazine_id;
        }

        public void setMagazine_id(String magazine_id) {
            this.magazine_id = magazine_id;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getRetail_price() {
            return retail_price;
        }

        public void setRetail_price(String retail_price) {
            this.retail_price = retail_price;
        }

        public String getGet_time() {
            return get_time;
        }

        public void setGet_time(String get_time) {
            this.get_time = get_time;
        }

        public String getExtended_time() {
            return extended_time;
        }

        public void setExtended_time(String extended_time) {
            this.extended_time = extended_time;
        }

        public String getTarget_type() {
            return target_type;
        }

        public void setTarget_type(String target_type) {
            this.target_type = target_type;
        }

        public String getIntro() {
            return Stringro;
        }

        public void setIntro(String Stringro) {
            this.Stringro = Stringro;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}