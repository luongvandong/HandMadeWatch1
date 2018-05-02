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
import android.widget.TextView;

/**
 * Created by vcom on 11/11/2016.
 */
public class FragmentChooseLogin extends Fragment implements View.OnClickListener {
    private TextView btnLogIn;
    private TextView btnSignUp;
    private ProgressDialog progressDialog;
    private Context context;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(com.donglv.watch.R.layout.fragment_choose_login, container, false);
        context = rootView.getContext();
        Log.e("fragment cho", "ok");
        init(rootView);
        return rootView;
    }

    private void init(View rootView) {

        btnSignUp = (TextView) rootView.findViewById(com.donglv.watch.R.id.btnSignUp);
        btnSignUp.setOnClickListener(this);
        btnLogIn = (TextView) rootView.findViewById(com.donglv.watch.R.id.btnLogIn);
        btnLogIn.setOnClickListener(this);
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
        if(progressDialog != null) {
            progressDialog.dismiss();
        }

    }
}
