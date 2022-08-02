package com.skyqi.module_base.view;///

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.skyqi.module_base.R;
import com.skyqi.module_base.utils.UnitConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/// * @ProjectName: paperprint
/// * @Author: qifanxin
/// * @CreateDate: 2022/3/9 6:28 下午
/// * @Description: 文件说明
///
public class VerifyEditText extends LinearLayout {

    //默认 item 个数为 4 个
    private final static int DEFAULT_ITEM_COUNT = 4;
    //默认每个 item 的宽高为 100
    private final static int DEFAULT_ITEM_SIZE = 100;
    //默认每个 item 的间距为 50
    private final static int DEFAULT_ITEM_MARGIN = 50;
    //默认每个 item 的字体大小为 20
    private final static int DEFAULT_ITEM_TEXT_SIZE = 20;

    private final List<TextView> mTextViewList = new ArrayList<>();

    private EditText mEditText;

    private InputListener mInputListener;

    public VerifyEditText(Context context) {
        this(context, null);
    }

    public VerifyEditText(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerifyEditText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, @Nullable AttributeSet attrs) {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER);
        TypedArray obtainArray = getContext().obtainStyledAttributes(attrs, R.styleable.VerifyEditText);
        int textViewSize = obtainArray.getInt(R.styleable.VerifyEditText_size, DEFAULT_ITEM_SIZE);
        int count = obtainArray.getInt(R.styleable.VerifyEditText_input_number, DEFAULT_ITEM_COUNT);
        int itemMargin = obtainArray.getInt(R.styleable.VerifyEditText_item_margin, DEFAULT_ITEM_MARGIN);
        int fontSize = obtainArray.getInt(R.styleable.VerifyEditText_item_text_size, DEFAULT_ITEM_TEXT_SIZE);
        Drawable background = obtainArray.getDrawable(R.styleable.VerifyEditText_item_background);
        obtainArray.recycle();
        if (count < 2) {
            count = 2;
        } else if (count > 6) {
            count = 6;
        }
        mEditText = new EditText(context);
        mEditText.setInputType(0x00000003);
        mEditText.setLayoutParams(new LinearLayout.LayoutParams(1, 1));
        mEditText.setCursorVisible(false);
        mEditText.setBackground(null);
        mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(count)});
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                TextView textView = mTextViewList.get(start);//获取对应的 textview
                if (before == 0) {//输入
                    CharSequence input = s.subSequence(start, s.length());//获取新输入的字
                    textView.setText(input);
                } else {//删除
                    textView.setText("");
                }
                if (mInputListener != null && s.length() == mTextViewList.size())
                    mInputListener.complete(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        addView(mEditText);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditText.requestFocus();
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(mEditText, InputMethodManager.SHOW_FORCED);
            }
        });
        for (int i = 0; i < count; i++) {
            TextView textView = new TextView(context);
            textView.setTextSize(fontSize);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.BLACK);
            LayoutParams layoutParams = new LayoutParams(UnitConverter.dp2px(context, textViewSize), UnitConverter.dp2px(context, textViewSize));
            if (i == 0)
                layoutParams.leftMargin = -1;
            else
                layoutParams.leftMargin = itemMargin;
            textView.setLayoutParams(layoutParams);
            if (background != null)
                textView.setBackground(background);
            addView(textView);
            mTextViewList.add(textView);
        }

    }

    ///清除显示数据
    public void clear() {
        mEditText.setText("");
        mTextViewList.forEach(item -> {
            item.setText("");
        });
    }

    public void setInputListener(InputListener inputListener) {
        mInputListener = inputListener;
        Editable content = mEditText.getText();
        if (!TextUtils.isEmpty(content) && content.toString().length() == mTextViewList.size()) {
            mInputListener.complete(content.toString());
        }
    }

    public interface InputListener {
        void complete(String content);
    }
}
