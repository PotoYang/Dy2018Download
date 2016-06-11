package com.example.potoyang.dy2018download;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 71579 on 2016/5/21.
 */

public class ListViewAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;
    private LayoutInflater layoutInflater;

    //构造方法，参数list传递的就是这一组数据的信息
    public ListViewAdapter(Context context, List<String> list) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            view = layoutInflater.inflate(R.layout.list_item, null);
            viewHolder.tv_title = (TextView) view.findViewById(R.id.tv_item);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_title.setText(list.get(position));
        return view;
    }

    class ViewHolder {
        TextView tv_title;
    }
}
