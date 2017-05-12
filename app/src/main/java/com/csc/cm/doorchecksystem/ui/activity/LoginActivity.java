package com.csc.cm.doorchecksystem.ui.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.csc.cm.doorchecksystem.R;
import com.csc.cm.doorchecksystem.business.Presenter;
import com.csc.cm.doorchecksystem.data.model.LoginResult;
import com.csc.cm.doorchecksystem.data.tool.SpUtil;

/**
 * Created by admin on 2017/1/16.
 */

public class LoginActivity extends Activity implements ILoginView{

    private Context mContext;
    private EditText tvLoginAcct;
    private EditText tvLoginPwd;

    private Presenter presenter;

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_login);

        mContext = this;
        presenter = new Presenter(this);
        dialog = new ProgressDialog(mContext);
        dialog.setCanceledOnTouchOutside(false);

        Button btn_login = (Button) findViewById(R.id.btn_login);
        tvLoginAcct = (EditText) findViewById(R.id.et_login_acct);
        tvLoginPwd = (EditText) findViewById(R.id.et_login_pwd);

        String acct = (String) SpUtil.get(mContext, "acct", "");
        String pwd = (String) SpUtil.get(mContext,"pwd", "");
        if (acct != null) tvLoginAcct.setText(acct);
        if (pwd != null) tvLoginPwd.setText(pwd);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoading();
                presenter.login(tvLoginAcct.getText().toString(), tvLoginPwd.getText().toString());
            }
        });
    }

    @Override
    public void loginSuccess(LoginResult.UserInfo user) {
        String acct = tvLoginAcct.getText().toString();
        String pwd = tvLoginPwd.getText().toString();
        SpUtil.put(mContext,"acct",acct);
        SpUtil.put(mContext,"pwd",pwd);

        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("userInfo", user);
        startActivity(intent);
        finish();
    }

    @Override
    public void loginFail(int error) {
        hideLoading();
        switch (error) {
            case 0:
                Toast.makeText(mContext, "登录失败，请重试！", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(mContext, "帐号或密码错误，请重试！", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    private void showLoading() {
        dialog.setMessage("登录中");
        dialog.show();
    }

    private void hideLoading() {
        dialog.dismiss();
    }
}
