package com.donglv.watch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.donglv.watch.common.HMW_Constant;
import com.donglv.watch.entity.ChatItem;
import com.nam.customlibrary.Libs_System;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by vcom on 09/12/2016.
 */
public class ChatAdapter extends ArrayAdapter<ChatItem.Data> {
    private ArrayList<ChatItem.Data> datas;
    private Context context;
    public ChatAdapter(Context context, ArrayList<ChatItem.Data> objects) {
        super(context, 0, objects);
        this.context = context;
        this.datas = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ChatItem.Data data = datas.get(position);
        if(Libs_System.getStringData(context, HMW_Constant.email).equals(data.getCreator().getEmail())) {
            convertView = inflater.inflate(com.donglv.watch.R.layout.item_chat, parent, false);
        }else {
            convertView = inflater.inflate(com.donglv.watch.R.layout.item_chat_r, parent, false);
        }

        TextView tvContent = (TextView) convertView.findViewById(com.donglv.watch.R.id.tvContent);
        ImageView imgAvatar = (ImageView) convertView.findViewById(com.donglv.watch.R.id.imgAvatar);
        Picasso.with(context).load(HMW_Constant.HOST+"/lbmedia/"+data.getCreator().getAvatar_id()).placeholder(com.donglv.watch.R.drawable.emgai).error(com.donglv.watch.R.drawable.emgai).into(imgAvatar);
        tvContent.setText(data.getContent());
        return convertView;
    }
}
