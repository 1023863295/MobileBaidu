package cn.pear.mobilebaidu.ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.Toast;

import cn.pear.mobilebaidu.R;
import cn.pear.mobilebaidu.base.BaseAty;
import cn.pear.mobilebaidu.components.MainWeChromeClient;
import cn.pear.mobilebaidu.components.MainWebviewClient;
import cn.pear.mobilebaidu.global.Constants;
import cn.pear.mobilebaidu.global.MyApplication;
import cn.pear.mobilebaidu.tool.WebConfig;
import cn.pear.mobilebaidu.view.PopWindowTools;
import cn.shpear.ad.sdk.AdItem;
import cn.shpear.ad.sdk.BaseAD;
import cn.shpear.ad.sdk.JavaScriptAdSupport;
import cn.shpear.ad.sdk.NotificationAd;
import cn.shpear.ad.sdk.SdkContext;
import cn.shpear.ad.sdk.listener.NotificationAdListener;

public class MainActivity extends BaseAty implements View.OnClickListener{
    public View viewBottom;
    private ImageButton imgBtnBack;
    private ImageButton imgBtnNext;
    private ImageButton imgBtnTool;
    private ImageButton imgBtnHome;

    private WebView mWebview;
    private MainWebviewClient mainWebviewClient;
    private MainWeChromeClient mainWeChromeClient;
    private JavaScriptAdSupport javaScriptAdSupport;

    private PopWindowTools popWindowTools;

    public WebView getmWebview() {
        return mWebview;
    }

    @Override
    protected void initData() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(SdkContext.permissions_required,1);
        }else{
            requestPermission();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String targetUrl = intent.getStringExtra("targetUrl");
        mWebview.loadUrl(targetUrl);
    }

    @Override
    protected int getLayoutView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mWebview = (WebView)findViewById(R.id.main_webview);
        WebConfig.setDefaultConfig(mWebview,this);
        mainWebviewClient = new MainWebviewClient(this);
        mWebview.setWebViewClient(mainWebviewClient);
        mainWeChromeClient = new MainWeChromeClient(this);
        mWebview.setWebChromeClient(mainWeChromeClient);

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        javaScriptAdSupport = new JavaScriptAdSupport(mWebview,pi);

        viewBottom = findViewById(R.id.main_bottom_bar);
        imgBtnBack = (ImageButton)viewBottom.findViewById(R.id.bottom_btn_back);
        imgBtnBack.setOnClickListener(this);
        imgBtnBack.setEnabled(false);
        imgBtnNext = (ImageButton)viewBottom.findViewById(R.id.bottom_btn_next);
        imgBtnNext.setOnClickListener(this);
        imgBtnNext.setEnabled(false);
        imgBtnTool = (ImageButton)viewBottom.findViewById(R.id.bottom_btn_tools);
        imgBtnTool.setOnClickListener(this);
        imgBtnHome = (ImageButton)viewBottom.findViewById(R.id.bottom_btn_home);
        imgBtnHome.setOnClickListener(this);

        popWindowTools = new PopWindowTools(this ,mWebview);
    }

    @Override
    protected void afterViewInit() {
        mWebview.loadUrl(Constants.URL_HOST);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bottom_btn_back:
                if (mWebview.canGoBack()){
                    mWebview.goBack();
                }
                updateBtnNext(true);
                break;
            case R.id.bottom_btn_next:
                if (mWebview.canGoForward()){
                    mWebview.goForward();
                }
                updateBtnBack(true);
                break;
            case R.id.bottom_btn_tools:
                popWindowTools.show();
                break;
            case R.id.bottom_btn_home:
                goHome();
                break;
        }
    }

    public void updateBtnBack(boolean canGoback){
        if(canGoback){
            imgBtnBack.setEnabled(true);
            imgBtnBack.setImageResource(R.drawable.home_1_b_black);
        }else {
            imgBtnBack.setEnabled(false);
            imgBtnBack.setImageResource(R.drawable.home_1_b_gray);
        }
    }

    public void updateBtnNext(boolean canGoForward){
        if(canGoForward){
            imgBtnNext.setEnabled(true);
            imgBtnNext.setImageResource(R.drawable.home_2_b_next_black);
        }else {
            imgBtnNext.setEnabled(false);
            imgBtnNext.setImageResource(R.drawable.home_2_next_gray);
        }
    }

    public synchronized void refreshBackForwardViews() {
        boolean backFlag = mWebview.canGoBack();
        updateBtnBack(backFlag);
        boolean forward = mWebview.canGoForward();
        updateBtnNext(forward);
    }

    //主页
    private void goHome(){
        mWebview.clearHistory();
        mWebview.loadUrl(Constants.URL_HOST);
        refreshBackForwardViews();
    }

    private long lastBackPress;
    @Override
    public void onBackPressed() {
        if (mWebview.canGoBack()){
            mWebview.goBack();
        }else  if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }else{
            if (System.currentTimeMillis() - lastBackPress < 2000) {
                super.onBackPressed();
            } else {
                lastBackPress = System.currentTimeMillis();
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void requestPermission(){
        if(!BaseAD.hasPermission(this)){
            ActivityCompat.requestPermissions(this, SdkContext.permissions_required, 1);
        }else{
            onPermissionGranted();
        }
    }

    public void onPermissionGranted(){
        ((MyApplication) getApplication()).initAdMax();
        pushNotice();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(BaseAD.hasPermission(this)){
            onPermissionGranted();
        }
    }

    //--发送广告通知
    NotificationAd  notificationAd;
    public static final String NOTIFICATION_AD_PID="111111";
    public static final String NATIVE_AD_PID="202";
    private void pushNotice(){
        Log.i("test","pushNotice");
        notificationAd  = new NotificationAd(getApplication(), NOTIFICATION_AD_PID, R.mipmap.ic_launcher,
                new NotificationAdListener() {
                    @Override
                    public void onADLoaded(AdItem adItem) {
                        notificationAd.push();
                    }

                    @Override
                    public void onADLoadFail(int i) {
                    }

                    @Override
                    public void onNoAD(int i) {
                    }
                });
        Intent intent = new Intent(getApplication(), MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(getApplication(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationAd.setPendingIntent(pi);
        notificationAd.loadAd();
    }
}
