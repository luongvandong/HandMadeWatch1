package com.donglv.watch.service;
import com.donglv.watch.common.HMW_Constant;
import retrofit.RestAdapter;

/**
 * Created by Administrator on 28/07/2015.
 */
public class BaseApi {
    public RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint(HMW_Constant.HOST)
            .build();
}
