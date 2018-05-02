package com.donglv.watch;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.donglv.watch.common.HMW_Constant;
import com.donglv.watch.common.RoundedImageView;
import com.donglv.watch.entity.SimpleClass;
import com.donglv.watch.entity.User;
import com.donglv.watch.fragment.FragmentCategory;
import com.donglv.watch.fragment.FragmentContactInfo;
import com.donglv.watch.fragment.FragmentContactUs;
import com.donglv.watch.fragment.FragmentFavorite;
import com.donglv.watch.fragment.FragmentHome;
import com.donglv.watch.fragment.FragmentMyShoppingCart;
import com.donglv.watch.fragment.FragmentProductOnSale;
import com.donglv.watch.fragment.FragmentSetting;
import com.donglv.watch.fragment.FragmentUserProfile;
import com.donglv.watch.fragment.FragmentUserProfile.UpdateImageAvatar;
import com.donglv.watch.service.HMWApi;
import com.nam.customlibrary.Libs_System;
import com.readystatesoftware.viewbadger.BadgeView;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;

import java.io.IOError;
import java.io.IOException;

import io.fabric.sdk.android.Fabric;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ActivityHome extends AppCompatActivity implements View.OnClickListener, UpdateImageAvatar {
    private ImageView imgChat;
    private RoundedImageView imgAvatar;
    private TextView tvName;
    private TextView tvSince;
    private TextView tvHome;
    private TextView tvUserProfile;
    private TextView tvMyShoppingCart;
    private TextView tvMyFavorites;
    private TextView tvBrowseCategory;
    private TextView tvProductOnSale;
    private TextView tvSetting;
    private TextView tvHelp;
    private TextView tvContactUs;
    private TextView tvSignOut;
    private TextView tvContactInfo;
    public static Drawer drawer;
    public static Toolbar toolbar;

    private ImageView iconHome;
    private ImageView icProfile;
    private ImageView iconMyCart;
    private ImageView iconCategory;
    private ImageView iconFavorites;
    private ImageView iconProductSale;
    private ImageView iconSetting;
    private ImageView iconContact;
    private ImageView iconSignOut;
    private ImageView iconContactInfo;


    private LinearLayout layoutUserLogin;
    private LinearLayout layoutUserNotLogin;
    private TextView btnToLogin;
    private LinearLayout layoutHome;
    private LinearLayout layoutUserProfile;
    private LinearLayout layoutMyShoppingCart;
    private LinearLayout layoutBrowseCategory;
    private LinearLayout layoutMyFavorites;
    private LinearLayout layoutProductOnSale;
    private LinearLayout layoutSetting;
    private LinearLayout layoutHelp;
    private LinearLayout layoutContactUs;
    private LinearLayout layoutSignOut;
    private LinearLayout slideMenu;
    private LinearLayout layoutContactInfo;
    private BadgeView badge;

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    private String token;
    private ProgressDialog progcDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_home);

