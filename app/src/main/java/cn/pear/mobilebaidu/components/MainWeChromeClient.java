package cn.pear.mobilebaidu.components;

import android.content.pm.ActivityInfo;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import cn.pear.mobilebaidu.R;
import cn.pear.mobilebaidu.bean.UrlBean;
import cn.pear.mobilebaidu.tool.GreenDaoUtils;
import cn.pear.mobilebaidu.ui.MainActivity;

/**
 * Created by liuliang on 2017/7/6.
 */

public class MainWeChromeClient extends WebChromeClient {
    private ProgressBar progressBar;
    private MainActivity mainActivity;
    CustomViewCallback customViewCallback;
    View parentView;
    FrameLayout videoFrame;

    public MainWeChromeClient(MainActivity activity){
        this.mainActivity = activity;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        parentView = (View)view.getParent();
        progressBar = (ProgressBar)parentView.findViewById(R.id.main_web_progress_bar);
        progressBar.setProgress(newProgress);
    }

    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        Log.i("test",title);
        if (!"百度一下".equals(title) ){
            mainActivity.refreshBackForwardViews();
            if (!"".equals(title)){
                insert(title,view.getUrl());
            }

        }
    }

    @Override
    public void onShowCustomView(View view, CustomViewCallback callback) {
        // 横屏显示
        mainActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        // 设置webView隐藏
        mainActivity.getmWebview().setVisibility(View.GONE);

        // 赋值给callback
        customViewCallback = callback;
        // 声明video，把之后的视频放到这里面去
        videoFrame = (FrameLayout)parentView.findViewById(R.id.main_videoContainer);
        // 将video放到当前视图中
        videoFrame.addView(view);
        videoFrame.setVisibility(View.VISIBLE);
        mainActivity.viewBottom.setVisibility(View.GONE);
        // 设置全屏
//        setFullScreen();
//        super.onShowCustomView(view, callback);
    }

    @Override
    public void onHideCustomView() {
        if (customViewCallback != null) {
            // 隐藏掉
            customViewCallback.onCustomViewHidden();
        }
        // 用户当前的首选方向
        mainActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        // 退出全屏
//        quitFullScreen();
        videoFrame.removeAllViews();
        videoFrame.setVisibility(View.GONE);
        // 设置WebView可见
        mainActivity.getmWebview().setVisibility(View.VISIBLE);
        mainActivity.viewBottom.setVisibility(View.VISIBLE);
//        super.onHideCustomView();
    }

    /**
     * 设置全屏
     */
    private void setFullScreen() {
        // 设置全屏的相关属性，获取当前的屏幕状态，然后设置全屏
        mainActivity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 全屏下的状态码：1098974464
        // 窗口下的状态吗：1098973440
    }

    /**
     * 退出全屏
     */
    private void quitFullScreen() {
        // 声明当前屏幕状态的参数并获取
        final WindowManager.LayoutParams attrs = mainActivity.getWindow().getAttributes();
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mainActivity.getWindow().setAttributes(attrs);
        mainActivity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    //插入数据库
    private void insert(String title,String url){
        UrlBean urlBean = new UrlBean(null,title,url,System.currentTimeMillis(),"");
        GreenDaoUtils.getSingleTon().getmDaoSession().getUrlBeanDao().insert(urlBean);
    }
}
