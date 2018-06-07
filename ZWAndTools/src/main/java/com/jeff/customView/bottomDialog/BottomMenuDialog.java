package com.jeff.customView.bottomDialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jeff.andUtils.UIUtils;
import com.jeff.customView.bannercircleview.R;

import butterknife.BindView;


public class BottomMenuDialog extends Dialog {


    private final Context mContext;

    private TextView titleText;
    private LinearLayout buttonGroup;
    private Button cancle;

    /**
     * 构造方法
     *
     * @param context 上下文
     */
    public BottomMenuDialog(Context context) {
        super(context);
        this.mContext = context;
        init();
    }

    private void init() {
        Window window = this.getWindow();
        window.setContentView(R.layout.bottom_dialog);
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT); //设置dialog的大小
        window.setGravity(Gravity.BOTTOM);
        window.setBackgroundDrawable(new ColorDrawable(0));
        window.setWindowAnimations(R.style.bottom_view_anim);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN |
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setCanceledOnTouchOutside(true);
        setCancelable(true);

        titleText = (TextView) findViewById(R.id.title_text);
        buttonGroup = (LinearLayout) findViewById(R.id.button_group);
        cancle = (Button) findViewById(R.id.cancel_btn);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        titleText.setVisibility(View.GONE);
    }




    /**
     * 设置dialog标题文字
     *
     * @param title
     * @return
     */
    public BottomMenuDialog setDialogTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            titleText.setVisibility(View.VISIBLE);
            titleText.setText(title);
        }
        return this;
    }

    /**
     * 增加dialog内按钮
     *
     * @param btnText
     * @param lis
     * @return
     */
    public BottomMenuDialog addButton(String btnText, final View.OnClickListener lis) {
        if (!TextUtils.isEmpty(btnText) && lis != null) {
            Button btn = new Button(mContext);
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                            UIUtils.dip2px(52));
            params.setMargins(0, UIUtils.dip2px(1), 0, 0);
            btn.setLayoutParams(params);
            btn.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.white_btn_bg));
            btn.setGravity(Gravity.CENTER);
            btn.setTextColor(mContext.getResources().getColor(R.color.colorPrimary));
            btn.setTextSize(16);
            btn.setText(btnText);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lis.onClick(v);
                    dismiss();
                }
            });
            buttonGroup.addView(btn);
        }
        return this;
    }

    public BottomMenuDialog setOnCancleClickListener(final View.OnClickListener lis) {
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                lis.onClick(v);
            }
        });
        return this;
    }


}
