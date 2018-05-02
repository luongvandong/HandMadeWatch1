package com.donglv.watch.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donglv.watch.ActivityHome;
import com.donglv.watch.MainActivity;
import com.donglv.watch.R;
import com.donglv.watch.common.HMW_Constant;
import com.donglv.watch.entity.ActionLikeResponse;
import com.donglv.watch.entity.HomeResponse;
import com.donglv.watch.entity.SimpleClass;
import com.donglv.watch.fragment.FragmentAddCart;
import com.donglv.watch.fragment.FragmentHome;
import com.donglv.watch.service.HMWApi;
import com.nam.customlibrary.Libs_System;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by vcom on 11/11/2016.
 */
public class ProductOnSaleAdapter extends ArrayAdapter<HomeResponse.Data> {
    private ArrayList<HomeResponse.Data> datas;
    private Context context;
    private Fragment fragment;

    private int likesNumber;

    public ProductOnSaleAdapter(Context context, ArrayList<HomeResponse.Data> objects) {
        super(context, 0, objects);
        this.context = context;
        this.datas = objects;
    }

    public ProductOnSaleAdapter(Context context, ArrayList<HomeResponse.Data> objects, Fragment fragment) {
        super(context, 0, objects);
        this.context = context;
        this.datas = objects;
        this.fragment = fragment;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_home, parent, false);
            holder = new ViewHolder();
            holder.tvLike = (TextView) convertView.findViewById(R.id.tvLikeNumber);
            holder.tvCommentNumber = (TextView) convertView.findViewById(R.id.tvCommentNumber);
            holder.btnLike = (ImageView) convertView.findViewById(R.id.btnLike);
            holder.btnFavorite = (ImageView) convertView.findViewById(R.id.btnFavorite);
            holder.tvDes = (TextView) convertView.findViewById(R.id.tvDes);
            holder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
            holder.btnBuy = (TextView) convertView.findViewById(R.id.btnBuy);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            holder.tvSalePrice =(TextView) convertView.findViewById(R.id.tvSalePrice);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final HomeResponse.Data data = datas.get(position);
        final ArrayList<HomeResponse.Liked> liked = datas.get(position).getLiked();
        final ArrayList<HomeResponse.Favorited> favorited = datas.get(position).getFavorited();
        likesNumber = data.getLikes_count();
        if (likesNumber == 0) {
            holder.btnLike.setBackgroundResource(R.drawable.ic_liked);
        } else {
            holder.btnLike.setBackgroundResource(R.drawable.ic_like);
        }
        if (data.getFavorited().size() == 0) {
            holder.btnFavorite.setBackgroundResource(R.drawable.favorites);
        } else {
            holder.btnFavorite.setBackgroundResource(R.drawable.favorites_active);
        }
        holder.tvLike.setText(data.getLikes_count() + " ");
        holder.tvPrice.setPaintFlags(holder.tvPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvSalePrice.setText(data.getSalePrice());
        holder.tvCommentNumber.setText(data.getComments_count() + " ");
        if (fragment != null) {
            holder.tvLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((FragmentHome) fragment).getProductInfo(data.getId());
                }
            });
        }
        holder.tvDes.setText(data.getTitle());
        int sale_active = data.getSaleActive();
        if (sale_active == 1) {
            holder.tvPrice.setText(data.getSalePrice() + " " + data.getCurrency());
        } else {
            holder.tvPrice.setText(data.getPrice() + " " + data.getCurrency());
        }
