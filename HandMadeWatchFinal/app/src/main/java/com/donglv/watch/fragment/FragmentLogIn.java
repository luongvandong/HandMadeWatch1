package com.donglv.watch.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.donglv.watch.ActivityHome;
import com.donglv.watch.common.GlobleVars;
import com.donglv.watch.common.HMW_Constant;
import com.donglv.watch.common.HMW_System;
import com.donglv.watch.entity.LoginResponse;
import com.donglv.watch.entity.SimpleClass;
import com.donglv.watch.entity.UploadImageResponse;
import com.donglv.watch.entity.User;
import com.donglv.watch.service.HMWApi;
import com.nam.customlibrary.Libs_System;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.donglv.watch.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

/**
 * Created by vcom on 11/11/2016.
 */
public class FragmentLogIn extends Fragment implements View.OnClickListener {
    private Toolbar toolbar;
    private ImageView imgBack;
    private EditText edtUsername;
    private EditText edtPassword;
    private TextView btnLogIn;
    private TextView tvFotgetPass;
    private Bundle bundle;
    private String screen = "";
    private ProgressDialog progressDialog;

    private TextView tvToRes;
    private TextView tvToForgot;
    private TextView btnLogInfb;
    private TextView btnGuestLogin;
    private CallbackManager callbackManager;

