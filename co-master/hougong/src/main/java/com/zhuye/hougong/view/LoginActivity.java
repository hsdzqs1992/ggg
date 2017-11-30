package com.zhuye.hougong.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.open.utils.HttpUtils;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.zhuye.hougong.MainActivity;
import com.zhuye.hougong.R;
import com.zhuye.hougong.bean.LoginCode;
import com.zhuye.hougong.bean.MessageEvent;
import com.zhuye.hougong.contants.Contants;
import com.zhuye.hougong.utils.SpUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "-------------------";
    private static final String REDIRECT_URL ="http://www.rocs/andro" ;
    @BindView(R.id.login_username)
    EditText loginUsername;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login_forgetpass)
    TextView loginForgetpass;
    @BindView(R.id.login_login)
    Button loginLogin;


    @BindView(R.id.login_regeist)
    Button loginRegeist;
    @BindView(R.id.activity_login)
    LinearLayout activityLogin;


    public static final String SCOPE =
            "email,direct_messages_read,direct_messages_write,"
                    + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
                    + "follow_app_official_microblog," + "invitation_write";
    String phone;
    String pass;
    Tencent mtencent;
    private UserInfo mUserInfo;
    WeiboAuth mWeiboAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

       // Typeface typeface = Typeface.createFromAsset(getAssets(), "iconfont.ttf");

        mtencent = Tencent.createInstance("1106476035", this.getApplicationContext());
        mWeiboAuth = new WeiboAuth(this, "348532613",
                REDIRECT_URL, SCOPE);

        String token = SpUtils.getString(this,"token","");
        if(!TextUtils.isEmpty(token)){
            startActivity(new Intent(this,MainActivity.class));
            finish();
        }
    }

    @OnClick({R.id.login_forgetpass, R.id.login_login, R.id.login_weixin, R.id.login_qq, R.id.login_weibo,R.id.login_regeist})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login_forgetpass:
                resetPassword();
                break;
            case R.id.login_login:

                login();

                break;
            case R.id.login_weixin:
                break;
            case R.id.login_qq:

                qqLogin();
                break;
            case R.id.login_weibo:
                mWeiboAuth.anthorize(new AuthListener());
                break;
            case R.id.login_regeist:
                startActivity(new Intent(LoginActivity.this,RegeistActivity.class));
                break;
        }
    }

    BaseUiListener baselisten;
    private void qqLogin() {

         baselisten= new BaseUiListener();
        mtencent.login(this,"all",baselisten);

    }

    private void resetPassword() {
        startActivity(new Intent(LoginActivity.this,ForgetPassActivity.class));
    }

    private void login() {
        phone=loginUsername.getText().toString().trim();
        pass=loginPassword.getText().toString().trim();
        if(TextUtils.isEmpty(phone)||TextUtils.isEmpty(pass)){
            Toast.makeText(LoginActivity.this,"手机号或密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }


//        EMClient.getInstance().login(phone, pass, new EMCallBack() {
//            @Override
//            public void onSuccess() {
//                CommentUtils.toast(LoginActivity.this,"登录成功");
//                //finish();
//            }
//
//            @Override
//            public void onError(int i, String s) {
//                CommentUtils.toast(LoginActivity.this,s);
//            }
//
//            @Override
//            public void onProgress(int i, String s) {
//
//            }
//        });
        loginFormserver();
    }

    private void loginFormserver() {
        OkGo.<String>post(Contants.LOGIN_URL)
                .params("mobile",phone)
                .params("password",pass)
                .execute(new StringCallback() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onSuccess(Response<String> response) {
                        Gson gson = new Gson();
                        //// TODO: 2017/11/28
                        LoginCode code = gson.fromJson(response.body(), LoginCode.class);
                        if(code.getCode().equals("200")){
                            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                            //startActivity(new Intent(LoginActivity.this,LoginActivity.class));
                            //保存token
                            SpUtils.setString(LoginActivity.this,"token",code.getData().getToken());
                            //发送更新通知
                            EventBus.getDefault().post(new MessageEvent());
                            Toast.makeText(LoginActivity.this,SpUtils.getString(LoginActivity.this,"token",""),Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }else {
                            Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode,resultCode,data,baselisten);
    }

    class  GetUserInfo implements IUiListener{

        @Override
        public void onComplete(Object o) {
            Log.i("---------",o.toString());
        }

        @Override
        public void onError(UiError uiError) {
            Log.i("---------",uiError.toString());
        }

        @Override
        public void onCancel() {

        }
    }


    String openid;
    String access_token;
    private class BaseUiListener implements IUiListener {
//        @Override
//        public void onComplete(JSONObject response) {
//            //mBaseMessageText.setText("onComplete:");
//            //mMessageText.setText(response.toString());
//            doComplete(response);
//        }

        protected void doComplete(JSONObject values) {

            Log.i("---------",values.toString());


        }

        @Override
        public void onComplete(Object o) {
            Log.i("---------",o.toString());

            JSONObject json = (JSONObject) o;
            try {
                openid = json.getString("openid");
                access_token = json.getString("access_token");
                String expires_in = json.getString("expires_in");

                mtencent.setOpenId(openid);
                mtencent.setAccessToken(access_token,expires_in);
                QQToken qqToken = mtencent.getQQToken();

                mUserInfo = new UserInfo(getApplicationContext(), qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        //是一个json串response.tostring
                        Log.e(TAG, "登录成功" + response.toString());
                        //登录成功后进行Gson解析即可获得你需要的QQ头像和昵称
                        //  Nickname  昵称
                        //Figureurl_qq_1 //头像
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e(TAG, "登录失败" + uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG, "登录取消");

                    }
                });

                // mtencent.login(LoginActivity.this,"all",new GetUserInfo());


                //getUserInfoInThread();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onError(UiError e) {
            //showResult("onError:", "code:" + e.errorCode + ", msg:"
                   // + e.errorMessage + ", detail:" + e.errorDetail);
            Log.i("---------",e.toString());
        }
        @Override
        public void onCancel() {
           // showResult("onCancel", "");
        }
    }

    public void getUserInfo()
    {
//        Bundle ben = new Bundle();
//        ben.putString("openid",openid);
//        ben.putString("access_token",access_token);

//        mtencent.requestAsync("get_simple_userinfo", null,
//                Constants.HTTP_GET, new BaseApiListener("get_simple_userinfo", false),
//                null);
    }

    public void getUserInfoInThread()
    {
        new Thread(){
            @Override
            public void run() {
                JSONObject json = null;
                try {
                    json = mtencent.request("all", null, Constants.HTTP_GET);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (HttpUtils.NetworkUnavailableException e) {
                    e.printStackTrace();
                } catch (HttpUtils.HttpStatusException e) {
                    e.printStackTrace();
                }

                System.out.println(json);
            }
        }.start();
    }



    class AuthListener implements WeiboAuthListener {
        @Override
        public void onComplete(Bundle values) {
// 从 Bundle 中解析 Token
            Oauth2AccessToken mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            if (mAccessToken.isSessionValid()) {
// 显示 Token
                //updateTokenView(false);
// 保存 Token 到 SharedPreferences
               // AccessTokenKeeper.writeAccessToken(WBAuthActivity.this, mAccessToken);

            } else {
// 当您注册的应用程序签名不正确时，就会收到 Code，请确保签名正确
                String code = values.getString("code", "");

            }
        }
        @Override
        public void onWeiboException(WeiboException e) {

        }

        @Override
        public void onCancel() {

        }

    }

}
