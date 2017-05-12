package com.csc.cm.doorchecksystem.data.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 2017/2/7.
 */
public abstract class BaseApi {
    private static final String baseUrl = "http://202.193.96.183:8080/kqapp/app/";
    private static Retrofit retrofit = null;
    private static IRetrofitServer iServer;
    private static final long DEFAULT_TIMEOUT = 300;

    public static IRetrofitServer getInstance() {
        if (retrofit == null) {
            synchronized (BaseApi.class) {
                if (retrofit == null) {

                    OkHttpClient client = new OkHttpClient.Builder()
                            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                            .build();

                    retrofit = new Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(client)
                            .build();
                    iServer = retrofit.create(IRetrofitServer.class);
                }
            }
        }
        return iServer;
    }
}