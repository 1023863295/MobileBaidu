package cn.pear.mobilebaidu.components;

import android.graphics.Bitmap;
import android.os.Build;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import cn.pear.mobilebaidu.R;
import cn.pear.mobilebaidu.global.Constants;
import cn.pear.mobilebaidu.ui.MainActivity;

/**
 * Created by liuliang on 2017/7/6.
 */

public class MainWebviewClient extends WebViewClient {
    private ProgressBar progressBar;
    private MainActivity mainActivity;

    public MainWebviewClient(MainActivity activity){
        this.mainActivity = activity;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        View parentView = (View)view.getParent();
        progressBar = (ProgressBar)parentView.findViewById(R.id.main_web_progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        mainActivity.refreshBackForwardViews();
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        return super.shouldInterceptRequest(view, request);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP){
            view.loadUrl(request.getUrl().toString());
            if (request.getUrl().toString().equals(Constants.URL_HOST) || request.getUrl().toString().equals(Constants.URL_HOST_S)){

            }else{

            }
        }else{
            view.loadUrl(request.toString());
        }
        return true;
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }
}
