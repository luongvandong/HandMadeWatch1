package com.donglv.watch.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.libre.mylibs.MyUtils;
import com.donglv.watch.ActivityHome;
import com.donglv.watch.R;
import com.donglv.watch.common.HMW_Constant;
import com.donglv.watch.entity.SimpleClass;
import com.donglv.watch.entity.UploadImageResponse;
import com.donglv.watch.entity.User;
import com.donglv.watch.service.HMWApi;
import com.nam.customlibrary.Libs_System;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

import static android.app.Activity.RESULT_OK;

/**
 * Created by vcom on 26/12/2016.
 */
public class FragmentUserProfile extends Fragment implements View.OnClickListener {
    private UpdateImageAvatar listener;
    private ProgressDialog progressDialog;
    private Context context;
    private ImageView imgAvatar;
    private EditText edtFirstName;
    private EditText edtLastName;
    private EditText edtEmail;
    private EditText edtTelephone;
    private EditText edtFax;
    private EditText edtCompany;
    private EditText edtAddress;
    private EditText edtAddress2;
    private EditText edtCity;
    private EditText edtPostCode;
    private EditText edtCountry;
    private EditText edtRegion;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private TextView btnCancel;
    private TextView btnSave;
    private Uri imageUri;
    private TypedFile image_edit;
    private File file;
    private String pictureImagePath;
    private BottomSheetBehavior bottomSheetBehavior;
    private Button btnFromCamera;
    private Button btnFromGallery;
    private Button btnCancelImage;
    private String userID;
    private int REQUEST_CODE_SOME_FEATURES_PERMISSIONS = 999;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(com.donglv.watch.R.layout.fragment_user_frofile, container, false);
        context = rootView.getContext();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int hasWritePermission = getActivity().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            int hasReadPermission = getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            int hasCameraPermission = getActivity().checkSelfPermission(Manifest.permission.CAMERA);

            List<String> permissions = new ArrayList<String>();
            if (hasWritePermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            } else {
            }

