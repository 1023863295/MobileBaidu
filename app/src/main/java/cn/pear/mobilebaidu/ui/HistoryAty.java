package cn.pear.mobilebaidu.ui;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import cn.pear.mobilebaidu.R;
import cn.pear.mobilebaidu.adapter.HistoryAdapter;
import cn.pear.mobilebaidu.base.BaseAty;
import cn.pear.mobilebaidu.bean.HistoryBean;
import cn.pear.mobilebaidu.bean.UrlBean;
import cn.pear.mobilebaidu.bean.UrlBeanDao;
import cn.pear.mobilebaidu.tool.DateUtils;
import cn.pear.mobilebaidu.tool.GreenDaoUtils;

/**
 * Created by liuliang on 2017/7/10.
 */

public class HistoryAty extends BaseAty implements View.OnClickListener{
    private ImageView imgBack;
    private TextView textTitle;

    private ExpandableListView expandableListView;
    private HistoryAdapter historyAdapter;
    private List<HistoryBean> historyBeanList;
    private List<UrlBean> childList;
    private View emptyView;

    @Override
    protected void initData() {
        historyBeanList = new ArrayList<>();
        QueryBuilder todayQb = GreenDaoUtils.getSingleTon().getmDaoSession().getUrlBeanDao().queryBuilder();
        //获取今天数据
        List<UrlBean> todayList = todayQb.where(UrlBeanDao.Properties
                .Update.gt(DateUtils.getDayBeginTimestamp()))
                .orderDesc(UrlBeanDao.Properties.Update).list();
        if (todayList!=null && todayList.size()>0){
            childList = new ArrayList<>();
            HistoryBean his = new HistoryBean();
            childList.addAll(todayList);
            his.setGroupTitle("今天");
            his.setItemList(todayList);
            historyBeanList.add(his);
        }
        //获取昨天数据
        QueryBuilder qbYestarday= GreenDaoUtils.getSingleTon().getmDaoSession().getUrlBeanDao().queryBuilder();
        List<UrlBean> yestardayList = qbYestarday.where(
                UrlBeanDao.Properties.Update.ge(DateUtils.getDayBeginTimestamp()-24*60*60*1000),
                UrlBeanDao.Properties.Update.lt(DateUtils.getDayBeginTimestamp()))
                .orderDesc(UrlBeanDao.Properties.Update).list();

        if (yestardayList!=null && yestardayList.size()>0){
            childList = new ArrayList<>();
            HistoryBean his = new HistoryBean();
            childList.addAll(yestardayList);
            his.setGroupTitle("昨天");
            his.setItemList(childList);
            historyBeanList.add(his);
        }

        //获取最近七天数据
        QueryBuilder qbSeven= GreenDaoUtils.getSingleTon().getmDaoSession().getUrlBeanDao().queryBuilder();
        List<UrlBean> sevenDayList = qbSeven.where(
                UrlBeanDao.Properties.Update.ge(DateUtils.getDayBeginTimestamp()-7*24*60*60*1000),
                UrlBeanDao.Properties.Update.lt(DateUtils.getDayBeginTimestamp()))
                .orderDesc(UrlBeanDao.Properties.Update).list();

        if (sevenDayList!=null && sevenDayList.size()>0){
            childList = new ArrayList<>();
            HistoryBean his = new HistoryBean();
            childList.addAll(sevenDayList);
            his.setGroupTitle("近7天");
            his.setItemList(childList);
            historyBeanList.add(his);
        }

        //上个月
        QueryBuilder qbLastMonth= GreenDaoUtils.getSingleTon().getmDaoSession().getUrlBeanDao().queryBuilder();
        List<UrlBean> lastMonthList = qbLastMonth.where(
                UrlBeanDao.Properties.Update.ge(DateUtils.getLastMonthFirstDay()),
                UrlBeanDao.Properties.Update.lt(DateUtils.getThisMonthFirstDay()))
                .orderDesc(UrlBeanDao.Properties.Update).list();
        if (lastMonthList!=null && lastMonthList.size()>0){
            childList = new ArrayList<>();
            HistoryBean his = new HistoryBean();
            childList.addAll(lastMonthList);
            his.setGroupTitle("上月");
            his.setItemList(childList);
            historyBeanList.add(his);
        }

        //更早
        QueryBuilder qbMore = GreenDaoUtils.getSingleTon().getmDaoSession().getUrlBeanDao().queryBuilder();
        List<UrlBean> moreList = qbMore.where(UrlBeanDao.Properties
                .Update.le(DateUtils.getLastMonthFirstDay()))
                .orderDesc(UrlBeanDao.Properties.Update).list();

        if (moreList!=null && moreList.size()>0){
            childList = new ArrayList<>();
            HistoryBean his = new HistoryBean();
            childList.addAll(moreList);
            his.setGroupTitle("更多");
            his.setItemList(childList);
            historyBeanList.add(his);
        }

        historyAdapter = new HistoryAdapter(this,historyBeanList);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.aty_history_layout;
    }

    @Override
    protected void initView() {
        imgBack = (ImageView)findViewById(R.id.head_top_img_back);
        imgBack.setOnClickListener(this);
        textTitle = (TextView)findViewById(R.id.head_top_text_title);
        textTitle.setText("历史");
        textTitle.setTextColor(Color.BLACK);

        emptyView = (TextView)findViewById(R.id.history_expty_textView);

        expandableListView = (ExpandableListView)findViewById(R.id.history_expand_list);
        expandableListView.setAdapter(historyAdapter);
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Intent intent = new Intent(HistoryAty.this,MainActivity.class);
                intent.putExtra("targetUrl",historyBeanList.get(groupPosition).getItemList().get(childPosition).getUrl());
                startActivity(intent);
                return false;
            }
        });
        expandableListView.setEmptyView(emptyView);

        setAnimation();
    }

    @Override
    protected void afterViewInit() {

    }

    private void setAnimation() {
        AnimationSet set = new AnimationSet(true);

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(100);
        set.addAnimation(animation);

        animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        animation.setDuration(100);
        set.addAnimation(animation);
        LayoutAnimationController controller = new LayoutAnimationController(set, 0.5f);

        expandableListView.setLayoutAnimation(controller);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.head_top_img_back:
                finish();
                break;
        }
    }
}
