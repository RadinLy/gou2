package com.dongmango.gou2.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.superframe.baseadapter.BaseAdapterHelper;
import com.dev.superframe.baseadapter.QuickAdapter;
import com.dev.superframe.utils.DateTimeUtil;
import com.dev.superframe.utils.NumUtil;
import com.dev.superframe.utils.glide.GlideUtil;
import com.dev.superframe.view.SquareImageView;
import com.dongmango.gou2.R;
import com.dongmango.gou2.bean.BookClassBean;
import com.dongmango.gou2.bean.CartoonBean;
import com.dongmango.gou2.bean.ClassBean;
import com.dongmango.gou2.bean.CrowdfundingBean;
import com.dongmango.gou2.bean.FreeReadBean;
import com.dongmango.gou2.utils.GetStateValueUtil;
import com.dongmango.gou2.view.RectangleImageView;
import com.dongmango.gou2.view.numberprogressbar.NumberProgressBar;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/4/14.
 */

public class AdapterUtil<T> {
    /**
     * 分类父级
     */
    public int iCheckFather = 0;

    public QuickAdapter<T> getClassFatherAdapter(final Context context, List<T> list) {
        return new QuickAdapter<T>(context, R.layout.item_class_father, list) {
            @SuppressWarnings("deprecation")
            @Override
            protected void convert(BaseAdapterHelper helper, T item) {
                ClassBean o = (ClassBean) item;
                if (helper.getPosition() == iCheckFather) {
                    helper.setBackgroundColor(R.id.v_tag, context.getResources().getColor(R.color.red));
                    helper.setBackgroundColor(R.id.tv_name, context.getResources().getColor(R.color.white));
                    helper.setBackgroundColor(R.id.ll_bg, context.getResources().getColor(R.color.white));
                    helper.setTextColor(R.id.tv_name, context.getResources().getColor(R.color.red));
                } else {
                    helper.setBackgroundColor(R.id.v_tag, context.getResources().getColor(R.color.activity_bg));
                    helper.setBackgroundColor(R.id.tv_name, context.getResources().getColor(R.color.activity_bg));
                    helper.setBackgroundColor(R.id.ll_bg, context.getResources().getColor(R.color.activity_bg));
                    helper.setTextColor(R.id.tv_name, context.getResources().getColor(R.color.text_color_deep));
                }
                helper.setText(R.id.tv_name, o.getCname());
            }
        };
    }

    /**
     * 分类子级
     */
    public QuickAdapter<T> getClassChildAdapter(final Context context, List<T> list) {
        return new QuickAdapter<T>(context, R.layout.item_class_child, list) {
            @SuppressWarnings("deprecation")
            @Override
            protected void convert(BaseAdapterHelper helper, T item) {
                ClassBean o = (ClassBean) item;
                SquareImageView iv = helper.getView().findViewById(R.id.iv_display);
                GlideUtil.loadImageView(context, R.mipmap.no_img, o.getImage(), iv);
                //GlideUtil.loadImageView(context, R.mipmap.no_img, "http://bpic.588ku.com/element_origin_min_pic/17/07/12/e2c51e6c1468a96cf68154345f3a6a2c.jpg", iv);
                helper.setText(R.id.tv_name, o.getCname());
            }
        };
    }

    /**
     * 众筹
     */
    public QuickAdapter<T> getCrowdfundingAdapter(final Context context, List<T> list) {
        return new QuickAdapter<T>(context, R.layout.item_crowdfunding, list) {
            @Override
            protected void convert(BaseAdapterHelper helper, T item) {
                CrowdfundingBean.ListDataBean o = (CrowdfundingBean.ListDataBean) item;

                Map<String, String> mapTotal = DateTimeUtil.getTimeDifference(new Date(Long.valueOf(o.getCreate_time() + "000")), new Date(Long.valueOf(o.getEnd_time() + "000")));
                Map<String, String> mapPass = DateTimeUtil.getTimeDifference(new Date(Long.valueOf(o.getCreate_time() + "000")), new Date());
                Map<String, String> map = DateTimeUtil.getTimeDifference(new Date(), new Date(Long.valueOf(o.getEnd_time() + "000")));

                ImageView ivDisplay = helper.getView().findViewById(R.id.iv_display);
                GlideUtil.loadImageView(context, R.mipmap.no_img, o.getCover(), ivDisplay);
                //0未开始 1进行中 2已结束
                helper.setText(R.id.tv_state, GetStateValueUtil.getCrowdfundingStateValue(o.getStatus()));
                helper.setText(R.id.tv_name, o.getTitle());

                int progress = (NumUtil.getInt(mapPass.get("days")) * 24 + NumUtil.getInt(mapPass.get("hours"))) * 100 / (NumUtil.getInt(mapTotal.get("days")) * 24 + NumUtil.getInt(mapTotal.get("hours")));
                NumberProgressBar npbDisplay = helper.getView().findViewById(R.id.npb_display);
                npbDisplay.setProgress(progress);

                helper.setText(R.id.tv_zcrs, o.getPeople_num() + "人");
                helper.setText(R.id.tv_ycje, "￥" + o.getNow_amount());

                helper.setText(R.id.tv_jjssj, map.get("days") + "天" + map.get("hours") + "小时");
            }
        };
    }

