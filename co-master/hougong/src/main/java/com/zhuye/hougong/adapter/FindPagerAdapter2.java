package com.zhuye.hougong.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.cjj.MaterialRefreshLayout;
import com.zhuye.hougong.R;
import com.zhuye.hougong.adapter.find.FindBaseAdapter;
import com.zhuye.hougong.adapter.find.FindGuanZhuAdapter;
import com.zhuye.hougong.adapter.find.FindTongChengAdapter;
import com.zhuye.hougong.adapter.find.FindZuiXinAdapter;
import com.zhuye.hougong.bean.DongTaiBean;
import com.zhuye.hougong.utils.DensityUtil;
import com.zhuye.hougong.view.PingLunActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzzy on 2017/11/21.
 */

public class FindPagerAdapter2 extends PagerAdapter {


    private  Context conn;
    private List<String> titles = new ArrayList<>();
    List<FindBaseAdapter> mAdapters = new ArrayList<>(3);


    public FindPagerAdapter2(Context con) {
        conn = con;
        titles.add("最新");
        titles.add("关注");
        titles.add("同城");
        mAdapters.add(new FindZuiXinAdapter(conn));
        mAdapters.add(new FindGuanZhuAdapter(conn));
        mAdapters.add(new FindTongChengAdapter(conn));

//        fargments.add(new ZuiXinFragment());
//        fargments.add(new GuanZhuFragment());
//        fargments.add(new TongChengFragment());
    }

    DongTaiBean findGuanZhuData;
    public void setFindGuanZhuData(DongTaiBean homeRecycleBean){
        findGuanZhuData = homeRecycleBean;
        //int love = ((HomeRecycleBean.DataBean) homeTuijianData.getData().get(position)).getLove();
        mAdapters.get(1).addData(homeRecycleBean.getData());
    }

    DongTaiBean findZhuiXinData;
    public void setFindZhuiXinData(DongTaiBean homeRecycleBean){
        findZhuiXinData = homeRecycleBean;
        //int love = ((HomeRecycleBean.DataBean) homeTuijianData.getData().get(position)).getLove();
        mAdapters.get(0).addData(homeRecycleBean.getData());
    }

    DongTaiBean findTongChengData;
    public void setFindTongChengData(DongTaiBean homeRecycleBean){
        findTongChengData = homeRecycleBean;
        //int love = ((HomeRecycleBean.DataBean) homeTuijianData.getData().get(position)).getLove();
        mAdapters.get(2).addData(homeRecycleBean.getData());
    }



//    public FindPagerAdapter2(FragmentManager fm) {
//        super(fm);
//        titles.add("最新");
//        titles.add("关注");
//        titles.add("同城");
//        fargments.add(new ZuiXinFragment());
//        fargments.add(new GuanZhuFragment());
//        fargments.add(new TongChengFragment());
//    }


    @Override
    public int getCount() {
        return titles.size();
    }

    MaterialRefreshLayout materialRefreshLayout;
    RecyclerView recyclerView;
    FindZuiXinAdapter messageXiaoXi;

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }



        @Override
    public Object instantiateItem(ViewGroup container, int position) {
            View view = View.inflate(conn, R.layout.common_recycle, null);
            materialRefreshLayout = view.findViewById(R.id.common_material);
            recyclerView = view.findViewById(R.id.commot_recycle);
            //BaseFindFragment messageXiaoXi = (BaseFindFragment) fargments.get(position);
            //messageXiaoXi = new FindZuiXinAdapter(conn,titles);
            FindBaseAdapter findBaseAdapter = mAdapters.get(position);
            recyclerView.setAdapter(findBaseAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(conn));
            // initView();
            //paihangThree = view.findViewById(R.id.paihang_three);
            container.addView(view);
            initListener();
            return view;
    }

    private void initListener() {

        mAdapters.get(0).setOnItemClickListener(new BaseHolder.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, final int position) {
               // CommentUtils.toast(conn,"点击最新了");
                view.findViewById(R.id.find_zuixin_pinglun).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(conn, PingLunActivity.class);
                        intent.putExtra("dynamic_id",findZhuiXinData.getData().get(position).getDynamic_id());
                        conn.startActivity(intent);
                    }
                });




                view.findViewById(R.id.find_zuixin_dianzan).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //点赞



                    }
                });

                view.findViewById(R.id.find_zuixin_liwu).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //conn.startActivity(new Intent(conn, PingLunActivity.class));
                        View vie = View.inflate(conn,R.layout.bottom_window,null);
                        final PopupWindow popupWindow = new PopupWindow(conn);
                        popupWindow.setContentView(vie);
                        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                        popupWindow.setHeight(DensityUtil.dip2px(conn,349f));
                        popupWindow.showAtLocation(vie, Gravity.BOTTOM, 0, 0);
                        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
                        popupWindow.setOutsideTouchable(true);
                       // popupWindow.setFocusable(true);
                        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                            @Override
                            public boolean onTouch(View v, MotionEvent event) {
                                if (popupWindow.isShowing()) {
                                    popupWindow.dismiss();
                                    return true;
                                }
                                return false;
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
