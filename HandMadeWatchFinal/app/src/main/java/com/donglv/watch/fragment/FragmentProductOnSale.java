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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.donglv.watch.ActivityHome;
import com.donglv.watch.R;
import com.donglv.watch.adapter.ProductOnSaleAdapter;
import com.donglv.watch.entity.HomeResponse;
import com.donglv.watch.service.HMWApi;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by vcom on 26/12/2016.
 */
public class FragmentProductOnSale extends Fragment implements View.OnClickListener {
    private ProgressDialog progressDialog;
    private Context context;
    private ListView listHome;
    private ProductOnSaleAdapter homeAdapter;
    private ArrayList<HomeResponse.Data> datas = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(com.donglv.watch.R.layout.fragment_home, container, false);
        ActivityHome.toolbar.setNavigationIcon(com.donglv.watch.R.drawable.ic_list);
        ActivityHome.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ActivityHome.drawer.isDrawerOpen()) {
                    ActivityHome.drawer.openDrawer();
                }
            }
        });
        context = rootView.getContext();
        init(rootView);
        getProductOnSale();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView txtTitle =(TextView) ActivityHome.toolbar.findViewById(R.id.txtToolbarTitle);
        txtTitle.setText(getText(R.string.product_on_sale));
    }

    private void init(View rootView) {
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

    private void getProductOnSale() {
        showProgressDialog(context, "loading");
        HMWApi api = new HMWApi();
        api.service().getProductOnSale("sale", new Callback<HomeResponse>() {
            @Override
            public void success(HomeResponse homeResponse, Response response) {
                dismissProgressDialog();
                datas = homeResponse.getData();
                homeAdapter = new ProductOnSaleAdapter(context, datas);
                listHome.setAdapter(homeAdapter);
            }

            @Override
            public void failure(RetrofitError error) {
                dismissProgressDialog();
                Log.e("get Product on sale", error.getMessage());
            }
        });
    }
}
