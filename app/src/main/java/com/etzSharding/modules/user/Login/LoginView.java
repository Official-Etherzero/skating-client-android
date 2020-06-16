package com.etzSharding.modules.user.Login;

import com.etzSharding.base.BaseView;
import com.etzSharding.bean.DailyEarningsListBean;
import com.etzSharding.bean.UserBean;

public interface LoginView extends BaseView {

    void requestFail(String msg);

    void login(UserBean user);
}
