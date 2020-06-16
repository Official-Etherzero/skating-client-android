package com.etzSharding.data;


import com.etzSharding.app.UrlFactory;
import com.etzSharding.bean.AuthenticationBean;
import com.etzSharding.bean.BaseEtzBean;
import com.etzSharding.bean.MyNodeDataBean;
import com.etzSharding.bean.NodeBean;
import com.etzSharding.bean.NodeRevenueBean;
import com.etzSharding.bean.NodeRevenueDataBean;
import com.etzSharding.bean.TeamNodeDataBean;
import com.etzSharding.bean.TixanBean;
import com.etzSharding.bean.UserBean;
import com.etzSharding.http.HttpRequets;
import com.etzSharding.http.callback.JsonCallback;
import com.etzSharding.utils.MyLog;
import com.lzy.okgo.model.Response;

import java.util.List;

/**
 * Created by Administrator on
 */
public class RemoteDataSource implements DataSource {
    private static RemoteDataSource INSTANCE;

    public static RemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RemoteDataSource();
        }
        return INSTANCE;
    }

    private RemoteDataSource() {

    }

    public void loadDayK(int count, DataCallback loadDataCallback) {

    }

    @Override
    public void getPhoneCode(String json, DataCallback<Integer> dataCallback) {

        HttpRequets.postRequest(UrlFactory.getPhoneCodeURL(), INSTANCE, json, new JsonCallback<BaseEtzBean>() {
            @Override
            public void onSuccess(Response<BaseEtzBean> response) {
                MyLog.d("code=" + response.body().code + "msg=" + response.body().msg);
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().code);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });

    }

    @Override
    public void getEmailCode(String json, DataCallback<Integer> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getEmailCodeURL(), INSTANCE, json, new JsonCallback<BaseEtzBean>() {
            @Override
            public void onSuccess(Response<BaseEtzBean> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().code);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void phoneRegister(String json, DataCallback<UserBean> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getPhoneRegisterURL(), INSTANCE, json, new JsonCallback<BaseEtzBean<UserBean>>() {
            @Override
            public void onSuccess(Response<BaseEtzBean<UserBean>> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void EmailRegister(String json, DataCallback<UserBean> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getEmailRegisterURL(), INSTANCE, json, new JsonCallback<BaseEtzBean<UserBean>>() {
            @Override
            public void onSuccess(Response<BaseEtzBean<UserBean>> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void phoneLogin(String json, DataCallback<UserBean> dataCallback) {
        HttpRequets.getRequest(UrlFactory.getLoginPhoneURL(), INSTANCE, json, new JsonCallback<BaseEtzBean<UserBean>>() {
            @Override
            public void onSuccess(Response<BaseEtzBean<UserBean>> response) {
                MyLog.i("-------------" + response.body().code);
                MyLog.i("-------------" + response.body().msg);
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void emailLogin(String json, DataCallback<UserBean> dataCallback) {
        HttpRequets.getRequest(UrlFactory.getLoginEmailURL(), INSTANCE, json, new JsonCallback<BaseEtzBean<UserBean>>() {
            @Override
            public void onSuccess(Response<BaseEtzBean<UserBean>> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void verifySMSCode(String json, DataCallback<Integer> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getVerifySMSCodeURL(), INSTANCE, json, new JsonCallback<BaseEtzBean>() {
            @Override
            public void onSuccess(Response<BaseEtzBean> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().code);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void verifyMailCode(String json, DataCallback<Integer> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getVerifyMailCodeURL(), INSTANCE, json, new JsonCallback<BaseEtzBean>() {
            @Override
            public void onSuccess(Response<BaseEtzBean> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().code);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void retsetPWDbyPhone(String json, DataCallback<Integer> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getRetsetPWDbyPhoneURL(), INSTANCE, json, new JsonCallback<BaseEtzBean>() {
            @Override
            public void onSuccess(Response<BaseEtzBean> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().code);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void retsetPWDbyMail(String json, DataCallback<Integer> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getRetsetPWDbyMailURL(), INSTANCE, json, new JsonCallback<BaseEtzBean>() {
            @Override
            public void onSuccess(Response<BaseEtzBean> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().code);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void getUserInfo(String json, DataCallback<UserBean> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getUserInfoURL(), INSTANCE, json, new JsonCallback<BaseEtzBean<UserBean>>() {
            @Override
            public void onSuccess(Response<BaseEtzBean<UserBean>> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void bindWallet(String json, DataCallback<Integer> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getBindWalletURL(), INSTANCE, json, new JsonCallback<BaseEtzBean>() {
            @Override
            public void onSuccess(Response<BaseEtzBean> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().code);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void withdraw(String json, DataCallback<TixanBean> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getWithdrawURL(), INSTANCE, json, new JsonCallback<BaseEtzBean<TixanBean>>() {
            @Override
            public void onSuccess(Response<BaseEtzBean<TixanBean>> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void getMyNodeList(String json, DataCallback<MyNodeDataBean> dataCallback) {
        HttpRequets.getRequest(UrlFactory.getMyNodeListURL(), INSTANCE, json, new JsonCallback<BaseEtzBean<MyNodeDataBean>>() {
            @Override
            public void onSuccess(Response<BaseEtzBean<MyNodeDataBean>> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data);
                }
            }
        });
    }

    @Override
    public void getMyNodeRevenueList(String json, DataCallback<NodeRevenueDataBean> dataCallback) {
        HttpRequets.getRequest(UrlFactory.getNodeRewardListURL(), INSTANCE, json, new JsonCallback<BaseEtzBean<NodeRevenueDataBean>>() {
            @Override
            public void onSuccess(Response<BaseEtzBean<NodeRevenueDataBean>> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data);
                }
            }
        });
    }

    @Override
    public void getNodeList(String json, DataCallback<List<NodeBean>> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getNodeListURL(), INSTANCE, json, new JsonCallback<BaseEtzBean<List<NodeBean>>>() {
            @Override
            public void onSuccess(Response<BaseEtzBean<List<NodeBean>>> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data);
                }
            }
        });

    }

    @Override
    public void buyNode(String json, DataCallback<Integer> dataCallback) {
        HttpRequets.postRequest(UrlFactory.buyNodeURL(), INSTANCE, json, new JsonCallback<BaseEtzBean>() {

            @Override
            public void onSuccess(Response<BaseEtzBean> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().code);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }

            }
        });

    }

    @Override
    public void getTeamNodeList(String json, DataCallback<TeamNodeDataBean> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getTeamNodeListURL(), INSTANCE, json, new JsonCallback<BaseEtzBean<TeamNodeDataBean>>() {

            @Override
            public void onSuccess(Response<BaseEtzBean<TeamNodeDataBean>> response) {
                MyLog.i("==================" + response.body().data.toString());
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }

        });
    }

    @Override
    public void getTeamRewardList(String json, DataCallback<List<NodeRevenueBean>> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getTeamRewardListURL(), INSTANCE, json, new JsonCallback<BaseEtzBean<NodeRevenueDataBean>>() {


            @Override
            public void onSuccess(Response<BaseEtzBean<NodeRevenueDataBean>> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data.getUserNodeList());
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });

    }

    @Override
    public void signin(String json, DataCallback<String> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getSigninURL(), INSTANCE, json, new JsonCallback<BaseEtzBean<String>>() {
            @Override
            public void onSuccess(Response<BaseEtzBean<String>> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });
    }

    @Override
    public void getAliDescribeVerifyToken(String json, DataCallback<AuthenticationBean> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getAliDescribeVerifyTokenURL(), INSTANCE, json, new JsonCallback<BaseEtzBean<AuthenticationBean>>() {
            @Override
            public void onSuccess(Response<BaseEtzBean<AuthenticationBean>> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code,response.body().msg);
                }
            }
        });

    }

    @Override
    public void getAliDescribeVerifyResult(String json, DataCallback<AuthenticationBean> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getAliDescribeVerifyResultURL(), INSTANCE, json, new JsonCallback<BaseEtzBean<AuthenticationBean>>() {
            @Override
            public void onSuccess(Response<BaseEtzBean<AuthenticationBean>> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data);
                } else {
                    dataCallback.onDataNotAvailable(response.body().code,response.body().msg);
                }
            }
        });
    }

    @Override
    public void getDetailedList(String json, DataCallback<List<NodeRevenueBean>> dataCallback) {
        HttpRequets.postRequest(UrlFactory.getDetailedListURL(), INSTANCE, json, new JsonCallback<BaseEtzBean<NodeRevenueDataBean>>() {
            @Override
            public void onSuccess(Response<BaseEtzBean<NodeRevenueDataBean>> response) {
                if (response.body().code == 0) {
                    dataCallback.onDataLoaded(response.body().data.getUserNodeList());
                } else {
                    dataCallback.onDataNotAvailable(response.body().code, response.body().msg);
                }
            }
        });

    }

    @Override
    public void getDepositList(String token, DataCallback dataCallback) {

    }

    @Override
    public void commitSellerApply(String token, String coinId, String json, DataCallback
            dataCallback) {

    }


}
