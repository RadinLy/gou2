package com.dongmango.gou2.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */

public class TestDataUtil {
    public static List getList() {
        List<Object> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new Object());
        }
        return list;
    }

    public static List getList(int pageSize) {
        List<Object> list = new ArrayList<>();
        if (pageSize > 0) {
            for (int i = 0; i < pageSize; i++) {
                list.add(new Object());
            }
        }
        return list;
    }

    public static List<String> getImgList() {
        List<String> list = new ArrayList<>();
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492159221334&di=4e8ce381fdd94e21ebfbf9733379e20b&imgtype=jpg&src=http%3A%2F%2Fimg4.imgtn.bdimg.com%2Fit%2Fu%3D3690651282%2C4074945279%26fm%3D214%26gp%3D0.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492159261913&di=2500bd44106e4f9b5123098ff2554739&imgtype=0&src=http%3A%2F%2Fimg2.myhsw.cn%2F2015-08-11%2F10d388fc.jpg");
        list.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492159333498&di=e040ad1d572a194f7a51d63c222bf82e&imgtype=0&src=http%3A%2F%2Fdl.bizhi.sogou.com%2Fimages%2F2012%2F09%2F30%2F43225.jpg");
        return list;
    }

    public static String getWebUrl() {
        return "http://auto.3g.163.com/haval/15101/#khd002";
        //return "http://3g.163.com/touch/article.html?from=auto&docid=CI5GF4620008856R";
    }

    public static String getText() {
        return "教学要求： \n" +
                "学习并掌握常用商品介绍的写作方法。 教学重点与难点： \n" +
                "常用商品介绍的写法，尤其是以介绍商品用途为主和以介绍商品选购为主的商品介绍的写法。 教学内容与步骤： \n" +
                "一、新课引入：从同学们常见的对商品的推销中引入本课的学习。 二、商品介绍的概念： \n" +
                "商品介绍是商品经营者或服务提供者扩大销售或提供服务而简要地介绍商品的性能、商品的信息，使用保养等知识的一种实用性文体。 三、商品介绍与商品广告的区别： \n" +
                "主要有以下三方面的区别，其一，作者不同，商品介绍的作者是商品销售的人员，而商业广告的作者是广告的专业创作人员；其二，文字表达形式不同，商品介绍的文字朴实，一般使用叙述、说明性的语言，商业广告可以运用多种表现形式，语言生动形象活泼；其三是表达形式不同，商品内介绍主要用文字说明，商业广告可以使用多种表达形式。 四、商品介绍与产品说明书的区别： \n" +
                "主要有三方面的区别，一是目的不同，商品介绍是指导消费，属于售前服务，产品说明是为了指导消费者使用保养商品，属于售后服务；二是内容不同，商品介绍侧重于商品的某一方面的知识，产品说明是对某一种商品的比较全面地作知识说明，让消费者掌握；三是作者不同，商品介绍的作者一般是销售商品的有关人员，产品说明的作者一般是生产商品的企业有关人员。 五、商品介绍的作用： １、指导消费的作用。 ２、提供商品信息的作用。 ３、提高商品的使用效率作用。 六、商品介绍的分类： \n" +
                "商品介绍的类别一般有以下的划分： １、以介绍对商品的评价为主的商品介绍。 ２、以介绍商品品种和规格为主的商品介绍。 ３、以介绍商品质量要求为主的商品介绍。 ４、以介绍商品选购与鉴别为主的商品介绍。";
        //return "http://3g.163.com/touch/article.html?from=auto&docid=CI5GF4620008856R";
    }
}
