package com.donglv.watch.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
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
import com.donglv.watch.adapter.FavoriteAdapter;
import com.donglv.watch.common.HMW_Constant;
import com.donglv.watch.entity.HomeResponse;
import com.donglv.watch.service.HMWApi;
import com.nam.customlibrary.Libs_System;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by vcom on 11/11/2016.
 */
public class FragmentFavorite extends Fragment implements View.OnClickListener {
    private ProgressDialog progressDialog;
    private Context context;
    private ListView listHome;
    private FavoriteAdapter favoriteAdapter;
    private ArrayList<HomeResponse.Data> datas = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(com.donglv.watch.R.layout.fragment_home, container, false);
        ActivityHome.toolbar.setNavigationIcon(R.drawable.ic_list);
        ActivityHome.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ActivityHome.drawer.isDrawerOpen()) {
                    Libs_System.hideKeyboard(getActivity());
                    ActivityHome.drawer.openDrawer();
                }
            }
        });
        context = rootView.getContext();
        init(rootView);
        getMyFavorite();
        String android_id= Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.e("da",android_id);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView txtTitle =(TextView) ActivityHome.toolbar.findViewById(R.id.txtToolbarTitle);
        txtTitle.setText(getText(R.string.my_favorites));
    }

    private void init(View rootView) {
        listHome = (ListView) rootView.findViewById(com.donglv.watch.R.id.listHome);
        listHome.setVerticalScrollBarEnabled(false);
        listHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("post_id", datas.get(position).getId()+"");
                FragmentProductInfo fragmentProductInfo  = new FragmentProductInfo();
                fragmentProductInfo.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(com.donglv.watch.R.id.main, fragmentProductInfo).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

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

    public void getMyFavorite() {
        HMWApi api = new HMWApi();
        api.service().getMyFavorite("Bearer " + Libs_System.getStringData(context, HMW_Constant.TOKEN), "application/json", new Callback<HomeResponse>() {
            @Override
            public void success(HomeResponse homeResponse, Response response) {
                favoriteAdapter = new FavoriteAdapter(context, homeResponse.getData(), FragmentFavorite.this);
                listHome.setAdapter(favoriteAdapter);
                datas = homeResponse.getData();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("myfavorite", error.getMessage());
                if(error.getResponse()!=null&&error.getResponse().getStatus()==401){
                    Libs_System.showToast(context,context.getString(com.donglv.watch.R.string.unauthorized));
                    Libs_System.insertStringData(getContext(), HMW_Constant.TOKEN, "");
                    ((Activity) getContext()).finish();
                    getContext().startActivity(((Activity) getContext()).getIntent());
                }
            }
        });
    }
}
