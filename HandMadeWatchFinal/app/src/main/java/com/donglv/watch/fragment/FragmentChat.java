package com.donglv.watch.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.donglv.watch.MainActivity;
import com.donglv.watch.R;
import com.donglv.watch.adapter.ChatAdapter;
import com.donglv.watch.common.HMW_Constant;
import com.donglv.watch.entity.ChatItem;
import com.donglv.watch.service.HMWApi;
import com.nam.customlibrary.Libs_System;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by vcom on 09/12/2016.
 */
public class FragmentChat  extends Fragment implements View.OnClickListener{
    private ProgressDialog progressDialog;
    private Context context;
    private LinearLayout btnSend;
    private EditText edtContent;
    private ChatAdapter chatAdapter;
    private ListView listChat;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE|WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        View rootView = inflater.inflate(R.layout.fragment_chat, container, false);
    context = rootView.getContext();
    init(rootView);
    getMessage();
    return rootView;
}

    private void init(View rootView) {
        btnSend = (LinearLayout) rootView.findViewById( R.id.btnSend );
        btnSend.setOnClickListener(this);
        edtContent = (EditText)rootView.findViewById( R.id.edtContent );
        listChat = (ListView) rootView.findViewById(R.id.listChat);
        listChat.setDivider(null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                if(checkLogin(context)){
                    showDialog(context);
                    return;
                }
                if(edtContent.getText().toString().equals("")){
                    Libs_System.showToast(context, "nhập comment");
                    return;
                }
                Libs_System.hideKeyboard(getActivity());
                edtContent.setText("");

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

    private boolean checkLogin(final Context context) {
        if (Libs_System.getStringData(context, HMW_Constant.TOKEN).equals("")) {
            return true;
        }
        return false;
    }

    private void showDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Bạn cần phải đăng nhâp");
        builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(context, MainActivity.class));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void getMessage(){
        HMWApi api = new HMWApi();
        api.service().getAllMess("Bearer " + Libs_System.getStringData(context, HMW_Constant.TOKEN), "application/json","f6c7292ae91e457da9354812a2c97968", new Callback<ChatItem>() {
            @Override
            public void success(ChatItem chatItem, Response response) {
                chatAdapter = new ChatAdapter(context, chatItem.getData());
                listChat.setAdapter(chatAdapter);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("get All mess", error.getMessage());
                if(error.getResponse()!=null&&error.getResponse().getStatus()==401){
                    Libs_System.showToast(context,context.getString(R.string.unauthorized));
                    Libs_System.insertStringData(getContext(), HMW_Constant.TOKEN, "");
                    ((Activity) getContext()).finish();
                    getContext().startActivity(((Activity) getContext()).getIntent());
                }
            }
        });
    }
}
