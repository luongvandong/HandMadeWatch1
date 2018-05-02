package com.donglv.watch.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.donglv.watch.R;
import com.donglv.watch.common.HMW_Constant;
import com.donglv.watch.entity.ProductInfo;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;

/**
 * Created by vcom on 15/11/2016.
 */
public class CommentAdapter extends ArrayAdapter<ProductInfo.Comments> {
    private ArrayList<ProductInfo.Comments> datas;
    private Context context;

    public CommentAdapter(Context context, ArrayList<ProductInfo.Comments> objects) {
        super(context, 0, objects);
        this.context = context;
        this.datas = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_comment, parent, false);
        final ProductInfo.Comments data = datas.get(position);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
        TextView tvContent = (TextView) convertView.findViewById(R.id.tvContent);
        final ImageView imageView = (ImageView) convertView.findViewById(R.id.imgAvatar);
        String url = "";
        if (data.getCreator() != null) {
            url = HMW_Constant.HOST + "/lbmedia/" + data.getCreator().getAvatar_id();
        }
        Glide.with(context).load(url).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });
        if (data.getCreator() != null) {
            if (data.getCreator().isAdmin()) {
                tvTitle.setTextColor(ContextCompat.getColor(getContext(), R.color.textview_name_admin));
            }
            tvTitle.setText(data.getCreator().getName());
            tvContent.setText(data.getContent() + "");
        }
        return convertView;
    }

}
