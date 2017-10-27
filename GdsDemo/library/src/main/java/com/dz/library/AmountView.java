package com.dz.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 数字加减按钮布局（纯数字加减）
 * Created by zxl on 2017/4/11.
 */

public class AmountView extends LinearLayout implements View.OnClickListener, TextWatcher {

    private static final String TAG = "AmountView";
    private int amount = 1; //数量
    private int maxAmount = 1;//最大值
    private int minAmount = 0;//最小值

    private OnAmountChangeListener mListener;

    private TextView etAmount;
    private Button btnDecrease;
    private Button btnIncrease;

    public AmountView(Context context) {
        this(context, null);
    }

    public AmountView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_amount, this);
        etAmount = (TextView) findViewById(R.id.etAmount);
        btnDecrease = (Button) findViewById(R.id.btnDecrease);
        btnIncrease = (Button) findViewById(R.id.btnIncrease);
        btnDecrease.setOnClickListener(this);
        btnIncrease.setOnClickListener(this);
//        etAmount.addTextChangedListener(this);

        TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attrs, R.styleable.AmountView);
        int btnWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnWidth, 40);
        int btnHeight = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnHeight, 40);
        int tvWidth = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_tvWidth, 80);
        int tvTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_tvTextSize, 0);
        int btnTextSize = obtainStyledAttributes.getDimensionPixelSize(R.styleable.AmountView_btnTextSize, 0);
        obtainStyledAttributes.recycle();

        LayoutParams btnParams = new LayoutParams(btnWidth, btnHeight);
        btnDecrease.setLayoutParams(btnParams);
        btnIncrease.setLayoutParams(btnParams);
        if (btnTextSize != 0) {
            btnDecrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
            btnIncrease.setTextSize(TypedValue.COMPLEX_UNIT_PX, btnTextSize);
        }

        LayoutParams textParams = new LayoutParams(tvWidth, LayoutParams.MATCH_PARENT);
        etAmount.setLayoutParams(textParams);
        if (tvTextSize != 0) {
            etAmount.setTextSize(tvTextSize);
        }
    }

    public void setOnAmountChangeListener(OnAmountChangeListener onAmountChangeListener) {
        this.mListener = onAmountChangeListener;
    }

    /**
     * 设置初始值
     *
     * @param maxAmount 最大值
     * @param maxAmount 默认值
     */
    public void setAmount(int defaultAmount, int maxAmount) {
        this.amount = defaultAmount;
        this.maxAmount = maxAmount;
        etAmount.setText(amount + "");

    }

    /**
     * 设置初始值
     *
     * @param defaultAmount 默认值
     * @param minAmount 最小值
     * @param maxAmount 最大值
     */
    public void setAmount(int defaultAmount, int minAmount, int maxAmount) {
        this.amount = defaultAmount;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
        etAmount.setText(amount + "");

    }

    public int getAmount() {
        return amount;
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btnDecrease) {
            if (amount > minAmount) {
                amount--;
                etAmount.setText(amount + "");
            }
        } else if (i == R.id.btnIncrease) {
            if (amount < maxAmount) {
                amount++;
                etAmount.setText(amount + "");
            }
        }

        etAmount.clearFocus();

        if (mListener != null) {
            mListener.onAmountChange(this, amount);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if (s.toString().isEmpty())
            return;
        amount = Integer.valueOf(s.toString());
        if (amount > maxAmount) {
            etAmount.setText(maxAmount + "");
            return;
        }

        if (mListener != null) {
            mListener.onAmountChange(this, amount);
        }
    }


    public interface OnAmountChangeListener {
        void onAmountChange(View view, int amount);
    }

}
