package com.wenwu.pm.activity.find.activity.topic;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.wenwu.pm.R;
import com.wenwu.pm.activity.find.activity.topic.HotFragmentAdapter;
import com.wenwu.pm.activity.find.activity.topic.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 热议话题-最热
 */
public class HotFragment extends Fragment {
    private RecyclerView recycler;
    private List<Model> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        init();
        return inflater.inflate(R.layout.fragment_hot, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

         recycler =view.findViewById(R.id.recycler_hot);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recycler.setLayoutManager(layoutManager);
        HotFragmentAdapter adapter = new HotFragmentAdapter(R.layout.home_item,list);
        recycler.setAdapter(adapter);

    }

    public void init() {
        list = new ArrayList<>();
            Model model = new Model();
            model.setLike(1);
            model.setTitle("宠物穿着打扮");
            model.setUserPhoto(R.drawable.dog);
            model.setUserName("理想世界");
            list.add(model);

    }


    public static Toast toast(Context context, String msg) {
        return  Toast.makeText(context, msg, Toast.LENGTH_SHORT);
    }
}
