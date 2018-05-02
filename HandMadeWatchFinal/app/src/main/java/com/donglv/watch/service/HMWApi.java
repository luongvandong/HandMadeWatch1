package com.donglv.watch.service;


/**
 * Created by Administrator on 24/11/2015.
 */
public class HMWApi extends BaseApi {
    private HMWService hmwService;

    public HMWService service() {
        if (hmwService == null) {
            hmwService = restAdapter.create(HMWService.class);
        }
        return hmwService;
    }

}
