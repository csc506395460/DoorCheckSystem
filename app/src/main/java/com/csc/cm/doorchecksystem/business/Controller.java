package com.csc.cm.doorchecksystem.business;

import com.csc.cm.doorchecksystem.data.api.BaseApi;
import com.csc.cm.doorchecksystem.data.model.LoginResult;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by admin on 2017/2/17.
 */
public class Controller {

    //    //静态内部类单例
    private static class ControllerHolder {
        private static final Controller INSTANCE = new Controller();
    }

    private Controller() {}

    public static final Controller getInstance() {
        return ControllerHolder.INSTANCE;
    }

    public void Login(String name, String pwd, final OnLoginListener onLoginListener) {

        Call<LoginResult> call = BaseApi.getInstance().postLogin(name, pwd);

        call.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                LoginResult loginResult = response.body();
                if (loginResult == null) {
                    onLoginListener.onError(1);
                    return;
                }
                if (loginResult.status.equals("SUCCESS")) {
                    onLoginListener.onSuccess(loginResult.user);
                } else {
                    onLoginListener.onError(1);
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {
                onLoginListener.onError(0);
            }
        });
    }
}
