package com.donglv.watch.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.donglv.watch.ActivityHome;
import com.donglv.watch.R;
import com.donglv.watch.common.HMW_Constant;
import com.donglv.watch.common.HMW_System;
import com.donglv.watch.entity.SimpleClass;
import com.donglv.watch.entity.User;
import com.donglv.watch.service.HMWApi;
import com.nam.customlibrary.Libs_System;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Kai on 2/2/2017.
 */

public class FragmentSetting extends Fragment {

    private Button btnSave;

    private Switch mySwitch;

    private Context context;

    private TextView tvSettingControl;

    private SharedPreferences sharedpreferences;

    private Boolean turnOnOff;

    private int notificationEnable;

    private String userId;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_setting, container, false);
        getUserInfo();
        context = rootView.getContext();
        init(rootView);

        mySwitch.setChecked(HMW_System.getBooleanData(getContext(), HMW_Constant.NOTIFICATION_ON_FLAG));
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    notificationEnable = 0;
                } else {
                    notificationEnable = 1;
                }
                Libs_System.insertBooleanData(getContext(),HMW_Constant.NOTIFICATION_ON_FLAG,isChecked);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setNotification(userId, notificationEnable);
            }
        });
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView txtTitle =(TextView) ActivityHome.toolbar.findViewById(R.id.txtToolbarTitle);
        txtTitle.setText(getText(R.string.setting));
    }

    private void getUserInfo() {
        HMWApi api = new HMWApi();
        api.service().getUserInfo("Bearer " + Libs_System.getStringData(getActivity(), HMW_Constant.TOKEN), "application/json", new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                userId = user.getId();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }


    public void setNotification(String userId, final int notificationEnable) {
        HMWApi api = new HMWApi();
        api.service().setNotification("Bearer " + Libs_System.getStringData(context, HMW_Constant.TOKEN), "application/json", userId, notificationEnable, new Callback<SimpleClass>() {
            @Override
            public void success(SimpleClass simpleClass, Response response) {
                if (simpleClass.getCode().equals("200")) {
                    switch (notificationEnable) {
                        case 0:
                            Toast.makeText(context, getResources().getString(R.string.turn_on_toast_noti), Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            Toast.makeText(context, getResources().getString(R.string.turn_off_toast_noti), Toast.LENGTH_SHORT).show();
                            break;
                    }

                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }


    public void init(View view) {
        btnSave = (Button) view.findViewById(R.id.btnSaveSetting);
        mySwitch = (Switch) view.findViewById(R.id.mySwitch);
    }
}
