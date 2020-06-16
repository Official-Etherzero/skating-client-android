package com.etzSharding.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.etzSharding.R;
import com.etzSharding.bean.NodeBean;
import com.etzSharding.utils.DateUtil;
import com.etzSharding.utils.Md5Utils;
import com.etzSharding.utils.ToastUtils;
import com.etzSharding.utils.Util;


public class NodeBuyDialog extends Dialog implements View.OnClickListener {

    private TextView create_time;
    private TextView period;
    private TextView close_time;
    private TextView day_output;
    private TextView robot_buy_class;
    private EditText buy_pwd;
    protected OnConfirmClickListener mListener;
    private NodeBean bean;

    public NodeBuyDialog(@NonNull Context context, NodeBean bean) {
        super(context, R.style.MyDialog);
        this.bean = bean;
    }

    public NodeBuyDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.robot_buy_layout);
        setCanceledOnTouchOutside(false);
        create_time = findViewById(R.id.robot_create_time);
        period = findViewById(R.id.robot_period);
        close_time = findViewById(R.id.robot_close_time);
        day_output = findViewById(R.id.robot_day_output);
        buy_pwd = findViewById(R.id.robot_buy_pwd);
        robot_buy_class = findViewById(R.id.robot_buy_class);
        Long current = System.currentTimeMillis();
        create_time.setText(DateUtil.getDateAll(current));
        close_time.setText(DateUtil.getDateDue(bean.getPeriod() == null ? 0 : Integer.valueOf(bean.getPeriod())));
        period.setText(String.format(getContext().getResources().getString(R.string.day), bean.getPeriod()));
        day_output.setText( bean.getEarnings()+"%");
        int miniId = Integer.valueOf(bean.getMType());
        switch (miniId) {
            case 1:
                robot_buy_class.setText(R.string.tiyan_node);
                break;
            case 2:
                robot_buy_class.setText(R.string.primary_node);
                break;
            case 3:
                robot_buy_class.setText(R.string.intermediate_node);
                break;
            case 4:
                robot_buy_class.setText(R.string.advanced_node);
                break;
        }
//        初始化界面控件的事件
        initEvent();
    }

    private void initEvent() {
        findViewById(R.id.robot_buy_cancel).setOnClickListener(this);
        findViewById(R.id.robot_buy_confirm).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.robot_buy_confirm:// 确定
                String pwd = buy_pwd.getText().toString().trim();
                if (Util.isNullOrEmpty(pwd)) {
                    ToastUtils.showLongToast(getContext(), R.string.robot_buy_pwd);
                } else if (mListener != null) {
                    mListener.setConfirmClick(pwd, bean.getMiniID());
                }
                break;
            case R.id.robot_buy_cancel:
                dismiss();
                break;
        }
    }

    /**
     * 定义一个接口，公布出去 在Activity中操作按钮的单击事件
     */
    public interface OnConfirmClickListener {
        void setConfirmClick(String pwd, String miniId);
    }

    public void setOnConfirmClickListener(NodeBuyDialog.OnConfirmClickListener listener) {
        this.mListener = listener;
    }


}
