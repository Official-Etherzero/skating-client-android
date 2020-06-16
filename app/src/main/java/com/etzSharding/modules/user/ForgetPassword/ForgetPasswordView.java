package com.etzSharding.modules.user.ForgetPassword;

import com.etzSharding.base.BaseView;
import com.etzSharding.bean.DailyEarningsListBean;
import com.etzSharding.bean.UserBean;

public interface ForgetPasswordView extends BaseView {
    void requestFail(String msg);

    void codeSuccess(int code);

    void verificationSuccess(int code);

    void resetSuccess(int code);


}
