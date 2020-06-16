package com.etzSharding.modules.dot.Node;

import com.etzSharding.base.BaseView;
import com.etzSharding.bean.NodeBean;

import java.util.List;

public interface NodeView extends BaseView {
    void requestFail(int code,String msg);

    void NodeData(List<NodeBean> datas);

    void buyNode(int code);

    void signinSuccess(String etz);
    void signinFail(int code, String msg);
}
