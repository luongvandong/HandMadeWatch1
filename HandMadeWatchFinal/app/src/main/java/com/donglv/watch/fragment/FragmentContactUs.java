package com.donglv.watch.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nam.customlibrary.Libs_System;
import com.squareup.picasso.Picasso;

import com.donglv.watch.ActivityHome;
import com.donglv.watch.R;
import com.donglv.watch.common.HMW_Constant;
import com.donglv.watch.entity.SimpleClass;
import com.donglv.watch.entity.User;
import com.donglv.watch.service.HMWApi;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Kai on 1/19/2017.
 */

public class FragmentContactUs extends Fragment implements View.OnClickListener {


    private EditText edtEmail;
    private EditText edtMessage;
    private Button btnSave;
    private Context context;
    private ProgressDialog progressDialog;
    private String userName;
    private ImageView imgAvatar;
    private TextView tvName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_contact_us, container, false);
        context = rootView.getContext();
        init(rootView);
        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();
        TextView txtTitle =(TextView) ActivityHome.toolbar.findViewById(R.id.txtToolbarTitle);
        txtTitle.setText(getText(R.string.contact_us));
    }
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btnSaveContact:
                loginAction();
                break;
        }
    }

    public void sendContact(String name, String email, String message)
    {
        showProgressDialog(getContext(),"loading...");
        HMWApi api = new HMWApi();
        api.service().sendContact(name, email, message, new Callback<SimpleClass>() {
            @Override
            public void success(SimpleClass simpleClass, Response response) {
                dismissProgressDialog();
               if (simpleClass.getCode().equals("200"))
               {
                   showAlert(getActivity().getString(R.string.success));
                   edtEmail.setText("");
                   edtMessage.setText("");
               }else
               {
                   showAlert(getActivity().getString(R.string.alert_message_fail));
               }
            }

            @Override
            public void failure(RetrofitError error) {
                dismissProgressDialog();

            }
        });
    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void showAlert(String message) {
        AlertDialog dialog = new AlertDialog.Builder(getActivity()).setMessage(message).setPositiveButton(R.string.btn_ok,
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).create();

        dialog.show();
    }

    public void init(View view)
    {
        imgAvatar =(ImageView) view.findViewById(R.id.imgAvatar);
        edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        edtMessage = (EditText) view.findViewById(R.id.edtMessage);
        btnSave = (Button) view.findViewById(R.id.btnSaveContact);
        tvName =(TextView) view.findViewById(R.id.tvName);
        btnSave.setOnClickListener(this);
        getUserInfo();
    }

    public void loginAction() {
        String email = edtEmail.getText().toString().trim();
        String message = edtMessage.getText().toString().trim();
        if (null == email || email.isEmpty()) {
            Toast.makeText(getActivity(), getActivity().getResources().getText(R.string.empty_email), Toast.LENGTH_SHORT).show();
        } else if (null == message || message.isEmpty()) {
            Toast.makeText(getActivity(), getActivity().getResources().getText(R.string.empty_message), Toast.LENGTH_SHORT).show();
        } else if(!isEmailValid(email))
        {
            Toast.makeText(getActivity(), getActivity().getResources().getText(R.string.type_email_invalid), Toast.LENGTH_SHORT).show();
        } else
        {
            sendContact(userName, email, message);
        }

    }

    private void getUserInfo() {
        showProgressDialog(context, "loading");
        HMWApi api = new HMWApi();
        api.service().getUserInfo("Bearer " + Libs_System.getStringData(context, HMW_Constant.TOKEN), "application/json", new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                dismissProgressDialog();
                Picasso.with(context).load(HMW_Constant.HOST + "/lbmedia/" + user.getAvatar_id()).into(imgAvatar);
                tvName.setText(user.getName());
                userName =user.getName();
            }

            @Override
            public void failure(RetrofitError error) {
                dismissProgressDialog();
                if(error.getResponse()!=null&&error.getResponse().getStatus()==401){
                    Libs_System.showToast(context,context.getString(com.donglv.watch.R.string.unauthorized));
                    Libs_System.insertStringData(getContext(), HMW_Constant.TOKEN, "");
                    ((Activity) getContext()).finish();
                    getContext().startActivity(((Activity) getContext()).getIntent());
                }
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
        Libs_System.insertIntData(getContext(),HMW_Constant.NUMBER_NOTIFICATION,1);
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
