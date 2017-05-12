package com.csc.cm.doorchecksystem.business;

import com.csc.cm.doorchecksystem.data.model.LoginResult;
import com.csc.cm.doorchecksystem.ui.activity.ILoginView;

/**
 * Created by admin on 2017/1/20.
 */
public class Presenter implements OnLoginListener{

    private ILoginView mLoginView;

    public Presenter(ILoginView loginView) {
        mLoginView = loginView;
    }

    public void login(String name, String pwd) {
        Controller.getInstance().Login(name, pwd, this);
    }

    @Override
    public void onSuccess(LoginResult.UserInfo user) {
        mLoginView.loginSuccess(user);
    }

    @Override
    public void onError(int type) {
        mLoginView.loginFail(0);
    }
}
