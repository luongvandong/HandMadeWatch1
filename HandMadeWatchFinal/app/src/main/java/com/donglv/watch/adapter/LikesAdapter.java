package com.donglv.watch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.donglv.watch.common.RoundedImageView;
import com.donglv.watch.entity.ProductInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by vcom on 15/11/2016.
 */
public class LikesAdapter extends ArrayAdapter<ProductInfo.Likes> {
    private ArrayList<ProductInfo.Likes> datas;
    private Context context;
    public LikesAdapter(Context context, ArrayList<ProductInfo.Likes> objects) {
        super(context, 0, objects);
        this.context = context;
        this.datas = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(com.donglv.watch.R.layout.item_comment, parent, false);
        TextView tvTitle = (TextView) convertView.findViewById(com.donglv.watch.R.id.tvTitle);
        TextView tvContent = (TextView) convertView.findViewById(com.donglv.watch.R.id.tvContent);
        RoundedImageView roundedImageView =(RoundedImageView) convertView.findViewById(com.donglv.watch.R.id.imgAvatar);
        final ProductInfo.Likes data = datas.get(position);
        Picasso.with(context).load("http://handmadewatch.libre.com.vn/lbmedia/"+data.getCreator().getAvatar_id()).into(roundedImageView);
        tvTitle.setText(data.getCreator().getName());
        tvContent.setText(data.getCreator().getEmail());
        return convertView;
    }

}
