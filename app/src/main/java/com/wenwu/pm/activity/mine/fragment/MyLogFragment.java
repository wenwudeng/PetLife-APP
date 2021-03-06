package com.wenwu.pm.activity.mine.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.wenwu.pm.R;
import com.wenwu.pm.activity.home.bean.CardViewItemBean;
import com.wenwu.pm.activity.mine.adapter.LogRecyclerAdapter;
import com.wenwu.pm.goson.MyLogJson;
import com.wenwu.pm.utils.JsonUtil;
import com.wenwu.pm.utils.OkHttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * @author:wenwudeng
 * @date:1:20 PM 3/12/2020
 */
public class MyLogFragment extends Fragment {

    private List<CardViewItemBean> cardViewItemBeanList = new ArrayList<>();
    private SwipeRefreshLayout swipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.my_log,container,false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
       getMyLogData(JsonUtil.loginJson.getData().getId());
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_my_log);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        LogRecyclerAdapter adapter = new LogRecyclerAdapter(cardViewItemBeanList,this);
        recyclerView.setAdapter(adapter);
        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_my_log);
        swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#FF5a60"));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(getActivity(),"刷新完成", Toast.LENGTH_SHORT).show();
                    }
                },200);
            }
        });
    }



    /*获取我的模块的日志数据*/
    public void getMyLogData(int userId) {
        Map<String, Object> param = new HashMap<>();
        param.put("userid", userId);
        OkHttpUtil.sendPostRequest("article/getAllArticle", param, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String returnData = response.body().string();
                MyLogJson json = new Gson().fromJson(returnData, MyLogJson.class);
                List<MyLogJson.Data> dataList = json.getData();
                for (MyLogJson.Data data : dataList) {
                    CardViewItemBean cardViewItemBean = new CardViewItemBean(data.getUserid(),data.getId(),data.getTitle(),
                            data.getImg(),data.getContent(),JsonUtil.loginJson.getData().getUserName(),JsonUtil.loginJson.getData().getPhoto(),data.getLike());
                    cardViewItemBeanList.add(cardViewItemBean);
                }
            }
        });
    }
}