    /**
     * 商品
     */
    public QuickAdapter<T> getGoodsAdapter(final Context context, List<T> list) {
        return new QuickAdapter<T>(context, R.layout.item_goods, list) {
            @Override
            protected void convert(BaseAdapterHelper helper, T item) {
                TextView tvMoneyTip = helper.getView().findViewById(R.id.tv_money_tip);
                tvMoneyTip.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
        };
    }

    /**
     * 最新书籍
     */
    public QuickAdapter<T> getNowBookAdapter(final Context context, List<T> list) {
        return new QuickAdapter<T>(context, R.layout.item_book, list) {
            @Override
            protected void convert(BaseAdapterHelper helper, T item) {
                FreeReadBean.XinBean o = (FreeReadBean.XinBean) item;
                RectangleImageView iv = helper.getView().findViewById(R.id.iv_display);
                GlideUtil.loadImageView(context, R.mipmap.no_img, o.getLogo(), iv);

                helper.setText(R.id.tv_name, o.getBookname());
            }
        };
    }

    /**
     * 最热书籍
     */
    public QuickAdapter<T> getHotBookAdapter(final Context context, List<T> list) {
        return new QuickAdapter<T>(context, R.layout.item_book, list) {
            @Override
            protected void convert(BaseAdapterHelper helper, T item) {
                FreeReadBean.HotBean o = (FreeReadBean.HotBean) item;
                RectangleImageView iv = helper.getView().findViewById(R.id.iv_display);
                GlideUtil.loadImageView(context, R.mipmap.no_img, o.getLogo(), iv);

                helper.setText(R.id.tv_name, o.getBookname());
            }
        };
    }

    /**
     * 书籍
     */
    public QuickAdapter<T> getBookAdapter(final Context context, List<T> list) {
        return new QuickAdapter<T>(context, R.layout.item_book, list) {
            @Override
            protected void convert(BaseAdapterHelper helper, T item) {
                BookClassBean.ListDataBean o = (BookClassBean.ListDataBean) item;
                RectangleImageView iv = helper.getView().findViewById(R.id.iv_display);
                GlideUtil.loadImageView(context, R.mipmap.no_img, o.getLogo(), iv);

                helper.setText(R.id.tv_name, o.getBookname());
            }
        };
    }

    /**
     * 附近书刊
     */
    public QuickAdapter<T> getNearCartoonAdapter(final Context context, List<T> list) {
        return new QuickAdapter<T>(context, R.layout.item_near_cartoon, list) {
            @Override
            protected void convert(BaseAdapterHelper helper, T item) {
                CartoonBean.PeripherysBean o = (CartoonBean.PeripherysBean) item;
                ImageView ivDisplay = helper.getView().findViewById(R.id.iv_display);
                GlideUtil.loadImageView(context, R.mipmap.no_img, o.getCover(), ivDisplay);

                helper.setText(R.id.tv_name, o.getTitle());
                helper.setText(R.id.tv_info, o.getIntro());
                helper.setText(R.id.tv_price, "￥" + o.getPrice());
                helper.setText(R.id.tv_state, o.getStatus());
                helper.setText(R.id.tv_price_tip, "￥" + o.getRetail_price());

                TextView tvPriceTip = helper.getView().findViewById(R.id.tv_price_tip);
                tvPriceTip.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            }
        };
    }
}
