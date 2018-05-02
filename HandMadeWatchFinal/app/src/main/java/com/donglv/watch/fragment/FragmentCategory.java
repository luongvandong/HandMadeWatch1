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
import android.widget.GridView;
import android.widget.TextView;

import com.donglv.watch.ActivityHome;
import com.donglv.watch.R;
import com.donglv.watch.adapter.CategoryAdapter;
import com.donglv.watch.entity.Category;
import com.donglv.watch.service.HMWApi;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by vcom on 15/11/2016.
 */
public class FragmentCategory extends Fragment implements View.OnClickListener{
    private ProgressDialog progressDialog;
    private Context context;
    private GridView listCategory;
    private CategoryAdapter categoryAdapter;
    private ArrayList<Category.Data> datas = new ArrayList<>();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_category, container, false);
        ActivityHome.toolbar.setNavigationIcon(R.drawable.ic_list);
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
        getAllCategory();
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView txtTitle =(TextView) ActivityHome.toolbar.findViewById(R.id.txtToolbarTitle);
        txtTitle.setText(getText(R.string.browse_category));
    }

    private void init(View rootView) {
        listCategory = (GridView) rootView.findViewById(R.id.listCategory);
        listCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putString("category_id", datas.get(position).getId()+"");
                FragmentCategoryInfo fragmentProductInfo  = new FragmentCategoryInfo();
                fragmentProductInfo.setArguments(bundle);
                getFragmentManager().beginTransaction().replace(R.id.main, fragmentProductInfo).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogIn:
                getFragmentManager().beginTransaction().replace(R.id.main, new FragmentLogIn()).addToBackStack(null).commit();
                break;
            case R.id.btnSignUp:
                getFragmentManager().beginTransaction().replace(R.id.main, new FragmentRegister()).addToBackStack(null).commit();
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
        if(progressDialog != null) {
            progressDialog.dismiss();
        }

    }

    private void getAllCategory(){
        HMWApi api = new HMWApi();
        api.service().getAllCategory(new Callback<Category>() {
            @Override
            public void success(Category category, Response response) {
                categoryAdapter = new CategoryAdapter(context, category.getData());
                listCategory.setAdapter(categoryAdapter);
                datas = category.getData();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("category", error.getMessage()+"");
            }
        });
    }

}
