package com.zhuye.hougong.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderLayout;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zhuye.hougong.R;
import com.zhuye.hougong.bean.PersonDetailBean;
import com.zhuye.hougong.contants.Contants;
import com.zhuye.hougong.http.MyCallback;
import com.zhuye.hougong.utils.SpUtils;
import com.zhuye.hougong.weidgt.RoundedCornerImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonHomePageActivity extends AppCompatActivity {


    @BindView(R.id.person_home_back)
    ImageView personHomeBack;
    @BindView(R.id.person_home_touxiang)
    RoundedCornerImageView personHomeTouxiang;
    @BindView(R.id.person_home_name)
    TextView personHomeName;
    @BindView(R.id.person_home_id)
    TextView personHomeId;
    @BindView(R.id.person_home_vip)
    TextView personHomeVip;
    @BindView(R.id.person_home_persondetail)
    ImageView personHomePersondetail;
    @BindView(R.id.person_home_slider)
    SliderLayout personHomeSlider;
    @BindView(R.id.person_home_age)
    TextView personHomeAge;
    @BindView(R.id.person_home_dizhi)
    TextView personHomeDizhi;
    @BindView(R.id.person_home_jietong)
    TextView personHomeJietong;
    @BindView(R.id.person_home_jiage_liaotia)
    TextView personHomeJiageLiaotia;
    @BindView(R.id.person_home_jiage_video)
    TextView personHomeJiageVideo;
    @BindView(R.id.person_home_meishi)
    TextView personHomeMeishi;
    @BindView(R.id.person_home_yinyue)
    TextView personHomeYinyue;
    @BindView(R.id.person_home_jiaoyou)
    TextView personHomeJiaoyou;
    @BindView(R.id.person_home_qingting)
    TextView personHomeQingting;
    @BindView(R.id.person_home_dongtai_shumu)
    TextView personHomeDongtaiShumu;
    @BindView(R.id.person_home_dongtai_tou)
    ImageView personHomeDongtaiTou;
    @BindView(R.id.person_home_dongtai_title)
    TextView personHomeDongtaiTitle;
    @BindView(R.id.person_home_gengxin_time)
    TextView personHomeGengxinTime;
    @BindView(R.id.person_home_dontai_detail)
    LinearLayout personHomeDontaiDetail;
    @BindView(R.id.person_home_liwu_shu)
    TextView personHomeLiwuShu;
    @BindView(R.id.person_home_liwu_zhong1)
    ImageView personHomeLiwuZhong1;
    @BindView(R.id.person_home_liwu_zhong2)
    ImageView personHomeLiwuZhong2;
    @BindView(R.id.person_home_liwu_zhong3)
    ImageView personHomeLiwuZhong3;
    @BindView(R.id.person_home_liwu_zhong4)
    ImageView personHomeLiwuZhong4;
    @BindView(R.id.person_home_liwu_zhong5)
    ImageView personHomeLiwuZhong5;
    @BindView(R.id.person_home_video)
    Button personHomeVideo;
    @BindView(R.id.person_home_audio)
    Button personHomeAudio;
    @BindView(R.id.person_guanzhu)
    ImageView personGuanzhu;
    @BindView(R.id.person_dasang)
    ImageView personDasang;
    @BindView(R.id.person_liaotian)
    ImageView personLiaotian;
    String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_home_page);
        ButterKnife.bind(this);

        //传入uid
        uid = getIntent().getStringExtra("uid");
        initData();
    }

    PersonDetailBean person;
    private void initData() {
        OkGo.<String>post(Contants.communication)
                .params("uid",uid)
                .params("token", SpUtils.getString(PersonHomePageActivity.this,"token",""))
                .execute(new MyCallback() {
                    @Override
                    protected void doFailue() {

                    }

                    @Override
                    protected void excuess(Response<String> response) {
                        String i = response.body();
                        Gson gson = new Gson();
                        person= gson.fromJson(response.body(),PersonDetailBean.class);
                        Log.i("-----",i);
                        Glide.with(PersonHomePageActivity.this).load(Contants.BASE_URL+person.getData().getFace()).into(personHomeTouxiang);
                        personHomeName.setText(person.getData().getNickname());
                        personHomeId.setText(person.getData().getUid());
                        personHomeAge.setText(person.getData().getAge());
                        personHomeDizhi.setText(person.getData().getCity());

                    }
                });
//        OkGo.<String>post(Contants.personcenter)
//                .params("uid",uid)
//                .params("token", SpUtils.getString(PersonHomePageActivity.this,"token",""))
//                .execute(new MyCallback() {
//                    @Override
//                    protected void doFailue() {
//
//                    }
//
//                    @Override
//                    protected void excuess(Response<String> response) {
//                        String i = response.body();
//                        Gson gson = new Gson();
//                        person= gson.fromJson(response.body(),PersonDetailBean.class);
//                        Log.i("-----",i);
//                        Glide.with(PersonHomePageActivity.this).load(Contants.BASE_URL+person.getData().getFace()).into(personHomeTouxiang);
//                        personHomeName.setText(person.getData().getNickname());
//                        personHomeId.setText(person.getData().getUid());
//                        personHomeAge.setText(person.getData().getAge());
//                        personHomeDizhi.setText(person.getData().getCity());
//
//                    }
//                });
    }


    @OnClick({R.id.person_home_back, R.id.person_home_persondetail, R.id.person_home_dontai_detail, R.id.person_home_video, R.id.person_home_audio, R.id.person_guanzhu, R.id.person_dasang, R.id.person_liaotian})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.person_home_back:
                finish();
                break;
            case R.id.person_home_persondetail:
                if(person != null){
                    Intent intent = new Intent(PersonHomePageActivity.this,OtherPersonDetailActivity.class);
                    intent.putExtra("personinfo",person.getData().getUid());
                    startActivity(intent);
                }

                break;
            case R.id.person_home_dontai_detail:

                break;
            case R.id.person_home_video:

                break;
            case R.id.person_home_audio:

                break;
            case R.id.person_guanzhu:

                break;
            case R.id.person_dasang:

                break;
            case R.id.person_liaotian:

                break;
        }
    }
}
