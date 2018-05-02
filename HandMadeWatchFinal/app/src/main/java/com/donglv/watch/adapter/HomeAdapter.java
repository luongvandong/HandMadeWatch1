package com.donglv.watch.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.donglv.watch.ActivityHome;
import com.donglv.watch.MainActivity;
import com.donglv.watch.R;
import com.donglv.watch.common.HMW_Constant;
import com.donglv.watch.entity.ActionLikeResponse;
import com.donglv.watch.entity.HomeResponse;
import com.donglv.watch.entity.ProductInfo;
import com.donglv.watch.entity.SimpleClass;
import com.donglv.watch.fragment.FragmentAddCart;
import com.donglv.watch.fragment.FragmentHome;
import com.donglv.watch.service.HMWApi;
import com.nam.customlibrary.Libs_System;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.donglv.watch.R.id.tvLikeNumber;
import static com.nam.customlibrary.Libs_System.dismissProgressDialog;

/**
 * Created by vcom on 11/11/2016.
 */
public class HomeAdapter extends ArrayAdapter<HomeResponse.Data> {
    private ArrayList<HomeResponse.Data> datas;
    private Context context;
    private Fragment fragment;
    private int likesNumber;
    public static LinearLayout layoutPager;

    public static LinearLayout getLayoutPager() {
        return layoutPager;
    }

    public HomeAdapter(Context context, ArrayList<HomeResponse.Data> objects) {
        super(context, 0, objects);
        this.context = context;
        this.datas = objects;
    }

