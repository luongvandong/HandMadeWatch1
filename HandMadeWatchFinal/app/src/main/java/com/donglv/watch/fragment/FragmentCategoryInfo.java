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
import com.donglv.watch.adapter.CategoryInfoAdapter2;
import com.donglv.watch.adapter.HomeAdapter;
import com.donglv.watch.entity.CategoryInfo;
import com.donglv.watch.service.HMWApi;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by vcom on 15/11/2016.
 */
public class FragmentCategoryInfo extends Fragment implements View.OnClickListener {
    private ProgressDialog progressDialog;
    private Context context;
    private HMWApi api = new HMWApi();
    private String category_id;
    private ListView listCategory;
    private CategoryInfoAdapter2 categoryInfoAdapter;
    private HomeAdapter homeAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(com.donglv.watch.R.layout.fragment_category_info, container, false);
        ActivityHome.toolbar.setNavigationIcon(com.donglv.watch.R.drawable.ic_back_30);
        ActivityHome.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        context = rootView.getContext();
        Bundle bundle = getArguments();
        category_id = bundle.getString("category_id");
        init(rootView);
        getCategoryInfo(category_id);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView txtTitle = (TextView) ActivityHome.toolbar.findViewById(R.id.txtToolbarTitle);
        txtTitle.setText(getText(R.string.browse_category));
    }

    private void init(View rootView) {
        listCategory = (ListView) rootView.findViewById(com.donglv.watch.R.id.listCategory);
        listCategory.setVerticalScrollBarEnabled(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case com.donglv.watch.R.id.btnSend:
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

    private void getCategoryInfo(String category_id) {
        Log.e("oh", category_id);
        showProgressDialog(context, "loading");
        api.service().getCategoryInfo(category_id, new Callback<CategoryInfo>() {
            @Override
            public void success(final CategoryInfo categoryInfo, Response response) {
                dismissProgressDialog();
                Log.d("nguyenvadasdasd", categoryInfo + " ");
                categoryInfoAdapter = new CategoryInfoAdapter2(context, categoryInfo.getData().getPosts());
                listCategory.setAdapter(categoryInfoAdapter);
                listCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Bundle bundle = new Bundle();
                        bundle.putString("post_id", categoryInfo.getData().getPosts().get(position).getId() + "");
                        FragmentProductInfo fragmentProductInfo = new FragmentProductInfo();
                        fragmentProductInfo.setArguments(bundle);
                        getFragmentManager().beginTransaction().replace(com.donglv.watch.R.id.main, fragmentProductInfo).addToBackStack(null).commit();
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
}
