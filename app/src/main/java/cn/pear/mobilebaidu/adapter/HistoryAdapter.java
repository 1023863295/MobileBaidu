package cn.pear.mobilebaidu.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.pear.mobilebaidu.R;
import cn.pear.mobilebaidu.bean.HistoryBean;
//import cn.pear.mobilebaidu.bean.UrlBean;

/**
 * Created by liuliang on 2017/7/10.
 */

public class HistoryAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<HistoryBean> mList;

    public HistoryAdapter(Context mContext, List<HistoryBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public int getGroupCount() {
        return mList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return mList.get(groupPosition).getItemList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return mList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return mList.get(groupPosition).getItemList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder;
        if (convertView == null){
            groupHolder = new GroupHolder();
            convertView = View.inflate(mContext, R.layout.history_group,null);
            groupHolder.textDateTime = (TextView)convertView.findViewById(R.id.history_group_textDate);
            convertView.setTag(groupHolder);
        }else{
            groupHolder = (GroupHolder)convertView.getTag();
        }

        groupHolder.textDateTime.setText(mList.get(groupPosition).getGroupTitle());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildHolder childHolder;
        if (convertView == null){
            childHolder = new ChildHolder();
            convertView = View.inflate(mContext, R.layout.history_child,null);
            childHolder.textTitle = (TextView)convertView.findViewById(R.id.HistoryRow_Title);
            childHolder.textUrl = (TextView)convertView.findViewById(R.id.HistoryRow_Url);
            childHolder.mImage = (ImageView)convertView.findViewById(R.id.HistoryRow_icon);
            convertView.setTag(childHolder);
        }else{
            childHolder = (ChildHolder)convertView.getTag();
        }
        childHolder.textTitle.setText(mList.get(groupPosition).getItemList().get(childPosition).getTitle());
        childHolder.textUrl.setText(mList.get(groupPosition).getItemList().get(childPosition).getUrl());
        childHolder.mImage.setImageResource(R.drawable.fav_icn_unknown);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    static class GroupHolder{
        TextView textDateTime;
    }

    static class ChildHolder{
        ImageView mImage;
        TextView textTitle;
        TextView textUrl;
    }
}
