package com.etzSharding.http;

import android.content.Context;
import android.os.Handler;
import android.os.NetworkOnMainThreadException;
import android.support.annotation.WorkerThread;

import com.etzSharding.app.MyApp;
import com.etzSharding.base.BaseUrl;
import com.etzSharding.bean.CurrencyEntity;
import com.etzSharding.bean.ETZRateBean;
import com.etzSharding.bean.ResponseBean;
import com.etzSharding.blockchain.BtcWalletManager;
import com.etzSharding.blockchain.EthWalletManager;
import com.etzSharding.blockchain.EtzWalletManager;
import com.etzSharding.http.callback.JsonCallback;
import com.etzSharding.modules.tools.threads.ETZExecutor;
import com.etzSharding.sqlite.RatesDataSource;
import com.etzSharding.utils.ActivityUtils;
import com.etzSharding.utils.MyLog;
import com.etzSharding.utils.SharedPrefsUitls;
import com.etzSharding.utils.Util;
import com.lzy.okgo.model.Response;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;


public class TimerHttpApiManage {

    private static TimerHttpApiManage instance;
    private Timer timer;

    private TimerTask timerTask;

    private Handler handler;

    private static String seekusdt = "0";

    private TimerHttpApiManage() {
        handler = new Handler();
    }

    public static TimerHttpApiManage getInstance() {

        if (instance == null) {
            instance = new TimerHttpApiManage();
        }
        return instance;
    }

    /**
     * 法币相对于Btc的汇率
     *
     * @param context
     */
    @WorkerThread
    private void updateRates(Context context) {
        if (ActivityUtils.isMainThread()) {
            throw new NetworkOnMainThreadException();
        }
        HttpRequets.getRequets(BaseUrl.HTTP_UPDATE_RATES, getClass(), new HashMap<String, String>(), new JsonCallback<ResponseBean<List<CurrencyEntity>>>() {

            @Override
            public void onSuccess(com.lzy.okgo.model.Response<ResponseBean<List<CurrencyEntity>>> response) {
                if (response.body() == null) return;
                List<CurrencyEntity> list = response.body().data;
                Set<CurrencyEntity> set = new LinkedHashSet<>();
                for (CurrencyEntity ce : list) {
//                    if (ce.code.equalsIgnoreCase("USD")) {
//                        String code = "BTC";
//                        String name = "UBIChain";
//                        String rate = (1/Float.valueOf(ce.rate))* Float.valueOf(seekusdt) + "";
//                        MyLog.i("updateErc20Rates==" + rate);
//                        MyLog.i("updateErc20Rates==" + seekusdt);
//                        String iso = "ETZ";
//                        CurrencyEntity ubi = new CurrencyEntity(code, name, Float.valueOf(rate), iso);
//                        set.add(ubi);
//                    }
                    set.add(ce);
                }
                if (set.size() > 0)
                    RatesDataSource.getInstance(context).putCurrencies(context, set);
            }
        });


    }


    private void initializeTimerTask(final Context context) {
        timerTask = new TimerTask() {
            public void run() {
                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        ETZExecutor.getInstance().forLightWeightBackgroundTasks().execute(new Runnable() {
                            @Override
                            public void run() {
                                updateData(context);
                            }
                        });
                    }
                });
            }
        };
    }

    @WorkerThread
    private void updateData(final Context context) {

        if (MyApp.isAppInBackground(context)) {
            MyLog.e("doInBackground: Stopping timer, no activity on.");
            stopTimerTask();
            return;
        }
        ETZExecutor.getInstance().forLightWeightBackgroundTasks().execute(new Runnable() {
            @Override
            public void run() {
                updateETZRates(context);
                updateErc20Rates(context);
                updateCurrentBalances();
            }
        });

        ETZExecutor.getInstance().forLightWeightBackgroundTasks().execute(new Runnable() {
            @Override
            public void run() {
                //get each wallet's rates
                updateRates(context);

            }
        });

    }

    /**
     * ETZ相对于Btc汇率
     *
     * @param context
     */
    @WorkerThread
    private synchronized void updateETZRates(Context context) {

        HttpRequets.getRequets(BaseUrl.HTTP_ETZ_RATE, getClass(), new HashMap<String, String>(), new JsonCallback<ETZRateBean>() {

            @Override
            public ETZRateBean convertResponse(okhttp3.Response response) throws Throwable {
                return super.convertResponse(response);
            }

            @Override
            public void onSuccess(Response<ETZRateBean> response) {

                String result=response.toString();
                MyLog.i("============"+result);
                if (response.body() != null) {
                    try {
                        if (Util.isNullOrEmpty(result)) {
                            MyLog.e("updateErc20Rates: Failed to fetch");
                            return;
                        }
                        Set<CurrencyEntity> tmp = new LinkedHashSet<>();
                        JSONObject json = new JSONObject(result);
                        JSONArray json1 = new JSONArray(json.getString("data"));
                        MyLog.i(result);
                        for (int i = 0; i < json1.length(); i++) {
                            JSONObject json2 = new JSONObject(json1.getString(i));
                            String code = "BTC";
                            String name = json2.getString("name");
                            String iso = json2.getString("symbol");
                            JSONObject json3 = new JSONObject(json2.getString("quotes"));
                            JSONObject json4 = new JSONObject(json3.getString("BTC"));
                            String rate = json4.getString("price");

                            CurrencyEntity ent = new CurrencyEntity(code, name, Float.valueOf(rate), iso);
                            tmp.add(ent);
                        }
                        RatesDataSource.getInstance(context).putCurrencies(context, tmp);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onError(Response<ETZRateBean> response) {
            }
        });


    }

    /***
     * 代币包括EASH相对于Btc汇率
     * @param context
     */
    @WorkerThread
    private synchronized void updateErc20Rates(Context context) {

    }

    @WorkerThread
    private synchronized void updateCurrentBalances() {
        //获取当前钱包
        String wid = SharedPrefsUitls.getInstance().getCurrentWallet();
        if (wid.startsWith("ETZ")) {
            EtzWalletManager.getInstance().setTokenList(SharedPrefsUitls.getInstance().getWalletTokenList(wid));
            EtzWalletManager.getInstance().updateBalance(wid);
        } else if (wid.startsWith("BTC")) {
            BtcWalletManager.getInstance().updateBalance(wid);
        } else if (wid.startsWith("ETH")) {
            EthWalletManager.getInstance().setTokenList(SharedPrefsUitls.getInstance().getWalletTokenList(wid));
            EthWalletManager.getInstance().updateBalance(wid);
        }

    }

    public void startTimer(Context context) {
        //set a new Timer
        if (timer != null) return;
        timer = new Timer();
        MyLog.e("startTimer: started...");
        //initialize the TimerTask's job
        initializeTimerTask(context);

        timer.schedule(timerTask, 1000, 15000);
    }

    public void stopTimerTask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


}
