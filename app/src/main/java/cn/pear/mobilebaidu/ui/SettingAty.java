package cn.pear.mobilebaidu.ui;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.pear.mobilebaidu.R;
import cn.pear.mobilebaidu.base.BaseAty;

/**
 * Created by liuliang on 2017/7/7.
 * 设置页面
 */

public class SettingAty extends BaseAty implements View.OnClickListener{
    private ImageView imgBack;
    private TextView textTitle;

    private TextView textAbout;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutView() {
        return R.layout.aty_setting_layout;
    }

    @Override
    protected void initView() {
        imgBack = (ImageView)findViewById(R.id.head_top_img_back);
        imgBack.setOnClickListener(this);
        textTitle = (TextView)findViewById(R.id.head_top_text_title);
        textTitle.setText("设置");
        textTitle.setTextColor(Color.BLACK);

        textAbout = (TextView)findViewById(R.id.setting_text_about);
        textAbout.setOnClickListener(this);
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
            case R.id.setting_text_about:
                Intent intentAbout = new Intent(this,AboutAty.class);
                startActivity(intentAbout);
                break;
        }
    }
}
