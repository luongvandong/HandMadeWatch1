package com.donglv.watch;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.donglv.watch.adapter.ChatAdapter;
import com.donglv.watch.common.HMW_Constant;
import com.donglv.watch.entity.ChatItem;
import com.donglv.watch.entity.Conversation;
import com.donglv.watch.entity.SimpleClass;
import com.donglv.watch.service.HMWApi;
import com.nam.customlibrary.Libs_System;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEventListener;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by vcom on 09/12/2016.
 */
public class TestActivity extends AppCompatActivity implements View.OnClickListener {
    private ProgressDialog progressDialog;
    private LinearLayout btnSend;
    private EditText edtContent;
    private ChatAdapter chatAdapter;
    private ListView listChat;
    private ArrayList<ChatItem.Data> chatItems = new ArrayList<>();
    private CountDownTimer timer;
    private String scroll = "";
    private String conversationId;
    private String loadMess = "";
    private ImageView imgBack;
    private ImageView imgChat;
    private TextView tvTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_chat);
        init();

        timer = new CountDownTimer(50000000, 3000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (loadMess.equals("ok")) {
                    getMessage2();
                }
            }

            @Override
            public void onFinish() {
            }
        };
        timer.start();
        introChat();
        KeyboardVisibilityEvent.setEventListener(
                TestActivity.this,
                new KeyboardVisibilityEventListener() {
                    @Override
                    public void onVisibilityChanged(boolean isOpen) {
                        if (isOpen) {
                            listChat.setSelection(chatItems.size());
                        } else {
                            listChat.setSelection(chatItems.size());
                        }
                    }
                });


    }


    private void init() {
        tvTitle =(TextView) findViewById(R.id.txtToolbarTitle);
        imgBack = (ImageView) findViewById(R.id.imgBack);
        imgBack.setOnClickListener(this);
        imgChat = (ImageView) findViewById(R.id.imgChat);
        imgChat.setVisibility(View.INVISIBLE);
        btnSend = (LinearLayout) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
        edtContent = (EditText) findViewById(R.id.edtContent);
        listChat = (ListView) findViewById(R.id.listChat);
        listChat.setDivider(null);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                if (checkLogin(TestActivity.this)) {
                    showDialog(TestActivity.this);
                    return;
                }
                if (edtContent.getText().toString().equals("")) {
                    Libs_System.showToast(TestActivity.this, "nhập comment");
                    return;
                }
                HMWApi api = new HMWApi();
                api.service().addMess("Bearer " + Libs_System.getStringData(TestActivity.this, HMW_Constant.TOKEN), "application/json", edtContent.getText().toString(), conversationId, new Callback<SimpleClass>() {
                    @Override
                    public void success(SimpleClass simpleClass, Response response) {
                        edtContent.setText("");
                        getMessage2();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        if(error.getResponse()!=null&&error.getResponse().getStatus()==401){
                            Libs_System.showToast(TestActivity.this,getString(R.string.unauthorized));
                            Libs_System.insertStringData(TestActivity.this, HMW_Constant.TOKEN, "");
                            finish();
                            startActivity(getIntent());
                        }

                    }
                });

                break;
            case R.id.imgBack:
                finish();
                break;
        }
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

    private boolean checkLogin(final Context context) {
        if (Libs_System.getStringData(context, HMW_Constant.TOKEN).equals("")) {
            return true;
        }
        return false;
    }

    private void showDialog(final Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this);
        builder.setMessage("Bạn cần phải đăng nhâp");
        builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(TestActivity.this, MainActivity.class));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void getMessage2() {
        final int size = chatItems.size();
        HMWApi api = new HMWApi();
        api.service().getAllMess("Bearer " + Libs_System.getStringData(TestActivity.this, HMW_Constant.TOKEN), "application/json", conversationId, new Callback<ChatItem>() {
            @Override
            public void success(ChatItem chatItem, Response response) {
                if (size < chatItem.getData().size()) {
                    chatAdapter = new ChatAdapter(TestActivity.this, chatItem.getData());
                    listChat.setAdapter(chatAdapter);
                    listChat.setSelection(chatItem.getData().size());
                }
                chatItems = chatItem.getData();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("get All mess", error.getMessage());
            }
        });
    }

    private void getMessage() {
        final int size = chatItems.size();
        HMWApi api = new HMWApi();
        api.service().getAllMess("Bearer " + Libs_System.getStringData(TestActivity.this, HMW_Constant.TOKEN), "application/json", conversationId, new Callback<ChatItem>() {
            @Override
            public void success(ChatItem chatItem, Response response) {
                if (size < chatItem.getData().size()) {
                    chatAdapter = new ChatAdapter(TestActivity.this, chatItem.getData());
                    listChat.setAdapter(chatAdapter);
                    listChat.setSelection(chatItem.getData().size());
                }
                chatItems = chatItem.getData();
                dismissProgressDialog();
                loadMess = "ok";

            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("get All mess", error.getMessage());
                if(error.getResponse()!=null&&error.getResponse().getStatus()==401){
                    Libs_System.showToast(TestActivity.this,getString(R.string.unauthorized));
                    Libs_System.insertStringData(TestActivity.this, HMW_Constant.TOKEN, "");
                    finish();
                    startActivity(getIntent());
                }
            }
        });
    }

    private void introChat() {
        showProgressDialog(TestActivity.this, "loading");
        HMWApi api = new HMWApi();
        api.service().createMyConversation("Bearer " + Libs_System.getStringData(TestActivity.this, HMW_Constant.TOKEN), "application/json", "hihi", new Callback<SimpleClass>() {
            @Override
            public void success(SimpleClass simpleClass, Response response) {
                HMWApi api1 = new HMWApi();
                api1.service().getIdMyConversation("Bearer " + Libs_System.getStringData(TestActivity.this, HMW_Constant.TOKEN), "application/json", new Callback<Conversation>() {
                    @Override
                    public void success(Conversation conversation, Response response) {
                        conversationId = conversation.getData().get(0).getId();
                        getMessage();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        dismissProgressDialog();
                        if (error.getResponse().getStatus() == 401) {
                            Libs_System.showToast(TestActivity.this, getString(R.string.unauthorized));
                            Libs_System.insertStringData(TestActivity.this, HMW_Constant.TOKEN, "");
                            finish();
                            startActivity(getIntent());
                        }
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                dismissProgressDialog();
                if (error.getResponse().getStatus() == 401) {
                    Libs_System.showToast(TestActivity.this, getString(R.string.unauthorized));
                    Libs_System.insertStringData(TestActivity.this, HMW_Constant.TOKEN, "");
                    finish();
                    startActivity(getIntent());
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
