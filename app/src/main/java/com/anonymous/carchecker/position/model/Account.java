package com.anonymous.carchecker.position.model;

import com.anonymous.carchecker.common.model.DataModel;

/**
 * Created by Huy Hieu on 1/14/2017.
 */

public class Account extends DataModel {
    public String mUserName;
    public String mPassword;

    public Account(String mUserName, String password) {
        this.mUserName = mUserName;
        this.mPassword = password;
    }
}
