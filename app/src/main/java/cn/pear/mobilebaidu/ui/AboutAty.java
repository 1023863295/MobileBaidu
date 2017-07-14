package cn.pear.mobilebaidu.ui;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.pear.mobilebaidu.R;
import cn.pear.mobilebaidu.base.BaseAty;

/**
 * Created by liuliang on 2017/7/13.
 */

public class AboutAty extends BaseAty implements View.OnClickListener{
    private ImageView imgBack;
    private TextView textTitle;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutView() {
        return R.layout.aty_about;
    }

    @Override
    protected void initView() {
        imgBack = (ImageView)findViewById(R.id.head_top_img_back);
        imgBack.setOnClickListener(this);
        textTitle = (TextView)findViewById(R.id.head_top_text_title);
        textTitle.setText("关于");
        textTitle.setTextColor(Color.BLACK);
    }

    @Override
    protected void afterViewInit() {

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
