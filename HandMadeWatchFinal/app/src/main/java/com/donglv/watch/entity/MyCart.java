package com.donglv.watch.entity;

/**
 * Created by vcom on 16/11/2016.
 */
public class MyCart {
    private String image;
    private String title;
    private String des;
    private String add;
    private String info;
    private String product_id;

    public MyCart(String image, String title, String des, String add, String info, String product_id) {
        this.image = image;
        this.title = title;
        this.des = des;
        this.add = add;
        this.info = info;
        this.product_id = product_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getAdd() {
        return add;
    }

    public void setAdd(String add) {
        this.add = add;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }
}
