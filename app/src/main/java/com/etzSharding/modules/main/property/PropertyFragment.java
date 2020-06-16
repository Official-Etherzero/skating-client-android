package com.etzSharding.modules.main.property;

import android.annotation.SuppressLint;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.etzSharding.R;
import com.etzSharding.app.MyApp;
import com.etzSharding.base.BaseFragment;
import com.etzSharding.base.Constants;
import com.etzSharding.bean.MyNodeDataBean;
import com.etzSharding.bean.TeamNodeDataBean;
import com.etzSharding.bean.TixanBean;
import com.etzSharding.bean.Token;
import com.etzSharding.bean.TokenInfo;
import com.etzSharding.bean.UserBean;
import com.etzSharding.bean.WalletBean;
import com.etzSharding.blockchain.BaseWalletManager;
import com.etzSharding.blockchain.BtcWalletManager;
import com.etzSharding.modules.dot.MyNode.MyNodeActivity;
import com.etzSharding.modules.main.MainActivity;
import com.etzSharding.modules.tools.threads.ETZExecutor;
import com.etzSharding.modules.user.Login.LoginActivity;
import com.etzSharding.modules.walletmanage.WalletsMaster;
import com.etzSharding.modules.walletmanage.createwallet.CreateWallet;
import com.etzSharding.modules.walletmanage.importwallet.ImportWallet;
import com.etzSharding.modules.walletoperation.receive.ReceiveQrCodeActivity;
import com.etzSharding.modules.walletoperation.send.SendActivity;
import com.etzSharding.modules.walletoperation.wallet.WalletActivity;
import com.etzSharding.modules.walletsetting.WalletSetting;
import com.etzSharding.utils.ClipboardManager;
import com.etzSharding.utils.CurrencyUtils;
import com.etzSharding.utils.ETZAnimator;
import com.etzSharding.utils.MyLog;
import com.etzSharding.utils.SharedPrefsUitls;
import com.etzSharding.utils.ToastUtils;
import com.etzSharding.utils.Util;
import com.etzSharding.utils.sysinfo.QMUIStatusBarHelper;
import com.gyf.barlibrary.ImmersionBar;

