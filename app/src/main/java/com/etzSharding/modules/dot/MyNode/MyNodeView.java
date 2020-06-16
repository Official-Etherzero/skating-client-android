package com.etzSharding.modules.dot.MyNode;

import com.etzSharding.base.BaseView;
import com.etzSharding.bean.MyNodeBean;
import com.etzSharding.bean.MyNodeDataBean;
import com.etzSharding.bean.NodeRevenueDataBean;
import com.etzSharding.bean.UserBean;

public interface MyNodeView extends BaseView {
    void requestFail(int code, String msg);

    void MyNodeData(MyNodeDataBean data);

    void NodeRevenueData(NodeRevenueDataBean data);
}