//        VersionChecker versionChecker = new VersionChecker();
//        versionChecker.execute();

        token = Libs_System.getStringData(ActivityHome.this, HMW_Constant.TOKEN);
        toolbar = setUpToolbar();
        getSupportFragmentManager().beginTransaction().replace(R.id.main, new FragmentHome()).commit();
        init();

        if (!Libs_System.getStringData(ActivityHome.this, HMW_Constant.TOKEN).equals("")) {
            layoutUserLogin.setVisibility(View.VISIBLE);
            layoutUserNotLogin.setVisibility(View.GONE);
            layoutUserProfile.setVisibility(View.VISIBLE);
            layoutMyShoppingCart.setVisibility(View.VISIBLE);
            layoutBrowseCategory.setVisibility(View.VISIBLE);
            layoutMyFavorites.setVisibility(View.VISIBLE);
            layoutProductOnSale.setVisibility(View.VISIBLE);
            layoutContactUs.setVisibility(View.VISIBLE);
            layoutSignOut.setVisibility(View.VISIBLE);
            layoutContactInfo.setVisibility(View.VISIBLE);
            getUserInfo();
        }

        token = FirebaseInstanceId.getInstance().getToken();
        if (Libs_System.getStringData(ActivityHome.this, HMW_Constant.IS_FRIST_INSTALL).equals("")) {
            HMWApi api = new HMWApi();
            api.service().sendTokenFristInstall(token, "android", new Callback<SimpleClass>() {
                @Override
                public void success(SimpleClass simpleClass, Response response) {
                    Libs_System.insertStringData(ActivityHome.this, HMW_Constant.IS_FRIST_INSTALL, "installed");
                }

                @Override
                public void failure(RetrofitError error) {
                }
            });
        }

        if (token != null && !token.isEmpty() && !Libs_System.getStringData(ActivityHome.this, HMW_Constant.TOKEN).isEmpty()) {
            Log.e("firebase Token", token);
            Log.e("normal token", Libs_System.getStringData(ActivityHome.this, HMW_Constant.TOKEN));
            HMWApi api = new HMWApi();
            api.service().sendFCMToken("Bearer " + Libs_System.getStringData(ActivityHome.this, HMW_Constant.TOKEN), "application/json", token, "android", new Callback<SimpleClass>() {
                @Override
                public void success(SimpleClass simpleClass, Response response) {
                    Log.e("send FCM token done", simpleClass.getDescription());
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e("send FCM token error", error.toString());
                }
            });
        }
    }


    //setup toolbar
    private Toolbar setUpToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationIcon(R.drawable.ic_list);
        return toolbar;
    }

    // init main
    private void init() {
        imgChat = (ImageView) findViewById(R.id.imgChat);
        imgChat.setOnClickListener(this);
        setupDrawerLayout();
        initSlideMenuItem();
        if (Libs_System.getIntData(getApplicationContext(), HMW_Constant.NUMBER_NOTIFICATION) != 0) {
            View target = findViewById(R.id.tvBadge);
            badge = new BadgeView(this, target);
            badge.setText("" + Libs_System.getIntData(getApplicationContext(), HMW_Constant.NUMBER_NOTIFICATION));
            badge.setTextSize(10);
            badge.show();
        }
    }

    //init slide menu item
    private void initSlideMenuItem() {
        slideMenu = (LinearLayout) drawer.getDrawerLayout().findViewById(R.id.slideMenu);
        slideMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        layoutUserLogin = (LinearLayout) drawer.getDrawerLayout().findViewById(R.id.layoutUserLogin);
        layoutUserLogin.setOnClickListener(this);
        layoutUserNotLogin = (LinearLayout) drawer.getDrawerLayout().findViewById(R.id.layoutUserNotLogin);
        layoutUserNotLogin.setOnClickListener(this);
        btnToLogin = (TextView) drawer.getDrawerLayout().findViewById(R.id.btnToLogin);
        btnToLogin.setOnClickListener(this);
        layoutHome = (LinearLayout) drawer.getDrawerLayout().findViewById(R.id.layoutHome);
        layoutHome.setOnClickListener(this);
        layoutUserProfile = (LinearLayout) drawer.getDrawerLayout().findViewById(R.id.layoutUserProfile);
        layoutUserProfile.setOnClickListener(this);
        layoutMyShoppingCart = (LinearLayout) drawer.getDrawerLayout().findViewById(R.id.layoutMyShoppingCart);
        layoutMyShoppingCart.setOnClickListener(this);
        layoutBrowseCategory = (LinearLayout) drawer.getDrawerLayout().findViewById(R.id.layoutBrowseCategory);
        layoutBrowseCategory.setOnClickListener(this);
        layoutMyFavorites = (LinearLayout) drawer.getDrawerLayout().findViewById(R.id.layoutMyFavorites);
        layoutMyFavorites.setOnClickListener(this);
        layoutProductOnSale = (LinearLayout) drawer.getDrawerLayout().findViewById(R.id.layoutProductOnSale);
        layoutProductOnSale.setOnClickListener(this);
        layoutSetting = (LinearLayout) drawer.getDrawerLayout().findViewById(R.id.layoutSetting);
        layoutSetting.setOnClickListener(this);
        layoutContactUs = (LinearLayout) drawer.getDrawerLayout().findViewById(R.id.layoutContactUs);
        layoutContactUs.setOnClickListener(this);
        layoutSignOut = (LinearLayout) drawer.getDrawerLayout().findViewById(R.id.layoutSignOut);
        layoutSignOut.setOnClickListener(this);
        layoutContactInfo = (LinearLayout) drawer.getDrawerLayout().findViewById(R.id.layoutContactInfo);
        layoutContactInfo.setOnClickListener(this);

        imgAvatar = (RoundedImageView) drawer.getDrawerLayout().findViewById(R.id.imgAvatar);
        tvName = (TextView) drawer.getDrawerLayout().findViewById(R.id.tvName);
        tvSince = (TextView) drawer.getDrawerLayout().findViewById(R.id.tvSince);
        tvHome = (TextView) drawer.getDrawerLayout().findViewById(R.id.tvHome);
        tvUserProfile = (TextView) drawer.getDrawerLayout().findViewById(R.id.tvUserProfile);
        tvMyShoppingCart = (TextView) drawer.getDrawerLayout().findViewById(R.id.tvMyShoppingCart);
        tvMyFavorites = (TextView) drawer.getDrawerLayout().findViewById(R.id.tvMyFavorites);
        tvBrowseCategory = (TextView) drawer.getDrawerLayout().findViewById(R.id.tvBrowseCategory);
        tvProductOnSale = (TextView) drawer.getDrawerLayout().findViewById(R.id.tvProductOnSale);
        tvSetting = (TextView) drawer.getDrawerLayout().findViewById(R.id.tvSetting);

        tvContactUs = (TextView) drawer.getDrawerLayout().findViewById(R.id.tvContactUs);
        tvSignOut = (TextView) drawer.getDrawerLayout().findViewById(R.id.tvSignOut);
        tvContactInfo = (TextView) drawer.getDrawerLayout().findViewById(R.id.tvContactInfo);

//        imgAvatar.setOnClickListener(this);
//        tvName.setOnClickListener(this);
//        tvSince.setOnClickListener(this);
//        tvHome.setOnClickListener(this);
//        tvUserProfile.setOnClickListener(this);
//        tvMyShoppingCart.setOnClickListener(this);
//        tvMyFavorites.setOnClickListener(this);
//        tvBrowseCategory.setOnClickListener(this);
//        tvProductOnSale.setOnClickListener(this);
//        tvSetting.setOnClickListener(this);
//        tvHelp.setOnClickListener(this);
//        tvContactUs.setOnClickListener(this);
//        tvSignOut.setOnClickListener(this);


        iconHome = (ImageView) drawer.getDrawerLayout().findViewById(R.id.iconHome);
        icProfile = (ImageView) drawer.getDrawerLayout().findViewById(R.id.icProfile);
        iconMyCart = (ImageView) drawer.getDrawerLayout().findViewById(R.id.iconMyCart);
        iconCategory = (ImageView) drawer.getDrawerLayout().findViewById(R.id.iconCategory);
        iconFavorites = (ImageView) drawer.getDrawerLayout().findViewById(R.id.iconFavorites);
        iconProductSale = (ImageView) drawer.getDrawerLayout().findViewById(R.id.iconProductSale);
        iconSetting = (ImageView) drawer.getDrawerLayout().findViewById(R.id.iconSetting);
        iconContact = (ImageView) drawer.getDrawerLayout().findViewById(R.id.iconContact);
        iconSignOut = (ImageView) drawer.getDrawerLayout().findViewById(R.id.iconSignOut);
        iconContactInfo = (ImageView) drawer.getDrawerLayout().findViewById(R.id.iconContactInfo);
    }

    //setup drawer layout
    private void setupDrawerLayout() {
        LayoutInflater inflater = getLayoutInflater();
        drawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withDisplayBelowStatusBar(false)
                .withCustomView(inflater.inflate(R.layout.slide_menu, null))
                .withTranslucentStatusBarProgrammatically(true)
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        Libs_System.hideKeyboard(ActivityHome.this);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        Libs_System.hideKeyboard(ActivityHome.this);
                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {

                    }
                })
                .build();
        drawer.getActionBarDrawerToggle().setDrawerIndicatorEnabled(false);
    }

    public void disableNavigatorDrawer() {
        drawer.getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void onClick(View v) {
        Log.e("id", v.getId() + "");
//        Intent intent = new Intent(ActivityHome.this, UserActivity.class);
//        Bundle bundle = new Bundle();

        switch (v.getId()) {
            case R.id.btnToLogin:
                drawer.closeDrawer();
                Libs_System.insertStringData(ActivityHome.this, HMW_Constant.TOKEN, "");
                Intent intent1 = new Intent(this, MainActivity.class);
                intent1.putExtra("sign out", "ok");
                startActivity(intent1);
                finish();
                break;
            case R.id.imgChat:
                if (checkLogin(ActivityHome.this)) {
                    showDialog(ActivityHome.this);
                    return;
                }
                if (badge != null) {
                    badge.hide();
                }
                Libs_System.insertIntData(ActivityHome.this, HMW_Constant.NUMBER_NOTIFICATION, 0);
                startActivity(new Intent(this, TestActivity.class));
                break;

            case R.id.layoutHome:
                setupMenu();
                toolbar.setNavigationIcon(R.drawable.ic_list);
                tvHome.setTextColor(getResources().getColor(R.color.black));
                iconHome.setImageResource(R.drawable.home_active);
                getSupportFragmentManager().beginTransaction().replace(R.id.main, new FragmentHome()).commit();
                drawer.closeDrawer();
                break;
            case R.id.layoutUserProfile:
                if (checkLogin(ActivityHome.this)) {
                    showDialog(ActivityHome.this);
                    return;
                }
                toolbar.setNavigationIcon(R.drawable.ic_list);
                setupMenu();
                tvUserProfile.setTextColor(getResources().getColor(R.color.black));
                icProfile.setImageResource(R.drawable.profile_active);
                getSupportFragmentManager().beginTransaction().replace(R.id.main, new FragmentUserProfile()).commit();
                drawer.closeDrawer();
                break;
            case R.id.layoutMyShoppingCart:
                if (checkLogin(ActivityHome.this)) {
                    showDialog(ActivityHome.this);
                    return;
                }
                setupMenu();
                toolbar.setNavigationIcon(R.drawable.ic_list);
                tvMyShoppingCart.setTextColor(getResources().getColor(R.color.black));
                iconMyCart.setImageResource(R.drawable.cart_active);
                getSupportFragmentManager().beginTransaction().replace(R.id.main, new FragmentMyShoppingCart()).commit();
                drawer.closeDrawer();
                break;
            case R.id.layoutMyFavorites:
                if (checkLogin(ActivityHome.this)) {
                    showDialog(ActivityHome.this);
                    return;
                }
                toolbar.setNavigationIcon(R.drawable.ic_list);
                setupMenu();
                tvMyFavorites.setTextColor(getResources().getColor(R.color.black));
                iconFavorites.setImageResource(R.drawable.favorites_active);
                getSupportFragmentManager().beginTransaction().replace(R.id.main, new FragmentFavorite()).addToBackStack(null).commit();
                drawer.closeDrawer();
                break;
            case R.id.layoutBrowseCategory:
                toolbar.setNavigationIcon(R.drawable.ic_list);
                setupMenu();
                tvBrowseCategory.setTextColor(getResources().getColor(R.color.black));
                iconCategory.setImageResource(R.drawable.category_active);
                getSupportFragmentManager().beginTransaction().replace(R.id.main, new FragmentCategory()).commit();
                drawer.closeDrawer();
                break;
            case R.id.layoutProductOnSale:
                toolbar.setNavigationIcon(R.drawable.ic_list);
                setupMenu();
                tvProductOnSale.setTextColor(getResources().getColor(R.color.black));
                iconProductSale.setImageResource(R.drawable.sale_active);
                getSupportFragmentManager().beginTransaction().replace(R.id.main, new FragmentProductOnSale()).commit();
                drawer.closeDrawer();
                break;
            case R.id.layoutSetting:
                toolbar.setNavigationIcon(R.drawable.ic_list);
                setupMenu();
                tvSetting.setTextColor(getResources().getColor(R.color.black));
                iconSetting.setImageResource(R.drawable.setting_active);
                getSupportFragmentManager().beginTransaction().replace(R.id.main, new FragmentSetting()).commit();
                drawer.closeDrawer();
                break;
            case R.id.layoutContactInfo:
                toolbar.setNavigationIcon(R.drawable.ic_list);
                setupMenu();
                tvContactInfo.setTextColor(getResources().getColor(R.color.black));
                iconContactInfo.setImageResource(R.drawable.contact_info_active);
                getSupportFragmentManager().beginTransaction().replace(R.id.main, new FragmentContactInfo()).commit();
                drawer.closeDrawer();
                break;
            case R.id.layoutContactUs:
                if (checkLogin(ActivityHome.this)) {
                    showDialog(ActivityHome.this);
                    return;
                }
                toolbar.setNavigationIcon(R.drawable.ic_list);
                setupMenu();
                tvContactUs.setTextColor(getResources().getColor(R.color.black));
                iconContact.setImageResource(R.drawable.contact_us_active);
                drawer.closeDrawer();
                getSupportFragmentManager().beginTransaction().replace(R.id.main, new FragmentContactUs()).commit();
                break;
            case R.id.layoutSignOut:
                toolbar.setNavigationIcon(R.drawable.ic_list);
                setupMenu();
                tvSignOut.setTextColor(getResources().getColor(R.color.black));
                iconSignOut.setImageResource(R.drawable.signout);
                drawer.closeDrawer();
                Libs_System.insertStringData(ActivityHome.this, HMW_Constant.TOKEN, "");
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("sign out", "ok");
                startActivity(intent);
                finish();
                break;

        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen()) {
            drawer.closeDrawer();
            return;
        }
        finishAffinity();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setupMenu() {
        tvHome.setTextColor(getResources().getColor(R.color.md_teal_500));
        tvUserProfile.setTextColor(getResources().getColor(R.color.md_teal_500));
        tvMyShoppingCart.setTextColor(getResources().getColor(R.color.md_teal_500));
        tvMyFavorites.setTextColor(getResources().getColor(R.color.md_teal_500));
        tvBrowseCategory.setTextColor(getResources().getColor(R.color.md_teal_500));
        tvProductOnSale.setTextColor(getResources().getColor(R.color.md_teal_500));
        tvSetting.setTextColor(getResources().getColor(R.color.md_teal_500));
        tvContactUs.setTextColor(getResources().getColor(R.color.md_teal_500));
        tvSignOut.setTextColor(getResources().getColor(R.color.md_teal_500));
        tvContactInfo.setTextColor(getResources().getColor(R.color.md_teal_500));


        iconHome.setImageResource(R.drawable.home);
        icProfile.setImageResource(R.drawable.profile);
        iconMyCart.setImageResource(R.drawable.cart);
        iconCategory.setImageResource(R.drawable.category);
        iconFavorites.setImageResource(R.drawable.favorites);
        iconProductSale.setImageResource(R.drawable.sale);
        iconSetting.setImageResource(R.drawable.setting);
        iconContact.setImageResource(R.drawable.contact_us);
        iconContactInfo.setImageResource(R.drawable.contact_info);
        iconSignOut.setImageResource(R.drawable.signout_active);

    }

    private boolean checkLogin(final Context context) {
        if (Libs_System.getStringData(context, HMW_Constant.TOKEN).equals("")) {
            return true;
        }
        return false;
    }

    private void showDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Ban phai dang nhap?");
        builder.setPositiveButton(getResources().getString(R.string.login), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(context, MainActivity.class));
//                ((ActivityHome)context).finish();
            }
        });
        builder.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void getUserInfo() {
        HMWApi api = new HMWApi();
        api.service().getUserInfo("Bearer " + Libs_System.getStringData(ActivityHome.this, HMW_Constant.TOKEN), "application/json", new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                try {
                    Log.i("TAG", "token: " + "Bearer " + Libs_System.getStringData(ActivityHome.this, HMW_Constant.TOKEN));
                    Picasso.with(ActivityHome.this).load(HMW_Constant.HOST + "/lbmedia/" + user.getAvatar_id()).into(imgAvatar);

                } catch (IOError ie) {
                    Log.d("e", ie.toString());
                }
                tvName.setText(user.getName());
                Log.i("TAG", "success: " + user.getAvatar_id());
                String year = user.getCreated_at().split("-")[0];
                tvSince.setText(year + " " + getResources().getString(R.string.member_since));

            }

            @Override
            public void failure(RetrofitError error) {
                if (error.getResponse() != null && error.getResponse().getStatus() == 401) {
                    Libs_System.showToast(ActivityHome.this, getString(R.string.unauthorized));
                    Libs_System.insertStringData(ActivityHome.this, HMW_Constant.TOKEN, "");
                    finish();
                    startActivity(getIntent());
                }
            }
        });
    }

    public void clearBadge(String token) {
        HMWApi api = new HMWApi();
        if (null == token) {
            api.service().sendClearBadge("Bearer " + Libs_System.getStringData(getApplicationContext(), HMW_Constant.TOKEN), "application/json", "hihi", token, new Callback<SimpleClass>() {
                @Override
                public void success(SimpleClass simpleClass, Response response) {
                    Log.e("clear_badge", simpleClass.getDescription());
                }

                @Override
                public void failure(RetrofitError error) {
                }
            });
        }
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        clearBadge(token);
    }

    @Override
    public void onDataSelected(String data) {
        Picasso.with(ActivityHome.this).load(HMW_Constant.HOST + "/lbmedia/" + data).into(imgAvatar);
    }


    class VersionChecker extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            showProgressDialog();
        }

        protected String doInBackground(String... arg0) {
            String newVersion = "";
//            try {
////                newVersion = Jsoup.connect("https://play.google.com/store/apps/details?id=com.msab.handmadewatch&hl=en")
////                        .timeout(30000)
////                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
////                        .referrer("http://www.google.com")
////                        .get()
////                        .select("div[itemprop=softwareVersion]")
////                        .first()
////                        .ownText();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (NullPointerException e) {
//                Log.d("saodayaa", e.toString());
//            }
            return newVersion;
        }

        @Override
        protected void onPostExecute(String newVersion) {
            dismissProgressDialog();
//            if (newVersion == null || newVersion.isEmpty()) {
//                checkVersionDialog(1);
//            } else if (!newVersion.equals(BuildConfig.VERSION_NAME)) {
//                checkVersionDialog(0);
//            }

        }
    }


    private void showProgressDialog() {
        progcDialog = new ProgressDialog(this);
        progcDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progcDialog.setIndeterminate(false);
        progcDialog.setCancelable(false);
        progcDialog.setMessage(getString(R.string.loading));
        // progcDialog.show();
    }

    private void dismissProgressDialog() {
        if (progcDialog != null) {
            progcDialog.dismiss();
        }
    }

    public void checkVersionDialog(final int type) {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(ActivityHome.this);
        builder1.setMessage(getString(R.string.message_check));
        builder1.setTitle(getString(R.string.title_check));
        builder1.setCancelable(false);
        builder1.setPositiveButton(
                getString(R.string.btn_ok),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        if (type == 0) {
                            String url = "https://play.google.com/store/apps/details?id=" + "com.msab.handmadewatch" + "&hl=en";
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse(url));
                            startActivity(i);
                        } else if (type == 1) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.check_fail), Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }
                    }
                });


        AlertDialog alert11 = builder1.create();
        alert11.show();
    }
}
