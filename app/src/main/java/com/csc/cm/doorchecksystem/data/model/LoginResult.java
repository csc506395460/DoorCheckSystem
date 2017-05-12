package com.csc.cm.doorchecksystem.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/2/7.
 */
public class LoginResult {
    public String massage;
    public String status;
    public UserInfo user;

    public class UserInfo implements Serializable{
        public int id;
        public String userCode;
        public String userName;

    }
}