import org.bitcoinj.core.Coin;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class PropertyFragment extends BaseFragment<PropertyView, PropertyPresenter> implements PropertyView, View.OnClickListener {
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    Unbinder unbinder;
    @BindView(R.id.tv_total_value)
    TextView tvTotalValue;
    @BindView(R.id.property_wallet_address)
    TextView tvWalletAddress;
    @BindView(R.id.property_ll_title)
    LinearLayout ll;
    @BindView(R.id.wallet_balance_currency)
    TextView walletBalanceCurrency;
    @BindView(R.id.wallet_balance_fiat)
    TextView walletBalanceFiat;
    @BindView(R.id.no_wallet)
    TextView noWallet;
    @BindView(R.id.dot_balance_currency)
    TextView dotBalanceCurrency;
    @BindView(R.id.dot_balance_fiat)
    TextView dotBalanceFiat;
    @BindView(R.id.total_usdt)
    TextView totalUsdt;
    @BindView(R.id.add_wallet)
    ImageView addWallet;

    private WalletBean wallet;
    private Timer timer;

    private TimerTask timerTask;
    private Handler handler;
    private TokenInfo tokenInfo;
    String userToken;
    UserBean userBean;
    @Override
    public PropertyPresenter initPresenter() {
        return new PropertyPresenter();
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        wallet = presenter.getCurrentWallet();
        if (wallet != null)
            tokenInfo = new TokenInfo(wallet.getAddress(), "EtherZero", "ETZ", "", wallet.getStartColor(), wallet.getEndColor(), wallet.getDecimals());
        handler = new Handler();
        BtcWalletManager.getInstance().wbld.observe(this, new Observer<Coin>() {
            @Override
            public void onChanged(@Nullable Coin coin) {
                MyLog.i("coin1-------------" + coin.getValue());
            }
        });
//        String userToken = SharedPrefsUitls.getInstance().getUserToken();
//        presenter.bindWallet(userToken, wallet.getAddress());
    }

    @Override
    protected void initData() {

        if (getActivity() == null) return;
        getActivity().runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                if (wallet != null) {
                    tvWalletAddress.setText(wallet.getAddress());
                    String iso = "ETZ";
                    String balance = presenter.getWalletBalance(wallet);
                    String fiatBalance = CurrencyUtils.getFormattedAmount(getActivity(), SharedPrefsUitls.getInstance().getPreferredFiatIso(),
                            WalletsMaster.getInstance().getWalletByIso(MyApp.getBreadContext(), iso).getFiatBalance(iso, balance));
                    String cryptoBalance = CurrencyUtils.getFormattedAmount(getActivity(), iso, new BigDecimal(Util.isNullOrEmpty(balance) ? "0" : balance));
//                    String totalBalance = CurrencyUtils.getFormattedAmountnotlabe(getActivity(), iso, new BigDecimal(Util.isNullOrEmpty(balance) ? "0" : balance), 5);
                    walletBalanceCurrency.setText(cryptoBalance);
                    walletBalanceFiat.setText(String.format(getString(R.string.USDT), "0"));
//                    tvTotalValue.setText(totalBalance);
//                    totalUsdt.setText(String.format(getString(R.string.USDT), "0"));
                    initTotal();
                    walletBalanceCurrency.setVisibility(View.VISIBLE);
                    walletBalanceFiat.setVisibility(View.VISIBLE);
                    noWallet.setVisibility(View.GONE);
                    addWallet.setVisibility(View.GONE);
                    MyLog.i("-----------" + userToken);
                } else {
                    walletBalanceCurrency.setVisibility(View.GONE);
                    walletBalanceFiat.setVisibility(View.GONE);
                    noWallet.setVisibility(View.VISIBLE);
                    addWallet.setVisibility(View.VISIBLE);

                }
            }
        });

    }


    @Override
    public void initEvent() {
        drawer.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                QMUIStatusBarHelper.setStatusBarLightMode(getActivity());
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                QMUIStatusBarHelper.setStatusBarDarkMode(getActivity());
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_property01;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    // 打开关闭DrawerLayout
//    private void openOrCloseDrawerLayout() {
//        boolean drawerOpen = drawer.isDrawerOpen(Gravity.START);
//        if (drawerOpen) {
//            drawer.closeDrawer(Gravity.START);
//
//        } else {
//            drawer.openDrawer(Gravity.START);
//
//        }
//    }


    @OnClick({R.id.property_wallet_address, R.id.btn_scan, R.id.btn_send, R.id.btn_receive, R.id.home_set, R.id.add_wallet, R.id.home_wallet_rl, R.id.home_dot_rl})
    public void onClick(View view) {
        Intent intent = null;

        switch (view.getId()) {
            case R.id.property_wallet_address:
                if (tokenInfo == null) {
                    ToastUtils.showLongToast(getActivity(), R.string.add_wallet_hint);
                    return;
                }
                ClipboardManager.putClipboard(MainActivity.getApp(), wallet.address);
                break;
            case R.id.btn_scan:
                if (tokenInfo == null) {
                    ToastUtils.showLongToast(getActivity(), R.string.add_wallet_hint);
                    return;
                }
                ETZAnimator.openScanner(getActivity(), Constants.SCANNER_REQUEST);
                break;
            case R.id.home_set:
                if (tokenInfo == null) {
                    ToastUtils.showLongToast(getActivity(), R.string.add_wallet_hint);
                    return;
                }
                intent = new Intent(getActivity(), WalletSetting.class);
                intent.putExtra("wallet", presenter.getCurrentWallet());
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                break;
            case R.id.btn_send:
                if (tokenInfo == null) {
                    ToastUtils.showLongToast(getActivity(), R.string.add_wallet_hint);
                    return;
                }
                intent = new Intent(getActivity(), SendActivity.class);
                intent.putExtra("iso", Objects.requireNonNull(tokenInfo.symbol));
                startActivity(intent);
                break;
            case R.id.btn_receive:
                if (tokenInfo == null) {
                    ToastUtils.showLongToast(getActivity(), R.string.add_wallet_hint);
                    return;
                }
                intent = new Intent(getActivity(), ReceiveQrCodeActivity.class);
                intent.putExtra("tokenInfo", Objects.requireNonNull(tokenInfo));
                startActivity(intent);
                break;
            case R.id.add_wallet:
                if (userBean != null) {
                    if (Util.isNullOrEmpty(userBean.getWalletAddr())) {
                        intent = new Intent(getActivity(), CreateWallet.class);
                        startActivity(intent);
                    } else {
                        intent = new Intent(getActivity(), ImportWallet.class);
                        intent.putExtra(Constants.WALLET_TYPE, "ETZ");
                        startActivity(intent);
                    }
                }

                break;
            case R.id.home_wallet_rl:
                if (tokenInfo == null) {
                    ToastUtils.showLongToast(getActivity(), R.string.add_wallet_hint);
                    return;
                }
                intent = new Intent(getActivity(), WalletActivity.class);
                intent.putExtra("item", new Token(tokenInfo, presenter.getWalletBalance(wallet)));
                startActivity(intent);
                break;
            case R.id.home_dot_rl:
                intent = new Intent(getActivity(), MyNodeActivity.class);
                startActivity(intent);
                break;

        }
    }

    @Override
    public void onDestroyView() {
        MyLog.i("init1---------------onDestroyView");
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onStop() {
        MyLog.i("init1---------------onStop");
        super.onStop();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onPause() {
        MyLog.i("init1---------------onPause");
        super.onPause();
        stopTimerTask();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ImmersionBar.with(this).destroy();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        if (isVisibleToUser) {
//            wallet = presenter.getCurrentWallet();
//            if (wallet != null)
//                tokenInfo = new TokenInfo(wallet.getAddress(), "EtherZero", "ETZ", "", wallet.getStartColor(), wallet.getEndColor(), wallet.getDecimals());
            startTimer(getActivity());
            userToken = SharedPrefsUitls.getInstance().getUserToken();
            if (!Util.isNullOrEmpty(userToken) && presenter != null)
                presenter.getUserInfo(userToken);
        } else {
            if (drawer != null) {
                boolean drawerOpen = drawer.isDrawerOpen(Gravity.START);
                if (drawerOpen) {
                    drawer.closeDrawer(Gravity.START);
                }
            }
            stopTimerTask();
//        super.setUserVisibleHint(isVisibleToUser);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        userToken = SharedPrefsUitls.getInstance().getUserToken();
        if (!Util.isNullOrEmpty(userToken))
            presenter.getUserInfo(userToken);

        wallet = presenter.getCurrentWallet();
        if (wallet != null)
            tokenInfo = new TokenInfo(wallet.getAddress(), "EtherZero", "ETZ", "", wallet.getStartColor(), wallet.getEndColor(), wallet.getDecimals());
        startTimer(getActivity());
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
                                initData();
                            }
                        });
                    }
                });
            }
        };
    }

    public void startTimer(Context context) {
        //set a new Timer
        MyLog.e("init1---------------startTimer: started...");
        if (timer != null) return;
        timer = new Timer();
        MyLog.e("init1---------------startTimer: started...null");
        //initialize the TimerTask's job
        initializeTimerTask(context);

        timer.schedule(timerTask, 1000, 5000);
    }

    public void stopTimerTask() {
        //stop the timer, if it's not already null
        MyLog.e("init1---------------stopTimerTask: started...");
        if (timer != null) {
            MyLog.e("init1---------------stopTimerTask: started...null");
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void requestFail(int code, String msg) {
        ToastUtils.showLongToast(getActivity(), msg);
        if (code == 401) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
    }

    @Override
    public void userInfo(UserBean user) {
        MyLog.i("-------------" + user.toString());
        userBean=user;
        if (Util.isNullOrEmpty(user.getWalletAddr())) {
            noWallet.setText(R.string.assets_new_wallet);
        } else {
            noWallet.setText(R.string.assets_import_wallet);
        }
        dotBalanceCurrency.setText(String.format(getString(R.string.ETZ), user.getETZ()));
        dotBalanceFiat.setText(String.format(getString(R.string.USDT), "0"));
        initTotal();

    }

    @Override
    public void withdrawSuccess(TixanBean bean) {

    }

    @Override
    public void signinFail(int code, String msg) {

    }

    @Override
    public void signinSuccess(String etz) {

    }

    @Override
    public void MyNodeData(MyNodeDataBean data) {

    }

    @Override
    public void setTeamNodeData(TeamNodeDataBean data) {

    }

    private void initTotal() {
        if (wallet == null) return;
        String nodeAmount = userBean == null ? "0" : userBean.getETZ();
        String iso = "ETZ";
        String balance;
        if (wallet == null) {
            balance ="0";
        } else {
            balance = presenter.getWalletBalance(wallet);
        }
        BaseWalletManager wallet = WalletsMaster.getInstance().getWalletByIso(getActivity(), iso);
        String total = wallet.getCryptoForSmallestCrypto(getActivity(),new BigDecimal(balance)).add(new BigDecimal(nodeAmount)).toPlainString();
        tvTotalValue.setText(total);
        totalUsdt.setText(String.format(getString(R.string.USDT), "0"));
    }

}
