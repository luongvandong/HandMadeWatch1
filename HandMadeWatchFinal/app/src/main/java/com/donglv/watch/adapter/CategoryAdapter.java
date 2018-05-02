package com.donglv.watch.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.donglv.watch.R;
import com.donglv.watch.ActivityHome;

import com.donglv.watch.common.HMW_Constant;
import com.donglv.watch.entity.Category;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by vcom on 15/11/2016.
 */
public class CategoryAdapter extends ArrayAdapter<Category.Data> {
    private ArrayList<Category.Data> datas;
    private Context context;

    public CategoryAdapter(Context context, ArrayList<Category.Data> objects) {
        super(context, 0, objects);
        this.context = context;
        this.datas = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_all_product_in_category, parent, false);
        ImageView img = (ImageView) convertView.findViewById(R.id.imgProduct);
        TextView tvNameCategoty = (TextView) convertView.findViewById(R.id.nameCategory);
     //   img.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getHeight()));
        final Category.Data data = datas.get(position);
//        ImageView img1 = (ImageView)convertView.findViewById( R.id.img1 );
//        ImageView img2 = (ImageView)convertView.findViewById( R.id.img2 );
//        ImageView img3 = (ImageView)convertView.findViewById( R.id.img3 );
//        ImageView img4 = (ImageView)convertView.findViewById( R.id.img4 );
//
//        TextView tvTitle = (TextView)convertView.findViewById( R.id.tvTitle );
//        TextView tvCountProduct = (TextView)convertView.findViewById( R.id.tvCountProduct );
//        final Category.Data data = datas.get(position);
//        if(data.getPosts().size() > 5){
//            img1.setVisibility(View.VISIBLE);
//            img2.setVisibility(View.VISIBLE);
//            img3.setVisibility(View.VISIBLE);
//            img4.setVisibility(View.VISIBLE);
//            Picasso.with(context).load(HMW_Constant.HOST+"/media/"+data.getPosts().get(0).getMedia().get(0).getId()).into(img1);
//            Picasso.with(context).load(HMW_Constant.HOST+"/media/"+data.getPosts().get(1).getMedia().get(0).getId()).into(img2);
//            Picasso.with(context).load(HMW_Constant.HOST+"/media/"+data.getPosts().get(2).getMedia().get(0).getId()).into(img3);
//            Picasso.with(context).load(HMW_Constant.HOST+"/media/"+data.getPosts().get(3).getMedia().get(0).getId()).into(img4);
//        }else {
//            switch (data.getPosts().size()) {
//                case 1:
//                    img1.setVisibility(View.VISIBLE);
//                    Picasso.with(context).load(HMW_Constant.HOST + "/media/" + data.getPosts().get(0).getMedia().get(0).getId()).into(img1);
//                    break;
//                case 2:
//                    img1.setVisibility(View.VISIBLE);
//                    img2.setVisibility(View.VISIBLE);
//                    Picasso.with(context).load(HMW_Constant.HOST + "/media/" + data.getPosts().get(0).getMedia().get(0).getId()).into(img1);
//                    Picasso.with(context).load(HMW_Constant.HOST + "/media/" + data.getPosts().get(1).getMedia().get(0).getId()).into(img2);
//                    break;
//                case 3:
//                    img1.setVisibility(View.VISIBLE);
//                    img2.setVisibility(View.VISIBLE);
//                    img3.setVisibility(View.VISIBLE);
//                    Picasso.with(context).load(HMW_Constant.HOST + "/media/" + data.getPosts().get(0).getMedia().get(0).getId()).into(img1);
//                    Picasso.with(context).load(HMW_Constant.HOST + "/media/" + data.getPosts().get(1).getMedia().get(0).getId()).into(img2);
//                    Picasso.with(context).load(HMW_Constant.HOST + "/media/" + data.getPosts().get(2).getMedia().get(0).getId()).into(img3);
//                    break;
//                case 4:
//                    img1.setVisibility(View.VISIBLE);
//                    img2.setVisibility(View.VISIBLE);
//                    img3.setVisibility(View.VISIBLE);
//                    img4.setVisibility(View.VISIBLE);
//                    Picasso.with(context).load(HMW_Constant.HOST + "/media/" + data.getPosts().get(0).getMedia().get(0).getId()).into(img1);
//                    Picasso.with(context).load(HMW_Constant.HOST + "/media/" + data.getPosts().get(1).getMedia().get(0).getId()).into(img2);
//                    Picasso.with(context).load(HMW_Constant.HOST + "/media/" + data.getPosts().get(2).getMedia().get(0).getId()).into(img3);
//                    Picasso.with(context).load(HMW_Constant.HOST + "/media/" + data.getPosts().get(3).getMedia().get(0).getId()).into(img4);
//                    break;
//            }
//        }
//
//        tvTitle.setText(data.getTitle());
//        tvCountProduct.setText(data.getPosts_count()+"Product");
        if(data.getMedia()!=null&& !data.getMedia().isEmpty()) {
            Glide.with(context).load(HMW_Constant.HOST + "/lbmedia/" + data.getMedia().get(0).getId()).into(img);
        }
        tvNameCategoty.setText(data.getTitle());

        return convertView;
    }
    private int getHeight(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((ActivityHome)context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.widthPixels/3;
        return height;
    }
}
