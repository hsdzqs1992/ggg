package com.zhuye.hougong.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cjj.MaterialRefreshLayout;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.zhuye.hougong.R;
import com.zhuye.hougong.adapter.home.HomeBaseAdapter;
import com.zhuye.hougong.adapter.home.HomeHuoYueAdapter;
import com.zhuye.hougong.adapter.home.HomeMianFeiAdapter;
import com.zhuye.hougong.adapter.home.HomeTuiJianAdapter;
import com.zhuye.hougong.adapter.home.HomeXinRenAdapter;
import com.zhuye.hougong.bean.HomeBanner;
import com.zhuye.hougong.bean.HomeRecycleBean;
import com.zhuye.hougong.contants.Contants;
import com.zhuye.hougong.http.MyCallback;
import com.zhuye.hougong.utils.CommentUtils;
import com.zhuye.hougong.utils.SpUtils;
import com.zhuye.hougong.view.PersonHomePageActivity;
import com.zhuye.hougong.weidgt.MyGridLayoutManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzzy on 2017/11/21.
 */

public class HomePagerAdapter2 extends PagerAdapter {


    private  Context conn;
    private List<String> titles = new ArrayList<>();
    private List<HomeBaseAdapter> mAdapters = new ArrayList<>();

    HomeBanner homeBanner;

    private View rotview;


    public HomePagerAdapter2(Context con) {
        conn = con;
        titles.add("推荐");
        titles.add("活跃");
        titles.add("新人");
        titles.add("免费");
        mAdapters.add(new HomeTuiJianAdapter(conn));
        mAdapters.add(new HomeHuoYueAdapter(conn));
        mAdapters.add(new HomeXinRenAdapter(conn));
        mAdapters.add(new HomeMianFeiAdapter(conn));

    }

