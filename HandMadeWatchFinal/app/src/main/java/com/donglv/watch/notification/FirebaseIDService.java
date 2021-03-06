package com.donglv.watch.notification;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import com.donglv.watch.common.HMW_Constant;
import com.donglv.watch.entity.SimpleClass;
import com.donglv.watch.service.HMWApi;
import com.nam.customlibrary.Libs_System;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Dr.Cuong on 1/6/2017.
 */

public class FirebaseIDService extends FirebaseInstanceIdService {
    private static final String TAG = "FirebaseIDService";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        if(!Libs_System.getStringData(getBaseContext(), HMW_Constant.TOKEN).isEmpty()) {
            sendRegistrationToServer(refreshedToken);
        }
    }

    /**
     * Persist token to third-party servers.
     *
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        HMWApi api = new HMWApi();
        api.service().sendFCMToken("Bearer " + Libs_System.getStringData(getBaseContext(),
                HMW_Constant.TOKEN), "application/json", token,
                "android", new Callback<SimpleClass>() {
            @Override
            public void success(SimpleClass simpleClass, Response response) {
                Log.e("send FCM token done",simpleClass.getDescription());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("send FCM token error",error.toString());
            }
        });
    }
}