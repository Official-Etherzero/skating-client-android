package com.etzSharding.modules.user.rigest;

import com.etzSharding.base.BaseView;
import com.etzSharding.bean.UserBean;

public interface RegisterView extends BaseView {

    void requestFail(String msg);

    void codeSuccess(int code);

    void RegisterSuccess(UserBean user);


}