//        Picasso.with(context).load(HMW_Constant.HOST + "/lbmedia/" + data.getMedia().get(0).getId()).error(R.drawable.ic_loadding).into(holder.img, new com.squareup.picasso.Callback() {
//            @Override
//            public void onSuccess() {
//                Bitmap bitmap = ((BitmapDrawable) holder.img.getDrawable()).getBitmap();
//                int width = bitmap.getWidth();
//                int height = bitmap.getHeight();
//                holder.img.getLayoutParams().height = (int) (HMW_System.getScreenWidth() * ((float) height / (float) width));
//                holder.img.setImageBitmap(bitmap);
//            }
//
//            @Override
//            public void onError() {
//
//            }
//        });
        Glide.with(context).load(HMW_Constant.HOST + "/lbmedia/" + data.getMedia().get(0).getId()).into(holder.img);
        Log.e("img", HMW_Constant.HOST + "/media/" + data.getMedia().get(0).getId());
        holder.btnLike.setOnClickListener(new View.OnClickListener() {
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
                        if (likesNumber == 0) {
                            holder.btnLike.setBackgroundResource(R.drawable.ic_liked);
                        } else {
                            holder.btnLike.setBackgroundResource(R.drawable.ic_like);
                        }
                        if (data.getLiked().size() == 0) {
                            liked.add(new HomeResponse.Liked());
                            data.setLiked(liked);
                            holder.tvLike.setText(likesNumber);
                        } else {
                            liked.clear();
                            data.setLiked(liked);
                            holder.tvLike.setText(likesNumber + "");
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
        holder.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkLogin(context)) {
                    showDialog(context);
                    return;
                }
                if(data.getSaleActive()==1){
                    addToCart(HMW_Constant.HOST + "/lbmedia/" + data.getMedia().get(0).getId(), data.getTitle(), data.getSalePrice(), data.getId());
                }else {
                    addToCart(HMW_Constant.HOST + "/lbmedia/" + data.getMedia().get(0).getId(), data.getTitle(), data.getPrice(), data.getId());
                }
            }
        });
        holder.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                addOrRemoveFavorite(data.getId());
                if (checkLogin(context)) {
                    showDialog(context);
                    return;
                }
                HMWApi api = new HMWApi();
                api.service().favorite("Bearer " + Libs_System.getStringData(context, HMW_Constant.TOKEN), "application/json", "hihi", data.getId(), new Callback<SimpleClass>() {
                    @Override
                    public void success(SimpleClass simpleClass, Response response) {

                        if (!simpleClass.getDescription().contains("Un")) {
                            holder.btnFavorite.setBackgroundResource(R.drawable.favorites_active);
                            favorited.clear();
                            favorited.add(new HomeResponse.Favorited());
                            data.setFavorited(favorited);
                        } else {
                            holder.btnFavorite.setBackgroundResource(R.drawable.favorites);
                            favorited.clear();
                            favorited.add(new HomeResponse.Favorited());
                            data.setFavorited(favorited);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("like", error.getMessage() + "");
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


    private void addToCart(String image, String title, String des, String product_id) {
        Bundle bundle = new Bundle();
        bundle.putString("image", image);
        bundle.putString("title", title);
        bundle.putString("des", des);
        bundle.putString("product_id", product_id);
        FragmentAddCart fragmentAddCart = new FragmentAddCart();
        fragmentAddCart.setArguments(bundle);
        ((ActivityHome) context).getSupportFragmentManager().beginTransaction().replace(R.id.main, fragmentAddCart).addToBackStack(null).commit();


    }

    private void addOrRemoveFavorite(String id) {
        if (checkLogin(context)) {
            showDialog(context);
            return;
        }
        HMWApi api = new HMWApi();
        api.service().favorite("Bearer " + Libs_System.getStringData(context, HMW_Constant.TOKEN), "application/json", "hihi", id, new Callback<SimpleClass>() {
            @Override
            public void success(SimpleClass simpleClass, Response response) {
                Libs_System.showToast(context, simpleClass.getDescription());
                if (simpleClass.getDescription().contains("Un")) {
                } else {
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("like", error.getMessage() + "");
                if (error.getResponse() != null && error.getResponse().getStatus() == 401) {
                    Libs_System.showToast(context, context.getString(R.string.unauthorized));
                    Libs_System.insertStringData(getContext(), HMW_Constant.TOKEN, "");
                    ((Activity) getContext()).finish();
                    getContext().startActivity(((Activity) getContext()).getIntent());
                }
            }
        });
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

    private int getHeight() {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((ActivityHome) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.widthPixels;
        return height;
    }

    static class ViewHolder {
        TextView tvLike;
        TextView tvCommentNumber;
        TextView tvDes;
        TextView tvPrice;
        TextView tvSalePrice;
        TextView btnBuy;
        ImageView btnLike;
        ImageView btnFavorite;
        ImageView img;
    }
}
