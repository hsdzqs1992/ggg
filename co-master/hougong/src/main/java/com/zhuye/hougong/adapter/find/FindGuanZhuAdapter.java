package com.zhuye.hougong.adapter.find;

import android.content.Context;

import com.zhuye.hougong.adapter.BaseHolder;

import java.util.List;

/**
 * Created by zzzy on 2017/11/22.
 */

public class FindGuanZhuAdapter extends FindBaseAdapter {


    public FindGuanZhuAdapter(Context conn, List data) {
        super(conn, data);
    }

    public FindGuanZhuAdapter(Context conn) {
        super(conn);
    }

    @Override
    protected void conver(BaseHolder holder, int position) {
        super.conver(holder,position);

//        //图片的处理
//        if(((DongTaiBean.DataBean)data.get(position)).getPhoto_type()==2){
//            //有图  给recycleview设置数据
//            RecyclerView recyclerView = (RecyclerView) holder.getView(R.id.find_zuixin_tuji);
//
//            if(((DongTaiBean.DataBean)data.get(position)).getPhoto().size()==1){
//                Find3Adapter find4Adapter = new Find3Adapter(conn,((DongTaiBean.DataBean)data.get(position)).getPhoto());
//                recyclerView.setAdapter(find4Adapter);
//                recyclerView.setLayoutManager(new LinearLayoutManager(conn));
//            }else if(((DongTaiBean.DataBean)data.get(position)).getPhoto().size()>1){
//                Find4Adapter find4Adapter = new Find4Adapter(conn,((DongTaiBean.DataBean)data.get(position)).getPhoto());
//                recyclerView.setAdapter(find4Adapter);
//                recyclerView.setLayoutManager(new GridLayoutManager(conn,3));
//            }
//        }
    }
}
