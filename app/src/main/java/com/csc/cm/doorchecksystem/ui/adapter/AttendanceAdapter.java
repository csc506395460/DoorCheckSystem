package com.csc.cm.doorchecksystem.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.csc.cm.doorchecksystem.R;
import com.csc.cm.doorchecksystem.data.model.CheckResult;
import com.csc.cm.doorchecksystem.data.tool.TimeTranUtil;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2017/1/21.
 */
public class AttendanceAdapter extends BaseAdapter {

    private List<CheckResult.CheckInfo> mList;
    private Context mContext;

    public AttendanceAdapter(Context context, List<CheckResult.CheckInfo> list) {
        this.mContext = context;
        this.mList = list;
    }

    public void setAdapter(List<CheckResult.CheckInfo> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_item, null);
            holder = new ViewHolder();

            holder.ivItemType = (ImageView)convertView.findViewById(R.id.iv_item_type);
            holder.tvItemAddr = (TextView)convertView.findViewById(R.id.tv_item_addr);
            holder.tvItemName = (TextView)convertView.findViewById(R.id.tv_item_name);
            holder.tvItemTime = (TextView)convertView.findViewById(R.id.tv_item_time);
            holder.ivItemState = (ImageView)convertView.findViewById(R.id.iv_item_state);
            holder.tvItemState = (TextView)convertView.findViewById(R.id.tv_item_state);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        if (mList.get(position).eventName != null) {
            if (mList.get(position).eventName.equals("进门")) {
                holder.ivItemType.setBackgroundResource(R.color.main_purple_color);
                holder.ivItemState.setBackgroundResource(R.drawable.ic_in_time);
            } else {
                holder.ivItemType.setBackgroundResource(R.color.main_red_color);
                holder.ivItemState.setBackgroundResource(R.drawable.ic_out_time);
            }
        } else {
            holder.ivItemType.setBackgroundResource(R.color.main_red_color);
            holder.ivItemState.setBackgroundResource(R.drawable.ic_out_time);
        }
        holder.tvItemAddr.setText(mList.get(position).controlName);
        holder.tvItemName.setText(mList.get(position).employeeName);
        holder.tvItemTime.setText(TimeTranUtil.dateTimeToHmString(mList.get(position).eventTime));
        holder.tvItemState.setText(mList.get(position).eventName);
        return convertView;
    }

    public static class ViewHolder {
        public ImageView ivItemType;
        public TextView tvItemAddr;
        public TextView tvItemName;
        public TextView tvItemTime;
        public ImageView ivItemState;
        public TextView tvItemState;
    }
}
