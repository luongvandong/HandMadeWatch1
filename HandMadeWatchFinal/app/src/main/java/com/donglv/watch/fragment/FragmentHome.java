package com.donglv.watch.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.donglv.watch.ActivityHome;
import com.donglv.watch.R;
import com.donglv.watch.adapter.HomeAdapter;
import com.donglv.watch.adapter.LikesAdapter;
import com.donglv.watch.common.HMW_Constant;
import com.donglv.watch.entity.HomeResponse;
import com.donglv.watch.entity.ProductInfo;
import com.donglv.watch.service.HMWApi;
import com.nam.customlibrary.Libs_System;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by vcom on 11/11/2016.
 */
public class FragmentHome extends Fragment implements View.OnClickListener {
    private TextView btnLogIn;
    private TextView btnSignUp;
    private ProgressDialog progressDialog;
    private Context context;
    private ListView listHome;
    private ListView listLikeUser;
    private HomeAdapter homeAdapter;
    private ArrayList<HomeResponse.Data> datas = new ArrayList<>();
    private RelativeLayout overlayLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(com.donglv.watch.R.layout.fragment_home, container, false);
//        ActivityHome.toolbar.setNavigationIcon(com.msab.handmadewatch.R.drawable.ic_list);

        context = rootView.getContext();
        init(rootView);
        getAllProduct();

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView txtTitle = (TextView) ActivityHome.toolbar.findViewById(R.id.txtToolbarTitle);
        txtTitle.setText(getText(R.string.home));
        ActivityHome.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ActivityHome.drawer.isDrawerOpen()) {
                    ActivityHome.drawer.openDrawer();
                }
            }
        });
    }

    private void init(View rootView) {
        overlayLayout = (RelativeLayout) rootView.findViewById(com.donglv.watch.R.id.overlayLayout);
        overlayLayout.setOnClickListener(this);
        listLikeUser = (ListView) rootView.findViewById(com.donglv.watch.R.id.listLikeUser);
        listHome = (ListView) rootView.findViewById(com.donglv.watch.R.id.listHome);
        listHome.setVerticalScrollBarEnabled(false);
        listHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("post_id", datas.get(position).getId() + "");
                FragmentProductInfo fragmentProductInfo = new FragmentProductInfo();
                fragmentProductInfo.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(com.donglv.watch.R.id.main, fragmentProductInfo).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case com.donglv.watch.R.id.overlayLayout:
                overlayLayout.setVisibility(View.GONE);
                break;
            case com.donglv.watch.R.id.btnLogIn:
                getFragmentManager().beginTransaction().replace(com.donglv.watch.R.id.main, new FragmentLogIn()).addToBackStack(null).commit();
                break;
            case com.donglv.watch.R.id.btnSignUp:
                getFragmentManager().beginTransaction().replace(com.donglv.watch.R.id.main, new FragmentRegister()).addToBackStack(null).commit();
                break;
        }
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

    public void getProductInfo(String post_id) {
        showProgressDialog(context, "loading");
        HMWApi api = new HMWApi();
        api.service().getProductInfo("Bearer " + Libs_System.getStringData(context, HMW_Constant.TOKEN), "application/json", "hihi", post_id, new Callback<ProductInfo>() {
            @Override
            public void success(ProductInfo productInfo, Response response) {
                dismissProgressDialog();
                if (productInfo.getCode().equals("200")) {
                    LikesAdapter likesAdapter = new LikesAdapter(context, productInfo.getData().getLikes());
                    overlayLayout.setVisibility(View.VISIBLE);
                    listLikeUser.setAdapter(likesAdapter);
                }

            }

            @Override
            public void failure(RetrofitError error) {
                dismissProgressDialog();
            }
        });
    }

    private void getAllProduct() {
        if (Libs_System.getStringData(context, HMW_Constant.TOKEN).equals("")) {
            HMWApi api = new HMWApi();
            api.service().getAllProduct(new Callback<HomeResponse>() {
                @Override
                public void success(HomeResponse homeResponse, Response response) {
                    datas = homeResponse.getData();
                    if (datas != null)
                        homeAdapter = new HomeAdapter(context, datas, getFragmentManager().findFragmentById(com.donglv.watch.R.id.main));
                        listHome.setAdapter(homeAdapter);
                }

                @Override
                public void failure(RetrofitError error) {
                    if (error.getResponse() != null && error.getResponse().getStatus() == 401) {
                        Libs_System.showToast(context, context.getString(com.donglv.watch.R.string.unauthorized));
                        Libs_System.insertStringData(getContext(), HMW_Constant.TOKEN, "");
                        ((Activity) getContext()).finish();
                        getContext().startActivity(((Activity) getContext()).getIntent());
                    }
                }
            });
        } else {
            HMWApi api = new HMWApi();
            api.service().getAllProductWithLogin("Bearer " + Libs_System.getStringData(context, HMW_Constant.TOKEN), "application/json", new Callback<HomeResponse>() {
                @Override
                public void success(HomeResponse homeResponse, Response response) {
                    datas = homeResponse.getData();
                    homeAdapter = new HomeAdapter(context, datas, getFragmentManager().findFragmentById(com.donglv.watch.R.id.main));
                    listHome.setAdapter(homeAdapter);
                }

                @Override
                public void failure(RetrofitError error) {
                    if (error.getResponse() == null) {
                        return;
                    }
                    if (error.getResponse() != null && error.getResponse().getStatus() == 401) {
                        Libs_System.showToast(context, context.getString(com.donglv.watch.R.string.unauthorized));
                        Libs_System.insertStringData(getContext(), HMW_Constant.TOKEN, "");
                        ((Activity) getContext()).finish();
                        getContext().startActivity(((Activity) getContext()).getIntent());
                    }

                }
            });
        }
    }
}
