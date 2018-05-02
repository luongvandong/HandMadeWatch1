package com.donglv.watch.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.donglv.watch.ActivityHome;
import com.donglv.watch.R;
import com.donglv.watch.common.HMW_Constant;

import java.util.List;


/**
 * Created by NguyenQuang on 4/5/2017.
 */

public class AdapterSliderImageProductInfo extends PagerAdapter {
    ActivityHome context;
    List<String> listImage;
    String idProduct;

    public AdapterSliderImageProductInfo(Context context, List<String> listImage) {
        this.listImage = listImage;
        this.context = (ActivityHome) context;
    }

    public int getCount() {
        return listImage.size();
    }

    public Object instantiateItem(View collection, final int position) {
        ImageView view = new ImageView(context);
        view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);
        String link = HMW_Constant.HOST + "/lbmedia/" + listImage.get(position);
        Glide.with(context).load(link).placeholder(R.drawable.ic_loadding).error(R.drawable.ic_loadding).into(view);
        ((android.support.v4.view.ViewPager) collection).addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(View arg0, int arg1, Object arg2) {
        ((android.support.v4.view.ViewPager) arg0).removeView((View) arg2);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == ((View) arg1);
    }

    @Override
    public Parcelable saveState() {
        return null;
    }
}