package com.zhuye.hougong.adapter.find;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zhuye.hougong.R;
import com.zhuye.hougong.adapter.BaseHolder;
import com.zhuye.hougong.adapter.BaseRecycleAdapter;
import com.zhuye.hougong.bean.DongTaiBean;
import com.zhuye.hougong.contants.Contants;
import com.zhuye.hougong.weidgt.RoundedCornerImageView;

import java.util.List;

/**
 * Created by zzzy on 2017/11/22.
 */

public  class FindBaseAdapter extends BaseRecycleAdapter {


    public FindBaseAdapter(Context conn) {
        super(conn);
    }

    public FindBaseAdapter(Context conn, List data) {
        super(conn, data);
    }

    @Override
    protected int getResId() {
        return R.layout.find_zuixin_item;
    }

    @Override
    protected void conver(BaseHolder holder, int position) {

        //头像
        RoundedCornerImageView ivew = ((RoundedCornerImageView)holder.getView(R.id.find_zuixin_touxiang));
        int a = 0;
        String url = ((DongTaiBean.DataBean)data.get(position)).getFace();
        Glide.with(conn).load(Contants.BASE_URL + url).into(ivew);

        //名称和年龄
        ((TextView)holder.getView(R.id.find_zuixin_name)).setText(((DongTaiBean.DataBean)data.get(position)).getNickname()+"");
        ((TextView)holder.getView(R.id.find_zuixin_age)).setText(((DongTaiBean.DataBean)data.get(position)).getAge()+"");

        //城市
        ((TextView)holder.getView(R.id.find_zuixin_dizhi)).setText(((DongTaiBean.DataBean)data.get(position)).getCity());

        //时间
        ((TextView)holder.getView(R.id.find_zuixin_data)).setText(((DongTaiBean.DataBean)data.get(position)).getCreate_time());

        //title
        ((TextView)holder.getView(R.id.find_zuixin_title)).setText(((DongTaiBean.DataBean)data.get(position)).getContent());


        //点赞
        ((TextView)holder.getView(R.id.find_zuixin_dianzan)).setText(((DongTaiBean.DataBean)data.get(position)).getZan()+"");

        //评论
        //((TextView)holder.getView(R.id.find_zuixin_pinglun)).setText(((DongTaiBean.DataBean)data.get(position)).getPing_count()+"");

        //礼物
       // ((TextView)holder.getView(R.id.find_zuixin_liwu)).setText(((DongTaiBean.DataBean)data.get(position)).getGift());

        //自己是否点赞

        //图片的处理
//        if(((DongTaiBean.DataBean)data.get(position)).getPhoto_type()==2){
//            RelativeLayout rl = holder.getView(R.id.con);
//            rl.setVisibility(View.VISIBLE);
//            int conunt = ((DongTaiBean.DataBean)data.get(position)).getPhoto().size();
//            if(conunt==1){
//                ImageView iv = holder.getView(R.id.da);
//                Glide.with(conn).load(Contants.BASE_URL+data.get(0)).into(iv);
//            }else if(conunt >1){
//                LinearLayout ll1 = holder.getView(R.id.ll1);
//                ll1.setVisibility(View.VISIBLE);
//                if(conunt==2){
//                    ImageView iv1 = holder.getView(R.id.dong1);
//                    ImageView iv2 = holder.getView(R.id.dong2);
//                    Glide.with(conn).load(Contants.BASE_URL+data.get(0)).into(iv1);
//                    Glide.with(conn).load(Contants.BASE_URL+data.get(1)).into(iv2);
//                }else if(conunt == 3){
//                    ImageView iv1 = holder.getView(R.id.dong1);
//                    ImageView iv2 = holder.getView(R.id.dong2);
//                    ImageView iv3 = holder.getView(R.id.dong3);
//                    Glide.with(conn).load(Contants.BASE_URL+data.get(0)).into(iv1);
//                    Glide.with(conn).load(Contants.BASE_URL+data.get(1)).into(iv2);
//                    Glide.with(conn).load(Contants.BASE_URL+data.get(2)).into(iv3);
//                }else if(conunt >3){
//                    LinearLayout ll2 = holder.getView(R.id.ll2);
//                    ll2.setVisibility(View.VISIBLE);
//                    if(conunt == 4){
//                        ImageView iv1 = holder.getView(R.id.dong1);
//                        ImageView iv2 = holder.getView(R.id.dong2);
//                        ImageView iv3 = holder.getView(R.id.dong3);
//                        ImageView iv4 = holder.getView(R.id.dong4);
//                        Glide.with(conn).load(Contants.BASE_URL+data.get(0)).into(iv1);
//                        Glide.with(conn).load(Contants.BASE_URL+data.get(1)).into(iv2);
//                        Glide.with(conn).load(Contants.BASE_URL+data.get(2)).into(iv3);
//                        Glide.with(conn).load(Contants.BASE_URL+data.get(3)).into(iv4);
//                    }else if(conunt == 5){
//                        ImageView iv1 = holder.getView(R.id.dong1);
//                        ImageView iv2 = holder.getView(R.id.dong2);
//                        ImageView iv3 = holder.getView(R.id.dong3);
//                        ImageView iv4 = holder.getView(R.id.dong4);
//                        ImageView iv5 = holder.getView(R.id.dong5);
//                        Glide.with(conn).load(Contants.BASE_URL+data.get(0)).into(iv1);
//                        Glide.with(conn).load(Contants.BASE_URL+data.get(1)).into(iv2);
//                        Glide.with(conn).load(Contants.BASE_URL+data.get(2)).into(iv3);
//                        Glide.with(conn).load(Contants.BASE_URL+data.get(3)).into(iv4);
//                        Glide.with(conn).load(Contants.BASE_URL+data.get(4)).into(iv5);
//                    }else if(conunt == 6){
//                        ImageView iv1 = holder.getView(R.id.dong1);
//                        ImageView iv2 = holder.getView(R.id.dong2);
//                        ImageView iv3 = holder.getView(R.id.dong3);
//                        ImageView iv4 = holder.getView(R.id.dong4);
//                        ImageView iv5 = holder.getView(R.id.dong5);
//                        ImageView iv6 = holder.getView(R.id.dong6);
//                        Glide.with(conn).load(Contants.BASE_URL+data.get(0)).into(iv1);
//                        Glide.with(conn).load(Contants.BASE_URL+data.get(1)).into(iv2);
//                        Glide.with(conn).load(Contants.BASE_URL+data.get(2)).into(iv3);
//                        Glide.with(conn).load(Contants.BASE_URL+data.get(3)).into(iv4);
//                        Glide.with(conn).load(Contants.BASE_URL+data.get(4)).into(iv5);
//                        Glide.with(conn).load(Contants.BASE_URL+data.get(5)).into(iv6);
//                    }
//                }
//            }
//        }


//ImageView iv1 = holder.getView(R.id.dong1);
//                    ImageView iv2 = holder.getView(R.id.dong2);
//                    Glide.with(conn).load(Contants.BASE_URL+data.get(1)).into(iv1);
//                    Glide.with(conn).load(Contants.BASE_URL+data.get(2)).into(iv2);
        if(((DongTaiBean.DataBean)data.get(position)).getPhoto_type()==2){
            //有图  给recycleview设置数据
            RecyclerView recyclerView = (RecyclerView) holder.getView(R.id.find_zuixin_tuji);
            if(((DongTaiBean.DataBean)data.get(position)).getPhoto().size()==1){
                Find3Adapter find4Adapter = new Find3Adapter(conn,((DongTaiBean.DataBean)data.get(position)).getPhoto());
                recyclerView.setAdapter(find4Adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(conn));
            }else if(((DongTaiBean.DataBean)data.get(position)).getPhoto().size()>1){
                Find4Adapter find4Adapter = new Find4Adapter(conn,((DongTaiBean.DataBean)data.get(position)).getPhoto());
                recyclerView.setAdapter(find4Adapter);
                recyclerView.setLayoutManager(new GridLayoutManager(conn,3));
            }
        }
    }
}