    private String firstName;
    private String lastName;
    private String email;
    private Context context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        onPrintHashFB();
       String t= printKeyHash(getActivity());
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        context = rootView.getContext();
        init(rootView);
        return rootView;
    }

    private void init(View rootView) {
        edtUsername = (EditText) rootView.findViewById(R.id.edtUsername);
        edtUsername.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edtUsername.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.use), null, null, null);
                    edtPassword.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.key), null, null, null);
                } else {
                    edtUsername.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.use2), null, null, null);
                    edtPassword.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.key2), null, null, null);
                }
            }
        });
        edtPassword = (EditText) rootView.findViewById(R.id.edtPassword);
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//
//            // Force a right-aligned text entry, otherwise latin character input,
//            // like "abc123", will jump to the left and may even disappear!
//            edtPassword.setTextDirection(View.TEXT_DIRECTION_RTL);
//
//            // Make the "Enter password" hint display on the right hand side
//            edtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
//        }
//        edtPassword.addTextChangedListener(new TextWatcher() {
//
//            boolean inputTypeChanged;
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//                // Workaround https://code.google.com/p/android/issues/detail?id=201471 for Android 4.4+
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                    if (s.length() > 0) {
//                        if (!inputTypeChanged) {
//
//                            // When a character is typed, dynamically change the EditText's
//                            // InputType to PASSWORD, to show the dots and conceal the typed characters.
//                            edtPassword.setInputType(InputType.TYPE_CLASS_TEXT |
//                                    InputType.TYPE_TEXT_VARIATION_PASSWORD |
//                                    InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
//
//                            // Move the cursor to the correct place (after the typed character)
//                         edtPassword.setSelection(s.length());
//
//                            inputTypeChanged = true;
//                        }
//                    } else {
//
//                        // Reset EditText: Make the "Enter password" hint display on the right
//                        edtPassword.setInputType(InputType.TYPE_CLASS_TEXT |
//                                InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
//
//                        inputTypeChanged = false;
//                    }
//                }
//
//            }
//        });
        btnLogIn = (TextView) rootView.findViewById(R.id.btnLogIn);
        btnLogIn.setOnClickListener(this);
        tvToRes = (TextView) rootView.findViewById(R.id.tvToRes);
        tvToRes.setOnClickListener(this);
        tvToForgot = (TextView) rootView.findViewById(R.id.tvToForgot);
        tvToForgot.setOnClickListener(this);
        btnLogInfb = (TextView) rootView.findViewById(R.id.btnLogInfb);
        btnLogInfb.setOnClickListener(this);
        btnGuestLogin = (TextView) rootView.findViewById(R.id.btnGuestLogin);
        btnGuestLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogInfb:
                loginWithFaceBook();
                break;
            case R.id.btnGuestLogin:
                Intent intent = new Intent(getActivity(), ActivityHome.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.btnLogIn:
                if (!Libs_System.connectionStatus(context)) {
                    Libs_System.showToast(context, context.getString(R.string.please_check_connection));
                    return;
                }
                Libs_System.hideKeyboard(getActivity());
                if (edtUsername.getText().toString().equals("") || edtPassword.getText().toString().equals("")) {
                    Libs_System.showToast(context, getString(R.string.please_input_data_vi));
                    return;
                }
                showProgressDialog(context, getString(R.string.loading));
                HMWApi api = new HMWApi();
                Log.d("nguyenvanquang", edtUsername.getText().toString().trim() + " " + edtPassword.getText().toString().trim());
                api.service().login("password", "2", HMW_Constant.CLIENT_SECRET, edtUsername.getText().toString().trim(), edtPassword.getText().toString().trim(), "", new Callback<LoginResponse>() {
                    @Override
                    public void success(LoginResponse loginResponse, Response response) {
                        dismissProgressDialog();
                        Libs_System.insertStringData(context, HMW_Constant.email, edtUsername.getText().toString());
                        Libs_System.insertStringData(context, HMW_Constant.TOKEN, loginResponse.getAccess_token());
                        sendFirebaseTokenToServer();
                        startActivity(new Intent(getActivity(), ActivityHome.class));
                        getActivity().finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("chuyengidat", edtPassword.getText().toString());
                        Log.d("chuyengidat", error.toString());
                        dismissProgressDialog();
                        Libs_System.showToast(context, context.getString(R.string.request_server_fail));
                    }
                });
                break;
            case R.id.tvToRes:
                getFragmentManager().beginTransaction().replace(R.id.main, new FragmentRegister()).addToBackStack(null).commit();

                break;
            case R.id.tvToForgot:
                getFragmentManager().beginTransaction().replace(R.id.main, new FragmentFogotPassword()).addToBackStack(null).commit();
                break;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Libs_System.hideKeyboard(getActivity());
    }

    private void sendFirebaseTokenToServer() {
        String token = FirebaseInstanceId.getInstance().getToken();
        if (token != null && !token.isEmpty()) {
            Log.e("firebase Token", token);
            Log.e("normal token", Libs_System.getStringData(getActivity(), HMW_Constant.TOKEN));
            HMWApi api = new HMWApi();
            api.service().sendFCMToken("Bearer " + Libs_System.getStringData(getActivity(), HMW_Constant.TOKEN), "application/json", token, "android", new Callback<SimpleClass>() {
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

    private void loginWithFaceBook() {
        Log.d("davaoady", "batdau");
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().logOut();
        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile,email"));

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("davaoady", loginResult.toString());
                        Log.e("fb token", loginResult.getAccessToken().getToken());
//                        Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
                        GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.e("all result", object.toString());
                                        try {
                                            email = object.getString("email");
                                            lastName = object.getString("last_name");
                                            firstName = object.getString("first_name");
                                            Log.i("idfb", "onCompleted: " + object.get("id"));
                                            onRegisterFB(object.getString("id"), object.getString("name"));
                                        } catch (JSONException e) {
                                            // TODO Auto-generated catch block
                                            try {
                                                lastName = object.getString("last_name");
                                                firstName = object.getString("first_name");
                                                onRegisterFB(object.getString("id"), object.getString("name"));
                                            } catch (JSONException ex) {
                                                // TODO Auto-generated catch block
                                                ex.printStackTrace();
                                            }
                                            e.printStackTrace();
                                        }

                                    }

                                });
                        //get avatar http:// graph.facebook.com/{facebook-Id}/picture?width=160&height=160
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,first_name,last_name");
                        request.setParameters(parameters);
                        request.executeAsync();
                    }

                    @Override
                    public void onCancel() {
                        Log.e("facebook login error", "cancel");
                        Log.d("davaoady1", "132131");
//                        Log.e("fb token", AccessToken.getCurrentAccessToken().getToken());
//                        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(),
//                                new GraphRequest.GraphJSONObjectCallback() {
//                                    @Override
//                                    public void onCompleted(JSONObject object, GraphResponse response) {
//                                        Log.e("all result", object.toString());
//                                        try {
//                                            email = object.getString("email");
//                                            lastName = object.getString("last_name");
//                                            firstName =object.getString("first_name");
//                                            onRegisterFB(object.getString("id"), object.getString("name"));
//                                        } catch (JSONException e) {
//                                            // TODO Auto-generated catch block
//                                            try {
//                                                lastName = object.getString("last_name");
//                                                firstName =object.getString("first_name");
//                                                onRegisterFB(object.getString("id"), object.getString("name"));
//                                            } catch (JSONException ex) {
//                                                // TODO Auto-generated catch block
//                                                ex.printStackTrace();
//                                            }
//                                            e.printStackTrace();
//                                        }
//
//                                    }
//
//                                });
                        //get avatar http:// graph.facebook.com/{facebook-Id}/picture?width=160&height=160
//                        Bundle parameters = new Bundle();
//                        parameters.putString("fields", "id,name,email,first_name,last_name");
//                        request.setParameters(parameters);
//                        request.executeAsync();Bundle parameters = new Bundle();
//                        parameters.putString("fields", "id,name,email,first_name,last_name");
//                        request.setParameters(parameters);
//                        request.executeAsync();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("davaoady", error.toString());
                        Log.e("facebook login error", error.toString());
//                        Toast.makeText(context, "Faild!", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void onRegisterFB(final String facebookID, final String userName) {

        showProgressDialog(getContext(), "loading...");
        HMWApi api = new HMWApi();
        api.service().registerFB(facebookID, userName, new Callback<SimpleClass>() {
            @Override
            public void success(SimpleClass simpleClass, Response response) {
                Toast.makeText(context, "register success!", Toast.LENGTH_SHORT).show();
                dismissProgressDialog();
                Log.e("startloginfb", simpleClass.getCode());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("TAG", "failure: " + error.toString());
                dismissProgressDialog();
                if (error.getResponse().getStatus() == 200 || error.getResponse().getStatus() == 501) {
                    Log.e("startloginfb", "ok");
                    onLoginWithFB(facebookID, userName);
                } else {
                    Libs_System.showToast(getContext(), getString(R.string.request_server_fail));
                    Log.e("registerError", error.toString());
                }
            }
        });
    }

    private Target targetLoadImageFromURL() {
        Log.e("picassoImageTarget", " picassoImageTarget");
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                Log.e("picassoImageTarget", "done");
                onUploadImage(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.e("picassoImageTarget", "error");
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable != null) {
                }
            }
        };
    }

    private void onLoginWithFB(final String facebookId, final String userName) {
        Log.e("beginloginFB", "ok");
        showProgressDialog(getContext(), "loading...");
        HMWApi api = new HMWApi();
        api.service().loginFB(HMW_Constant.CLIENT_ID,
                HMW_Constant.CLIENT_SECRET,
                facebookId,
                HMW_Constant.PASSWORD_LOGIN_FACEBOOK,
                HMW_Constant.GRAND_TYPE, "",
                new Callback<LoginResponse>() {
                    @Override
                    public void success(LoginResponse loginResponse, Response response) {
                        Log.i("TAG", "success: ");
                        dismissProgressDialog();
                        Libs_System.insertStringData(context, HMW_Constant.email, edtUsername.getText().toString());
                        Libs_System.insertStringData(context, HMW_Constant.TOKEN, loginResponse.getAccess_token());
                        GlobleVars.TOKEN = loginResponse.getAccess_token();
                        sendFirebaseTokenToServer();
                        Log.i("TAG", "userName: " + userName);
                        Log.e("fbavatarImage", "http://graph.facebook.com/" + "1970530519841666" + "/picture?width=200&height=200");
                        Picasso.with(getContext()).load("https://graph.facebook.com/" + facebookId + "/picture?width=200&height=200").into(targetLoadImageFromURL());
                        startActivity(new Intent(context, ActivityHome.class));
                        getActivity().finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        dismissProgressDialog();
                        Log.d("nguyenquang", error.toString());
                        Toast.makeText(context, "Error!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void onUploadImage(Bitmap bitmap) {
        Log.e("uploadimage", "begin upload");
        File file = HMW_System.saveBitmapToFile(context, bitmap, "picture");
        TypedFile image_edit = new TypedFile("multipart/form-data", file);
        HMWApi api = new HMWApi();
        Log.e("token", "Bearer " + GlobleVars.TOKEN);
        api.service().upLoadImage("Bearer " + GlobleVars.TOKEN, "application/json", image_edit, new Callback<UploadImageResponse>() {
            @Override
            public void success(UploadImageResponse uploadImageResponse, Response response) {
                Log.e("uploadimage", "done");
                if (uploadImageResponse.getCode() == 200) {
                    getUserInfo(uploadImageResponse.getData().getId());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("uploadimage fail", error.toString());
            }
        });
    }


    private void getUserInfo(final String avatarID) {
        HMWApi api = new HMWApi();
        api.service().getUserInfo("Bearer " + Libs_System.getStringData(context, HMW_Constant.TOKEN), "application/json", new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                dismissProgressDialog();
                onUpdateProfile(user.getId(), avatarID);

            }

            @Override
            public void failure(RetrofitError error) {
                dismissProgressDialog();
            }
        });
    }

    private void onUpdateProfile(String userID, String avatarID) {
        HMWApi api = new HMWApi();
        firstName = firstName != null ? firstName : "";
        lastName = lastName != null ? lastName : "";
        String mEmail = email != null ? email : "";
        api.service().putUserInfo(userID,
                "Bearer " + Libs_System.getStringData(context, HMW_Constant.TOKEN),
                "application/json",
                firstName,
                lastName,
                mEmail,
                avatarID,
                new Callback<SimpleClass>() {
                    @Override
                    public void success(SimpleClass simpleClass, Response response) {

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                }
        );
    }
    public String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (android.content.pm.Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }


    private void onPrintHashFB() {
        try {
            PackageInfo info = getActivity().getPackageManager().getPackageInfo("com.donglv.watch", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String sign = Base64.encodeToString(md.digest(), Base64.DEFAULT);
                Log.e("MY KEY HASH:", sign);
//                Toast.makeText(getApplicationContext(), sign, Toast.LENGTH_LONG).show();
            }
        } catch (PackageManager.NameNotFoundException e) {
        } catch (NoSuchAlgorithmException e) {
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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
        if (progressDialog != null) {
            progressDialog.dismiss();
        }

    }
}