            if (hasCameraPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.CAMERA);
            } else {
            }

            if (hasReadPermission != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);

            } else {
            }

            if (!permissions.isEmpty()) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), REQUEST_CODE_SOME_FEATURES_PERMISSIONS);
            }
        }
        checkPermission();
        init(rootView);
        getUserInfo();
        return rootView;
    }

    private void checkPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 999);
        } else {
        }
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    getActivity(), new String[]{Manifest.permission.CAMERA}, 998);
        } else {
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {

            case 999:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                }
                break;
            case 998:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        TextView txtTitle = (TextView) ActivityHome.toolbar.findViewById(R.id.txtToolbarTitle);
        txtTitle.setText(getText(R.string.user_profile));
    }

    private void init(View rootView) {
        bottomSheetBehavior = BottomSheetBehavior.from(rootView.findViewById(com.donglv.watch.R.id.bottomSheetLayout));
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
        imgAvatar = (ImageView) rootView.findViewById(com.donglv.watch.R.id.imgAvatar);
        imgAvatar.setOnClickListener(this);
        edtFirstName = (EditText) rootView.findViewById(com.donglv.watch.R.id.edtFirstName);
        edtLastName = (EditText) rootView.findViewById(com.donglv.watch.R.id.edtLastName);
        edtEmail = (EditText) rootView.findViewById(com.donglv.watch.R.id.edtEmail);
        edtTelephone = (EditText) rootView.findViewById(com.donglv.watch.R.id.edtTelephone);
        edtFax = (EditText) rootView.findViewById(com.donglv.watch.R.id.edtFax);
        edtCompany = (EditText) rootView.findViewById(com.donglv.watch.R.id.edtCompany);
        edtAddress = (EditText) rootView.findViewById(com.donglv.watch.R.id.edtAddress);
        edtAddress2 = (EditText) rootView.findViewById(com.donglv.watch.R.id.edtAddress2);
        edtCity = (EditText) rootView.findViewById(com.donglv.watch.R.id.edtCity);
        edtPostCode = (EditText) rootView.findViewById(com.donglv.watch.R.id.edtPostCode);
        edtCountry = (EditText) rootView.findViewById(com.donglv.watch.R.id.edtCountry);
        edtRegion = (EditText) rootView.findViewById(com.donglv.watch.R.id.edtRegion);
        edtPassword = (EditText) rootView.findViewById(com.donglv.watch.R.id.edtPassword);
        edtConfirmPassword = (EditText) rootView.findViewById(com.donglv.watch.R.id.edtConfirmPassword);
        btnCancel = (TextView) rootView.findViewById(com.donglv.watch.R.id.btnCancel);
        btnCancel.setOnClickListener(this);
        btnSave = (TextView) rootView.findViewById(com.donglv.watch.R.id.btnSave);
        btnSave.setOnClickListener(this);

        btnCancelImage = (Button) rootView.findViewById(com.donglv.watch.R.id.btnCancelImage);
        btnCancelImage.setOnClickListener(this);
        btnFromCamera = (Button) rootView.findViewById(com.donglv.watch.R.id.btnFromCamera);
        btnFromCamera.setOnClickListener(this);
        btnFromGallery = (Button) rootView.findViewById(com.donglv.watch.R.id.btnFromGallery);
        btnFromGallery.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnCancel:
                getFragmentManager().beginTransaction().replace(R.id.main, new FragmentHome()).commit();
                break;
            case R.id.btnSave:
                if (edtFirstName.getText().toString().trim().isEmpty()) {
                    Libs_System.showToast(context, getString(com.donglv.watch.R.string.first_name_should_not_empty));
                    Log.e("error", getString(com.donglv.watch.R.string.first_name_should_not_empty));
                    return;
                }
                if (edtLastName.getText().toString().trim().isEmpty()) {
                    Libs_System.showToast(context, getString(com.donglv.watch.R.string.last_name_should_not_empty));
                    Log.e("error", getString(com.donglv.watch.R.string.last_name_should_not_empty));
                    return;
                }

                if (edtEmail.getText().toString().trim().isEmpty()) {
                    Libs_System.showToast(context, getString(com.donglv.watch.R.string.email_should_not_empty));
                    Log.e("error", getString(com.donglv.watch.R.string.email_should_not_empty));
                    return;
                }
                if (edtTelephone.getText().toString().trim().isEmpty()) {
                    Libs_System.showToast(context, getString(com.donglv.watch.R.string.telephone_should_not_empty));
                    Log.e("error", getString(com.donglv.watch.R.string.telephone_should_not_empty));
                    return;
                }
                if (edtAddress.getText().toString().trim().isEmpty()) {
                    Libs_System.showToast(context, getString(com.donglv.watch.R.string.address_should_not_empty));
                    Log.e("error", getString(com.donglv.watch.R.string.address_should_not_empty));
                    return;
                }
                if (edtCountry.getText().toString().trim().isEmpty()) {
                    Libs_System.showToast(context, getString(com.donglv.watch.R.string.country_should_not_empty));
                    Log.e("error", getString(com.donglv.watch.R.string.country_should_not_empty));
                    return;
                }
                if (edtRegion.getText().toString().trim().isEmpty()) {
                    Libs_System.showToast(context, getString(com.donglv.watch.R.string.region_should_not_empty));
                    Log.e("error", getString(com.donglv.watch.R.string.region_should_not_empty));
                    return;
                }

                if (!edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())) {
                    Libs_System.showToast(context, getString(com.donglv.watch.R.string.new_password_must_be_equals_confirm_new_password));
                    Log.e("error", getString(com.donglv.watch.R.string.new_password_must_be_equals_confirm_new_password));
                    return;
                }
                putUserInfo();
                break;
            case R.id.imgAvatar:
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                break;
            case R.id.btnCancelImage:
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                break;
            case R.id.btnFromCamera:
                Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(takePicture, 0);
                break;
            case R.id.btnFromGallery:
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto, 1);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        try {
            switch (requestCode) {
                case 0:
                    if (resultCode == RESULT_OK) {
                        Bitmap photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
                        imgAvatar.setImageBitmap(photo);
                        File file = MyUtils.saveBitmapToFile(MyUtils.resize(photo, 800, 800), "picture" + ".jpg");
                        image_edit = new TypedFile("multipart/form-data", file);
                    }
                    break;
                case 1:
                    if (resultCode == RESULT_OK) {
                        displayImageFromGallery(imageReturnedIntent, imgAvatar);
                    }
                    break;
            }
        } catch (Exception e) {
            Toast.makeText(getContext(), "Please try again", Toast.LENGTH_LONG)
                    .show();
        }

    }

    public void showProgressDialog(Context context, String message) {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(0);
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.show();
        Libs_System.insertIntData(getContext(), HMW_Constant.NUMBER_NOTIFICATION, 1);
    }

    public void dismissProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    private void putUserInfo() {
        showProgressDialog(context, "loading");
        final HMWApi api = new HMWApi();
        if (image_edit != null) {
            api.service().upLoadImage("Bearer " + Libs_System.getStringData(context, HMW_Constant.TOKEN), "application/json", image_edit, new Callback<UploadImageResponse>() {
                @Override
                public void success(final UploadImageResponse uploadImageResponse, Response response) {
                    dismissProgressDialog();
                    api.service().putUserInfo(userID,
                            "Bearer " + Libs_System.getStringData(context, HMW_Constant.TOKEN),
                            "application/json",
                            uploadImageResponse.getData().getId(),
                            edtFirstName.getText().toString(),
                            edtLastName.getText().toString(),
                            edtEmail.getText().toString(),
                            edtTelephone.getText().toString(),
                            edtAddress.getText().toString(),
                            edtAddress2.getText().toString(),
                            edtCity.getText().toString(),
                            edtCountry.getText().toString(),
                            edtRegion.getText().toString(),
                            edtFax.getText().toString(),
                            edtCompany.getText().toString(),
                            edtPostCode.getText().toString(),
                            edtPassword.getText().toString(),
                            new Callback<SimpleClass>() {
                                @Override
                                public void success(SimpleClass simpleClass, Response response) {
                                    dismissProgressDialog();
                                    listener.onDataSelected(uploadImageResponse.getData().getId());
                                    Libs_System.showToast(context, "نجاح");
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    dismissProgressDialog();
                                    if (error.getResponse() != null && error.getResponse().getStatus() == 401) {
                                        Libs_System.showToast(context, context.getString(com.donglv.watch.R.string.unauthorized));
                                        Libs_System.insertStringData(getContext(), HMW_Constant.TOKEN, "");
                                        ((Activity) getContext()).finish();
                                        getContext().startActivity(((Activity) getContext()).getIntent());
                                    }
                                    Log.e("get us info", error.getMessage());
                                }
                            });
                }

                @Override
                public void failure(RetrofitError error) {
                    dismissProgressDialog();

                }
            });
        } else {
            api.service().putUserInfo2(userID,
                    "Bearer " + Libs_System.getStringData(context, HMW_Constant.TOKEN),
                    "application/json",
                    edtFirstName.getText().toString(),
                    edtLastName.getText().toString(),
                    edtEmail.getText().toString(),
                    edtTelephone.getText().toString(),
                    edtAddress.getText().toString(),
                    edtAddress2.getText().toString(),
                    edtCity.getText().toString(),
                    edtCountry.getText().toString(),
                    edtRegion.getText().toString(),
                    edtFax.getText().toString(),
                    edtCompany.getText().toString(),
                    edtPostCode.getText().toString(),
                    edtPassword.getText().toString(),
                    new Callback<SimpleClass>() {
                        @Override
                        public void success(SimpleClass simpleClass, Response response) {
                            dismissProgressDialog();
                            Libs_System.showToast(context, "نجاح");
                        }

                        @Override
                        public void failure(RetrofitError error) {
                            dismissProgressDialog();
                            if (error.getResponse() != null && error.getResponse().getStatus() == 401) {
                                Libs_System.showToast(context, context.getString(com.donglv.watch.R.string.unauthorized));
                                Libs_System.insertStringData(getContext(), HMW_Constant.TOKEN, "");
                                ((Activity) getContext()).finish();
                                getContext().startActivity(((Activity) getContext()).getIntent());
                            }
                            Log.e("get us info", error.getMessage());
                        }
                    });
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
                userID = user.getId();
                edtFirstName.setText(user.getFirst_name());
                edtLastName.setText(user.getLast_name());
                edtEmail.setText(user.getEmail());
                edtTelephone.setText(user.getTelephone());
                edtFax.setText(user.getFax());
                edtCompany.setText(user.getCompany());
                edtAddress.setText(user.getAddress1());
                edtAddress2.setText(user.getAddress2());
                edtCity.setText(user.getCity());
                edtPostCode.setText(user.getPost_code());
                edtCountry.setText(user.getCountry());
                edtRegion.setText(user.getRegion_state());
            }

            @Override
            public void failure(RetrofitError error) {
                dismissProgressDialog();
                if (error.getResponse() != null && error.getResponse().getStatus() == 401) {
                    Libs_System.showToast(context, context.getString(com.donglv.watch.R.string.unauthorized));
                    Libs_System.insertStringData(getContext(), HMW_Constant.TOKEN, "");
                    ((Activity) getContext()).finish();
                    getContext().startActivity(((Activity) getContext()).getIntent());
                }
            }
        });
    }

    private void displayImageFromGallery(Intent data, ImageView imageView) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String imgDecodableString = cursor.getString(columnIndex);
        cursor.close();
        Bitmap bitmap = MyUtils.bitmapRotate(imgDecodableString);
        imageView.setImageBitmap(bitmap);
        File file = MyUtils.saveBitmapToFile(MyUtils.resize(bitmap, 800, 800), "picture" + ".jpg");
        image_edit = new TypedFile("multipart/form-data", file);
    }

    public interface UpdateImageAvatar {
        void onDataSelected(String data);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            listener = (UpdateImageAvatar) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement MyInterface ");
        }
    }
}
