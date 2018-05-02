package com.donglv.watch.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donglv.watch.R;
import com.donglv.watch.entity.MyCart;

import java.util.ArrayList;

/**
 * Created by vcom on 16/11/2016.
 */
public class MyShoppingCartAdapter extends BaseAdapter {
    private ArrayList<MyCart> datas = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;

    public MyShoppingCartAdapter(Context context, ArrayList<MyCart> objects) {
        this.context = context;
        this.datas = objects;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_my_shopping_cart, parent, false);
            holder = new ViewHolder();
            holder.imgAvatar = (ImageView) convertView.findViewById(R.id.imgAvatar);
            holder.tvTitle = (TextView) convertView.findViewById(com.donglv.watch.R.id.tvTitle);
            holder.tvDes = (TextView) convertView.findViewById(com.donglv.watch.R.id.tvDes);
            holder.tvAdd = (TextView) convertView.findViewById(com.donglv.watch.R.id.tvAdd);
            holder.tvInformation = (TextView) convertView.findViewById(com.donglv.watch.R.id.tvInformation);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final MyCart data = datas.get(position);
        Glide.with(context).load(data.getImage()).into(holder.imgAvatar);
        holder.tvTitle.setText(data.getTitle());

        holder.tvDes.setText(data.getDes());
        holder.tvAdd.setText(data.getAdd());
        holder.tvInformation.setText(data.getInfo());

        /*LayoutInflater inflater;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(com.msab.handmadewatch.R.layout.item_my_shopping_cart, parent, false);
        ImageView imgAvatar = (ImageView)convertView.findViewById( com.msab.handmadewatch.R.id.imgAvatar );
        TextView tvTitle = (TextView)convertView.findViewById( com.msab.handmadewatch.R.id.tvTitle );
        TextView tvDes = (TextView)convertView.findViewById( com.msab.handmadewatch.R.id.tvDes );
        TextView tvAdd = (TextView)convertView.findViewById( com.msab.handmadewatch.R.id.tvAdd );
        TextView tvInformation = (TextView)convertView.findViewById( com.msab.handmadewatch.R.id.tvInformation );
        final MyCart data = datas.get(position);
        Glide.with(context).load(data.getImage()).into(imgAvatar);
        tvTitle.setText(data.getTitle());
        tvDes.setText(data.getDes());
        tvAdd.setText(data.getAdd());
        tvInformation.setText(data.getInfo());*/
        return convertView;
    }

    public String getProductInfo(String productId) {
        final String[] curency = {""};

        return curency[0];
    }

    class ViewHolder {
        ImageView imgAvatar;
        TextView tvTitle;
        TextView tvDes;
        TextView tvAdd;
        TextView tvInformation;
    }


}