    public HomeAdapter(Context context, ArrayList<HomeResponse.Data> objects, Fragment fragment) {
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
            convertView = inflater.inflate(R.layout.item_home_slide, parent, false);
            layoutPager = (LinearLayout) convertView.findViewById(R.id.layout_pager);
            holder = new ViewHolder();
            holder.tvLike = (TextView) convertView.findViewById(tvLikeNumber);
            holder.tvCommentNumber = (TextView) convertView.findViewById(com.donglv.watch.R.id.tvCommentNumber);
            holder.btnLike = (ImageView) convertView.findViewById(com.donglv.watch.R.id.btnLike);
            holder.btnFavorite = (ImageView) convertView.findViewById(com.donglv.watch.R.id.btnFavorite);
            holder.tvDes = (TextView) convertView.findViewById(com.donglv.watch.R.id.tvDes);
            holder.tvPrice = (TextView) convertView.findViewById(com.donglv.watch.R.id.tvPrice);
            holder.btnBuy = (TextView) convertView.findViewById(com.donglv.watch.R.id.btnBuy);
            holder.tvSalePrice = (TextView) convertView.findViewById(R.id.tvSalePrice);
            holder.tabLayout = (TabLayout) convertView.findViewById(R.id.tabDots);
            holder.viewPager = (android.support.v4.view.ViewPager) convertView.findViewById(R.id.viewPaper);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final HomeResponse.Data data = datas.get(position);
        final ArrayList<HomeResponse.Favorited> favorited = datas.get(position).getFavorited();
        final ArrayList<HomeResponse.Liked> liked = datas.get(position).getLiked();
        likesNumber = liked.size();
        if (likesNumber == 0) {
            holder.btnLike.setBackgroundResource(R.drawable.ic_liked);
        } else {
            holder.btnLike.setBackgroundResource(R.drawable.ic_like);
        }
        if (favorited.size() == 0) {
            holder.btnFavorite.setBackgroundResource(R.drawable.favorites);
        } else {
            holder.btnFavorite.setBackgroundResource(R.drawable.favorites_active);
        }
        holder.tvLike.setText(data.getLikes_count() + " ");
        holder.tvSalePrice.setVisibility(View.GONE);
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

        if (data.getMedia() != null && !data.getMedia().isEmpty()) {
            List<String> listImae = new ArrayList<String>();

            for (int i = 0; i < data.getMedia().size(); i++) {
                listImae.add(data.getMedia().get(i).getId());
                AdapterSliderImage adapterSliderImage = new AdapterSliderImage(context, listImae, datas.get(position).getId() + "");
                holder.viewPager.setAdapter(adapterSliderImage);

                if (listImae.size() <= 1) {
                    holder.tabLayout.setVisibility(View.INVISIBLE);
                } else {
                    holder.tabLayout.setVisibility(View.VISIBLE);
                }
                holder.tabLayout.setupWithViewPager(holder.viewPager, true);
            }
        }

        holder.btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                like(data.getId());
                if (checkLogin(context)) {
                    showDialog(context);
                    return;
                }
                final HMWApi api = new HMWApi();
                api.service().like("Bearer " + Libs_System.getStringData(context, HMW_Constant.TOKEN), "application/json", "hihi", data.getId(), new Callback<ActionLikeResponse>() {
                    @Override
                    public void success(ActionLikeResponse actionLikeResponse, Response response) {

                        api.service().getProductInfo("Bearer " + Libs_System.getStringData(context, HMW_Constant.TOKEN), "application/json", "hihi", data.getId(), new Callback<ProductInfo>() {
                            @Override
                            public void success(ProductInfo productInfo, Response response) {
                                if (productInfo.getData().getLiked() != null) {
                                    if (productInfo.getData().getLiked().size() == 0) {
                                        holder.btnLike.setBackgroundResource(R.drawable.ic_liked);
                                    } else {
                                        holder.btnLike.setBackgroundResource(R.drawable.ic_like);
                                    }
                                } else {
                                    holder.btnLike.setBackgroundResource(com.donglv.watch.R.drawable.ic_liked);
                                }
                                if (productInfo.getData().getLikes() != null) {
                                    likesNumber = productInfo.getData().getLikes().size();
                                    holder.tvLike.setText(likesNumber + "");
                                } else {
                                    holder.tvLike.setText(0 + "");
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                dismissProgressDialog();
                            }
                        });
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("favorite", error.getMessage() + "");
                        if (error.getResponse() != null && error.getResponse().getStatus() == 401) {
                            Libs_System.showToast(context, context.getString(com.donglv.watch.R.string.unauthorized));
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
                if (data.getSaleActive() == 1) {
                    addToCart(HMW_Constant.HOST + "/lbmedia/" + data.getMedia().get(0).getId(), data.getTitle(), data.getSalePrice(), data.getId());
                } else {
                    addToCart(HMW_Constant.HOST + "/lbmedia/" + data.getMedia().get(0).getId(), data.getTitle(), data.getPrice(), data.getId());
                }
            }
        });
        holder.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkLogin(context)) {
                    showDialog(context);
                    return;
                }
                final HMWApi api = new HMWApi();
                api.service().favorite("Bearer " + Libs_System.getStringData(context, HMW_Constant.TOKEN), "application/json", "hihi", data.getId(), new Callback<SimpleClass>() {
                    @Override
                    public void success(SimpleClass simpleClass, Response response) {
                        Libs_System.showToast(context, simpleClass.getDescription());
                        if (!simpleClass.getDescription().contains("Un")) {
                            holder.btnFavorite.setBackgroundResource(R.drawable.favorites);
                            favorited.clear();
                            data.setFavorited(favorited);
                        } else {
                            holder.btnFavorite.setBackgroundResource(R.drawable.favorites_active);
                            favorited.add(new HomeResponse.Favorited());
                            data.setFavorited(favorited);
                        }

                        api.service().getProductInfo("Bearer " + Libs_System.getStringData(context, HMW_Constant.TOKEN), "application/json", "hihi", data.getId(), new Callback<ProductInfo>() {
                            @Override
                            public void success(ProductInfo productInfo, Response response) {
                                List<HomeResponse.Favorited> favoriteds = productInfo.getData().getFavorited();
                                if (favoriteds.size() == 0) {
                                    holder.btnFavorite.setBackgroundResource(R.drawable.favorites);
                                } else {
                                    holder.btnFavorite.setBackgroundResource(R.drawable.favorites_active);
                                }
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                dismissProgressDialog();
                            }
                        });
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("like", error.getMessage() + "");
                        if (error.getResponse() != null && error.getResponse().getStatus() == 401) {
                            Libs_System.showToast(context, context.getString(com.donglv.watch.R.string.unauthorized));
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
        ((ActivityHome) context).getSupportFragmentManager().beginTransaction().replace(com.donglv.watch.R.id.main, fragmentAddCart).addToBackStack(null).commit();


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
                    Libs_System.showToast(context, context.getString(com.donglv.watch.R.string.unauthorized));
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
        builder.setMessage(context.getString(com.donglv.watch.R.string.you_must_login));
        builder.setPositiveButton(context.getString(com.donglv.watch.R.string.login), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(context, MainActivity.class));
            }
        });
        builder.setNegativeButton(context.getString(com.donglv.watch.R.string.cancel), new DialogInterface.OnClickListener() {
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
        TabLayout tabLayout;
        android.support.v4.view.ViewPager viewPager;
    }
}
