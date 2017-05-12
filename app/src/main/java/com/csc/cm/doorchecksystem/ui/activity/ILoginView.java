package com.csc.cm.doorchecksystem.ui.activity;

import com.csc.cm.doorchecksystem.data.model.LoginResult;

/**
 * Created by admin on 2017/2/17.
 */
public interface ILoginView {

    void loginSuccess(LoginResult.UserInfo user);

    void loginFail(int error);

}
