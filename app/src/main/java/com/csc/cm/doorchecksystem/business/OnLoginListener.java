package com.csc.cm.doorchecksystem.business;

import com.csc.cm.doorchecksystem.data.model.LoginResult;

/**
 * Created by admin on 2017/2/17.
 */
public interface OnLoginListener {

    void  onSuccess(LoginResult.UserInfo user);

    /**
     * 失败的时候回调
     */
    void  onError(int type);
}
