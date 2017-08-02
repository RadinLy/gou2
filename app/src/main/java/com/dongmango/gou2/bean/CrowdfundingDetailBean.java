package com.dongmango.gou2.bean;

/**
 * Created by Administrator on 2017/8/1.
 */

public class CrowdfundingDetailBean {

    /**
     * id : 1
     * image : http://www.dongmango.cn/Uploads/Picture/2016-11-17/582d1941e0cd0.jpg
     * authorid : 1
     * intro : 众筹测试
     * status : 1
     * content : 我的颜色是什么 </h1>这是什么形状</h1>我的身体</h1>这是什么</h1></p>
     * create_time : 1482988000
     * end_time : 1504195200
     * now_amount : 266.00
     * people_num : 3
     * least_money : 10.00
     * author_avatar : http://www.dongmango.cn/Uploads/face.jpg
     */

    private String id;
    private String image;
    private String title;
    private String authorid;
    private String intro;
    private String status;
    private String content;
    private String create_time;
    private String end_time;
    private String now_amount;
    private String people_num;
    private String least_money;
    private String author_avatar;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorid() {
        return authorid;
    }

    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getLeast_money() {
        return least_money;
    }

    public void setLeast_money(String least_money) {
        this.least_money = least_money;
    }

    public String getAuthor_avatar() {
        return author_avatar;
    }

    public void setAuthor_avatar(String author_avatar) {
        this.author_avatar = author_avatar;
    }
}
