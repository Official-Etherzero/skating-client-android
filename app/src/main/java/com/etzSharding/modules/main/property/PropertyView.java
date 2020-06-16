package com.etzSharding.modules.main.property;

import com.etzSharding.base.BaseView;
import com.etzSharding.bean.MyNodeDataBean;
import com.etzSharding.bean.TeamNodeDataBean;
import com.etzSharding.bean.TixanBean;
import com.etzSharding.bean.UserBean;

public interface PropertyView extends BaseView {

    void requestFail(int code, String msg);

    void userInfo(UserBean user);

    void withdrawSuccess(TixanBean bean);

    void signinFail(int code, String msg);
    void signinSuccess(String etz);

    void MyNodeData(MyNodeDataBean data);

    void setTeamNodeData(TeamNodeDataBean data);
}
