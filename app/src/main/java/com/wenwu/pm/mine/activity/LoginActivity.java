package com.wenwu.pm.mine.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.wenwu.pm.MainActivity;
import com.wenwu.pm.R;
import com.wenwu.pm.goson.Msg;
import com.wenwu.pm.modle.User;
import com.wenwu.pm.modle.UserImpl;
import com.wenwu.pm.utils.OkHttpUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private Button register;
    private Button login;
    private Button mobileToLogin;
    private Button forgetPassword;

    private EditText phoneInput;
    private EditText passwordInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_login);
        phoneInput = findViewById(R.id.loginPhone);
        passwordInput = findViewById(R.id.loginPassword);

        login = findViewById(R.id.loginButton);
        login.setOnClickListener(this);

        register = findViewById(R.id.register);
        register.setOnClickListener(this);

        mobileToLogin = findViewById(R.id.mobileLogin);
        mobileToLogin.setOnClickListener(this);

        forgetPassword = findViewById(R.id.forgotPassword);
        forgetPassword.setOnClickListener(this);
        }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.loginButton:
                String account = phoneInput.getText().toString();
                String password = passwordInput.getText().toString();
                loginWithOkHttp("login",account,password);
                break;

            case R.id.register:
            case R.id.mobileLogin:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;

            case R.id.forgotPassword:
                startActivity(new Intent(LoginActivity.this,ForgetActivity.class));
                break;
        }

    }

    /*实现登录*/
    public void loginWithOkHttp(String mapping, String account, String password) {
        OkHttpUtil.loginWithOkHttp(mapping, account, password, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //在这里对异常情况进行处理

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //得到服务器返回的具体内容

                final String responseData = response.body().string();
                System.out.println("===="+responseData);
                Msg msg = new Gson().fromJson(responseData, Msg.class);
                if (msg.getCode().equals("200")) {
                    Looper.prepare();
                    Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    Looper.loop();
                }else {
                    Looper.prepare();
                    Toast.makeText(LoginActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }
        });
    }

}
