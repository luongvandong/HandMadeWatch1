package com.donglv.watch.fragment;


import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.donglv.watch.ActivityHome;
import com.donglv.watch.MainActivity;

import com.donglv.watch.R;

/**
 * Created by Kai on 2/3/2017.
 */

public class FragmentContactInfo extends Fragment implements View.OnClickListener {

    private TextView tvPhone;

    private TextView tvPhone1;

    private TextView tvPhone2;

    private TextView tvEmail;

    private ImageView imgFace;

    private ImageView imgIns;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(com.donglv.watch.R.layout.fragment_contact_info, container, false);
        init(rootView);
        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();
        TextView txtTitle =(TextView) ActivityHome.toolbar.findViewById(R.id.txtToolbarTitle);
        txtTitle.setText(getText(R.string.contact_info));
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case com.donglv.watch.R.id.tvPhoneNumber:
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + getResources().getString(com.donglv.watch.R.string.number_phone3)));
                    if (ActivityCompat.checkSelfPermission(getActivity(),
                            Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    startActivity(callIntent);
                } catch (Error error) {
                    Log.d("ee", error.toString());
                }
                break;
            case com.donglv.watch.R.id.tvEmailContact:
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + getResources().getString(com.donglv.watch.R.string.email_contact)));
                startActivity(emailIntent);
                break;
            case com.donglv.watch.R.id.iconFaceBook:
                try {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(com.donglv.watch.R.string.link_face)));
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case com.donglv.watch.R.id.iconIns:
                try {
                    Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(getResources().getString(com.donglv.watch.R.string.link_instagram)));
                    startActivity(myIntent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case com.donglv.watch.R.id.tvPhoneNumber1:
                try {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + getResources().getString(com.donglv.watch.R.string.number_phone1)));
                    startActivity(callIntent);
                } catch (Error error) {
                    Log.d("ee", error.toString());
                }
                break;
//            case com.msab.handmadewatch.R.id.tvPhoneNumber2:
//                try {
//                    Intent callIntent = new Intent(Intent.ACTION_CALL);
//                    callIntent.setData(Uri.parse("tel:" + getResources().getString(com.msab.handmadewatch.R.string.number_phone2)));
//                    startActivity(callIntent);
//                } catch (Error error) {
//                    Log.d("ee", error.toString());
//                }
//                break;
        }
    }

    public void init(View view) {
        tvEmail = (TextView) view.findViewById(com.donglv.watch.R.id.tvEmailContact);
        tvEmail.setOnClickListener(this);
        tvPhone = (TextView) view.findViewById(com.donglv.watch.R.id.tvPhoneNumber);
        tvPhone.setOnClickListener(this);
        tvPhone1 = (TextView) view.findViewById(com.donglv.watch.R.id.tvPhoneNumber1);
        tvPhone1.setOnClickListener(this);
//        tvPhone2 = (TextView) view.findViewById(com.msab.handmadewatch.R.id.tvPhoneNumber2);
//        tvPhone2.setOnClickListener(this);
        imgFace = (ImageView) view.findViewById(com.donglv.watch.R.id.iconFaceBook);
        imgFace.setOnClickListener(this);
        imgIns = (ImageView) view.findViewById(com.donglv.watch.R.id.iconIns);
        imgIns.setOnClickListener(this);

    }


}