    List<String> url  = new ArrayList();;
    public void setData(HomeBanner homeBanner){
        this.homeBanner = homeBanner;
//        if(((TuiJianFragment)fargments.get(0))!=null){
//            ((TuiJianFragment)fargments.get(0)).setData(homeBanner);
//        }
        if(this.homeBanner!=null) {
            url.add(Contants.BASE_URL + homeBanner.getData().get(0).getImg());
            url.add(Contants.BASE_URL + homeBanner.getData().get(1).getImg());
            url.add(Contants.BASE_URL + homeBanner.getData().get(2).getImg());



//        DefaultSliderView defaultSliderView = new DefaultSliderView(getActivity());
//        defaultSliderView.image("");
//        sliderShow.addSlider(defaultSliderView);

        }

    }


//    public HomePagerAdapter2(FragmentManager fm) {
//        super(fm);
//        titles.add("推荐");
//        titles.add("活跃");
//        titles.add("新人");
//        titles.add("免费");
//        fargments.add(new TuiJianFragment());
//        fargments.add(new HuoYueFragment());
//        fargments.add(new XinRenFragment());
//        fargments.add(new MianFeiFragment());
//    }

//    public void setData(HomeBanner homeBanner){
//        this.homeBanner = homeBanner;
//        if(((TuiJianFragment)fargments.get(0))!=null){
//            ((TuiJianFragment)fargments.get(0)).setData(homeBanner);
//        }
//
//    }


    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }


    protected SliderLayout sliderShow;
    protected MaterialRefreshLayout materialRefreshLayout;

    protected RecyclerView recyclerView;
    protected HomeTuiJianAdapter homeTuiJianAdapter;
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = View.inflate(conn, R.layout.fragment_home_vp, null);
        materialRefreshLayout = view.findViewById(R.id.fragment_home_materrial);
        recyclerView = view.findViewById(R.id.fragment_home_recycle);
        sliderShow= (SliderLayout) view.findViewById(R.id.slider);
        //homeTuiJianAdapter = new HomeTuiJianAdapter(conn,titles);
        HomeBaseAdapter homeBaseAdapter = mAdapters.get(position);
        recyclerView.setAdapter(homeBaseAdapter);
        recyclerView.setLayoutManager(new MyGridLayoutManager(conn,2));
        // initView();
        //paihangThree = view.findViewById(R.id.paihang_three);
        initData();
        container.addView(view);
        initListener();
        return view;
    }

    HomeRecycleBean homeTuijianData;
    HomeRecycleBean homeHuoYueData;
    HomeRecycleBean homeXinRenData;
    HomeRecycleBean homeMianFeiData;

    public void sethomeTuijianData(HomeRecycleBean homeRecycleBean){
        homeTuijianData = homeRecycleBean;
        //int love = ((HomeRecycleBean.DataBean) homeTuijianData.getData().get(position)).getLove();

        Log.i("iii",homeRecycleBean.getData().toString());
        mAdapters.get(0).addData(homeRecycleBean.getData());
    }
    public void sethomeHuoYueData(HomeRecycleBean homeRecycleBean){
        homeHuoYueData =homeRecycleBean;
        mAdapters.get(1).addData(homeRecycleBean.getData());
    }
    public void sethomeXinRenData(HomeRecycleBean homeRecycleBean){
        homeXinRenData =homeRecycleBean;
        mAdapters.get(2).addData(homeRecycleBean.getData());
    }
    public void sethomeMianFeiData(HomeRecycleBean homeRecycleBean){
        homeMianFeiData =homeRecycleBean;
        mAdapters.get(3).addData(homeRecycleBean.getData());
    }

    public void initData() {
        if(url != null && url.size() > 0){
            for (int i = 0 ; i<url.size();i++){
                DefaultSliderView defaultSliderView = new DefaultSliderView(conn);
                defaultSliderView.image(url.get(i));
                if(sliderShow!=null)
                    sliderShow.addSlider(defaultSliderView);
            }
        }

    }



    private void initListener() {



        mAdapters.get(0).setOnItemClickListener(new BaseHolder.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                initTuiJianAdapter(view,position);

               // conn.startActivity(new Intent(conn, PersonHomePageActivity.class));
            }
        });
        mAdapters.get(1).setOnItemClickListener(new BaseHolder.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {

                initHuoYueAdapter(view,position);
            }
        });
        mAdapters.get(2).setOnItemClickListener(new BaseHolder.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                conn.startActivity(new Intent(conn, PersonHomePageActivity.class));
            }
        });
        mAdapters.get(3).setOnItemClickListener(new BaseHolder.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, int position) {
                conn.startActivity(new Intent(conn, PersonHomePageActivity.class));
            }
        });
    }

    private void initHuoYueAdapter(View view, final int position) {
        view.findViewById(R.id.home_tuijian_item_touxiang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(conn, PersonHomePageActivity.class);
                intent.putExtra("uid",((HomeRecycleBean.DataBean) homeHuoYueData.getData().get(position)).getUid());
                conn.startActivity(intent);
            }
        });

        view.findViewById(R.id.home_tuijian_item_zhuboguanzhu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                int love = ((HomeRecycleBean.DataBean) homeHuoYueData.getData().get(position)).getLove();
                if(love == 0){
                    //未关注
                    isGuanzhu = false;
                    OkGo.<String>post(Contants.love)
                            .params("token", SpUtils.getString(conn,"token",""))
                            .params("uid",((HomeRecycleBean.DataBean) homeHuoYueData.getData().get(position)).getUid())
                            .execute(new MyCallback() {
                                         @Override
                                         protected void doFailue() {
                                             CommentUtils.toast(conn,"shibaile");
                                         }

                                         @Override
                                         protected void excuess(Response<String> response) {
                                             CommentUtils.toast(conn,"chenggongle");
                                             ((ImageView)view.findViewById(R.id.home_tuijian_item_zhuboguanzhu)).setImageResource(R.drawable.attention_on);
                                         }
                                     }
                            );
                }else if(love ==1){
                    //已关注

                    OkGo.<String>post(Contants.del_mylove)
                            .params("token", SpUtils.getString(conn,"token",""))
                            .params("uid",((HomeRecycleBean.DataBean) homeHuoYueData.getData().get(position)).getUid())
                            .execute(new MyCallback() {
                                         @Override
                                         protected void doFailue() {
                                             CommentUtils.toast(conn,"shibaile");
                                         }

                                         @Override
                                         protected void excuess(Response<String> response) {
                                             CommentUtils.toast(conn,"chenggongle");
                                             ((ImageView)view.findViewById(R.id.home_tuijian_item_zhuboguanzhu)).setImageResource(R.drawable.attention_off);
                                         }
                                     }
                            );
                    isGuanzhu = true;
                }
            }
        });


    }

    Boolean isGuanzhu = false;

    private void initTuiJianAdapter(View view, final int position) {

        //进入评论
        view.findViewById(R.id.home_tuijian_item_touxiang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(conn, PersonHomePageActivity.class);
                intent.putExtra("uid",((HomeRecycleBean.DataBean) homeTuijianData.getData().get(position)).getUid());
                conn.startActivity(intent);
            }
        });

        view.findViewById(R.id.home_tuijian_item_zhuboguanzhu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                int love = ((HomeRecycleBean.DataBean) homeTuijianData.getData().get(position)).getLove();
                if(love == 0){
                    //未关注
                    isGuanzhu = false;
                    OkGo.<String>post(Contants.love)
                            .params("token", SpUtils.getString(conn,"token",""))
                            .params("uid",((HomeRecycleBean.DataBean) homeTuijianData.getData().get(position)).getUid())
                            .execute(new MyCallback() {
                                         @Override
                                         protected void doFailue() {
                                             CommentUtils.toast(conn,"shibaile");
                                         }

                                         @Override
                                         protected void excuess(Response<String> response) {
                                             CommentUtils.toast(conn,"chenggongle");
                                             ((ImageView)view.findViewById(R.id.home_tuijian_item_zhuboguanzhu)).setImageResource(R.drawable.attention_on);
                                         }
                                     }
                            );
                }else if(love ==1){
                    //已关注

                    OkGo.<String>post(Contants.del_mylove)
                            .params("token", SpUtils.getString(conn,"token",""))
                            .params("uid",((HomeRecycleBean.DataBean) homeTuijianData.getData().get(position)).getUid())
                            .execute(new MyCallback() {
                                         @Override
                                         protected void doFailue() {
                                             CommentUtils.toast(conn,"shibaile");
                                         }

                                         @Override
                                         protected void excuess(Response<String> response) {
                                             CommentUtils.toast(conn,"chenggongle");
                                             ((ImageView)view.findViewById(R.id.home_tuijian_item_zhuboguanzhu)).setImageResource(R.drawable.attention_off);
                                         }
                                     }
                            );
                    isGuanzhu = true;
                }
            }
        });
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
