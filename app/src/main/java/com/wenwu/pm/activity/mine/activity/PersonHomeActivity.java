package com.wenwu.pm.activity.mine.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.wenwu.pm.R;
import com.wenwu.pm.activity.message.activity.MsgCollectPraiseActivity;
import com.wenwu.pm.activity.mine.adapter.MyPagerAdapter;
import com.wenwu.pm.activity.mine.fragment.MyCollectFragment;
import com.wenwu.pm.activity.mine.fragment.MyLogFragment;
import com.wenwu.pm.activity.mine.fragment.MyQuestionFragment;
import com.wenwu.pm.activity.mine.fragment.MyReviewFragment;
import com.wenwu.pm.activity.mine.fragment.PersonLogFragment;
import com.wenwu.pm.activity.mine.fragment.PersonQuestionFragment;
import com.wenwu.pm.activity.mine.fragment.PersonReviewFragment;
import com.wenwu.pm.goson.LoginReturnJson;
import com.wenwu.pm.utils.JsonUtil;
import com.wenwu.pm.utils.OkHttpUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class PersonHomeActivity extends AppCompatActivity implements View.OnClickListener {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private List<Fragment> fragmentList;

    private TextView follow;
    private TextView fans;
    private TextView collect;


    private TextView userName;
    private CircleImageView userPhoto;
    private TextView concernCount;
    private TextView fansCount;
    private TextView collectCount;
    private ImageView gender;
    private TextView city;
    private TextView pet;
    private TextView profile;
    private Toolbar toolbar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_show);
        initView();
    }

    private void initView() {
        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), initViewPager());
        viewPager = findViewById(R.id.view_pager_person);
        viewPager.setAdapter(myPagerAdapter);
        tabLayout = findViewById(R.id.tab_layout_person);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);

        toolbar = findViewById(R.id.person_home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userPhoto = findViewById(R.id.person_user_photo);

        gender = findViewById(R.id.person_sex);

        city = findViewById(R.id.person_city);

        pet = findViewById(R.id.person_pet);

        profile = findViewById(R.id.person_profile);

        show();

        concernCount = findViewById(R.id.person_concern_count);
        collectCount = findViewById(R.id.person_fans_count);
        fansCount = findViewById(R.id.person_collect_count);


        follow = findViewById(R.id.person_concern);
        follow.setOnClickListener(this);

        fans = findViewById(R.id.person_fans);
        fans.setOnClickListener(this);

        collect = findViewById(R.id.person_collect);
        collect.setOnClickListener(this);

    }

    public void show() {
        int id = getIntent().getIntExtra("id", 1);

        PersonLogFragment.getMyLogData(id);
        PersonQuestionFragment.getMyQuestionData(id);
        PersonReviewFragment.getMyCommentData(id);

        Map<String, Object> map = new HashMap<>();
        map.put("user_id", id);
        OkHttpUtil.sendPostRequest("user/get", map, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {}

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                LoginReturnJson json = new Gson().fromJson(data, LoginReturnJson.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        while (json==null);
                        PersonLogFragment.userName = json.getData().getUserName();
                        PersonLogFragment.photo = json.getData().getPhoto();

                        toolbar.setTitle(json.getData().getUserName());
                        Glide.with(PersonHomeActivity.this).load(json.getData().getPhoto()).into(userPhoto);
                        if (json.getData().getGender().equals("男")) {
                            gender.setImageResource(R.drawable.sex_boy_p);
                        } else {
                            gender.setImageResource(R.drawable.sex_girl_p);
                        }
                        city.setText(json.getData().getCity());
                        pet.setText(json.getData().getPet());
                        pet.setText(json.getData().getProfile());
                    }
                });
            }
        });

    }

    public List<Fragment> initViewPager() {
        fragmentList = new ArrayList<>();
        fragmentList.add(new PersonLogFragment());
        fragmentList.add(new PersonQuestionFragment());
        fragmentList.add(new PersonReviewFragment());
        return fragmentList;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.person_concern:
                Toast.makeText(v.getContext(), "关注", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(v.getContext(), FollowActivity.class));
                break;

            case R.id.person_fans:
                Toast.makeText(v.getContext(), "粉丝", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(v.getContext(), FansActivity.class));
                break;

            case R.id.person_collect:
                Toast.makeText(v.getContext(), "收藏", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(v.getContext(), MsgCollectPraiseActivity.class));
                break;
        }
    }

}
