package com.donglv.watch.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import com.donglv.watch.entity.HomeResponse;
import com.donglv.watch.entity.SimpleClass;
import com.donglv.watch.fragment.FragmentAddCart;
import com.donglv.watch.fragment.FragmentFavorite;
import com.donglv.watch.service.HMWApi;

import com.bumptech.glide.Glide;
import com.nam.customlibrary.Libs_System;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by vcom on 11/11/2016.
 */
public class FavoriteAdapter extends ArrayAdapter<HomeResponse.Data> {
    private ArrayList<HomeResponse.Data> datas;
    private Context context;
    private FragmentFavorite fragmentFavorite;

    private int likesNumber;

    public FavoriteAdapter(Context context, ArrayList<HomeResponse.Data> objects, FragmentFavorite fragmentFavorite) {
        super(context, 0, objects);
        this.context = context;
        this.datas = objects;
        this.fragmentFavorite = fragmentFavorite;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.item_home, parent, false);
//        TextView tvLike = (TextView) convertView.findViewById(R.id.tvLike);
        final ImageView btnLike = (ImageView) convertView.findViewById(R.id.btnLike);
        final ImageView btnFavorite = (ImageView) convertView.findViewById(R.id.btnFavorite);
        TextView tvDes = (TextView) convertView.findViewById(R.id.tvDes);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
        TextView tvSalePrice = (TextView) convertView.findViewById(R.id.tvSalePrice);
        TextView btnBuy = (TextView) convertView.findViewById(R.id.btnBuy);
        ImageView img = (ImageView) convertView.findViewById(R.id.img);
        final TextView tvLikeNumber = (TextView) convertView.findViewById(R.id.tvLikeNumber);
        final TextView tvCommentNumber = (TextView) convertView.findViewById(R.id.tvCommentNumber);
        final HomeResponse.Data data = datas.get(position);
        final ArrayList<HomeResponse.Liked> liked = datas.get(position).getLiked();
        final ArrayList<HomeResponse.Favorited> favorited = datas.get(position).getFavorited();
//        tvLike.setText(data.getLikes_count()+"");
        int saleActive = data.getSaleActive();
        if (saleActive == 1) {
            tvPrice.setText(data.getSalePrice() + " " + data.getCurrency());
        } else {
            tvPrice.setText(data.getPrice() + " " + data.getCurrency());
        }
        tvDes.setText(data.getTitle());
        int commentsNumber = data.getComments_count();
        likesNumber = data.getLikes_count();
        tvLikeNumber.setText(data.getLikes_count() + "" );
        tvCommentNumber.setText(data.getComments_count() + "");
        if (likesNumber == 0) {
            btnLike.setBackgroundResource(R.drawable.ic_liked);
        } else {
            btnLike.setBackgroundResource(R.drawable.ic_like);
        }

        if (data.getFavorited().size() == 0) {
            btnFavorite.setBackgroundResource(R.drawable.favorites);
        } else {
            btnFavorite.setBackgroundResource(R.drawable.favorites_active);
        }
        tvSalePrice.setVisibility(View.GONE);
        if(data.getMedia()!=null&& !data.getMedia().isEmpty()) {
            Glide.with(context).load(HMW_Constant.HOST + "/lbmedia/" + data.getMedia().get(0).getId()).into(img);
        }
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //like(data.getId());
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
                            btnLike.setBackgroundResource(R.drawable.ic_liked);
                        } else {
                            btnLike.setBackgroundResource(R.drawable.ic_like);
                        }
                        if (data.getLiked().size() == 0) {
                            liked.add(new HomeResponse.Liked());
                            data.setLiked(liked);
                            tvLikeNumber.setText(likesNumber + "");
                        } else {
                            liked.clear();
                            data.setLiked(liked);
                            tvLikeNumber.setText(likesNumber + "");
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
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
        btnFavorite.setOnClickListener(new View.OnClickListener() {
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
                            btnFavorite.setBackgroundResource(R.drawable.favorites_active);
                            favorited.clear();
                            data.setFavorited(favorited);
                        } else {
                            Libs_System.showToast(context, simpleClass.getDescription());
                            btnFavorite.setBackgroundResource(R.drawable.favorites);
                            favorited.add(new HomeResponse.Favorited());
                            data.setFavorited(favorited);
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
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
        btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart(HMW_Constant.HOST + "/lbmedia/" + data.getMedia().get(0).getId(), data.getTitle(), "hihi", data.getId());
            }
        });
        tvPrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                addOrRemoveFavorite(data.getId());
            }
        });
        return convertView;
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
        HMWApi api = new HMWApi();
        api.service().favorite("Bearer " + Libs_System.getStringData(context, HMW_Constant.TOKEN), "application/json", "hihi", id, new Callback<SimpleClass>() {
            @Override
            public void success(SimpleClass simpleClass, Response response) {
                Libs_System.showToast(context, simpleClass.getDescription());
                fragmentFavorite.getMyFavorite();

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

}
