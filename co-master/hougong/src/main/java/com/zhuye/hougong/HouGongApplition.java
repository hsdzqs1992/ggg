package com.zhuye.hougong;

import android.app.Application;
import android.support.multidex.MultiDex;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.zhuye.hougong.model.Modle;

/**
 * Created by zzzy on 2017/11/23.
 */

public class HouGongApplition extends Application {


    static Gson gson;

    @Override
    public void onCreate() {
        super.onCreate();

        gson = new Gson();
        MultiDex.install(this);

        EMOptions options =new EMOptions();
        options.setAcceptInvitationAlways(false);
        options.setAutoAcceptGroupInvitation(false);


        // 是否自动将消息附件上传到环信服务器，默认为True是使用环信服务器上传下载，如果设为 false，需要开发者自己处理附件消息的上传和下载
        options.setAutoTransferMessageAttachments(true);
        // 是否自动下载附件类消息的缩略图等，默认为 true 这里和上边这个参数相关联
        options.setAutoDownloadThumbnail(true);
        EMClient.getInstance().init(this, options);
        EMClient.getInstance().setDebugMode(true);
        Modle.getInstance().init(this);
        Fresco.initialize(this);

    }

    public static Gson getGson(){
        return gson;
    }
}
