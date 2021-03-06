package com.donglv.watch.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.donglv.watch.ActivityHome;
import com.donglv.watch.MainActivity;
import com.donglv.watch.R;
import com.donglv.watch.adapter.AdapterSliderImageProductInfo;
import com.donglv.watch.adapter.CommentAdapter;
import com.donglv.watch.common.HMW_Constant;
import com.donglv.watch.common.NonScrollListView;
import com.donglv.watch.entity.ActionLikeResponse;
import com.donglv.watch.entity.HomeResponse;
import com.donglv.watch.entity.ProductInfo;
import com.donglv.watch.entity.SimpleClass;
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
public class FragmentProductInfo extends Fragment implements View.OnClickListener {
    private TextView btnLogIn;
    private TextView btnSignUp;
    private ProgressDialog progressDialog;
    private Context context;
    private HMWApi api = new HMWApi();
    private String post_id;

    private android.support.v4.view.ViewPager imgProduct;
    private TextView tvLike;
    private TextView tvCmt;
    private TextView tvTitle;
    private TextView tvMaterial;
    private TextView tvColor;
    private ImageView btnSend;
    private EditText edtContent;
    private TextView tvPrice;
    private NonScrollListView listComment;

    private CommentAdapter commentAdapter;
    private TextView btnBuy;

    private String image;
    private String title;
    private String des;

