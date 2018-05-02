package com.donglv.watch.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donglv.watch.R;
import com.donglv.watch.common.HMW_Constant;
import com.donglv.watch.entity.MyCart;
import com.donglv.watch.entity.ProductInfo;
import com.donglv.watch.service.HMWApi;
import com.nam.customlibrary.Libs_System;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by vcom on 16/11/2016.
 */
public class FragmentAddCart extends Fragment implements View.OnClickListener {
    private ProgressDialog progressDialog;
    private Context context;
    private Bundle bundle;
    private String image;
    private String title;
    private String des;


    private ImageView imgAvatar;
    private TextView tvTitle;
    private TextView tvDes;
    private EditText edtAddress;
    private EditText edtInformation;
    private TextView tvAdd;
    private String product_id;
    private String currency = "";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_cart, container, false);
        context = rootView.getContext();
        init(rootView);
        bundle = getArguments();
        image = bundle.getString("image");
        title = bundle.getString("title");
        des = bundle.getString("des");
        product_id = bundle.getString("product_id");
        getProductInfo(product_id);
        Glide.with(context).load(image + "?height=500&width=500&style=scale_to_fill").into(imgAvatar);
        tvTitle.setText(title);

        return rootView;
    }

    private void init(View rootView) {
        imgAvatar = (ImageView) rootView.findViewById(R.id.imgAvatar);
        tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
        tvDes = (TextView) rootView.findViewById(R.id.tvDes);
        edtAddress = (EditText) rootView.findViewById(R.id.edtAddress);
        edtInformation = (EditText) rootView.findViewById(R.id.edtInformation);
        tvAdd = (TextView) rootView.findViewById(R.id.tvAdd);
        tvAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvAdd:
                if (edtAddress.getText().toString().isEmpty()) {
                    Libs_System.showToast(getContext(), getString(R.string.telephone_should_not_empty));
                    return;
                }
                if (edtInformation.getText().toString().isEmpty()) {
                    Libs_System.showToast(getContext(), getString(R.string.address_should_not_empty));
                    return;
                }
                MyCart myCart = new MyCart(image, title, des + currency, edtAddress.getText().toString(), edtInformation.getText().toString(), product_id);
                HMW_Constant.myCarts.add(myCart);
                getFragmentManager().beginTransaction().replace(R.id.main, new FragmentMyShoppingCart()).addToBackStack(null).commit();
                Libs_System.showToast(getContext(), getString(R.string.success));
                break;
        }
    }

    public void getProductInfo(String product_id) {
        HMWApi hmwApi = new HMWApi();
        hmwApi.service().getProductInfo("Bearer " + Libs_System.getStringData(context, HMW_Constant.TOKEN), "application/json", "application/json", product_id, new Callback<ProductInfo>() {
            @Override
            public void success(ProductInfo productInfo, Response response) {
                currency = productInfo.getData().getCurrency();
                tvDes.setText(des + currency);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("bisaoday", error.toString());
            }
        });
    }

    public void showProgressDialog(Context context, String message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(0);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }


    }

}
