package com.etzSharding.modules.user.VerifiedName;

import com.etzSharding.base.BaseView;
import com.etzSharding.bean.AuthenticationBean;
import com.etzSharding.bean.UserBean;

public interface VerifiedView extends BaseView {

    void requestFail(int code,String msg);

    void setVerifyToken(AuthenticationBean user);

    void setVerifyResult(AuthenticationBean user);
}
