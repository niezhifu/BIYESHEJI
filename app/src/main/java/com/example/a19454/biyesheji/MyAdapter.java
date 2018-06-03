package com.example.a19454.biyesheji;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * 本程序由聂智福编写
 * meil：nzfyjdr@gmail.com
 * qq:194540790
 */
public class MyAdapter extends BaseAdapter {
    private Context mContext;
    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewholder = null;
        if(convertView == null){
            convertView = View.inflate(mContext, R.layout.layout_alphabet,null);
            viewholder = new ViewHolder();
            viewholder.img_sms=(ImageView)convertView.findViewById(R.id.img_sms);
            viewholder.img_detail=(ImageView)convertView.findViewById(R.id.img_detail);
            viewholder.img_share=(ImageView)convertView.findViewById(R.id.img_share);
            viewholder.img_delete=(ImageView)convertView.findViewById(R.id.img_delete);
            convertView.setTag(viewholder);
        }
        else{
            viewholder = (ViewHolder)convertView.getTag();
        }
        return convertView;
    }

    public static class ViewHolder
    {
        ImageView img_sms;
        ImageView img_detail;
        ImageView img_share;
        ImageView img_delete;
    }
}
