package cn.pear.mobilebaidu.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by liuliang on 2017/7/6.
 */

public abstract class BaseAty extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutView());
        initData();
        initView();
        afterViewInit();
    }

    /**
     * 初始化数据，包括从bundle中获取数据保存到当前activity中
     */
    protected abstract void initData();


    /**
     * 设置viewlayout
     */
    protected abstract int getLayoutView();

    /**
     * 初始化界面，如获取界面中View的名称并保存，定义Title的文字，以及定义各个控件的处理事件
     */
    protected abstract void initView();

    /**
     * 界面初始化之后的后处理，如启动网络读取数据、启动定位等
     */
    protected abstract void afterViewInit();
}
