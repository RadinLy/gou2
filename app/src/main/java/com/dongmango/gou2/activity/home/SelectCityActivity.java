package com.dongmango.gou2.activity.home;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.dev.superframe.base.BaseActivity;
import com.dev.superframe.http.HttpManager;
import com.dev.superframe.utils.Json;
import com.dev.superframe.utils.PinYinUtil;
import com.dev.superframe.utils.PreferenceUtil;
import com.dev.superframe.utils.TopBarUtil;
import com.dev.superframe.view.sortlist.SideBar;
import com.dev.superframe.view.sortlist.SortAdapter;
import com.dev.superframe.view.sortlist.SortModel;
import com.dongmango.gou2.R;
import com.dongmango.gou2.bean.CityBean;
import com.dongmango.gou2.config.KeyValueConstants;
import com.dongmango.gou2.httpmanager.GetJsonUtil;
import com.dongmango.gou2.httpmanager.HttpJsonUtil;
import com.dongmango.gou2.view.GLoadingDialog;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SelectCityActivity extends BaseActivity {
    @BindView(R.id.lv_display)
    ListView lvDisplay;
    @BindView(R.id.tv_dialog)
    TextView tvDialog;
    @BindView(R.id.sidebar)
    SideBar sidebar;

    private List<SortModel> mList = new ArrayList<>();
    private SortAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectcity);
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
        TopBarUtil.initTitle(getActivity(), R.id.tv_base_title, "选择城市");
    }

    @Override
    public void initData() {
        httpList();
    }

    @Override
    public void initEvent() {
        lvDisplay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PreferenceUtil.setPrefString(getActivity(), KeyValueConstants.KEY_CITY_ID, mList.get(i).getId());
                PreferenceUtil.setPrefString(getActivity(), KeyValueConstants.KEY_CITY_NAME, mList.get(i).getName());
                EventBus.getDefault().post(mList.get(i));
                finish();
            }
        });
        sidebar.setTextView(tvDialog);
        //设置右侧触摸监听
        sidebar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    lvDisplay.setSelection(position);
                }
            }
        });
    }

    private void httpList() {
        GLoadingDialog.showDialogForLoading(getActivity(), true);
        String json = PreferenceUtil.getPrefString(getActivity(), KeyValueConstants.KEY_CLASS_JSON, "");
        bindListData(Json.parseArray(json, CityBean.class));
        HttpJsonUtil.getCityInfoList(10001, new HttpManager.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int requestCode, String resultJson, Exception e) {
                if (0 == GetJsonUtil.getResponseCode(resultJson)) {
                    String json = GetJsonUtil.getResponseData(resultJson);
                    PreferenceUtil.setPrefString(getActivity(), KeyValueConstants.KEY_CLASS_JSON, json);
                    bindListData(Json.parseArray(json, CityBean.class));
                } else {
                    showShortToast(GetJsonUtil.getResponseError(resultJson));
                }
                GLoadingDialog.cancelDialogForLoading();
            }
        });
    }

    private void bindListData(List<CityBean> list) {
        if (list == null || list.size() <= 0) {
            return;
        }
        mList = filledData(list);
        mAdapter = new SortAdapter(getActivity(), mList);
        lvDisplay.setAdapter(mAdapter);
    }

    /**
     * 为ListView填充数据
     *
     * @param list
     * @return
     */
    private List<SortModel> filledData(List<CityBean> list) {
        List<SortModel> mSortList = new ArrayList<>();
        if (list == null && list.size() <= 0) {
            return mSortList;
        }
        for (CityBean o : list) {
            SortModel sortModel = new SortModel();
            sortModel.setName(o.getRegion_name());
            sortModel.setId(o.getRegion_id());

            //汉字转换成拼音
            String oneStr = o.getRegion_name();
            if (TextUtils.isEmpty(oneStr)) {
                oneStr = "0";
            }
            String pinyin = PinYinUtil.cn2Spell(oneStr.substring(0, 1));
            String sortString = pinyin.substring(0, 1).toUpperCase();

            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                sortModel.setSortLetters(sortString.toUpperCase());
            } else {
                sortModel.setSortLetters("#");
            }
            mSortList.add(sortModel);
        }
        return mSortList;
    }
}
