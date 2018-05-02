package com.donglv.watch.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.donglv.watch.R;
import com.nam.customlibrary.Libs_System;

/**
 * Created by Duc_Anh on 5/23/2016.
 */
public class FragmentFogotPassword extends Fragment implements View.OnClickListener {
    private Context context;
    private ImageView imgBack;
    private EditText edtEmail;
    private TextView btnChangePass;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_forgot_password, container, false);
        init(rootView);
        context = rootView.getContext();
        init(rootView);
        return rootView;
    }

    private void init(View rootView) {
        edtEmail = (EditText) rootView.findViewById(R.id.edtEmail);
//        imgBack = (ImageView) rootView.findViewById(R.id.imgBack);
//        imgBack.setOnClickListener(this);
        btnChangePass = (TextView) rootView.findViewById(R.id.btnChangePass);
        btnChangePass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
//            case R.id.imgBack:
//                getFragmentManager().popBackStack();
//                Log.e("click", "ok");
//                break;
            case R.id.btnChangePass:
//                Libs_System.hideKeyboard(getActivity());
//                if(edtEmail.getText().toString().trim().length()==0){
//                   Libs_System.showToast(context, getString(R.string.please_input_data_vi));
//                }else if(!Bwhere_System.isEmailValid(edtEmail.getText().toString().trim())) {
//                    Libs_System.showToast(context, getString(R.string.input_correctly_form_email));
//                }else {
//                    showProgressDialog(context, getString(R.string.loading));
//                    BWhereApi api = new BWhereApi();
//                    api.service().forgetPassword(edtEmail.getText().toString().trim(), new Callback<ForgetPasswordRespond>() {
//                        @Override
//                        public void success(ForgetPasswordRespond forgetPasswordRespond, Response response) {
//                            dismissProgressDialog();
//                            if(forgetPasswordRespond.getCode().equals("200")){
//                                Libs_System.showToast(context, context.getString(R.string.password_have_been_reset));
//                                getFragmentManager().beginTransaction().add(R.id.main, new FragmentLogIn()).commit();
//                            }else if(forgetPasswordRespond.getCode().equals("401")){
//                                Libs_System.showToast(context, forgetPasswordRespond.getMessage());
//                            }
//                        }
//
//                        @Override
//                        public void failure(RetrofitError error) {
//                            dismissProgressDialog();
//                            Libs_System.showToast(context, getString(R.string.request_server_fail));
//                        }
//                    });
//
//                }
                break;

        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Libs_System.hideKeyboard(getActivity());
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