    private ImageView btnBack;
    private ImageView btnFavorite;
    private ImageView btnLike;
    private int likesNumber;
    private Drawable liked;
    private Drawable notLike;
    private Drawable favorited;
    private Drawable notFavorite;
    private boolean isSent = false;
    private TabLayout tabLayout;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_product_infomation, container, false);

        ActivityHome.toolbar.setNavigationIcon(R.drawable.ic_back_30);
        ActivityHome.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        liked = ContextCompat.getDrawable(getContext(), R.drawable.ic_liked);
        notLike = ContextCompat.getDrawable(getContext(), R.drawable.ic_like);
        favorited = ContextCompat.getDrawable(getContext(), R.drawable.favorites);
        notFavorite = ContextCompat.getDrawable(getContext(), R.drawable.favorites_active);
        context = rootView.getContext();
        Bundle bundle = getArguments();
        post_id = bundle.getString("post_id");
        getProductInfo(post_id);
        init(rootView);
        return rootView;
    }

    private void init(View rootView) {
        btnLike = (ImageView) rootView.findViewById(R.id.btnLike);
        btnLike.setOnClickListener(this);
        imgProduct = (android.support.v4.view.ViewPager) rootView.findViewById(R.id.viewPaper);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabDots);
        btnFavorite = (ImageView) rootView.findViewById(R.id.btnFavorite);
        btnFavorite.setOnClickListener(this);
        tvLike = (TextView) rootView.findViewById(R.id.tvLikeNumber);
        tvCmt = (TextView) rootView.findViewById(R.id.tvCommentNumber);
        tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
        tvMaterial = (TextView) rootView.findViewById(R.id.tvMaterial);
        tvColor = (TextView) rootView.findViewById(R.id.tvColor);
        tvPrice = (TextView) rootView.findViewById(R.id.tvPrice);
        listComment = (NonScrollListView) rootView.findViewById(R.id.listComment);
        edtContent = (EditText) rootView.findViewById(R.id.edtContent);
        btnSend = (ImageView) rootView.findViewById(R.id.btnSend);
        btnBuy = (TextView) rootView.findViewById(R.id.btnBuy);
        btnBuy.setOnClickListener(this);
        btnSend.setOnClickListener(this);
        listComment.setDivider(null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                if (isSent == true) {
                    isSent = false;
                }
                Log.e("button send", "test");
                if (checkLogin(context)) {
                    showDialog(context);
                    return;
                }
                if (edtContent.getText().toString().equals("")) {
                    Libs_System.showToast(context, getString(R.string.input_message));
                    return;
                }
                Libs_System.hideKeyboard(getActivity());
                if (isSent == false) {
                    btnSend.setEnabled(false);
                    api.service().comment("Bearer " + Libs_System.getStringData(context, HMW_Constant.TOKEN), "application/json", edtContent.getText().toString() + "", post_id, new Callback<SimpleClass>() {
                        @Override
                        public void success(SimpleClass simpleClass, Response response) {
                            getProductInfo(post_id);
                            edtContent.setText("");
                            isSent = true;
                            btnSend.setEnabled(true);
                            Log.i("TAG", "success: " + isSent);
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            Log.e("cmt", error.getMessage() + "");
                            if (error.getResponse() != null && error.getResponse().getStatus() == 401) {
                                Libs_System.showToast(context, context.getString(R.string.unauthorized));
                                Libs_System.insertStringData(getContext(), HMW_Constant.TOKEN, "");
                                ((Activity) getContext()).finish();
                                getContext().startActivity(((Activity) getContext()).getIntent());
                            }
                        }
                    });
                }

                break;
            case R.id.btnBuy:
                if (checkLogin(context)) {
                    showDialog(context);
                    return;
                }
                addToCart(image, title, des);
                break;
            case R.id.btnFavorite:
                addOrRemoveFavorite(post_id);
                break;
            case R.id.btnLike:
                like(post_id);
                break;

        }
    }

    public void showProgressDialog(Context context, String message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(0);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        if (context != null)
            progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

    }

    private void getProductInfo(final String post_id) {
        showProgressDialog(context, "loading");
        Log.d("nguyenquandasd", post_id);
        Log.d("nguyeuquangdas", Libs_System.getStringData(context, HMW_Constant.TOKEN));
        if (Libs_System.getStringData(context, HMW_Constant.TOKEN) != null && !Libs_System.getStringData(context, HMW_Constant.TOKEN).equals("")) {
            Log.d("davaoday", "cotoken");
            api.service().getProductInfo("Bearer " + Libs_System.getStringData(context, HMW_Constant.TOKEN), "application/json", "hihi", post_id, new Callback<ProductInfo>() {
                @Override
                public void success(ProductInfo productInfo, Response response) {

                    dismissProgressDialog();
                    Log.d("nguyenquang", response.getReason());
                    tvTitle.setText(productInfo.getData().getTitle());
                    int sale_active = productInfo.getData().getSaleActive();
                    if (sale_active == 1) {
                        tvPrice.setText(productInfo.getData().getSalePrice() + " " + productInfo.getData().getCurrency());
                    } else {
                        tvPrice.setText(productInfo.getData().getPrice() + " " + productInfo.getData().getCurrency());
                    }
                    likesNumber = productInfo.getData().getLikes_count();

                    List<HomeResponse.Favorited> favoriteds = productInfo.getData().getFavorited();
                    if (favoriteds.size() == 0) {
                        btnFavorite.setBackgroundResource(R.drawable.favorites);
                    } else {
                        btnFavorite.setBackgroundResource(R.drawable.favorites_active);
                    }
                    final List<ProductInfo.Liked> liked = productInfo.getData().getLiked();
                    likesNumber = liked.size();
                    if (likesNumber == 0) {
                        btnLike.setBackgroundResource(R.drawable.ic_liked);
                    } else {
                        btnLike.setBackgroundResource(R.drawable.ic_like);
                    }
                    if (productInfo.getData().getFavorited().size() == 0) {
                        btnFavorite.setImageDrawable(favorited);
                    } else {
                        btnFavorite.setImageDrawable(notFavorite);
                    }
                    Log.e("notLike number", "" + likesNumber);
                    tvCmt.setText("" + productInfo.getData().getComments_count());
                    commentAdapter = new CommentAdapter(context, productInfo.getData().getComments());
                    listComment.setAdapter(commentAdapter);
                    tvLike.setText("" + productInfo.getData().getLikes_count());
              /*  if (productInfo.getData().getMedia() != null && !productInfo.getData().getMedia().isEmpty()) {
                    Glide.with(context).load(HMW_Constant.HOST + "/lbmedia/" + productInfo.getData().getMedia().get(0).getId()).into(imgProduct);
                }*/
                    if (productInfo.getData().getMedia() != null && !productInfo.getData().getMedia().isEmpty()) {
                        List<String> listImage = new ArrayList<String>();
                        for (int i = 0; i < productInfo.getData().getMedia().size(); i++) {
                            listImage.add(productInfo.getData().getMedia().get(i).getId());
                            AdapterSliderImageProductInfo adapterSliderImage = new AdapterSliderImageProductInfo(context, listImage);
                            imgProduct.setAdapter(adapterSliderImage);
                            if (listImage.size() <= 1) {
                                tabLayout.setVisibility(View.INVISIBLE);
                            } else {
                                tabLayout.setVisibility(View.VISIBLE);
                            }
                            tabLayout.setupWithViewPager(imgProduct, true);
                        }
                    /*Glide.with(context).load(HMW_Constant.HOST + "/lbmedia/" + productInfo.getData().getMedia().get(0).getId()).placeholder(R.drawable.ic_loadding).error(R.drawable.ic_loadding).into(imgProduct);*/
                    }
                    tvMaterial.setText(productInfo.getData().getDescription());
                    Log.e("task force", HMW_Constant.HOST + "/lbmedia/" + productInfo.getData().getMedia().get(0).getId());
                    image = HMW_Constant.HOST + "/lbmedia/" + productInfo.getData().getMedia().get(0).getId();
                    title = productInfo.getData().getTitle();
                    des = "hihi";
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("chuyengiday", error.toString());
                    dismissProgressDialog();
                }
            });
        } else {
            Log.d("davaoday", "khongcotoken");
            api.service().getProductInfoNoToken("application/json", "hihi", post_id, new Callback<ProductInfo>() {
                @Override
                public void success(ProductInfo productInfo, Response response) {

                    dismissProgressDialog();
                    Log.d("nguyenquang", response.getReason());
                    tvTitle.setText(productInfo.getData().getTitle());
                    int sale_active = productInfo.getData().getSaleActive();
                    if (sale_active == 1) {
                        tvPrice.setText(productInfo.getData().getSalePrice() + " " + productInfo.getData().getCurrency());
                    } else {
                        tvPrice.setText(productInfo.getData().getPrice() + " " + productInfo.getData().getCurrency());
                    }
                    likesNumber = productInfo.getData().getLikes_count();

                    List<HomeResponse.Favorited> favoriteds = productInfo.getData().getFavorited();
                    if (favoriteds.size() == 0) {
                        btnFavorite.setBackgroundResource(R.drawable.favorites);
                    } else {
                        btnFavorite.setBackgroundResource(R.drawable.favorites_active);
                    }
                    final List<ProductInfo.Liked> liked = productInfo.getData().getLiked();
                    likesNumber = liked.size();
                    if (likesNumber == 0) {
                        btnLike.setBackgroundResource(R.drawable.ic_liked);
                    } else {
                        btnLike.setBackgroundResource(R.drawable.ic_like);
                    }
                    if (productInfo.getData().getFavorited().size() == 0) {
                        btnFavorite.setImageDrawable(favorited);
                    } else {
                        btnFavorite.setImageDrawable(notFavorite);
                    }
                    Log.e("notLike number", "" + likesNumber);
                    tvCmt.setText("" + productInfo.getData().getComments_count());
                    commentAdapter = new CommentAdapter(context, productInfo.getData().getComments());
                    listComment.setAdapter(commentAdapter);
                    tvLike.setText("" + productInfo.getData().getLikes_count());
              /*  if (productInfo.getData().getMedia() != null && !productInfo.getData().getMedia().isEmpty()) {
                    Glide.with(context).load(HMW_Constant.HOST + "/lbmedia/" + productInfo.getData().getMedia().get(0).getId()).into(imgProduct);
                }*/
                    if (productInfo.getData().getMedia() != null && !productInfo.getData().getMedia().isEmpty()) {
                        List<String> listImage = new ArrayList<String>();
                        for (int i = 0; i < productInfo.getData().getMedia().size(); i++) {
                            listImage.add(productInfo.getData().getMedia().get(i).getId());
                            AdapterSliderImageProductInfo adapterSliderImage = new AdapterSliderImageProductInfo(context, listImage);
                            imgProduct.setAdapter(adapterSliderImage);
                            if (listImage.size() <= 1) {
                                tabLayout.setVisibility(View.INVISIBLE);
                            } else {
                                tabLayout.setVisibility(View.VISIBLE);
                            }
                            tabLayout.setupWithViewPager(imgProduct, true);
                        }
                    /*Glide.with(context).load(HMW_Constant.HOST + "/lbmedia/" + productInfo.getData().getMedia().get(0).getId()).placeholder(R.drawable.ic_loadding).error(R.drawable.ic_loadding).into(imgProduct);*/
                    }
                    tvMaterial.setText(productInfo.getData().getDescription());
                    Log.e("task force", HMW_Constant.HOST + "/lbmedia/" + productInfo.getData().getMedia().get(0).getId());
                    image = HMW_Constant.HOST + "/lbmedia/" + productInfo.getData().getMedia().get(0).getId();
                    title = productInfo.getData().getTitle();
                    des = "hihi";
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("chuyengiday", error.toString());
                    dismissProgressDialog();
                }
            });
        }
    }

    private void addToCart(String image, String title, String des) {
        Bundle bundle = new Bundle();
        bundle.putString("image", image);
        bundle.putString("title", title);
        bundle.putString("des", des);
        FragmentAddCart fragmentAddCart = new FragmentAddCart();
        fragmentAddCart.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.main, fragmentAddCart).addToBackStack(null).commit();

    }


    private boolean checkLogin(final Context context) {
        if (Libs_System.getStringData(context, HMW_Constant.TOKEN).equals("")) {
            return true;
        }
        return false;
    }

    private void showDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(getString(R.string.you_must_login));
        builder.setPositiveButton(getString(R.string.login), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(context, MainActivity.class));
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void addOrRemoveFavorite(String id) {
        if (checkLogin(context)) {
            showDialog(context);
            return;
        }
        final HMWApi api = new HMWApi();
        api.service().favorite("Bearer " + Libs_System.getStringData(context, HMW_Constant.TOKEN), "application/json", "hihi", id, new Callback<SimpleClass>() {
            @Override
            public void success(SimpleClass simpleClass, Response response) {
                Drawable drawable = btnFavorite.getDrawable() == favorited ? notFavorite : favorited;
                btnFavorite.setImageDrawable(drawable);
                if (!simpleClass.getDescription().contains("Un")) {
                    btnFavorite.setBackgroundResource(R.drawable.favorites);
                } else {
                    btnFavorite.setBackgroundResource(R.drawable.favorites_active);
                }

                api.service().getProductInfo("Bearer " + Libs_System.getStringData(context, HMW_Constant.TOKEN), "application/json", "hihi", post_id, new Callback<ProductInfo>() {
                    @Override
                    public void success(ProductInfo productInfo, Response response) {
                        List<HomeResponse.Favorited> favoriteds = productInfo.getData().getFavorited();
                        Log.d("nguyenquang1", favoriteds.size() + "");
                        if (favoriteds.size() == 0) {
                            btnFavorite.setBackgroundResource(R.drawable.favorites);
                        } else {
                            btnFavorite.setBackgroundResource(R.drawable.favorites_active);
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
                Log.e("notLike", error.getMessage() + "");
                if (error.getResponse() != null && error.getResponse().getStatus() == 401) {
                    Libs_System.showToast(context, context.getString(R.string.unauthorized));
                    Libs_System.insertStringData(getContext(), HMW_Constant.TOKEN, "");
                    ((Activity) getContext()).finish();
                    getContext().startActivity(((Activity) getContext()).getIntent());
                }
            }
        });
    }


    private void like(final String id) {
        if (checkLogin(context)) {
            showDialog(context);
            return;
        }
        final HMWApi api = new HMWApi();
        api.service().like("Bearer " + Libs_System.getStringData(context, HMW_Constant.TOKEN), "application/json", "hihi", id, new Callback<ActionLikeResponse>() {
            @Override
            public void success(ActionLikeResponse actionLikeResponse, Response response) {

                api.service().getProductInfo("Bearer " + Libs_System.getStringData(context, HMW_Constant.TOKEN), "application/json", "hihi", id, new Callback<ProductInfo>() {
                    @Override
                    public void success(ProductInfo productInfo, Response response) {
                        if (productInfo.getData().getLiked() != null) {
                            if (productInfo.getData().getLiked().size() == 0) {
                                btnLike.setBackgroundResource(R.drawable.ic_liked);
                            } else {
                                btnLike.setBackgroundResource(R.drawable.ic_like);
                            }
                        } else {
                            btnLike.setBackgroundResource(com.donglv.watch.R.drawable.ic_liked);
                        }
                        if (productInfo.getData().getLikes() != null) {
                            likesNumber = productInfo.getData().getLikes().size();
                            tvLike.setText(likesNumber + "");
                        } else {
                            tvLike.setText(0 + "");
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
                    Libs_System.showToast(context, context.getString(R.string.unauthorized));
                    Libs_System.insertStringData(getContext(), HMW_Constant.TOKEN, "");
                    ((Activity) getContext()).finish();
                    getContext().startActivity(((Activity) getContext()).getIntent());
                }
            }
        });
    }

//    private void like2(String id) {
//        if (checkLogin(context)) {
//            showDialog(context);
//            return;
//        }
//        HMWApi api = new HMWApi();
//        api.service().notLike("Bearer " + Libs_System.getStringData(context, HMW_Constant.TOKEN), "application/json", "hihi", id, new Callback<ActionLikeResponse>() {
//            @Override
//            public void success(ActionLikeResponse actionLikeResponse, Response response) {
//                likesNumber = actionLikeResponse.getPost().getLikes_count();
//                Log.d("clgita", likesNumber + "");
//                if (likesNumber == 0) {
//                    btnLike.setBackgroundResource(alghima.io.anaqa.R.drawable.ic_liked);
//                } else {
//                    btnLike.setBackgroundResource(alghima.io.anaqa.R.drawable.ic_like);
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.e("favorite", error.getMessage() + "");
//                if (error.getResponse() != null && error.getResponse().getStatus() == 401) {
//                    Libs_System.showToast(context, context.getString(alghima.io.anaqa.R.string.unauthorized));
//                    Libs_System.insertStringData(getContext(), HMW_Constant.TOKEN, "");
//                    ((Activity) getContext()).finish();
//                    getContext().startActivity(((Activity) getContext()).getIntent());
//                }
//            }
//        });
//    }
}
