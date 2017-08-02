package com.dongmango.gou2.activity.freeread;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.dev.superframe.base.BaseActivity;
import com.dev.superframe.http.HttpManager;
import com.dev.superframe.utils.Json;
import com.dev.superframe.utils.TopBarUtil;
import com.dongmango.gou2.R;
import com.dongmango.gou2.bean.FreeBookDetailBean;
import com.dongmango.gou2.httpmanager.GetJsonUtil;
import com.dongmango.gou2.httpmanager.HttpJsonUtil;
import com.dongmango.gou2.view.GLoadingDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;


/**
 * Created by yiw on 2016/1/6.
 */
public class BookPagerActivity extends BaseActivity {
    public static final String INTENT_IMAGESIZE = "imagesize";
    @BindView(R.id.vp_display)
    ViewPager vpDisplay;
    @BindView(R.id.tv_pager)
    TextView tvPager;
    @BindView(R.id.ll_indicator)
    LinearLayout llIndicator;

    public ImageSize imageSize;

    private List<FreeBookDetailBean.ListDataBean> mList = new ArrayList<>();
    private ImageAdapter mAdapter;
    private int page = 1;
    private int totalPage = 0;
    private int total = 0;

    public static void createIntent(Context context, String id, String title, ImageSize imageSize) {
        Intent intent = new Intent(context, BookPagerActivity.class);
        intent.putExtra(INTENT_ID, id);
        intent.putExtra(INTENT_TITLE, title);
        intent.putExtra(INTENT_IMAGESIZE, imageSize);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookpager);
        ButterKnife.bind(this);

        initView();
        initData();
        initEvent();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void initView() {
        TopBarUtil.initBtnBack(getActivity(), R.id.tv_base_back);
        TopBarUtil.initTitle(getActivity(), R.id.tv_base_title, "免费阅读区");
        autoSetTitle();
    }

    @Override
    public void initData() {
        imageSize = (ImageSize) getIntent().getSerializableExtra(INTENT_IMAGESIZE);

        httpData();

        mAdapter = new ImageAdapter(this);
        mAdapter.setDatas(mList);
        mAdapter.setImageSize(imageSize);
        vpDisplay.setAdapter(mAdapter);
    }

    @Override
    public void initEvent() {
        vpDisplay.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tvPager.setText(position + 1 + "/" + total);
                if (mList.size() > 0) {
                    if (position == mList.size() - 5) {
                        if (totalPage > page) {
                            page++;
                            httpData();
                        }
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void httpData() {
        String id = getIntentText(INTENT_ID);
        HttpJsonUtil.getFreeDetail(id, page + "", 10001, new HttpManager.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                if (0 == GetJsonUtil.getResponseCode(resultJson)) {
                    String json = GetJsonUtil.getResponseData(resultJson);
                    bindListData(Json.parseObject(json, FreeBookDetailBean.class));
                } else {
                    showShortToast(GetJsonUtil.getResponseError(resultJson));
                }
                GLoadingDialog.cancelDialogForLoading();
            }
        });
    }

    private void bindListData(FreeBookDetailBean o) {
        if (o == null) {
            return;
        }
        totalPage = o.getTotalPage();
        total = o.getTotal();
        tvPager.setText("1/" + total);
        mList.addAll(o.getListData());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            return super.dispatchTouchEvent(ev);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private static class ImageAdapter extends PagerAdapter {

        private List<FreeBookDetailBean.ListDataBean> datas = new ArrayList<>();
        private LayoutInflater inflater;
        private Context context;
        private ImageSize imageSize;
        private ImageView smallImageView = null;

        public void setDatas(List<FreeBookDetailBean.ListDataBean> datas) {
            if (datas != null)
                this.datas = datas;
        }

        public void setImageSize(ImageSize imageSize) {
            this.imageSize = imageSize;
        }

        public ImageAdapter(Context context) {
            this.context = context;
            this.inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            if (datas == null) return 0;
            return datas.size();
        }


        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View view = inflater.inflate(R.layout.item_bookpager, container, false);
            if (view != null) {
                final PhotoView imageView = (PhotoView) view.findViewById(R.id.image);

                if (imageSize != null) {
                    //预览imageView
                    smallImageView = new ImageView(context);
                    FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(imageSize.getWidth(), imageSize.getHeight());
                    layoutParams.gravity = Gravity.CENTER;
                    smallImageView.setLayoutParams(layoutParams);
                    smallImageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    ((FrameLayout) view).addView(smallImageView);
                }

                //loading
                final ProgressBar loading = new ProgressBar(context);
                Drawable d = context.getResources().getDrawable(R.drawable.progress_loading);
                d.setBounds(1,1,16,16);
                loading.setIndeterminateDrawable(d);
                FrameLayout.LayoutParams loadingLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                loadingLayoutParams.gravity = Gravity.CENTER;
                loading.setLayoutParams(loadingLayoutParams);
                ((FrameLayout) view).addView(loading);

                final String imgurl = datas.get(position).getImage();

                Glide.with(context)
                        .load(imgurl)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存多个尺寸
                        .thumbnail(0.1f)//先显示缩略图  缩略图为原图的1/10
                        .error(R.mipmap.no_img)
                        .into(new GlideDrawableImageViewTarget(imageView) {
                            @Override
                            public void onLoadStarted(Drawable placeholder) {
                                super.onLoadStarted(placeholder);
                               /* if(smallImageView!=null){
                                    smallImageView.setVisibility(View.VISIBLE);
                                    Glide.with(context).load(imgurl).into(smallImageView);
                                }*/
                                loading.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                                super.onLoadFailed(e, errorDrawable);
                                /*if(smallImageView!=null){
                                    smallImageView.setVisibility(View.GONE);
                                }*/
                                loading.setVisibility(View.GONE);
                            }

                            @Override
                            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                                super.onResourceReady(resource, animation);
                                loading.setVisibility(View.GONE);
                                /*if(smallImageView!=null){
                                    smallImageView.setVisibility(View.GONE);
                                }*/
                            }
                        });
//                imageView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
//                    @Override
//                    public void onPhotoTap(View view, float x, float y) {
//                        ((Activity) context).finish();
//                    }
//                });
                container.addView(view, 0);
            }
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

        @Override
        public void restoreState(Parcelable state, ClassLoader loader) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static class ImageSize implements Serializable {

        private int width;
        private int height;

        public ImageSize(int width, int height) {
            this.width = width;
            this.height = height;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }
    }
}
