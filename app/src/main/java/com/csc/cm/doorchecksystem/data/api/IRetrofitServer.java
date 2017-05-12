package com.csc.cm.doorchecksystem.data.api;

import com.csc.cm.doorchecksystem.data.model.CheckResult;
import com.csc.cm.doorchecksystem.data.model.LoginResult;
import com.csc.cm.doorchecksystem.data.model.StudentResult;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by admin on 2017/2/7.
 */
public interface IRetrofitServer {

    /**
     * 登录请求
     * @param userName
     * @param password
     * @return
     */
    @FormUrlEncoded
    @POST("appLogin")
    Call<LoginResult> postLogin(@Field("userName") String userName, @Field("password") String password);

    /**
     * 查询考勤情况
     * @param userId
     * @param date
     * @param type
     * @param startId
     * @param count
     * @return
     */
    @FormUrlEncoded
    @POST("getSignData")
    Call<CheckResult> postCheck(@Field("userId") int userId, @Field("date") String date, @Field("type") int type,
                                @Field("startId") int startId, @Field("count") int count);

    /**
     * 查询学生信息详情
     * @param empId
     * @return
     */
    @FormUrlEncoded
    @POST("getEmpInfo")
    Call<StudentResult> postStudent(@Field("empId") int empId);


    @GET("")
    Call<LoginResult> get(@Query("key") String key, @Query("sort") String sort, @Query("time") String time);


    @GET("")
    Call<LoginResult> get();
}
