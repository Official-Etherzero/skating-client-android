package com.etzSharding.modules.dot.Team;

import com.etzSharding.base.BaseView;
import com.etzSharding.bean.NodeBean;
import com.etzSharding.bean.NodeRevenueBean;
import com.etzSharding.bean.TeamNodeDataBean;

import java.util.List;

public interface TeamView extends BaseView {
    void requestFail(int code, String msg);

    void setTeamNodeData(TeamNodeDataBean data);

    void setTeamRewardList(List<NodeRevenueBean> list);
}
