package com.donglv.watch.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.donglv.watch.ActivityHome;
import com.donglv.watch.MainActivity;
import com.donglv.watch.R;
import com.donglv.watch.common.HMW_Constant;
import com.donglv.watch.entity.ActionLikeResponse;
import com.donglv.watch.entity.CategoryInfo;
import com.donglv.watch.entity.HomeResponse;
import com.donglv.watch.service.HMWApi;
import com.nam.customlibrary.Libs_System;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by vcom on 15/11/2016.
 */
public class CategoryInfoAdapter extends ArrayAdapter<CategoryInfo.Posts> {
    private ArrayList<CategoryInfo.Posts> datas;
    private Context context;

    private int likesNumber;

    public CategoryInfoAdapter(Context context, ArrayList<CategoryInfo.Posts> objects) {
        super(context, 0, objects);
        this.context = context;
        this.datas = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_home_slide, parent, false);
        final CategoryInfo.Posts data = datas.get(position);

        final TextView tvLike = (TextView) convertView.findViewById(R.id.tvLikeNumber);
        final TextView tvComment = (TextView) convertView.findViewById(R.id.tvCommentNumber);
        TextView tvDes = (TextView) convertView.findViewById(R.id.tvDes);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);

        TextView tvSalePrice = (TextView) convertView.findViewById(R.id.tvSalePrice);
        final ImageView btnLike = (ImageView) convertView.findViewById(R.id.btnLike);
        ImageView btnComment = (ImageView) convertView.findViewById(R.id.btnComment);
        android.support.v4.view.ViewPager viewPager = (android.support.v4.view.ViewPager) convertView.findViewById(R.id.viewPaper);

        TabLayout tabLayout = (TabLayout) convertView.findViewById(R.id.tabDots);
        final ArrayList<HomeResponse.Liked> liked = datas.get(position).getLiked();
        likesNumber = data.getLikesCount();
        int commentsNumber = data.getCommentsCount();
        if (likesNumber <= 1) {
//            tvLike.setText(likesNumber + " " + context.getString(R.string.a_like));
            tvLike.setText(likesNumber + " ");
        } else {
//            tvLike.setText(likesNumber + " " + context.getString(R.string.likes));
            tvLike.setText(likesNumber + " ");
        }
        if (commentsNumber <= 1) {
//            tvComment.setText(commentsNumber + " " + context.getString(R.string.comment));
            tvComment.setText("" + commentsNumber);
        } else {
//            tvComment.setText(commentsNumber + " " + context.getString(R.string.comments));
            tvComment.setText("" + commentsNumber);
        }

        int saleActive = data.getSaleActive();
        if (saleActive == 1) {
            tvPrice.setText(data.getSalePrice() + " " + data.getCurrency());
        } else {
            tvPrice.setText(data.getPrice() + " " + data.getCurrency());
        }

        //img.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, getHeight()));
//        tvLike.setText(data.get()+"");
        tvSalePrice.setVisibility(View.GONE);
        tvDes.setText(data.getTitle());
        if (data.getMedia() != null && !data.getMedia().isEmpty()) {
            List<String> listImae = new ArrayList<String>();
            for (int i = 0; i < data.getMedia().size(); i++) {
                listImae.add(data.getMedia().get(i).getId());
                AdapterSliderImage adapterSliderImage = new AdapterSliderImage(context, listImae, datas.get(position).getId() + "");
                viewPager.setAdapter(adapterSliderImage);
                tabLayout.setupWithViewPager(viewPager, true);
            }
        }

        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                like(data.getId());
                if (checkLogin(context)) {
                    showDialog(context);
                    return;
                }
                HMWApi api = new HMWApi();
                api.service().like("Bearer " + Libs_System.getStringData(context, HMW_Constant.TOKEN), "application/json", "hihi", data.getId(), new Callback<ActionLikeResponse>() {
                    @Override
                    public void success(ActionLikeResponse actionLikeResponse, Response response) {
                        likesNumber = actionLikeResponse.getPost().getLikes_count();
                        if (data.getLiked().size() == 0) {
                            Libs_System.showToast(context, "Like success");
                            btnLike.setBackgroundResource(R.drawable.ic_liked);
                            liked.add(new HomeResponse.Liked());
                            data.setLiked(liked);
                            if (likesNumber <= 1) {
                                tvLike.setText(likesNumber + " " + context.getString(R.string.a_like));
                            } else {
                                tvLike.setText(likesNumber + " " + context.getString(R.string.likes));
                            }
                        } else {
                            Libs_System.showToast(context, "UnLike success");
                            btnLike.setBackgroundResource(R.drawable.ic_like);
                            liked.clear();
                            data.setLiked(liked);
                            if (likesNumber <= 1) {
                                tvLike.setText(likesNumber + " " + context.getString(R.string.a_like));
                            } else {
                                tvLike.setText(likesNumber + " " + context.getString(R.string.likes));
                            }
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("favorite", error.getMessage() + "");
                        if (error.getResponse() != null && error.getResponse().getStatus() == 401) {
                            Libs_System.showToast(context, context.getString(R.string.unauthorized));
                            Libs_System.insertStringData(getContext(), HMW_Constant.TOKEN, "");
                            ((Activity) getContext()).finish();
                            getContext().startActivity(((Activity) getContext()).getIntent());
                        }
                    }
                });
            }
        });
        return convertView;
    }

    private int getHeight() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((ActivityHome) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.widthPixels;
        return height;
    }

    private boolean checkLogin(final Context context) {
        if (Libs_System.getStringData(context, HMW_Constant.TOKEN).equals("")) {
            return true;
        }
        return false;
    }

    private void showDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.you_must_login));
        builder.setPositiveButton(context.getString(R.string.login), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(context, MainActivity.class));
            }
        });
        builder.setNegativeButton(context.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }
}
