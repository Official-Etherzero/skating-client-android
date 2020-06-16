package com.etzSharding.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.etzSharding.R;
import com.etzSharding.utils.Util;

public class VerificationCodePopupWindow extends PopupWindow {

    private TextView popPhone;
    private EditText popMobileCode;
    private TextView popSend;
    private TextView popBtnAffirm;
    private EditText popEmailCode;
    private TextView popEmailAcc;
    private TextView popEmailSend;
    private ConstraintLayout popMobile;
    private ConstraintLayout popEmail;
    private onPopupWindowListener listener;
    public TimeCount etime, ptime;
    private boolean isEmail;


    Context context;
    private View mPopView;
    private View.OnClickListener clickListener;

    public VerificationCodePopupWindow(Activity context, View.OnClickListener clickListener) {
        super(context);
        this.context = context;
        this.clickListener = clickListener;
        init(context);
        setPopupWindow();
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(final Context context) {
        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(context);
        //绑定布局
        mPopView = inflater.inflate(R.layout.pop_two_verification_layout, null);
        popPhone = mPopView.findViewById(R.id.pop_phone);
        popMobileCode = mPopView.findViewById(R.id.pop_mobile_code);
        popSend = mPopView.findViewById(R.id.pop_send);
        popBtnAffirm = mPopView.findViewById(R.id.pop_btn_affirm);
        popMobile = mPopView.findViewById(R.id.pop_mobile);
        popEmailAcc = mPopView.findViewById(R.id.pop_email_acc);
        popEmailCode = mPopView.findViewById(R.id.pop_mail_code);
        popEmailSend = mPopView.findViewById(R.id.pop_eamail_send);
        popEmail = mPopView.findViewById(R.id.pop_email);

        popMobileCode.addTextChangedListener(addET);
        popEmailCode.addTextChangedListener(addET);
        mPopView.findViewById(R.id.pop_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mPopView.findViewById(R.id.pop_btn_affirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pCode = popMobileCode.getText().toString().trim();
                String eCode = popEmailCode.getText().toString().trim();
                if (listener != null) {
                    listener.setAuthCode(pCode, eCode);
                }
            }
        });
        mPopView.findViewById(R.id.pop_eamail_send).setOnClickListener(clickListener);
        mPopView.findViewById(R.id.pop_send).setOnClickListener(clickListener);


    }

    /**
     * 设置窗口的相关属性
     */
    @SuppressLint("InlinedApi")
    private void setPopupWindow() {
        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
        this.setBackgroundDrawable(new ColorDrawable(0x30000000));// 设置背景透明
        this.setBackgroundDrawable(new BitmapDrawable());//注意这里如果不设置，下面的setOutsideTouchable(true);允许点击外部消失会失效
        this.setOutsideTouchable(true);   //设置外部点击关闭ppw窗口
        this.setFocusable(true);
    }

    public void isShowVerification(boolean isEmail, String acc) {
        this.isEmail = isEmail;
        if (isEmail) {
            popEmail.setVisibility(View.VISIBLE);
            popEmailAcc.setText(acc);
            etime = new TimeCount(60000, 1000, false);
            etime.start();
        } else {
            popMobile.setVisibility(View.VISIBLE);
            popPhone.setText(acc);
            ptime = new TimeCount(60000, 1000, true);
            ptime.start();
        }

    }

    private TextWatcher addET = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void afterTextChanged(Editable edt) {
            String pCode = popMobileCode.getText().toString().trim();
            String eCode = popEmailCode.getText().toString().trim();
            if (!Util.isNullOrEmpty(pCode) || !Util.isNullOrEmpty(eCode)) {
                popBtnAffirm.setEnabled(true);
            } else {
                popBtnAffirm.setEnabled(false);
            }

        }
    };

    public void setOnPopupListener(onPopupWindowListener listener) {
        this.listener = listener;
    }

    public interface onPopupWindowListener {
        void setAuthCode(String pCode, String eCode);
    }
    public void startTime(boolean isPhone) {
        ptime = new TimeCount(60000, 1000, isPhone);
        ptime.start();
    }
    class TimeCount extends CountDownTimer {
        private boolean isp;

        public TimeCount(long millisInFuture, long countDownInterval, boolean isPhone) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
            isp = isPhone;
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            if (isp) {
                popSend.setTextColor(context.getResources().getColor(
                        R.color.zt_main));
                popSend.setText(R.string.send);
                popSend.setClickable(true);
            } else {
                popEmailSend.setTextColor(context.getResources().getColor(
                        R.color.zt_main));
                popEmailSend.setText(R.string.send);
                popEmailSend.setClickable(true);
            }

        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            if (isp) {
                popSend.setClickable(false);
                popSend.setTextColor(context.getResources().getColor(
                        R.color.zt_black_30));
                popSend.setText(millisUntilFinished / 1000 + "s");
            } else {
                popEmailSend.setClickable(false);
                popEmailSend.setTextColor(context.getResources().getColor(
                        R.color.zt_black_30));
                popEmailSend.setText(millisUntilFinished / 1000 + "s");
            }

        }
    }

    @Override
    public void dismiss() {
        if (etime != null) etime.cancel();
        if (ptime != null) ptime.cancel();
        super.dismiss();
    }
}
